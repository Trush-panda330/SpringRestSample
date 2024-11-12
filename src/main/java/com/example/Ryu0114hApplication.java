package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement//サービスクラスの＠Transactional一緒にセットすることで初めて効果をもたらす
public class Ryu0114hApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ryu0114hApplication.class, args);
	}

}
