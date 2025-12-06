package com.example.dispatcherservice;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;

//테스트 바인더 임포트
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.support.MessageBuilder;

// 수기 임포트
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class) // 테스트 빌더 설정
class FunctionsStreamIntegrationTests {

	// 입력 바인딩 packlabel-in-0을 나타냄
	@Autowired
	private InputDestination input;

	// 출력 바인딩 packlabel-out-0을 나타냄
	@Autowired
	private OutputDestination output;

	// JSON 메시지 페이로드를 자바 객체로 역직렬화하기 위해 잭슨을 사용
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void whenOrderAcceptedThenDispatched() throws IOException {
		long orderId = 121;
		Message<OrderAcceptedMessage> inputMessage = MessageBuilder
				.withPayload(new OrderAcceptedMessage(orderId)).build();
		Message<OrderDispatchedMessage> expectedOutputMessage = MessageBuilder
				.withPayload(new OrderDispatchedMessage(orderId)).build();
		
		// 입력 채널로 메시지를 보냄
		this.input.send(inputMessage);
		assertThat(objectMapper.readValue(output.receive().getPayload(), OrderDispatchedMessage.class))
			// 출력 채널로부터 메시지를 확인	
			.isEqualTo(expectedOutputMessage.getPayload());
	}
}
