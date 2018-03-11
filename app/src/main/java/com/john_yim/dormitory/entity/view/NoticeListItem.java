package com.john_yim.dormitory.entity.view;

/**
 * Created by MSI-PC on 2018/2/13.
 */

public class NoticeListItem {
    private String id;
    private String sendDate;
    private String content;

    public NoticeListItem(String id, String sendDate, String content) {
        this.id = id;
        this.sendDate = sendDate;
        if (content.length() > 63) {
            this.content = content.substring(0, 62);
        } else {
            this.content = content;
        }
    }

    public String getId() {
        return id;
    }

    public String getSendDate() {
        return sendDate;
    }

    public String getContent() {
        return content;
    }
}
