package com.cbo.mongo.to.t24;

import com.cbo.mongo.to.t24.persistence.models.ReportModel;
import com.cbo.mongo.to.t24.persistence.repository.AccountInfoRepository;
import com.cbo.mongo.to.t24.services.AccountInfoService;
import com.cbo.mongo.to.t24.services.BalanceUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class Application implements CommandLineRunner {

	@Autowired
	private BalanceUpdateService balanceUpdateService;

	@Autowired
	private AccountInfoService accountInfoService;

	@Autowired
	private AccountInfoRepository accountInfoRepository;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {

		//update balance
		balanceUpdateService.updateBalance();
		System.out.println("-------------------------------");
		System.out.println("Balance up[dated successfully.");
		System.out.println("-------------------------------");

		System.exit(0);
	}
}
