package com.roje.game.core.entity.room;

import com.google.protobuf.Message;
import com.roje.game.core.entity.role.RoomRole;

import java.util.List;

public interface CardRoom<R extends RoomRole> extends Room<R>  {

    R creator();

    int round();

    boolean isRoundStart();

    int payment();

    boolean enter(R role);

    List<R> roomRoles();

    int roomMaxRoles();

    void sit(R role,int seat);

    /**
     * 房间创建成功后需要返回给客户端的数据
     * @return 用protobuf封装的消息
     */
    Message createResponse();
}
