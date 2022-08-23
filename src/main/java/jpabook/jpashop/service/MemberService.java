package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 스프링 부트 사용하니깐 서비스 어노테이션 필수!
@Service
// 서비스에선 이거 넣어줘야됨 어노테이션 쓰기만 하면 아래 메소드들이 전부 적용됨
// 조회할때는 @Transactional(readOnly = true)를 넣는게 성능 향상에 좋음
// 여기 위에 명시하면 전체가 다 먹히는데 이러면 쓰기일때 값이 안들어감 그래서 따로 쓰기에는 @Transactional를 적어줘야됨
// @Transactional 이렇게 적으면 기본적으로 readOnly = false 상태임
@Transactional(readOnly = true)
// 롬복에서 지원해주는 @RequiredArgsConstructor 사용하면 자동으로 final로 되어있는 필드에 생성자를 만들어줌
// 생성자가 하나있을때는 @Autowired를 생략해도 알아서 주입해준다함
@RequiredArgsConstructor
public class MemberService {

    // @Autowired이 스프링 부트가 스프링 빈에 등로되어있는 MemberRepository를 인젝션 해줌
    // 더 좋은 인젝션이 있다고함

    //    @Autowired
    //    private MemberRepository memberRepository;

    // 상단에 @RequiredArgsConstructor를 넣고 final로 필드를 바꾸는게 좋다함.
    private final MemberRepository memberRepository;

    // 회원 가입
    @Transactional
    public Long join(Member member){

        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    // 유효성 검사 로직
    private void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(findMembers.size() > 0){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    // 회원 한명 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

}
