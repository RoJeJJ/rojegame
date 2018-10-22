package com.roje.game.niuniu.manager;

import com.google.protobuf.InvalidProtocolBufferException;
import com.roje.game.core.config.GameProperties;
import com.roje.game.core.exception.ErrorData;
import com.roje.game.core.exception.RJException;
import com.roje.game.core.manager.room.RoomManager;
import com.roje.game.message.frame.Frame;
import com.roje.game.message.nn.NiuNiuCardRoomConfig;
import com.roje.game.niuniu.data.NNRole;
import com.roje.game.niuniu.data.NNRoom;

public class NNRoomManager extends RoomManager<NNRoom, NNRole> {

    public NNRoomManager(GameProperties properties) {
        super(properties);
    }

    @Override
    protected NNRoom createRoom(NNRole role, Frame frame) throws RJException {
        NiuNiuCardRoomConfig config;
        try {
            config = frame.getData().unpack(NiuNiuCardRoomConfig.class);
        } catch (InvalidProtocolBufferException e) {
            throw new RJException(ErrorData.CREATE_ROOM_CONFIG_ERROR);
        }
        checkConfig(config);
        return null;
    }

    private void checkConfig(NiuNiuCardRoomConfig config) {
        if (config.getMode())
    }
}
