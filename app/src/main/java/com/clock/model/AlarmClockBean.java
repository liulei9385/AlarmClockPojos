package com.clock.model;

import com.clock.anno.Table;

/**
 * USER: liulei
 * DATE: 2015/6/14
 * TIME: 17:34
 */
@Table(value = "amcolck")
public class AlarmClockBean extends BaseTableBean {

    private String name;
    private long starttime;
    private long endtime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStarttime() {
        return starttime;
    }

    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }
}