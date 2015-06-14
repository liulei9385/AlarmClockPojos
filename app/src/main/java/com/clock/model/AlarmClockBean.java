package com.clock.model;

import com.clock.anno.Field;
import com.clock.anno.Table;

/**
 * USER: liulei
 * DATE: 2015/6/14
 * TIME: 17:34
 */
@Table(value = "amclock")
public class AlarmClockBean extends BaseTableBean {

    @Field(name = "name", type = "varchar(20)")
    private String name;
    @Field(name = "starttime", type = "datetime")
    private long starttime;
    @Field(name = "endtime", type = "datetime")
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