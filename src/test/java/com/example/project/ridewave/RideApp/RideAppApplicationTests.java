package com.example.project.ridewave.RideApp;

import com.example.project.ridewave.RideApp.services.EmailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RideAppApplicationTests {

	@Autowired
	private EmailSenderService emailSenderService;

	@Test
	void contextLoads() {

		emailSenderService.sendEmail(
				"nilodid495@gufutu.com",
				"This is the Testing mail",
				"This is the text");

	}

	@Test
	void sendEmailMultiple(){
		String emails[] = {
				"nilodid495@gufutu.com",
				"3yz8ijzx10@osxofulk.com",
				"qisuso@citmo.net"
		};

		emailSenderService.sendEmail(
				emails,
				"Hello from Straw Pirates",
				"I am going to become the King of pirates");
	}

}
