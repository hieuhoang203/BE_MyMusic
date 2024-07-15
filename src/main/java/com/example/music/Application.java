package com.example.music;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Cloudinary getCloudinary(){
		return new Cloudinary(ObjectUtils.asMap(
				"cloud_name", "hieuhv203",
				"api_key", "626331438846633",
				"api_secret", "XDVR-B8ZQiCsIZFFqtvkGnh4G6g",
				"secure", true));
	}

	@Bean
	public JavaMailSender javaMailSender() {
		return new JavaMailSenderImpl();
	}

}
