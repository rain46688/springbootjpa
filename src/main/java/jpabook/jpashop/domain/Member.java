package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// @Entity 어노테이션은 JPA를 사용해 테이블과 매핑할 클래스에 붙여주는 어노테이션이다.
// 이 어노테이션을 붙임으로써 JPA가 해당 클래스를 관리하게 된다.
@Entity
@Getter @Setter
public class Member {

    // @Id는 특정 속성을 기본키로 설정하는 어노테이션이다.
    @Id
    // 속성	기능
    //@GeneratedValue(startegy = GenerationType.IDENTITY)	기본 키 생성을 DB에 위임 (Mysql)
    //@GeneratedValue(startegy = GenerationType.SEQUENCE)	DB시퀀스를 사용해서 기본 키 할당 (ORACLE)
    //@GeneratedValue(startegy = GenerationType.TABLE)	키 생성 테이블 사용 (모든 DB 사용 가능)
    //@GeneratedValue(startegy = GenerationType.AUTO)	선택된 DB에 따라 자동으로 전략 선택 (기본값!)
    @GeneratedValue
    // @Column은 객체 필드를 테이블 컬럼과 매핑한다.
    @Column(name = "member_id")
    private Long id;

    private String name;

    // 내장 타입 Address를 사용했다는 의미임 사용할때 @Embedded를 넣거나 Address에 @Embeddable를
    // 넣거나 둘중에 하나만하면 된다함
    @Embedded
    private Address address;

    // 일대다 관계 (서로 관게를 안적어주면 에러남!!!!)
    // 매우 중요한 부분은 테이블의 외래키는 한개 Order에 있는건데 참조 객체는 Member,Order 둘다있기 때문에
    // 하나를 OWNER로 정해야됨 이건 보통 MANY 쪽이 OWNER가 되는거라 아닌 컬럼에 mappedBy 표시해줘야된다!
    // Order의 private Member member; 에 의해서 참조된다라고 (mappedBy = "member") 추가로 넣어줘야됨!
    // 이리하여서 여기에 값을 넣는다고 Order 쪽 FK 값이 변경되거나 하지는 않는다!
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();


}
