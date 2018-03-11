package com.john_yim.dormitory.constant;

/**
 * Created by MSI-PC on 2018/2/15.
 */

public enum ResultType {
    SUCCESS(000, "success", "操作成功"), NO_USER(101, "failed", "没有该用户"), ERR_PASSWORD(102, "failed", "密码错误"),
    AL_REGISTER(103, "failed", "已经注册"), ERR_LOGIN(104, "failed", "登录错误"), ERR_DORMITORY(200, "failed", "查询宿舍失败"), ERR_STUDENT(201, "failed", "查询学生失败"),
    ERR_VIOLATION(202, "failed", "查询违规记录失败"), ERR_REPAIR(203, "failed", "查询维修记录失败"), ERR_DORADMIN(204, "failed", "查询宿舍管理员失败");

    private final int code;
    private final String result;
    private final String message;

    ResultType(int code, String result, String message) {
        // TODO Auto-generated constructor stub
        this.code = code;
        this.result = result;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getResult() {
        return result;
    }
}
