package com.gls.starter.freeswitch.esl.constants;

/**
 * @author george
 */
public interface EslEventName {
    /**
     * 通道创建事件
     */
    String CHANNEL_CREATE = "CHANNEL_CREATE";
    /**
     * 通道应答事件
     */
    String CHANNEL_ANSWER = "CHANNEL_ANSWER";
    /**
     * 通道桥接事件
     */
    String CHANNEL_BRIDGE = "CHANNEL_BRIDGE";
    /**
     * 通道挂断事件
     */
    String CHANNEL_HANGUP = "CHANNEL_HANGUP";
    /**
     *
     */
    String CHANNEL_HANGUP_COMPLETE = "CHANNEL_HANGUP_COMPLETE";
    /**
     *
     */
    String CUSTOM = "CUSTOM";
    /**
     *
     */
    String CHANNEL_DESTROY = "CHANNEL_DESTROY";
    /**
     *
     */
    String CHANNEL_ORIGINATE = "CHANNEL_ORIGINATE";
    /**
     *
     */
    String CHANNEL_OUTGOING = "CHANNEL_OUTGOING";
    /**
     * 心跳包
     */
    String HEARTBEAT = "HEARTBEAT";
    /**
     * 计费心跳包心跳包
     */
    String SESSION_HEARTBEAT = "SESSION_HEARTBEAT";
}
