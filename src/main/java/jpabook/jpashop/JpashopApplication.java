package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
		
//		롬복 테스트용으로 적었던것
//		Hello hello = new Hello();
//		hello.setData("hello");
//		String data = hello.getData();
//		System.out.println("data = " + data);

		// h2 디비 무조건 켜야 실행됨 안그러면 서버 안 올라감
		SpringApplication.run(JpashopApplication.class, args);
	}

}
