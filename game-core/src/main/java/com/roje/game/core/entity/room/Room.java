package com.roje.game.core.entity.room;

import com.google.protobuf.Message;
import com.roje.game.core.entity.role.impl.RoomRole;
import com.roje.game.core.exception.ErrorData;
import com.roje.game.core.thread.RoomScheduledExecutorService;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.action.Action;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;


@Slf4j
public abstract class Room {

    public interface RoomListener<M extends Room> {
        void roomDestroy(M room);
    }

    private final long id;

    protected final int maxRoomRoles;

    private final RoomRole[] sitRoles;

    private boolean gameStart;

    private boolean lock;

    private RoomListener roomListener;

    private final List<RoomRole> roomRoles;

    private RoomScheduledExecutorService executor;

    private int curHead;

    private ChannelGroup roomChannels;


    @SuppressWarnings("unchecked")
    public Room(long id, int person, int maxRoomRoles) {
        this.id = id;
        sitRoles = new RoomRole[person];
        gameStart = false;
        lock = false;
        this.maxRoomRoles = maxRoomRoles;
        roomRoles = new ArrayList<>();
        curHead = 0;
        roomChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    protected <R extends RoomRole>void inRoom(R role){
        roomRoles.add(role);
        roomChannels.add(role.getChannel());
    }

    protected void sendAll(Action action,Message message){
        MessageUtil.send(roomChannels,action,message);
    }

    public long id() {
        return id;
    }

    public boolean isGameStart() {
        return gameStart;
    }

    public boolean isLock() {
        return lock;
    }

    public int curHead(){
        return curHead;
    }

    @SuppressWarnings("unchecked")
    protected  <R extends RoomRole> List<R> sitRoles(){
        return Arrays.stream(sitRoles).map(roomRole -> (R) roomRole).collect(Collectors.toList());
    }

    protected int roomRoleSize(){
        return roomRoles.size();
    }

    /**
     * 使用protobuf 封装的房间信息,用于发送给加入房间的用户
     * @param role 接受消息的玩家
     * @return protobuf{@link Message}
     */
    protected abstract Message roomInfo(RoomRole role);

    /**
     * 当玩家坐下后被调用,用于坐下后的自定义逻辑,例如自动开始游戏
     * @param role 坐下的玩家
     */
    protected abstract void onSit(RoomRole role);

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
     */
    public <R extends RoomRole> void sit(R role,int seat){
        if (seat < 1 || seat > sitRoles.length) {
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
        curHead++;
        // TODO: 2018/11/8 通知房间内所有人有人坐下了

        onSit(role);
    }

    /**
     * 执行方法
     * @param consumer 方法
     */
    @SuppressWarnings("unchecked")
    protected <M extends Room> void execute(Consumer<M> consumer) {
        executor.getExecutorService().execute(()->consumer.accept((M) this));
    }

    /**
     * 延时执行方法
     * @param delay 延迟时间,单位:毫秒
     * @param consumer {@link Consumer}
     */
    @SuppressWarnings("unchecked")
    protected <M extends Room>void delayExecute(long delay, Consumer<M> consumer){
        executor.getExecutorService().schedule(() -> consumer.accept((M) this),delay, TimeUnit.MILLISECONDS);
    }

    public final void startGame(){
        if (gameStart)
            return;
        gameStart = true;
        for (RoomRole role:roomRoles) {
            if (role != null)
                role.initStart();
        }
        initStart();
    }

    protected abstract void initStart();
}
