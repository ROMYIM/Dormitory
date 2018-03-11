package com.john_yim.dormitory.entity;

import com.john_yim.dormitory.constant.BillType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by MSI-PC on 2018/2/24.
 */

public class Bill implements Serializable {
    private Integer id;
    private Date payDate;
    private BillType type;
    private Float payMoney;

    public Bill() {

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setType(BillType type) {
        this.type = type;
    }

    public BillType getType() {
        return type;
    }

    public void setPayMoney(Float payMoney) {
        this.payMoney = payMoney;
    }

    public Float getPayMoney() {
        return payMoney;
    }
}
