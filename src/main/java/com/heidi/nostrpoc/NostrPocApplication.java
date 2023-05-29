package com.heidi.nostrpoc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
@EnableKafka
public class NostrPocApplication {

    public static void main(String[] args) {
        SpringApplication.run(NostrPocApplication.class, args);
    }

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("NostrPoc-");
        executor.initialize();
        return executor;
    }

//    @Bean
    CommandLineRunner commandLineRunner(KafkaTemplate<String, String> kafkaTemplate) {
        return args -> {
            for(int i=0; i<100; i++ ){
                kafkaTemplate.send("nostr-topic", "Hello World :)" +  i);
            }
//            kafkaTemplate.send("nostr-topic", "Hello World :)");
        };
    }
}
