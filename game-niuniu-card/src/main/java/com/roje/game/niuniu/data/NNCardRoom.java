package com.roje.game.niuniu.data;

import com.google.protobuf.Message;
import com.roje.game.core.entity.room.impl.AbsCardRoom;
import com.roje.game.core.exception.ErrorData;
import com.roje.game.core.exception.RJException;
import com.roje.game.message.nn.NNCardRoomConfig;

public class NNCardRoom extends AbsCardRoom<NNRole> {

    private final int mode;

    private final int baseScore;

    private final int startMode;

    private final int maxTui;

    private final int maxQiang;

    private final int doublingMode;

    private final boolean szn;

    private final boolean whn;

    private final boolean thn;

    private final boolean hln;

    private final boolean zdn;

    private final boolean wxn;

    private final boolean ths;

    private final boolean fb_enterHalfWay;

    private final boolean fb_rub;

    private final boolean ma;

    private final boolean bet_doubling;

    private final boolean bet_limit;

    private final boolean joker;

    public NNCardRoom(long id, NNRole creator, int roomMaxRoles, Class<NNRole> roleType,NNCardRoomConfig config) {
        super(id, creator, config.getRound(), config.getPayment(), config.getPerson(),roomMaxRoles, roleType);
        mode = config.getMode();
        baseScore = config.getBaseScore();
        this.startMode = config.getStartMode();
        this.maxTui = config.getMaxTui();
        this.maxQiang = config.getMaxQiang();
        this.doublingMode = config.getDoublingMode();
        this.szn = config.getSzn();
        this.whn = config.getWhn();
        thn = config.getThn();
        hln = config.getHln();
        zdn = config.getZdn();
        wxn = config.getWxn();
        ths = config.getThs();
        fb_enterHalfWay = config.getFbEnterHalfWay();
        fb_rub = config.getFbRub();
        ma = config.getMa();
        bet_doubling = config.getBetDoubling();
        bet_limit = config.getBetLimit();
        joker = config.getJoker();
    }

    @Override
    protected void onSit(NNRole role, int seat) {

    }

    @Override
    public void enter0(NNRole role) throws RJException {
        if (isRoundStart() && fb_enterHalfWay){
            throw new RJException(ErrorData.ENTER_ROOM_NOT_ALLOW_JOIN_HALLWAY);
        }
    }

    @Override
    public Message roomInfo(NNRole role) {
        return null;
    }

    @Override
    public Message createResponse() {
        return null;
    }
}
