package com.john_yim.dormitory.entity.view;

/**
 * Created by MSI-PC on 2018/2/13.
 */

public class RepairListItem {
    private String id;
    private String sendDate;
    private String type;
    private String status;
    private String content;

    public RepairListItem(String id, String sendDate, String type, String status, String content) {
        this.id = id;
        this.sendDate = sendDate;
        this.type = type;
        this.status = status;
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

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getContent() {
        return content;
    }
}
