package com.roje.game.core.exception;

import lombok.Getter;

/**
 * 全局错误消息
 */
public enum  ErrorData {
//    def(0,"成功"),
    CREATE_ROOM_PARAMETER_ERROR(1,"无法处理的参数"),
    CREATE_ROOM_CAN_NOT_IN_ROOM(2,"不能在房间里面"),
    CREATE_ROOM_EXCEED_USER_LIMIT(3,"超过最大创建房间数"),
    CREATE_ROOM_CONFIG_ERROR(4,"房间配置出错"),
    CREATE_ROOM_UNKNOWN_ERROR(5,"未知错误"),
    CREATE_ROOM_USER_NOT_EXIST(6,"用户不存在"),
    CREATE_ROOM_USER_NOT_LOGGED(7,"用户没有登录"),
    CREATE_ROOM_GAME_SERVER_NOT_FOUND(8,"游戏服务器不可用"),
    CREATE_ROOM_ROOM_FULL(9,"服务器房间满了"),


    LOGIN_ACCOUNT_IS_NULL(100,"用户名不能为空"),
    LOGIN_INVALID_TOKEN(101,"无效的token"),
    LOGIN_LOGGED(102,"重复登录"),
    LOGIN_ANOTHER_SERVER(103,"正在登录其他服务器"),
    LOGIN_LOGGED_ANOTHER_SERVER(104,"已经登录其他服务器"),

    ENTER_ROOM_ROOM_FULL(201,"房间满了"),
    ENTER_ROOM_NO_SUCH_ROOM(202,"房间不存在"),
    ENTER_ROOM_JOINED_ANOTHER_ROOM(203,"已经加入了其他的房间"),
    ENTER_ROOM_ALREADY_JOIN(204,"已经在房间中了"),
    ENTER_ROOM_ERROR(205,"进入房间失败"),
    ENTER_ROOM_NOT_ALLOW_JOIN_HALLWAY(206,"不允许中途加入"),

    SIT_DOWN_SEAT_ERROR(301,"座位号错误"),
    SIT_DOWN_GAME_START(302,"游戏已经开始不能换位置"),
    SIT_DOWN_SEAT_HAS_PERSON(303,"位置已经有人了"),;
    @Getter
    private int code;
    @Getter
    private String msg;
    ErrorData(int i, String msg) {
        this.code = i;
        this.msg = msg;
    }
}
