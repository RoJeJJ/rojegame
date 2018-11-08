package com.roje.game.core.entity.room.impl;

import com.google.protobuf.Message;
import com.roje.game.core.entity.role.RoomRole;
import com.roje.game.core.entity.room.CardRoom;
import com.roje.game.core.exception.ErrorData;
import com.roje.game.core.exception.RJException;
import com.roje.game.core.thread.RoomScheduledExecutorService;
import com.roje.game.core.util.ChangePos;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.action.Action;
import com.roje.game.message.create_room.ChangePosRequest;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class AbsCardRoom<R extends RoomRole> implements CardRoom<R> {

    private final long id;

    private final R creator;

    private final int round;

    private final int payment;

    private final R[] sitRoles;

    private final int roomMaxRoles;

    private final List<R> roomRoles;

    private boolean roundStart;

    private boolean gameStart;


    private boolean lock;

    private Map<R, ChangePos<R>> changePosMap;

    private RoomScheduledExecutorService executor;

    @SuppressWarnings("unchecked")
    public AbsCardRoom(long id, R creator, int round, int payment, int maxPlayer,int roomMaxRoles, Class<R> roleType) {
        this.id = id;
        this.creator = creator;
        this.round = round;
        this.payment = payment;
        sitRoles = (R[]) Array.newInstance(roleType,maxPlayer);
        this.roomMaxRoles = roomMaxRoles;
        roomRoles = new ArrayList<>(roomMaxRoles);
        roundStart = false;
        gameStart = false;
        lock = false;
        changePosMap = new HashMap<>();
    }

    @Override
    public boolean isLock() {
        return lock;
    }

    @Override
    public R creator() {
        return creator;
    }

    @Override
    public List<R> roomRoles() {
        return roomRoles;
    }

    @Override
    public int roomMaxRoles() {
        return roomMaxRoles;
    }

    @Override
    public int round() {
        return round;
    }

    @Override
    public boolean isRoundStart() {
        return roundStart;
    }

    @Override
    public int payment() {
        return payment;
    }

    @Override
    public boolean enter(R role){
        if (lock)
            return false;
        if (roomRoles.size() >= roomMaxRoles){
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
        Message message = roomInfo(role);
        MessageUtil.send(role.getChannel(), Action.EntryRoomRes,message);
        return true;
    }

    public void sit(R role,int seat) {
        if (lock)
            return;
        if (seat < 0 || seat >= sitRoles.length){
            MessageUtil.sendErrorData(role.getChannel(),ErrorData.SIT_DOWN_SEAT_ERROR);
            return;
        }
        int oldSeat = role.getSeat();
        if (oldSeat != 0){ //已经坐下了,要换位置
            if (role.inGame()) { //如果玩家当前正在游戏中,不能换位置
                MessageUtil.sendErrorData(role.getChannel(), ErrorData.SIT_DOWN_GAME_START);
                return;
            }
            if (sitRoles[seat] == null){ //要换的位置没人,直接坐下去
                sitRoles[oldSeat] = null;
                sitRoles[seat] = role;
                role.setSeat(seat);
                // TODO: 2018/11/8  通知房间中的所有人,玩家换到新的位置(该位置没人)
            } else {
                //位置上已经有人了
                R exchangee = sitRoles[seat];
                if (exchangee.inGame()){ //被换位置的玩家在游戏中,不能换位置
                    MessageUtil.sendErrorData(role.getChannel(), ErrorData.SIT_DOWN_GAME_START);
                } else {
                    ChangePos<R> changePos = changePosMap.get(role);


                    // TODO: 2018/11/8  向被换位置的玩家发送换位置请求
                    ChangePosRequest.Builder builder = ChangePosRequest.newBuilder();
                    builder.setApplicantId(role.id());
                    MessageUtil.send(exchangee.getChannel(),Action.ChangePosReq,builder.build());
                }
            }
        } else { //还没有坐下去
            if (sitRoles[seat] != null) { //要坐下的位置有人了
                MessageUtil.sendErrorData(role.getChannel(), ErrorData.SIT_DOWN_SEAT_HAS_PERSON);
            }else { //没人,可以坐下了
                sitRoles[seat] = role;
                role.setSeat(seat);
                // TODO: 2018/11/8 通知房间中所有人有人坐下了

                onSit(role,seat);
            }
        }
    }

    /**
     * 玩家成功坐到座位上时被调用,用于处理坐下后的自定义逻辑,例如自动开始游戏
     * @param role 座位上的玩家
     * @param seat 座位号
     */
    protected abstract void onSit(R role, int seat);

    /**
     * 自定义加入房间逻辑,如果拒绝加入房间,抛出{@link RJException}异常
     * @param role 要加入房间的role
     * @throws RJException 自定义错误消息异常,用于返回客户端
     */
    public void enter0(R role) throws RJException {}

    @Override
    public long id() {
        return id;
    }

    @Override
    public R[] sitRoles() {
        return sitRoles;
    }

    @Override
    public boolean isGameStart() {
        return gameStart;
    }

    @Override
    public void setRoomListener(RoomListener roomListener) {

    }

    @Override
    public RoomScheduledExecutorService getExecutor() {
        return executor;
    }

    @Override
    public void setExecutor(RoomScheduledExecutorService executor) {
        this.executor = executor;
    }
}
