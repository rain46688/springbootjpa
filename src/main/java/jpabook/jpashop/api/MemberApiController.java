package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

// RestController를 적어 놓으면 자동으로 json 형태로 객체 데이터를 반환해줌
// @Controller에 @ReponseBody를 붙인 효과랑 같음
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    // 회원 등록 api
    // 엔티티는 이렇게 파라미터로 받는거나 리턴하는것은 안하는게 좋음
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    // 회원 등록 api 개선 버전
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    // 회원 수정 api
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id, @RequestBody @Valid UpdateMemberRequest request){
        // update안에서 멤버를 반환해서 가져와도 되지만
        // 그렇게되면 업데이트하면서 멤버를 조회하는거라 둘을 분리하는게 좋음
        memberService.update(id, request.getName());
        // 따로 밑에서 데이터를 조회해와서 반환해준다.
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    // 회원 조회 api
    @GetMapping("/api/v1/members")
    public List<Member> membersV1(){
        return memberService.findMembers();
    }

    // 회원 조회 api 개선 버전
    @GetMapping("/api/v2/members")
    public Result membersV2(){
        List<Member> findMembers = memberService.findMembers();
        // 엔티티 DTO 변환
        // 이렇게 진행해야 엔티티가 변경이 되어도 api 스팩이 변화되지 않음
        List<MemberDto> collect = findMembers.stream().map(m -> new MemberDto(m.getName())).collect(Collectors.toList());

        return new Result(collect.size(), collect);
    }

    // 등록용 ============================================
    // 따로 api 관련된 엔티티를 만들고 NotEmpty같은 정책을 넣어주는것
    // 직접 엔티티에 넣는것이 아니라
    @Data
    // 위 Data 어노테이션은 @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor를 합쳐놓은 어노테이션이다.
    static class CreateMemberRequest{
        @NotEmpty
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class CreateMemberResponse{
        private Long id;
    }

    // 업데이트용 ============================================
    @Data
    static class UpdateMemberRequest{
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
    }

    // 조회용 ============================================
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
    }


}
