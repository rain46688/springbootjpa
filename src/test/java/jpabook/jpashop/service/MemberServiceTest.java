package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest // 이거 안넣으면 @Autowired 안됨
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    // 롤백 하지만 insert 문을 보고싶을때 EntityManager 객체를 생성하고
    @Autowired
    EntityManager em;

    @Test
    // @Transactional 어노테이션이 자동으로 Test의 경우 롤백을 해버려서 insert분이 아예 안나감
    // insert문을 보고싶고 디비에 넣고 싶으면 이렇게 @Rollback(value = false) 넣어줘야됨 기본이 @Rollback(value = true) 롤백 true
    @Rollback(value = true)
    public void joinTest() throws Exception {
        //Given
        Member member = new Member();
        member.setName("kim");
        //When
        Long saveId = memberService.join(member);
        //Then
        // 롤백 하지만 insert 문을 보고싶을때 EntityManager 객체를 생성하고 em.flush();
        em.flush(); // 디비에 쿼리를 날리고 테스트가 끝날때 롤백을 진행하는것
        assertEquals(member, memberRepository.findOne(saveId));
    }


    @Test(expected = IllegalStateException.class)
    // @Test
    public void duplicateTest() throws  Exception{
        //Given
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");
        //When
        memberService.join(member1);
        memberService.join(member2);

        // try catch문으로 해도 예외가 처리가 되지만 @Test(expected = IllegalStateException.class) 이렇게 작성하면
        // 추가로 작성 안해도 그냥 알아서 try catch 처리를 해줌
        
//        try{
//            memberService.join(member2);
//        }catch (IllegalStateException e){
//            return;
//        }

        //Then
        fail(" 이 아래까지 코드가 내려오면 안된다 예외가 발생해야 한다.");
    }



}