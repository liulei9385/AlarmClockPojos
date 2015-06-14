package com.clock.model;

import com.clock.anno.Id;

/**
 * USER: liulei
 * DATE: 2015/6/14
 * TIME: 17:35
 */
public class BaseTableBean {
    @Id
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
