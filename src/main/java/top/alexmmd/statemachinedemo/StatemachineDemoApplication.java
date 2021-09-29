package top.alexmmd.statemachinedemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import reactor.core.publisher.Mono;
import top.alexmmd.statemachinedemo.enums.TurnstileEvents;
import top.alexmmd.statemachinedemo.enums.TurnstileStates;

import javax.annotation.Resource;

@SpringBootApplication
public class StatemachineDemoApplication implements CommandLineRunner {

    @Resource
    private StateMachine<TurnstileStates, TurnstileEvents> stateMachine;

    public static void main(String[] args) {
        SpringApplication.run(StatemachineDemoApplication.class, args);
    }

    @Override
    public void run(String... strings) {
        stateMachine.start();
        System.out.println("--- coin ---");
        stateMachine.sendEvent(TurnstileEvents.COIN);
        System.out.println("--- coin ---");
        stateMachine.sendEvent(TurnstileEvents.COIN);
        System.out.println("--- push ---");
        stateMachine.sendEvent(TurnstileEvents.PUSH);
        System.out.println("--- push ---");
        stateMachine.sendEvent(TurnstileEvents.PUSH);
        stateMachine.stop();


    }
}
