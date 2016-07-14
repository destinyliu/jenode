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

import java.util.Map;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.MQConsumer;

/**
 * The {@link ConsumerFactory} implementation to produce a new {@link MQConsumer} instance
 * for provided {@link Map} {@code configs} and optional {@code keyDeserializer},
 * {@code valueDeserializer} implementations on each {@link #createConsumer()}
 * invocation.
 *
 * @author Gary Russell
 * @author Murali Reddy
 */
public class DefaultRocketMQConsumerFactory implements ConsumerFactory {

	@Override
	public MQConsumer createConsumer() {
		return createRocketMQConsumer();
	}

	protected MQConsumer createRocketMQConsumer() {
		return new DefaultMQPushConsumer();
	}

	@Override
	public boolean isAutoCommit() {
		return false;
	}

}
