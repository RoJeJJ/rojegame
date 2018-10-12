import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.netty.channel.codec.DefaultMessageCodec;
import com.roje.game.message.Mid;
import com.roje.game.message.login.LoginMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyTest implements Runnable{
    private Channel channel;
    public void run(){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress("127.0.0.1",8088)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new DefaultMessageCodec());
                            pipeline.addLast(new TestHandler() );
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

    public class TestHandler extends SimpleChannelInboundHandler<byte[]>{
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, byte[] bytes) throws Exception {
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx){
            LoginMessage.LoginRequest.Builder builder = LoginMessage.LoginRequest.newBuilder();
            builder.setAccount("abc");
            builder.setPassword("123456");
            builder.setLoginType(LoginMessage.LoginType.ACCOUNT);
            builder.setVersion(1);
            builder.setMid(Mid.MID.LoginReq);
            ctx.writeAndFlush(builder.build());
        }
    }
}
