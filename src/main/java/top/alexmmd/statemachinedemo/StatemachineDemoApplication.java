package top.alexmmd.statemachinedemo;

import javafx.application.Application;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineMessageHeaders;
import reactor.core.publisher.Mono;
import top.alexmmd.statemachinedemo.enums.Events;
import top.alexmmd.statemachinedemo.enums.States;

import javax.annotation.Resource;

@SpringBootApplication
public class StatemachineDemoApplication implements CommandLineRunner {

    @Resource
    private StateMachine<States, Events> stateMachine;

    public static void main(String[] args) {
        SpringApplication.run(StatemachineDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        stateMachine.sendEvent(Events.E1);
//        stateMachine.sendEvent(Events.E2);
//        sendEventUsingTimeout();
    }

    void sendEventUsingTimeout() {
        stateMachine
                .sendEvent(Mono.just(MessageBuilder
                        .withPayload(Events.E1)
                        .setHeader(StateMachineMessageHeaders.HEADER_DO_ACTION_TIMEOUT, 50000)
                        .build()))
                .subscribe();

    }
}
