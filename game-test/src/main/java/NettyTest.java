import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Timer;
import java.util.TimerTask;

public class NettyTest implements Runnable{
    private EventLoopGroup boss;
    private EventLoopGroup worker;
    private Channel channel;
    public void run(){
        try {
            boss = new NioEventLoopGroup();
            worker = new NioEventLoopGroup();
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 2048)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.SO_LINGER, 0);
            ChannelFuture channelFuture = serverBootstrap.bind(8000).sync();
            System.out.println("TCP服务器已启动,监听端口:" + 8000);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("stop");
                    channel.close();
                }
            },5000);
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
        System.out.println("退出");
        if (boss != null && !boss.isShutdown() && !boss.isShuttingDown() ) {
            boss.shutdownGracefully();
        }
        if (worker != null && !worker.isShutdown() && !worker.isShuttingDown()) {
            worker.shutdownGracefully();
        }
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
}
