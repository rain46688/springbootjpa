package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

// 테스트할려면 두개 넣어줘야됨
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testMember() throws Exception{

        Member member = new Member();
        member.setUsername("memberA");

        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
//  h2 콘솔에 보면 MEMBER 테이블이있음
//  하지만 테이블만 있고 데이터는 들어가있지 않음
//  이유는 테스트에서 돌릴때는 트랜잭션이 롤백을 시켜버리기 때문임
        Assertions.assertThat(findMember).isEqualTo(member);
        // 이건 true 값이 나옴
        System.out.println("findMember == member " + (findMember == member));
    }
}