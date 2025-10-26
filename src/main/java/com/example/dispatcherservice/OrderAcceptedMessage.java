package com.example.dispatcherservice;

// 접수된 주문에 대한 이벤트를 나타내는 DTO
public record OrderAcceptedMessage(
		Long orderId
		
){}
