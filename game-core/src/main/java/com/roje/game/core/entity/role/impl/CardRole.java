package com.roje.game.core.entity.role.impl;

import com.google.protobuf.Any;
import com.roje.game.core.entity.room.impl.CardRoom;
import com.roje.game.core.entity.room.Room;
import com.roje.game.core.exception.ErrorData;
import com.roje.game.core.manager.room.impl.CardRoomManager;
import com.roje.game.core.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

@Slf4j
public class CardRole extends RoomRole {

    private final CardRoomManager roomManager;

    private final List<CardRoom> createRooms;

    public CardRole(long id, String account, String nickname, String avatar, long card, long gold, CardRoomManager roomManager) {
        super(id, account, nickname, avatar, card, gold);
        this.roomManager = roomManager;
        createRooms = new ArrayList<>();
    }

    public synchronized void createCardRoom(Any any) {
        if (createRooms.size() >= roomManager.getUserMaxCreateRoomCount()) {
            MessageUtil.sendErrorData(channel, ErrorData.CREATE_ROOM_EXCEED_USER_LIMIT);
        } else if (joinedRoom != null) {
            MessageUtil.sendErrorData(channel, ErrorData.CREATE_ROOM_CAN_NOT_IN_ROOM);
        } else {
            CardRoom room = roomManager.createRoom(this, any);
            if (room != null) {
                createRooms.add(room);
            }
        }
    }

    public synchronized void joinRoom(int roomId) {
        CardRoom room = roomManager.getRoom(roomId);
        if (room == null) {
            MessageUtil.sendErrorData(channel, ErrorData.ENTER_ROOM_NO_SUCH_ROOM);
        } else {
            if (joinedRoom != null){
                if (joinedRoom == room){
                    MessageUtil.sendErrorData(channel,ErrorData.ENTER_ROOM_ALREADY_JOIN);
                }else
                    MessageUtil.sendErrorData(channel,ErrorData.ENTER_ROOM_JOINED_ANOTHER_ROOM);
            }else {
                Future<Boolean> future = room.getExecutor().getExecutorService().submit(()->room.enter(this));
                try {
                    boolean success = future.get();
                    if (success)
                        joinedRoom = room;
                }catch (InterruptedException | ExecutionException e){
                    log.warn(nickname+"加入房间时出现异常",e);
                    MessageUtil.sendErrorData(channel,ErrorData.ENTER_ROOM_ERROR);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <M extends Room> void roomExecute(Consumer<M> consumer){
        if (joinedRoom != null)
            joinedRoom.getExecutor().getExecutorService()
            .execute(()->consumer.accept((M) joinedRoom));
    }
}