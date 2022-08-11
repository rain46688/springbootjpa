package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    // 다대다 관계
    // 실무에서는 @ManyToMany 를 사용안해야됨, 이건 이렇게도 사용할 수 있다는 예제임
    // 안쓰는 이유는 중간 매핑 테이블에 다른 값을 넣을수가 없음!!
    @ManyToMany
    // 이걸 중간에 일대다 다대일로 매핑 테이블이 존재해야됨 그 매핑 테이블을 설정하는것
    @JoinTable(name = "category_item",
     joinColumns = @JoinColumn(name = "category_id"),
     inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    // 하이라이키 구조로 만들기 위해서 이런식으로 하나의 클래스에서
    // 다대일 일대다로 연결을 시켜놓은것
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    // mappedBy는 보통 @OneToMany 뒤에 넣는다고 이해하면되려나.
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    // 연관 관계 메소드
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }

}
