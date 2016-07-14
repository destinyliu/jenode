/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.destinyshine.rocketmq.core;

import org.springframework.core.NestedRuntimeException;

import com.alibaba.rocketmq.common.message.Message;

/**
 * Exceptions when producing.
 *
 * @author Gary Russell
 *
 */
@SuppressWarnings("serial")
public class MQProducerException extends NestedRuntimeException {

	private final Message entity;

	public MQProducerException(Message entity, String message, Throwable cause) {
		super(message, cause);
		this.entity = entity;
	}

	public Message getEntity() {
		return entity;
	}
}
