package com.roje.game.golden.data;


import com.roje.game.core.entity.role.impl.CardRole;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

public class GFCardRole extends CardRole {

    private List<Integer> handCard;

    private int score;

    private boolean turnBet;//每轮是否已经下注

    private boolean show;

    public GFCardRole(long id,
                      String account,
                      String nickname,
                      String avatar,
                      long card,
                      long gold) {
        super(id, account, nickname, avatar, card, gold);
    }

    @Override
    protected void initStart0() {
        handCard.clear();
        turnBet = false;
        show = false;
    }

    public void addPoker(int card) {
        handCard.add(card);
    }

    public boolean isTurnBet() {
        return turnBet;
    }

    public void setTurnBet(boolean turnBet) {
        this.turnBet = turnBet;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public void deductScore(int score) {
        this.score -= score;
    }
}
