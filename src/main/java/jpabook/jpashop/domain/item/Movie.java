package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
// SINGLE_TABLE 정책 일때 구분값을 넣는건데 안넣으면 클래스명으로 들어가지만 따로 정해서 넣을수있음
@DiscriminatorValue("M")
public class Movie extends Item{

    private String director;
    private String actor;
}
