package top.alexmmd.statemachinedemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import top.alexmmd.statemachinedemo.enums.TurnstileEvents;
import top.alexmmd.statemachinedemo.enums.TurnstileStates;

import java.util.EnumSet;

/**
 * @author 汪永晖
 * @date 2021/9/22 17:09
 */
@Configuration
@EnableStateMachine
public class StatemachineConfigurer extends EnumStateMachineConfigurerAdapter<TurnstileStates, TurnstileEvents> {

    @Override
    public void configure(StateMachineStateConfigurer<TurnstileStates, TurnstileEvents> states)
            throws Exception {
        states
                .withStates()
                // 初识状态：Locked
                .initial(TurnstileStates.Locked)
                .states(EnumSet.allOf(TurnstileStates.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<TurnstileStates, TurnstileEvents> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(TurnstileStates.Unlocked).target(TurnstileStates.Locked)
                .event(TurnstileEvents.PUSH).action(customerPassAndLock())
                .and()
                .withExternal()
                .source(TurnstileStates.Locked).target(TurnstileStates.Unlocked)
                .event(TurnstileEvents.COIN).action(turnstileUnlock())
        ;
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<TurnstileStates, TurnstileEvents> config)
            throws Exception {
        config.withConfiguration()
                .machineId("turnstileStateMachine")
        ;
    }

    public Action<TurnstileStates, TurnstileEvents> turnstileUnlock() {
        return context -> System.out.println("解锁旋转门，以便游客能够通过" );
    }

    public Action<TurnstileStates, TurnstileEvents> customerPassAndLock() {
        return context -> System.out.println("当游客通过，锁定旋转门" );
    }

}
