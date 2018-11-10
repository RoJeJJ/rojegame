package com.roje.game.core.entity.room;

import com.google.protobuf.Message;
import com.roje.game.core.entity.role.impl.RoomRole;
import com.roje.game.core.exception.ErrorData;
import com.roje.game.core.thread.RoomScheduledExecutorService;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.Frame;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;


@Slf4j
public abstract class Room<R extends RoomRole> {

    public interface RoomListener<M extends Room> {
        void roomDestroy(M room);
    }

    private final long id;

    protected final int maxRoomRoles;

    protected final Map<Integer,R> sitRoles;

    private final Map<R,Integer> seatMap;

    protected final List<R> gamer;

    private boolean gameStart;

    protected boolean lock;

    private RoomListener roomListener;

    protected final List<R> roomRoles;

    private RoomScheduledExecutorService executor;

    private int curHead;

    private ChannelGroup roomChannels;

    private int person;

    public Room(long id, int person, int maxRoomRoles) {
        this.id = id;
        this.person = person;
        sitRoles = new HashMap<>();
        seatMap = new HashMap<>();
        gamer = new ArrayList<>();
        gameStart = false;
        lock = false;
        this.maxRoomRoles = maxRoomRoles;
        roomRoles = new ArrayList<>();
        curHead = 0;
        roomChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    protected void inRoom(R role){
        roomRoles.add(role);
        roomChannels.add(role.getChannel());
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

    /**
     * 使用protobuf 封装的房间信息,用于发送给加入房间的用户
     * @param role 接受消息的玩家
     * @return protobuf{@link Message}
     */
    protected abstract Message roomInfo(R role);

    /**
     * 当玩家坐下后被调用,用于坐下后的自定义逻辑,例如自动开始游戏
     * @param role 坐下的玩家
     */
    protected abstract void onSit(R role);

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
    public void sit(R role,int seat){
        if (lock)
            return;
        if (seat < 1 || seat > person) {
            log.info("请求坐入的座位号错误,{}",seat);
            MessageUtil.sendErrorData(role.getChannel(), ErrorData.SIT_DOWN_SEAT_ERROR);
            return;
        }
        Integer s = seatMap.get(role);
        if (s != null){
            log.info("已经坐入座位中,座位号{}",s);
            MessageUtil.sendErrorData(role.getChannel(),ErrorData.SIT_DOWN_ALREADY_SIT);
            return;
        }
        R temp = sitRoles.get(seat);
        if (temp != null){
            log.info("这个座位已经有玩家:{}",temp.nickname());
            MessageUtil.sendErrorData(role.getChannel(),ErrorData.SIT_DOWN_SEAT_HOLD);
            return;
        }
        sitRoles.put(seat,role);
        seatMap.put(role,seat);
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
        gamer.clear();
        for (int i=1;i<=sitRoles.size();i++){
            R role = sitRoles.get(i);
            if (role != null) {
                role.initStart();
                gamer.add(role);
            }
        }
        initStart();
    }

    protected abstract void initStart();
}
