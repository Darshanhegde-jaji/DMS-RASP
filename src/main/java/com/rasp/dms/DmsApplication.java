package com.rasp.dms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.rasp.dms.controller", "platform.webservice.map","com.rasp.dms.config","com.rasp.dms.service","com.rasp.dms", "platform.webservice.controller.base","ci",".src/main/java/com/rasp/dms/","platform.defined.account.controller"})
public class DmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DmsApplication.class, args);
	}

}
