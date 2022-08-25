package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
//    public List<Order> findAll(OrderSearch orderSearch){
//        return em.createQuery("select o from Order o",Order.class).getResultList();
//    }


}
