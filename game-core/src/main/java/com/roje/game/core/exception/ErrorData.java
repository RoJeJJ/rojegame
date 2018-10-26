package com.roje.game.core.exception;

import lombok.Getter;

/**
 * 全局错误消息
 */
public enum  ErrorData {
//    def(0,"成功"),
    CREATE_ROOM_CAN_NOT_IN_ROOM(1,"不能在房间里面"),
    CREATE_ROOM_EXCEED_USER_LIMIT(2,"超过最大创建房间数"),
    CREATE_ROOM_CONFIG_ERROR(3,"房间配置出错"),
    LOGIN_LOGGED_ALREADY(4,"已经登录"),
    LOGIN_PARAMS_NOT_BE_EMPTY(5,"登录参数不能为空"),
//    LOGIN_INVALID_CONNECTION(6,"无效的连接"),
    LOGIN_BAD_USERNAME(7,"用户名不存在"),
    LOGIN_BAD_TOKEN(8,"token无效"),
    NOT_LOGGED_IN(9,"没有登录"),
    CREATE_ROOM_NOT_ENOUGH_CARD(10,"房卡不足"),
    LOGIN_OTHER_CONNECTION_ACTIVE(11,"正在连接另一个服务器"),
    LOGIN_ALREADY_CONNECT_ANOTHER_SERVER(12,"已经登录到另一个服务器了"),;
    @Getter
    private int code;
    @Getter
    private String msg;
    ErrorData(int i, String msg) {
        this.code = i;
        this.msg = msg;
    }
}
