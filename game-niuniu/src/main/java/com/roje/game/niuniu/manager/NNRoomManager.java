package com.roje.game.niuniu.manager;

import com.google.protobuf.InvalidProtocolBufferException;
import com.roje.game.core.config.RoomProperties;
import com.roje.game.core.exception.ErrorData;
import com.roje.game.core.exception.RJException;
import com.roje.game.core.manager.room.RoomManager;
import com.roje.game.core.redis.service.RoomRedisService;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.message.create_room.CreateCardRoomRequest;
import com.roje.game.message.nn.NiuNiuCardRoomConfig;
import com.roje.game.niuniu.data.NNRole;
import com.roje.game.niuniu.data.NNRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NNRoomManager extends RoomManager<NNRole, NNRoom> {


    public NNRoomManager(RoomProperties properties,
                         RoomRedisService roomRedisService,
                         ServerInfo serverInfo) {
        super(properties, roomRedisService, serverInfo, service);
    }

    @Override
    public NNRoom createRoom0(CreateCardRoomRequest request) throws RJException {
        NiuNiuCardRoomConfig config;
        try {
            config = request.getConfig().unpack(NiuNiuCardRoomConfig.class);
        } catch (InvalidProtocolBufferException e) {
            log.warn("解析牛牛房间配置异常",e);
            throw new RJException(ErrorData.CREATE_ROOM_CONFIG_ERROR);
        }
        return null;
    }
}
