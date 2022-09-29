package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    // 일대일 관계
    // FK를 Delivery에 두던 Order에 두던 상관이 없지만 자주 사용하는 테이블에 두는게 좋다
    // 보통 Order를 조회해서 Delivery를 찾기 때문에 여기를 짭으로 선언함!
    @OneToOne(mappedBy = "delivery",fetch = FetchType.LAZY)
    @JsonIgnore
    private Order order;

    @Embedded
    private Address address;

    // 이건 스트링 값으로 넣는게 좋음 그래야 디비에 READY, COMP 이렇게 들어가지
    // @Enumerated(EnumType.ORDINAL) 이걸로 넣으면 임의의 0,1,2 이런값으로 들어가는데
    // 중간에 값이 추가가되면 상태가 밀려서 에러난다함!
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // READY, COMP

}
