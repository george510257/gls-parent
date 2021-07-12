package com.gls.starter.freeswitch.esl.listener;

import com.gls.starter.freeswitch.esl.constants.EslEventName;
import lombok.extern.slf4j.Slf4j;
import org.freeswitch.esl.client.IEslEventListener;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author george
 */
@Slf4j
@Component(EslEventName.CHANNEL_CREATE)
public class CreateEslEventListener implements IEslEventListener {
    @Override
    public void eventReceived(EslEvent event) {
        Map<String, String> header = event.getEventHeaders();
        log.info("CreateEslEventListener.eventReceived eventName:{}", event.getEventName());
        for (Map.Entry entry : header.entrySet()) {
            log.info("key：" + entry.getKey() + " , value：" + entry.getValue());
        }
    }

    @Override
    public void backgroundJobResultReceived(EslEvent event) {
        log.info("CreateEslEventListener.backgroundJobResultReceived eventName:{}", event.getEventName());
    }
}
