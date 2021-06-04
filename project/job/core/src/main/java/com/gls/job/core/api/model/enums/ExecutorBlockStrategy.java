package com.gls.job.core.api.model.enums;

/**
 * Created by xuxueli on 17/5/9.
 */
public enum ExecutorBlockStrategy {

    SERIAL_EXECUTION("Serial execution"),
    /*CONCURRENT_EXECUTION("并行"),*/
    DISCARD_LATER("Discard Later"),
    COVER_EARLY("Cover Early");

    private String title;

    private ExecutorBlockStrategy(String title) {
        this.title = title;
    }

    public static ExecutorBlockStrategy match(String name, ExecutorBlockStrategy defaultItem) {
        if (name != null) {
            for (ExecutorBlockStrategy item : ExecutorBlockStrategy.values()) {
                if (item.name().equals(name)) {
                    return item;
                }
            }
        }
        return defaultItem;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
