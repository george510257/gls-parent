package com.gls.job.core.base;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author george
 */
@Slf4j
@Getter
public abstract class BaseThread extends Thread {
    private volatile boolean stop = false;
    private String stopReason;

    public void toStop(String stopReason) {
        this.stop = true;
        this.stopReason = stopReason;
        this.interrupt();
        try {
            this.join();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void run() {
        // init
        try {
            initExecute();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        // execute
        while (!stop) {
            try {
                doExecute();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            try {
                if (!stop) {
                    sleepExecute();
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        // destroy
        try {
            destroyExecute();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 初始化方法
     *
     * @throws Exception
     */
    protected abstract void initExecute() throws Exception;

    /**
     * 执行方法
     *
     * @throws Exception
     */
    protected abstract void doExecute() throws Exception;

    /**
     * 设置执行间隔
     *
     * @throws Exception
     */
    protected abstract void sleepExecute() throws Exception;

    /**
     * 结束方法
     *
     * @throws Exception
     */
    protected abstract void destroyExecute() throws Exception;
}
