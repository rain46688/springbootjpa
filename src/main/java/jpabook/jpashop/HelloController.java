package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    // hello라는 url 들어오면 이거 호출됨
    @GetMapping("hello")
    public String hello(Model model){
        // model 이란거에서 데이터를 실어서 뷰에 넘길 수 있음
        // data라는 키 값의 value로 hello!!!를 넘기는것
        model.addAttribute("data","hello!!!");
        // 리턴은 화면 이름 resources/templates 밑에 뷰의 이름 원래 hello.html로 가라는것
        // hello 저기에 컨트롤 + 마우스 좌클릭하면 hello.html로 이동함
        return "hello";
    }
}
