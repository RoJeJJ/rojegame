package com.roje.game.golden.manager;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.roje.game.core.config.RoomProperties;
import com.roje.game.core.entity.role.impl.CardRole;
import com.roje.game.core.entity.room.impl.CardRoom;
import com.roje.game.core.manager.room.impl.CardRoomManager;
import com.roje.game.core.redis.service.RoomRedisService;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.service.Service;
import com.roje.game.golden.data.GFCardRoom;
import com.roje.game.message.nn.GFCardRoomConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GFCardRoomManager extends CardRoomManager {


    public GFCardRoomManager(RoomProperties properties,
                             ServerInfo serverInfo,
                             Service service,
                             RoomRedisService roomRedisService) {
        super(properties, serverInfo, service, roomRedisService);
    }

    @Override
    protected CardRoom createRoom0(long id, CardRole role, int roomMaxRole, Any any) {
        try {
            GFCardRoomConfig config = any.unpack(GFCardRoomConfig.class);
            return new GFCardRoom(id,role,roomMaxRole, config);
        }catch (InvalidProtocolBufferException e){
            log.warn("解析扎金花房卡配置消息异常",e);
            return null;
        }
    }
}
