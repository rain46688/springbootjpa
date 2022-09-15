package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    // 주문 등록 폼 생성
    @GetMapping("/order")
    public String createForm(Model model){
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members",members);
        model.addAttribute("items",items);

        return "order/orderForm";

    }

    // 주문 등록
    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count){

        orderService.order(memberId, itemId, count);
        return "redirect:/orders";

    }

    // 주문 목록 조회
    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model){
        // findOrders와 같이 서비스에서도 바로 리포지토리로 위임을 하는 구조
        // 단순 조회의 경우에는 바로 컨트롤러단에서 리포지토리를 호출해서 넣어도 무방하다.
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders",orders);

        // 상단에 @ModelAttribute("orderSearch") OrderSearch orderSearch 넣어놓으면
        // model.addAttribute("OrderSearch",OrderSearch); // 이 코드가 있는거랑 같음
        // 자동으로 html에서 sumit으로 넘어갈때도 url에 파라미터로 보내주는것도 하는듯
        // http://localhost:9090/orders?memberName=%EC%B5%9C&orderStatus=ORDER

        return "order/orderList";
    }

    // 주문 취소
    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }



}
