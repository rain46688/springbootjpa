package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
// SINGLE_TABLE 전략으로 진행하면 Book,album,movie 이게 테이블 생성시에는 하나의 테이블로 생성됨
// 다른 옵션들도있음 각각 따로 테이블명을 갖게 만든다던지.
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// SINGLE_TABLE로 진행하면 디비가 서로 구분할수있어야되서 구분자를 만드는거라함
@DiscriminatorColumn(name = "dtype")
// 추상 클래스로 만듬
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    // 다대다 짭!
    // 실무에서는 @ManyToMany 를 사용안해야됨, 이건 이렇게도 사용할 수 있다는 예제임
    // 안쓰는 이유는 중간 매핑 테이블에 다른 값을 넣을수가 없음!!
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
