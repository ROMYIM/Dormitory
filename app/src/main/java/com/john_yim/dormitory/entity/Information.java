package com.john_yim.dormitory.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by MSI-PC on 2018/2/18.
 */

public class Information implements Serializable {
    private Integer id;
    private String content;
    private Date sendDate;
    private Date deadline;

    public Information() {}

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public String getContent() {
        return content;
    }
}
