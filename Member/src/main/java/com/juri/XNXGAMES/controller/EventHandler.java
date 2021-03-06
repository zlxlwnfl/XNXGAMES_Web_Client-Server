package com.juri.XNXGAMES.controller;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.juri.XNXGAMES.DTO.BoardToMemberPostMessage;
import com.juri.XNXGAMES.service.MemberPostService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventHandler {

	MemberPostService memberPostService;
	
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "BoardToMemberQueue", durable = "true"),
			exchange = @Exchange(value = "BoardExchange", type = "direct", durable = "true"),
			key = "BoardToMember.Post"
			))
	public void boardToMemberPostHandle(final BoardToMemberPostMessage message) {
		switch(message.getType()) {
		case "create":
			memberPostService.insertMemberPost(message);
			break;
		case "delete":
			memberPostService.deleteMemberPost(message);
			break;
		}
	}
	
}
