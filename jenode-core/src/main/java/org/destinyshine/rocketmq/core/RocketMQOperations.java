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

import com.alibaba.rocketmq.client.producer.SendResult;
import org.springframework.messaging.Message;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * The basic Kafka operations contract returning {@link ListenableFuture}s.
 *
 * @param <K> the key type.
 * @param <V> the value type.
 *
 * @author Marius Bogoevici
 * @author Gary Russell
 */
public interface RocketMQOperations<K, V> {

    /**
     * Send the data to the default topic with no key or partition.
     * @param data The data.
     * @return a Future for the {@link SendResult}.
     */
    ListenableFuture<SendResult> sendDefault(V data);

    /**
     * Send the data to the default topic with the provided key and no partition.
     * @param key the key.
     * @param data The data.
     * @return a Future for the {@link SendResult}.
     */
    ListenableFuture<SendResult> sendDefault(K key, V data);

    /**
     * Send the data to the default topic with the provided key and partition.
     * @param partition the partition.
     * @param key the key.
     * @param data the data.
     * @return a Future for the {@link SendResult}.
     */
    ListenableFuture<SendResult> sendDefault(int partition, K key, V data);

    /**
     * Send the data to the provided topic with no key or partition.
     * @param topic the topic.
     * @param data The data.
     * @return a Future for the {@link SendResult}.
     */
    ListenableFuture<SendResult> send(String topic, V data);

    /**
     * Send the data to the provided topic with the provided key and no partition.
     * @param topic the topic.
     * @param key the key.
     * @param data The data.
     * @return a Future for the {@link SendResult}.
     */
    ListenableFuture<SendResult> send(String topic, K key, V data);

    /**
     * Send the data to the provided topic with the provided partition and no key.
     * @param topic the topic.
     * @param partition the partition.
     * @param data The data.
     * @return a Future for the {@link SendResult}.
     */
    ListenableFuture<SendResult> send(String topic, int partition, V data);

    /**
     * Send the data to the provided topic with the provided key and partition.
     * @param topic the topic.
     * @param partition the partition.
     * @param key the key.
     * @param data the data.
     * @return a Future for the {@link SendResult}.
     */
    ListenableFuture<SendResult> send(String topic, int partition, K key, V data);

    /**
     * Send a message with routing information in message headers. The message payload
     * may be converted before sending.
     * @param message the message to send.
     * @return a Future for the {@link SendResult}.
     */
//    ListenableFuture<SendResult> send(Message<?> message);

    /**
     * Flush the producer.
     */
    void flush();

}
