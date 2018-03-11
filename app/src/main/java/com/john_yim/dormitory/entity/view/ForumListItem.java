package com.john_yim.dormitory.entity.view;

/**
 * Created by MSI-PC on 2018/2/13.
 */

public class ForumListItem {
    String ownerName;
    String sendDate;
    String title;
    String replyCount;
    String goodCount;

    public ForumListItem(String ownerName, String sendDate, String title, String replyCount,
                         String goodCount) {
        this.ownerName = ownerName;
        this.sendDate = sendDate;
        this.title = title;
        this.replyCount = replyCount;
        this.goodCount = goodCount;
    }

    public String getGoodCount() {
        return goodCount;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getReplyCount() {
        return replyCount;
    }

    public String getSendDate() {
        return sendDate;
    }

    public String getTitle() {
        return title;
    }
}
