package top.alexmmd.statemachinedemo.config;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.action.StateDoActionPolicy;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import top.alexmmd.statemachinedemo.enums.Events;
import top.alexmmd.statemachinedemo.enums.States;

import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

/**
 * @author 汪永晖
 * @date 2021/9/30 10:16
 */
@Configuration
@EnableStateMachine
public class StateMachineConfig
        extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
//                .stateDoActionPolicy(StateDoActionPolicy.TIMEOUT_CANCEL)
//                .stateDoActionPolicyTimeout(10, TimeUnit.MILLISECONDS)
                .listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.S0)
                .state(States.S1)
                .state(States.S2);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(States.S0).target(States.S1).event(Events.E1).action(action(), errorAction())
                .and()
                .withExternal()
                .source(States.S1).target(States.S2).event(Events.E2).action(action(), errorAction());
    }

    @Bean
    public Action<States, Events> action() {
        return new Action<States, Events>() {

            @Override
            public void execute(StateContext<States, Events> context) {
                throw new RuntimeException("MyError");
            }
        };
    }

    @Bean
    public Action<States, Events> errorAction() {
        return new Action<States, Events>() {

            @Override
            public void execute(StateContext<States, Events> context) {
                // RuntimeException("MyError") added to context
                Exception exception = context.getException();
                System.out.println(exception.getMessage());
                System.out.println(context.getStateMachine().getState());
            }
        };
    }

//    @Bean
//    public Action<States, Events> action() {
//        return new Action<States, Events>() {
//
//            @Override
//            public void execute(StateContext<States, Events> context) {
//                // do something
//                System.out.println("action");
//            }
//        };
//    }


    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                if (ObjectUtil.isNotNull(from)) {
                    System.out.println("State from " + from.getId());
                }
                System.out.println("State change to " + to.getId());
            }
        };
    }
}
