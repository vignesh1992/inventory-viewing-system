package com.management.inventory.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.management.inventory.model.Item;

@Component
public class PublishToTopic {

	private static final String TOPIC_INVENTORY = "/topic/inventory";

	@Autowired
	private SimpMessagingTemplate template;

	private static final Logger logger = LoggerFactory.getLogger(PublishToTopic.class);

	public void send(Item item) {

		logger.info("Attempting to publish {} items to the topic", TOPIC_INVENTORY);

		this.template.convertAndSend(TOPIC_INVENTORY, item, new MessagePostProcessor() {

			@Override
			public Message<?> postProcessMessage(Message<?> message) {
				return message;
			}
		});

		logger.info("Items published successfully to the topic {}", TOPIC_INVENTORY);
	}

}
