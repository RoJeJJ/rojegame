package com.roje.game.cluster.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.roje.game.core.server.ServerInfo;
import lombok.Getter;

public class ResponseUtil {
    public enum ResponseData{
        SUCCESS(0,"ok"),
        USER_NOT_EXIST(0,"用户不存在"),
        USER_NAME_NOT_EMPTY(1,"用户名不能为空"),
        USER_NAME_ERROR(2,"用户名错误"),
        INVALID_TOKEN(3,"无效的token"),
        NO_SUCH_GAME(4,"没找到该游戏"),
        UNSUPPORTED_VERSION(5,"不支持的客户端版本");

        @Getter
        private int code;
        @Getter
        private String msg;
        ResponseData(int i, String msg) {
            this.code = i;
            this.msg = msg;
        }
    }

    public static String error(ResponseData data){
        JsonObject object = new JsonObject();
        object.addProperty("code",data.getCode());
        object.addProperty("msg",data.getMsg());
        return object.toString();
    }

    public static String success(JsonElement data){
        JsonObject object = new JsonObject();
        object.addProperty("code",ResponseData.SUCCESS.getCode());
        object.add("data",data);
        return object.toString();
    }
}
