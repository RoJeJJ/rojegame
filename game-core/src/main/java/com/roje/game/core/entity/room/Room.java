package com.roje.game.core.entity.room;

import com.google.protobuf.Message;
import com.roje.game.core.entity.role.impl.RoomRole;
import com.roje.game.core.exception.ErrorData;
import com.roje.game.core.thread.RoomScheduledExecutorService;
import com.roje.game.core.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public abstract class Room {

    public interface RoomListener {
        void roomDestroy(Room room);
    }

    private final long id;

    protected final int maxRoomRoles;

    private RoomRole[] sitRoles;

    private boolean gameStart;

    protected boolean lock;

    private RoomListener roomListener;

    protected List<RoomRole> roomRoles;

    private RoomScheduledExecutorService executor;

    public Room(long id, int person,int maxRoomRoles) {
        this.id = id;
        sitRoles = new RoomRole[person];
        gameStart = false;
        lock = false;
        this.maxRoomRoles = maxRoomRoles;
        roomRoles = new ArrayList<>();
    }

    public long id() {
        return id;
    }

    @SuppressWarnings("unchecked")
    public <R extends RoomRole> R[] sitRoles() {
        return (R[]) sitRoles;
    }

    public boolean isGameStart() {
        return gameStart;
    }

    public boolean isLock() {
        return lock;
    }

    /**
     * 使用protobuf 封装的房间信息,用于发送给加入房间的用户
     * @param role 接受消息的玩家
     * @param <R> {@link RoomRole}
     * @return protobuf{@link Message}
     */
    protected abstract <R extends RoomRole> Message roomInfo(R role);

    /**
     * 当玩家坐下后被调用,用于坐下后的自定义逻辑,例如自动开始游戏
     * @param role 坐下的玩家
     * @param <R> {@link RoomRole}
     */
    protected abstract <R extends RoomRole> void onSit(R role);

    public void setRoomListener(RoomListener roomListener) {
        this.roomListener = roomListener;
    }

    public RoomScheduledExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(RoomScheduledExecutorService executor) {
        this.executor = executor;
    }

    /**
     * 坐到指定的位置上,如果位置上有人,则不能坐下,后续可以加入换位置的功能
     * @param role 请求坐下的玩
     * @param seat 请求坐入的座位
     * @param <R> {@link RoomRole}
     */
    public <R extends RoomRole> void sit(R role,int seat){
        if (seat < 0 || seat >= sitRoles.length) {
            log.info("请求坐入的座位号错误,{}",seat);
            MessageUtil.sendErrorData(role.getChannel(), ErrorData.SIT_DOWN_SEAT_ERROR);
            return;
        }
        if (role.getSeat() != 0){
            log.info("已经坐入座位中,座位号{}",role.getSeat());
            MessageUtil.sendErrorData(role.getChannel(),ErrorData.SIT_DOWN_ALREADY_SIT);
            return;
        }
        if (sitRoles[seat] != null){
            log.info("这个座位已经有玩家:{}",sitRoles[seat].nickname());
            MessageUtil.sendErrorData(role.getChannel(),ErrorData.SIT_DOWN_SEAT_HOLD);
            return;
        }
        sitRoles[seat] = role;
        role.setSeat(seat);

        // TODO: 2018/11/8 通知房间内所有人有人坐下了

        onSit(role);
    }
}
