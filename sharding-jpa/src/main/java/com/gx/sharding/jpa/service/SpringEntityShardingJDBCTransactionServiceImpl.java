/*
 * Copyright 2016-2018 shardingsphere.io.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package com.gx.sharding.jpa.service;

import com.gx.sharding.jpa.entity.Order;
import com.gx.sharding.jpa.entity.OrderItem;
import com.gx.sharding.jpa.repository.OrderItemRepository;
import com.gx.sharding.jpa.repository.OrderRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 在客户端进行分库分表的Sharding-JDBC，
 * 虽然可以作为轻量级微服务框架灵活应用，
 * 但却没有作为云接入端进行统一管控的能力
 */
@Service("jdbcTransactionService")
public class SpringEntityShardingJDBCTransactionServiceImpl extends ShardingJDBCTransactionService implements SpringEntityTransactionService {
    
    @Resource
    private OrderRepository orderRepository;
    
    @Resource
    private OrderItemRepository orderItemRepository;
    
    @Override
    protected OrderRepository getOrderRepository() {
        return orderRepository;
    }
    
    @Override
    protected OrderItemRepository getOrderItemRepository() {
        return orderItemRepository;
    }
    
    @Override
    protected Order newOrder() {
        return new Order();
    }
    
    @Override
    protected OrderItem newOrderItem() {
        return new OrderItem();
    }
}