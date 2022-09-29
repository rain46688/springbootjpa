package jpabook.jpashop;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

	@Bean
	Hibernate5Module hibernate5Module(){
		Hibernate5Module hibernate5Module = new Hibernate5Module();
//		hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING,true); // 강제 지연 로딩 설정하는 코드
		return hibernate5Module;
	}

}
