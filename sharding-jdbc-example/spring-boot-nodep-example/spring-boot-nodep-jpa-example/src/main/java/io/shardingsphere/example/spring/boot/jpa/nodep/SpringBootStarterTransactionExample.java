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

package io.shardingsphere.example.spring.boot.jpa.nodep;

import io.shardingsphere.example.repository.api.service.TransactionService;
import io.shardingsphere.example.repository.jpa.service.SpringEntityTransactionService;
import io.shardingsphere.transaction.api.TransactionType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("io.shardingsphere.example.repository.jpa")
@EntityScan(basePackages = "io.shardingsphere.example.repository.jpa.entity")
@SpringBootApplication
public class SpringBootStarterTransactionExample {
    
    public static void main(final String[] args) {
        try (ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringBootStarterTransactionExample.class, args)) {
            System.out.println("本次为加事务测试");
            process(applicationContext);
        }
    }
    
    private static void process(final ConfigurableApplicationContext applicationContext) {
        TransactionService transactionService = getTransactionService(applicationContext);
        transactionService.processSuccess(false);
        processFailureSingleTransaction(transactionService, TransactionType.LOCAL);
        processFailureSingleTransaction(transactionService, TransactionType.XA);
        //暂未支持
//        processFailureSingleTransaction(transactionService, TransactionType.BASE);
    }
    
    private static void processFailureSingleTransaction(final TransactionService transactionService, final TransactionType type) {
        System.out.println("Print OrderItem Data 不能打印信息为事务回滚成功");
        try {
            switch (type) {
                case LOCAL:
                    transactionService.processFailureWithLocal();
                    break;
                case XA:
                    transactionService.processFailureWithXa();
                    break;
                case BASE:
                    transactionService.processFailureWithBase();
                    break;
                default:
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            transactionService.printData(false);
        }
    }
    
    private static TransactionService getTransactionService(final ConfigurableApplicationContext applicationContext) {
        return applicationContext.getBean("jdbcTransactionService", SpringEntityTransactionService.class);
    }
}
