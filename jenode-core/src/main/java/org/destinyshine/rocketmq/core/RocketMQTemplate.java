/*
 * Copyright 2015-2016 the original author or authors.
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

import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.destinyshine.rocketmq.KafkaException;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.MQProducer;
import com.alibaba.rocketmq.client.producer.SendCallback;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.remoting.exception.RemotingException;


/**
 * A template for executing high-level operations.
 *
 * @param <K> the key type.
 * @param <V> the value type.
 *
 * @author Marius Bogoevici
 * @author Gary Russell
 */
public class RocketMQTemplate<K, V> implements RocketMQOperations<K, V> {

	protected final Log logger = LogFactory.getLog(this.getClass()); //NOSONAR

	private final ProducerFactory<K, V> producerFactory;

	private final boolean autoFlush;
	private volatile MQProducer producer;

	private volatile String defaultTopic;

	private MessageSerializer<V> redisSerializer;


	/**
	 * Create an instance using the supplied producer factory and autoFlush false.
	 * @param producerFactory the producer factory.
	 */
	public RocketMQTemplate(ProducerFactory<K, V> producerFactory) {
		this(producerFactory, false);
	}

	/**
	 * Create an instance using the supplied producer factory and autoFlush setting.
	 * Set autoFlush to true if you wish to synchronously interact with Kafaka, calling
	 * {@link Future#get()} on the result.
	 * @param producerFactory the producer factory.
	 * @param autoFlush true to flush after each send.
	 */
	public RocketMQTemplate(ProducerFactory<K, V> producerFactory, boolean autoFlush) {
		this.producerFactory = producerFactory;
		this.autoFlush = autoFlush;
	}

	/**
	 * The default topic for send methods where a topic is not
	 * providing.
	 * @return the topic.
	 */
	public String getDefaultTopic() {
		return this.defaultTopic;
	}

	/**
	 * Set the default topic for send methods where a topic is not
	 * providing.
	 * @param defaultTopic the topic.
	 */
	public void setDefaultTopic(String defaultTopic) {
		this.defaultTopic = defaultTopic;
	}

	@Override
	public ListenableFuture<SendResult> sendDefault(V data) {
		return send(this.defaultTopic, data);
	}

	@Override
	public ListenableFuture<SendResult> sendDefault(K key, V data) {
		return send(this.defaultTopic, key, data);
	}

	@Override
	public ListenableFuture<SendResult> sendDefault(int partition, K key, V data) {
		return send(this.defaultTopic, partition, key, data);
	}

	@Override
	public ListenableFuture<SendResult> send(String topic, V data) {

		byte[] bytes = redisSerializer.serialize(data);
		MQMessage producerRecord = new MQMessage(topic, bytes);
		return doSend(producerRecord);
	}

	@Override
	public ListenableFuture<SendResult> send(String topic, K key, V data) {
		MQMessage producerRecord = new MQMessage();
		return doSend(producerRecord);
	}

	@Override
	public ListenableFuture<SendResult> send(String topic, int partition, V data) {
		MQMessage producerRecord = new MQMessage();
		return doSend(producerRecord);
	}

	@Override
	public ListenableFuture<SendResult> send(String topic, int partition, K key, V data) {
		MQMessage producerRecord = new MQMessage();
		return doSend(producerRecord);
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public ListenableFuture<SendResult> send(Message<?> message) {
////		ProducerRecord<?, ?> producerRecord = this.messageConverter.fromMessage(message, this.defaultTopic);
////		return doSend((ProducerRecord<K, V>) producerRecord);
//		return null;
//	}

	@Override
	public void flush() {
//		this.producer.flush();
	}

	/**
	 * Send the producer record.
	 * @param producerRecord the producer record.
	 */
	protected ListenableFuture<SendResult> doSend(final com.alibaba.rocketmq.common.message.Message producerRecord) {
		if (this.producer == null) {
			synchronized (this) {
				if (this.producer == null) {
					this.producer = this.producerFactory.createProducer();
				}
			}
		}
		if (this.logger.isTraceEnabled()) {
			this.logger.trace("Sending: " + producerRecord);
		}
		final SettableListenableFuture<SendResult> future = new SettableListenableFuture<>();
		try {
			this.producer.send(producerRecord, new SendCallback() {

                @Override
                public void onSuccess(SendResult sendResult) {
                    future.set(sendResult);
    //				if (KafkaTemplate.this.producerListener != null
    //						&& KafkaTemplate.this.producerListener.isInterestedInSuccess()) {
    //					KafkaTemplate.this.producerListener.onSuccess(producerRecord.topic(),
    //							producerRecord.partition(), producerRecord.key(), producerRecord.value(), metadata);
    //				}
                }

                @Override
                public void onException(Throwable throwable) {
                    future.setException(new KafkaException( "Failed to send", throwable));
    //				if (KafkaTemplate.this.producerListener != null) {
    //					KafkaTemplate.this.producerListener.onError(producerRecord.topic(),
    //							producerRecord.partition(), producerRecord.key(), producerRecord.value(), exception);
    //				}
                }

            });
		} catch (MQClientException e) {
			e.printStackTrace();
		} catch (RemotingException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (this.autoFlush) {
			flush();
		}
		if (this.logger.isTraceEnabled()) {
			this.logger.trace("Sent: " + producerRecord);
		}
		return future;
	}

	class MQMessage extends com.alibaba.rocketmq.common.message.Message {
		public MQMessage() {
		}

		public MQMessage(String topic, byte[] body) {
			super(topic, body);
		}

		public MQMessage(String topic, String tags, byte[] body) {
			super(topic, tags, body);
		}

		public MQMessage(String topic, String tags, String keys, byte[] body) {
			super(topic, tags, keys, body);
		}

		public MQMessage(String topic, String tags, String keys, int flag, byte[] body, boolean waitStoreMsgOK) {
			super(topic, tags, keys, flag, body, waitStoreMsgOK);
		}
	}

}
