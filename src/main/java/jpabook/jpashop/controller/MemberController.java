package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 멤버 등록 폼 생성
    @GetMapping("/members/new")
    public String createForm(Model model){
        // 실무에서는 바로 Member엔티티를 사용하지 않고
        // MemberForm이라는걸 만들어서 화면과 매핑되게 함
        // 이유는 엔티티에 유효성 검사 같은 어노테이션들이 들어가면
        // 엔티티가 지저분해져서 유지보수에 안좋음
        model.addAttribute("memberForm",new MemberForm());
        return "members/createMemberForm";
    }

    // 멤버 등록
    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult result){
        
        // 오류나면 다시 화면으로 보내버리기 에러나면 화면에 뿌려줘야되니깐
        // 이거를 안하면 바로 에러가남 에러를 다시 화면에 보내주는것
        // 이 안에 MemberForm에서 설정한 유효성 검사 멘트같은거 있음
        if(result.hasErrors()){
            return "members/createMemberForm";
        }

        // 멤버폼에서 데이터를 가져와서 등록에 필요한것만 넣기
        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());

        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);

        memberService.join(member);
        // 뒤로가기 하면 안되니깐 리다이렉트로 / 홈으로 보내버리기
        return "redirect:/";
    }

    // 멤버 조회
    @GetMapping("/members")
    public String list(Model model){
        log.info("members");
        // 실무에서 members를 바로 뿌려주는것보다는 dto에서 뿌려줄 데이터만 정제한 다음에 뿌려주는게 좋음
        // api를 만들때는 절대로 Member 같은 기존 엔티티를 보내주면 안됨! 따로 정제해서 필요한것만 뿌려주도록해야됨!
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }
    
}
