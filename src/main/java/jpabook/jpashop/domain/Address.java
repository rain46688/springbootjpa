package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

// 어딘가에서 내장되서 Address가 사용될수있다는걸 나타내기 위해서 @Embeddable 어노테이션을 붙임
@Embeddable
// 보통 실무에서는 게터는 열어두고 세터는 닫아두는게 좋다고함
// 여기저기에서 수정하면 찾기가 어려우니 그렇다함
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // 다른 생성자를 만들면 기본 생성자를 자동으로 생성 안해줘서
    // 기본 생성자 없으면 에러가나서 만들어둠 대신 protected로 만들어서 아래꺼랑 구분을 해둠 
    // 이걸로 호출하면 안되고 아래껄로 호출하라는 의미도 포함되어있음
    protected Address(){

    }

    // 이걸로 호출해야됨!
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
