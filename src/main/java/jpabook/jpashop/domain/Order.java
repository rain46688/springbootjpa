package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
// 테이블 이름을 orders로함 이거 디비에서 order by 때문에 오류 생겨서 보통 s를 붙인다고함
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // 다대일 관계 (서로 관게를 안적어주면 에러남!!!!)
    @ManyToOne(fetch = FetchType.LAZY)
    // 조인하는 컬럼이름 즉 FK 이름을 정하는것
    @JoinColumn(name = "member_id")
    private Member member;

    //cascade 옵션이란 @OneToMany 나 @ManyToOne에 옵션으로 줄 수 있는 값이다.
    //Entity의 상태 변화를 전파시키는 옵션이다.
    //만약 Entity의 상태 변화가 있으면 연관되어 있는(ex. @OneToMany, @ManyToOne) Entity에도 상태 변화를 전이시키는 옵션이다.
    //기본적으로는 아무 것도 전이시키지 않는다.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    // 일대일 관계
    // 여기가 찐임! OWNER
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // OneToOne 이랑 ManyToOne은 무조건 fetch = FetchType.LAZY 옵셥을 붙여줘야됨!
    // 그래야 성능 이슈가 안남 (fetch = FetchType.EAGER) 이렇게 하면 호출할때 연관된 테이블 다 가져와서
    // 성능이 안좋아짐 끝이 Many로 끝나는건 기본 값이 LAZY라 신경안써도됨
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime oderDate; // 주문시간

    // Delivery 설명 참조
    @Enumerated(EnumType.STRING)
    private OderStatus status; // 주문 상태 [ORDER, CANCEL]

    // 연관 관계 메소드
    // 관계가 있는 객체에 값을 넣을때 일일히 다 넣기 귀찮으니 메소드로 만들어놓고 편하게 사용하자는 취지인듯??
    // 좀 헷갈리긴함
    public void setMember(Member member){
        this.member = member;
        // Member 클래스에 private List<Order> orders = new ArrayList<>(); 에도 값을 넣어주는것
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
