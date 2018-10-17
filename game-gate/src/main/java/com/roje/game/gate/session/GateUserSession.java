package com.roje.game.gate.session;

import com.google.protobuf.Any;
import com.roje.game.core.session.Session;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.error.ErrorCode;
import com.roje.game.message.frame.Frame;
import com.roje.game.message.login.InnerLoginRequest;
import com.roje.game.message.login.LoginRequest;
import com.roje.game.message.login.LoginResponse;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class GateUserSession extends Session {
    enum LoginStatus {
        UnLogin, Logining, logined
    }

    @Getter
    @Setter
    private Channel hallChannel;
    @Getter
    @Setter
    private Channel gameChannel;

    private LoginStatus loginStatus;

    public GateUserSession(Channel channel) {
        super(channel);
    }

    @Override
    public void onOpen() {
        loginStatus = LoginStatus.UnLogin;
    }

    @Override
    public void onClosed() {
        super.onClosed();
    }

    public void sendToHall(Frame frame) {
        sendTo(hallChannel, frame);
    }

    private void sendTo(Channel channel, Frame frame) {
        if (uid == 0) {
            log.warn("还没有登录");
            MessageUtil.sendError(this.channel, ErrorCode.NotLoginOn,"还没有登录");
        } else
            MessageUtil.send(channel, uid, frame);
    }

    public void sendToGame(Frame frame) {
        sendTo(gameChannel, frame);
    }


    public synchronized void loginRequest(Frame frame, int gateId) throws Exception {
        switch (loginStatus) {
            case Logining:
                MessageUtil.sendError(channel, ErrorCode.RepeatedReq,"重复的登录请求");
                return;
            case logined:
                MessageUtil.sendError(channel, ErrorCode.AlreadyLogged,"已经登录了");
                return;
        }
        loginStatus = LoginStatus.Logining;
        Frame.Builder frameBuilder = frame.toBuilder();
        InnerLoginRequest.Builder builder = InnerLoginRequest.newBuilder();
        builder.setSessionId(id);
        builder.setGateId(gateId);
        builder.setRequest(frame.getData().unpack(LoginRequest.class));
        builder.setIp(((InetSocketAddress) channel.remoteAddress()).getAddress().getHostAddress());
        frameBuilder.setData(Any.pack(builder.build()));

        MessageUtil.send(hallChannel,frameBuilder.build());
    }

    public synchronized void loginResponse(Frame frame, LoginResponse response) {
        if (!response.getSuccess()){
            loginStatus = LoginStatus.UnLogin;
        }else {
            long uid = response.getUserInfo().getId();
            log.info("{}登录成功",uid);
            this.uid = uid;
        }
        Frame.Builder builder = frame.toBuilder();
        builder.setData(Any.pack(response));
        send(builder.build());
    }
}
