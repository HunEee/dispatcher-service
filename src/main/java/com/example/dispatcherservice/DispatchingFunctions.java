package com.example.dispatcherservice;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DispatchingFunctions {

	private static final Logger log = LoggerFactory.getLogger(DispatchingFunctions.class);

	@Bean // 주문을 포장하는 비즈니스 로직을 구현하는 함수
	public Function<OrderAcceptedMessage, Long> pack() {
		//OrderAcceptedMessage 객체를 입력으로 받는다. 
		return orderAcceptedMessage -> {
			log.info("The order with id {} is packed.", orderAcceptedMessage.orderId());
			// 주문의 식별자를(Long 타입)를 출력으로 반환
			return orderAcceptedMessage.orderId();
		};
	}


}
