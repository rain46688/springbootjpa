package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    // 오더 저장
    public void save(Order order){
        em.persist(order);
    }

    // 오더 하나 조회
    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

    // 오더 전체 조회 (검색 파라미터 넣어야되서 이건 동적 쿼리로 동작함 그래서 나중에 보기)
    public List<Order> findAll(OrderSearch orderSearch){
        // 이건 기본 쿼리
//        return em.createQuery("select o from Order o join o.member m where o.status = :status and m.name like :name",Order.class)
//                .setParameter("status", orderSearch.getOrderStatus())
//                .setParameter("name", orderSearch.getMemberName())
//                .getResultList();

        // 동적 쿼리
        // 이것도 나중에 쿼리 DSL로 변경할꺼라함
        // 이게 표준인데 쓰기 복잡하다고해서 실무에서는 잘 안씀
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Order, Member> m = o.join("member", JoinType.INNER); //회원과 조인
        List<Predicate> criteria = new ArrayList<>();
        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"),
                    orderSearch.getOrderStatus());
            criteria.add(status);
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name =
                    cb.like(m.<String>get("name"), "%" +
                            orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대 1000건
        return query.getResultList();
    }


/*
* V3. 엔티티를 조회해서 DTO로 변환(fetch join 사용O) 추가 코드
* */
    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery(
                        "select o from Order o" +
                                " join fetch o.member m" +
                                " join fetch o.delivery d", Order.class)
                .getResultList();
    }

    /* v3 api 고급 개발 부분 orders 
    * distinct 키워드를 넣으면 우리가 디비에 넣는것처럼 select문 부분에 넣어줌
    * 그래서 객체를 중복으로 받아오지 않고 하나씩만 가져와서 내부 객체를 매칭함
    * 그러나 실 디비에 쿼리가 나갈때 distinct는 소용이 없음 디비에서는 완벽히 같아서 중복 제거가되는데
    * 그게 아니기 때문임 어찌저찌 jpa가 보고 중복을 걸러서 컬렉션에 담아주게됨
    * */
    public List<Order> findAllWithItem() {
        return em.createQuery(
                        "select distinct o from Order o" +
                                " join fetch o.member m" +
                                " join fetch o.delivery d" +
                                " join fetch o.orderItems oi" +
                                " join fetch oi.item i", Order.class)
                .getResultList();
    }


    public List<Order> findAllWithMemberDelivery(int offset, int limit) {
        return em.createQuery(
                        "select o from Order o" +
                                " join fetch o.member m" +
                                " join fetch o.delivery d", Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
