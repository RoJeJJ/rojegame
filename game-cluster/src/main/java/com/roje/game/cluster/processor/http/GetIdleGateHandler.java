package com.roje.game.cluster.processor.http;

import com.google.gson.JsonObject;
import com.roje.game.cluster.manager.ServerManager;
import com.roje.game.core.processor.HttpProcessor;
import com.roje.game.core.processor.HttpRequestProcessor;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.util.HttpUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@HttpProcessor(path = "/server/gate")
public class GetIdleGateHandler extends HttpRequestProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(GetIdleGateHandler.class);
    private ServerManager serverManager;

    public void setServerManager(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void handler(ChannelHandlerContext ctx, DefaultHttpRequest request) {
        QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
        Map<String,List<String>> uriAttr = decoder.parameters();
        List<String> vl = uriAttr.get("version");
        String version = vl == null || vl.size() == 0 ?null:vl.get(0);
        int ver = StringUtils.isNumeric(version)?Integer.parseInt(version):0;
        ServerInfo serverInfo = serverManager.getIdleGateServer(ver);
        if (serverInfo == null){
            JsonObject object = new JsonObject();
            object.addProperty("code",101);
            object.addProperty("msg","没有可用的网关服务器");
            HttpUtils.send(ctx,HttpResponseStatus.OK,object.toString());
        }else {
            JsonObject object = new JsonObject();
            object.addProperty("code",0);
            JsonObject data = new JsonObject();
            data.addProperty("ip",serverInfo.getIp());
            data.addProperty("port",serverInfo.getPort());
            object.add("data",data);
            HttpUtils.send(ctx,HttpResponseStatus.OK,object.toString());
        }
    }
}
