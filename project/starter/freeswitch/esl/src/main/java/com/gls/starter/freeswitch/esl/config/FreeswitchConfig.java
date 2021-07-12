package com.gls.starter.freeswitch.esl.config;

import com.gls.starter.freeswitch.esl.constants.FreeswitchProperties;
import lombok.extern.slf4j.Slf4j;
import org.freeswitch.esl.client.IEslEventListener;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.inbound.InboundConnectionFailure;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author george
 */
@Slf4j
@Configuration
public class FreeswitchConfig {
    @Resource
    private FreeswitchProperties freeswitchProperties;
    @Resource
    private Map<String, IEslEventListener> eventListeners;

    @Bean
    public Client inboundClient() {
        final Client inboundClient = new Client();
        try {
            inboundClient.connect(freeswitchProperties.getHost(), freeswitchProperties.getPort(), freeswitchProperties.getPassword(), freeswitchProperties.getTimeoutSeconds());
        } catch (InboundConnectionFailure failure) {
            failure.printStackTrace();
            return null;
        }
        inboundClient.addEventListener(new IEslEventListener() {
            @Override
            public void eventReceived(EslEvent event) {
                IEslEventListener eventListener = getEventListener(event);
                if (eventListener != null) {
                    eventListener.eventReceived(event);
                }
            }

            @Override
            public void backgroundJobResultReceived(EslEvent event) {
                IEslEventListener eventListener = getEventListener(event);
                if (eventListener != null) {
                    eventListener.backgroundJobResultReceived(event);
                }
            }

            private IEslEventListener getEventListener(EslEvent event) {
                return eventListeners.get(event.getEventName());
            }
        });
        // 订阅你需要的ESl事件，all是订阅所有事件。
        // 具体事件列表请看https://freeswitch.org/confluence/display/FREESWITCH/Event+List
        inboundClient.setEventSubscriptions("plain", "all");
        return inboundClient;
    }
}
