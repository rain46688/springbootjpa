package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {
//    DAO랑 비슷한 기능을 하는것 리포지토리라고함
    
//    스프링 부트에선 이렇게 작성하면 자동으로 엔티티 매니저 관련된 코드들들 만들어줌
//    jpa 그레들에서 추가한것 때문임
    @PersistenceContext
    private EntityManager em;

//    멤버를 저장하는 api를 말하는듯
    public Long save(Member member){
        em.persist(member);
//        lombok 게터세터를 만들어놨으니깐 이렇게만하면됨
        return member.getId();
    }
    
//  멤버 찾는 api 말하는듯
    public Member find(Long id){
        return em.find(Member.class, id);
    }
//    컨트롤 + 시프트 + t 테스트 실행하기 JUNUT4로해서 OK

}
