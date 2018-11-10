package com.roje.game.golden.data;

import com.google.protobuf.Message;
import com.roje.game.core.entity.room.Room;
import com.roje.game.core.entity.room.impl.CardRoom;
import com.roje.game.core.exception.RJException;
import com.roje.game.golden.utils.Suit;
import com.roje.game.message.gf.GFCardRoomConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

@Slf4j
public class GFCardRoom extends CardRoom<GFCardRole> {

    private List<Integer> pokers;

    private GFCardRole banker;

    private GFCardRole actionRole;

    private final int base;

    private int pool;

    private List<GFCardRole> actionRoles;

    private GFCardRole winner;

    private int turn;

    private final int men;

    private int curBet;

    private final int maxTurn;

    private final boolean switch235;

    public GFCardRoom(long id,
                      GFCardRole role,
                      int maxRoomRoles,
                      GFCardRoomConfig config) {
        super(id, role, config.getPerson(), maxRoomRoles, config.getRound());
        base = config.getBase();
        men = config.getMen();
        maxTurn = config.getMaxTurn();
        switch235 = config.getSwitch235();
    }

    @Override
    protected Message roomInfo(GFCardRole role) {
        return null;
    }

    @Override
    protected void enter0(GFCardRole role) throws RJException {
    }

    @Override
    protected void initStart0() {
        pokers.clear();
        for (int i = 2; i <= 14; i++) {
            for (int j = 0; j < 4; j++)
                pokers.add(2 * 4 + j);
        }

        //洗牌
        Collections.shuffle(pokers);

        turn = 1;
        curBet = base;
        actionRoles = new ArrayList<>(gamer);
        //通知房间中所有人,游戏开始
        // TODO: 2018/11/10 通知所有人游戏开始

        //2秒后加底分
        delayExecute(2000, GFCardRoom::addBaseScore);
    }

    private GFCardRole getNextTurn(GFCardRole role){
        int index = actionRoles.indexOf(role);
        return actionRoles.get(index+1== actionRoles.size()?0:index+1);
    }

    private void addBaseScore(){
        for (GFCardRole role:gamer){
            pool += base;
            role.deductScore(base);
            // TODO: 2018/11/10 通知所有人,下底
        }
        //2秒后开始发牌
        delayExecute(2000,GFCardRoom::deal);
    }

    /**
     * 设置下一个操作的玩家
     * @param lastActionRole 上一个操作的玩家
     * @param isFold 上一个玩家是否选择弃牌
     */
    private void loopAction(GFCardRole lastActionRole,boolean isFold){

        actionRole = getNextTurn(lastActionRole);
        if (isFold)
            actionRoles.remove(lastActionRole);
        if (actionRoles.size() == 1) { //剩下一个人了,直接胜出
            win(actionRole);
        }else {
            if (actionRole.isTurnBet()) { //本轮下注结束
                if (turn >= maxTurn) { //达到最大圈数,强制比牌
                    forceOpen();
                }else { //开始下一轮
                    turn++;
                    for (GFCardRole role:actionRoles)
                        role.setTurnBet(false);
                    // TODO: 2018/11/10 通知客户端可以下注了,附带发送给所有人

                }
            }else {
                // TODO: 2018/11/10 通知客户端可以下注了,附带发送给所有人
            }
        }
    }

    public void action(GFCardRole role,int action,int add,long id){
        if (lock || actionRole != role)
            return;
        switch (action){
            case 1:
                showHand(role);
                break;
            case 2:
                fold(role);
                break;
            case 3:
                follow(role);
                break;
            case 4:
                add(role,add);
                break;
            case 5:
                bi(role,id);
                break;
        }
    }

    //看牌
    private void showHand(GFCardRole role){
        if (lock ||actionRole != role)
            return;
        if (turn <= men) //必闷,不能看牌
            return;
        if (!role.isShow()){
            role.setShow(true);
            // TODO: 2018/11/10 通知看牌
        }
    }

    //弃牌
    private void fold(GFCardRole role){

        // TODO: 2018/11/10 通知弃牌

        loopAction(role,true);
    }

    //跟注
    private void follow(GFCardRole role){
        int score;
        if (role.isShow()){ //看牌跟注
            score = curBet * 2;
        }else {
            score = curBet;
        }
        role.deductScore(score);
        role.setTurnBet(true);

        // TODO: 2018/11/10 通知跟注

        loopAction(role,false);
    }

    //加注
    private void add(GFCardRole role,int add){

    }

    private void bi(GFCardRole role,long id){
        if (role.id() == id)
            return;
        GFCardRole matcher = null;
        for (GFCardRole r:actionRoles) {
            if (r.id() == id)
                matcher = r;
        }
        if (matcher == null){
            return;
        }
    }

    @Override
    protected void onSit(GFCardRole role) {
        if (sitRoles.size() >= 2)
            startGame();
    }

    private void win(GFCardRole winner){

    }

    private GFCardRole ranBanker(){
        int index = new Random().nextInt(gamer.size());
        return gamer.get(index);
    }


    private void deal() {
        //确定庄家
        if (banker == null) { //第一把 ,随机庄家
            banker = ranBanker();
        }

        for (int i=0;i<3;i++){
            for (GFCardRole role:gamer){
                role.addPoker(pokers.remove(0));
            }
        }
        // TODO: 2018/11/10 通知所有人发牌

        //2秒后开始下注
        delayExecute(2000, (Consumer<GFCardRoom>) room -> room.loopAction(banker,false));
    }

    /**
     * 强制比牌
     */
    private void forceOpen(){
        // TODO: 2018/11/10  强制比牌
    }

    //计算牌型
    private Suit calc(List<Integer> handCard) {
        int[] n = ArrayUtils.toPrimitive(handCard.toArray(new Integer[0]));
        int three_kind = 0;
        int straight = -1;
        int flash = -1;
        int pair = -1;

        for (int i = 1; i < n.length; i++) {
            int color1 = n[i - 1] % 4;
            int face1 = n[i - 1] / 4;
            int color2 = n[i] % 4;
            int face2 = n[i] / 4;
            if (color1 == color2) {
                if (flash != 0)
                    flash = face2;
            } else
                flash = 0;
            if (face1 == face2) {
                straight = 0;
                if (pair > 0){
                    pair = 0;
                    three_kind = face2;
                }else
                    pair = face2;
            } else {
                if (straight != 0) {
                    if (face1 + 1 == face2)
                        straight = face2;
                    else if (face1 == 3 && face2 == 14)
                        straight = face1;
                    else
                        straight = 0;
                }
            }
        }
        if (three_kind > 0){
            Suit suit = Suit.three_kind;
            suit.setNext(three_kind);
            return suit;
        }
        if (straight > 0 && flash > 0){
            Suit suit = Suit.straight_flash;
            suit.setNext(straight);
            return suit;
        }
        if (flash > 0){
            Suit suit = Suit.flash;
            suit.setNext(n[0]/4+n[1]/4*16+n[2]/4*16*16);
            return suit;
        }
        if (straight > 0){
            Suit suit = Suit.straight;
            suit.setNext(straight);
        }
        if (pair > 0){
            int p = 0;
            for (int a : n) {
                if (a / 4 != pair)
                    p = a / 4;
            }
            Suit suit = Suit.pair;
            suit.setNext(p + pair * 16);
            return suit;
        }
        if (switch235){
            if (n[0]/4 == 2 && n[1]/4 == 3 && n[2]/4 == 5){
                Suit suit = Suit.ex235;
                suit.setNext(n[0]/4+n[1]/4*16+n[2]/4*16*16);
                return suit;
            }
        }
        Suit suit = Suit.high;
        suit.setNext(n[0]/4+n[1]/4*16+n[2]/4*16*16);
        return suit;
    }
}
