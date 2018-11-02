import com.google.protobuf.Any;
import com.roje.game.core.netty.channel.initializer.DefaultChannelInitializer;
import com.roje.game.message.action.Action;
import com.roje.game.message.error.ErrorMessage;
import com.roje.game.message.frame.Frame;
import com.roje.game.message.login.LoginRequest;
import com.roje.game.message.login.LoginResponse;
import com.roje.game.message.login.LoginType;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyTest implements Runnable{
    private Channel channel;
    public void run(){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress("127.0.0.1",8088)
                    .handler(new DefaultChannelInitializer(){
                        @Override
                        public void custom(ChannelPipeline pipeline) throws Exception {
                            pipeline.addLast(new TestHandler());
                        }
                    })
                    .option(ChannelOption.TCP_NODELAY,true)
                    .option(ChannelOption.SO_LINGER, 0);
            ChannelFuture channelFuture = bootstrap.connect().sync();
            channel = channelFuture.channel();
            channelFuture.channel().closeFuture().sync();
            System.out.println("TCP服务器已停止");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            stop();
        }
    }
    public void stop(){
       if (channel != null && channel.isActive())
           channel.close();
    }
    public void start(){
        Thread thread = new Thread(this);
        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NettyTest test = new NettyTest();
        test.start();
    }

    public class TestHandler extends SimpleChannelInboundHandler<Frame>{
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Frame frame) throws Exception{
            switch (frame.getAction()){
                case LoginRes:
                    LoginResponse response = frame.getData().unpack(LoginResponse.class);
                    if (response.getSuccess()){
                        log.info("登录成功");
                    }else {
                        log.info("登录失败");
                        log.info(response.getMsg());
                    }
                    break;
                case PubErrorMessage:
                    ErrorMessage errorMessage = frame.getData().unpack(ErrorMessage.class);
                    log.info("错误消息:{},{}",errorMessage.getErrCode().name(),errorMessage.getErrMsg());
            }
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx){
//            for (int i=0;i<10;i++){
                LoginRequest.Builder builder = LoginRequest.newBuilder();
                builder.setAccount("wuli");
                builder.setGameToken("123456");
                builder.setLoginType(LoginType.WeChat);
                builder.setVersion(1);
                Frame.Builder fb = Frame.newBuilder();
                fb.setAction(Action.LoginReq);
                fb.setData(Any.pack(builder.build()));
                ctx.writeAndFlush(fb);
//            }
        }
    }
}
