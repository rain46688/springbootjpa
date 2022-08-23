package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

// 스프링 부트 사용하니깐 어노테이션 필수!
@Repository
// 서비스 처럼 마찬가지로 @RequiredArgsConstructor 롬복꺼 추가하고 아래에 final 필드로 변경함
@RequiredArgsConstructor
public class MemberRepository {

    // 스프링 부트에서 엔티티 매니저를 만들어서 주입해주는 어노테이션
    // 최신 버전에서는 @PersistenceContext 아니어도 @Autowired로도 가능하다함
    //    @PersistenceContext
    //    private EntityManager em;

    private final EntityManager em;

    // 멤버 저장
    public void save(Member member){
        em.persist(member);
    }
    
    // 멤버 아이디로 한명 찾기
    public Member findOne(Long id){
       return em.find(Member.class, id);
    }

    // 멤버 리스트 찾기
    public List<Member> findAll(){
        // 여기에 select문은 일반 sql이 아닌 jpql이라는것임
        // 테이블을 대상으로 쿼리를하는게 아니라 객체를 대상으로 쿼리를함
        // Member 객체를 m으로 하고 그걸 전부 가져오는것 result set이 리스트로 오니깐
        // Member 객체를 갖는 리스트 형식으로 반환 타입을 만든것
        return em.createQuery("select m from Member as m",Member.class).getResultList();
    }

    // 멤버를 이름으로 조회하기
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name",Member.class)
                .setParameter("name",name)
                .getResultList();
    }

}
