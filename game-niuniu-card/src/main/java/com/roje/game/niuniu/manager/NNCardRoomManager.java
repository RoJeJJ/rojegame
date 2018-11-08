package com.roje.game.niuniu.manager;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.roje.game.core.config.RoomProperties;
import com.roje.game.core.manager.room.impl.AbsCardRoomManager;
import com.roje.game.core.redis.service.RoomRedisService;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.service.Service;
import com.roje.game.message.nn.NNCardRoomConfig;
import com.roje.game.niuniu.data.NNCardRoom;
import com.roje.game.niuniu.data.NNRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NNCardRoomManager extends AbsCardRoomManager<NNRole, NNCardRoom> {


    public NNCardRoomManager(RoomProperties properties,
                             RoomRedisService roomRedisService,
                             ServerInfo serverInfo,
                             Service service) {
        super(properties, roomRedisService, serverInfo, service);
    }

    @Override
    public NNCardRoom createRoom0(long id, NNRole role, int roomMaxRoles, Any any) {
        try {
            NNCardRoomConfig config = any.unpack(NNCardRoomConfig.class);
            return new NNCardRoom(id,role,roomMaxRoles,NNRole.class,config);
        } catch (InvalidProtocolBufferException e) {
            log.warn("解析牛牛房间配置异常",e);
            return null;
        }
    }
}
