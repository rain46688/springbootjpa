package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    // 조인하는 컬럼이름 즉 FK 이름을 정하는것
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice; // 주문 가격

    private int count; // 주문 수량

    // 아이템 재고 생성 메소드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setOrderPrice(orderPrice);

        // 주문을 했으니 해당 아이템의 재고를 카운트만큼 깍아줘야됨
        item.removeStock(count);
        return orderItem;
    }

    // 주문 취소시 아이템 재고 원복
    public void cancel() {
        // 주문 취소하면 아이템의 재고를 (주문한 수량 만큼 더해서)원복하는것
        item.addStock(count);
    }

    // 전체 가격 계산할때 각각 아이템의 갯수 * 가격으로 계산되는 값
    public int getTotalPrice() {
        // return getOrderPrice() * getCount(); // 이런식으로 해도되지만 같은거 안에서 호출할때는 바로 변수에 접근해도됨
        return orderPrice * count;

    }
}
