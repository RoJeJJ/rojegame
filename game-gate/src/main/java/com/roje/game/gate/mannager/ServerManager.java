package com.roje.game.gate.mannager;

import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.server.ServerState;
import com.roje.game.core.server.ServerType;
import com.roje.game.gate.session.UserSession;
import com.roje.game.message.common.CommonMessage;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


public class ServerManager {
    /**
     * key 服务器id
     */
    private Map<Integer,ServerInfo> serverMap = new ConcurrentHashMap<>();
    public CommonMessage.ErrorResponse errorResponse(CommonMessage.SystemErroCode code,String msg){
        CommonMessage.ErrorResponse.Builder builder = CommonMessage.ErrorResponse.newBuilder();
        builder.setErrorCode(code);
        if (StringUtils.isEmpty(msg))
            msg = "UnKnown";
        builder.setMsg(msg);
        return builder.build();
    }

    /**
     * 根据服务器类型获取空闲服务器信息
     * @param type 服务器类型
     * @param session {@link UserSession}
     * @return 空闲服务器信息
     */
    public ServerInfo getIdleServer(ServerType type, UserSession session){
        Optional<ServerInfo> optional = serverMap.values().stream().filter(info -> info.getState() == ServerState.NORMAL.getState()
                && info.getType() == type.getType()
                && info.getClientVersionCode() == session.getVersionCode())
                .min(Comparator.comparingInt(ServerInfo::getOnline));
        return optional.orElse(null);
    }
    public ServerInfo getServer(int id){
        return serverMap.get(id);
    }
}
