package com.roje.game.core.entity.room.impl;

import com.google.protobuf.Any;
import com.google.protobuf.Message;
import com.roje.game.core.entity.role.impl.RoomRole;
import com.roje.game.core.entity.room.Room;
import com.roje.game.core.exception.ErrorData;
import com.roje.game.core.exception.RJException;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.action.Action;
import com.roje.game.message.create_room.EntryRoomResponse;

public abstract class CardRoom extends Room {

    private final RoomRole creator;

    public RoomRole creator(){
        return creator;
    }

    public <R extends RoomRole> CardRoom(long id,R role, int person,int maxRoomRoles) {
        super(id, person,maxRoomRoles);
        this.creator = role;
    }

    public <R extends RoomRole> boolean enter(R role) {
        if (lock)
            return false;
        if (roomRoles.size() >= maxRoomRoles) {
            MessageUtil.sendErrorData(role.getChannel(), ErrorData.ENTER_ROOM_ROOM_FULL);
            return false;
        }
        try {
            enter0(role);
        } catch (RJException e) {
            MessageUtil.sendErrorData(role.getChannel(),e.getErrorData());
            return false;
        }
        roomRoles.add(role);
        EntryRoomResponse.Builder builder = EntryRoomResponse.newBuilder();
        Message message = roomInfo(role);
        builder.setData(Any.pack(message));
        MessageUtil.send(role.getChannel(), Action.EntryRoomRes,builder.build());
        return true;
    }

    /**
     * 自定义加入房间逻辑,如果不允许加入,抛出{@link RJException}
     * @param role 加入房卡房间的玩家
     * @param <R> 房卡房玩家
     * @throws RJException 自定义异常,用于返回客户端错误消息
     */
    protected <R extends RoomRole> void enter0(R role) throws RJException {}
}
