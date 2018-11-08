package com.roje.game.golden.manager;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.roje.game.core.config.RoomProperties;
import com.roje.game.core.manager.room.impl.AbsCardRoomManager;
import com.roje.game.core.redis.service.RoomRedisService;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.service.Service;
import com.roje.game.golden.data.GFCardRoom;
import com.roje.game.golden.data.GFRole;
import com.roje.game.message.nn.GFCardRoomConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GFCardRoomManager extends AbsCardRoomManager<GFRole, GFCardRoom> {

    public GFCardRoomManager(RoomProperties properties,
                             RoomRedisService roomRedisService,
                             ServerInfo serverInfo,
                             Service service) {
        super(properties, roomRedisService, serverInfo, service);
    }

    @Override
    public GFCardRoom createRoom0(long id, GFRole role, int roomMaxRoles, Any any) {
        try {
            GFCardRoomConfig config = any.unpack(GFCardRoomConfig.class);
            return new GFCardRoom(id,role,roomMaxRoles,GFRole.class,config);
        }catch (InvalidProtocolBufferException e){
            log.warn("解析扎金花房卡配置消息异常",e);
            return null;
        }
    }
}
