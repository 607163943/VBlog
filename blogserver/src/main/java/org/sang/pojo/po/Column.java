package org.sang.pojo.po;

import java.sql.Timestamp;

/**
 * Created by sang on 2017/12/19.
 */
public class Column {
    private Long id;
    private String cateName;
    private Timestamp date;

    public Column() {
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }
}
