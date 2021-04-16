package com.gls.job.core.api.model;

import java.io.Serializable;

/**
 * @author xuxueli 2020-04-11 22:27
 */
public class LogModel implements Serializable {
    private static final long serialVersionUID = 42L;
    private long logDateTim;
    private long logId;
    private int fromLineNum;

    public LogModel() {
    }

    public LogModel(long logDateTim, long logId, int fromLineNum) {
        this.logDateTim = logDateTim;
        this.logId = logId;
        this.fromLineNum = fromLineNum;
    }

    public long getLogDateTim() {
        return logDateTim;
    }

    public void setLogDateTim(long logDateTim) {
        this.logDateTim = logDateTim;
    }

    public long getLogId() {
        return logId;
    }

    public void setLogId(long logId) {
        this.logId = logId;
    }

    public int getFromLineNum() {
        return fromLineNum;
    }

    public void setFromLineNum(int fromLineNum) {
        this.fromLineNum = fromLineNum;
    }

}