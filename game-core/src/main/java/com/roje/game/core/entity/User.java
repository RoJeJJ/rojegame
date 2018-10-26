package com.roje.game.core.entity;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class User implements Serializable {

    private static final long serialVersionUID = -6398430384709961496L;

    private long id;
    //账户名
    private String account;
    //密码
    private String password;
    //昵称
    private String nickname;
    //头像
    private String headimg;
    //性别
    private int sex;
    //金币
    private long gold;
    //房卡
    private long card;

    @Override
    public String toString() {
        return "User:{id:"+id+",account:"+account+",nickname:"+nickname+"}";
    }

    public JsonObject userInfo(){
        JsonObject data = new JsonObject();
        data.addProperty("account",account);
        data.addProperty("headimg",headimg);
        data.addProperty("sex",sex);
        data.addProperty("id",id);
        data.addProperty("gold",gold);
        data.addProperty("card",card);
        return data;
    }
}
