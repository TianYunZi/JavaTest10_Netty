package chapterthird.packet;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Created by Admin on 2017/5/8.
 * TCP粘包拆包，客户端
 */
public class TimeClient {

    private void connect(int port, String host) throws InterruptedException {
        // 配置客户端NIO线程组
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loopGroup).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new TimeClientHandler());
                }
            });

            //发起异步连接
            ChannelFuture future = bootstrap.connect(host, port).sync();
            // 当代客户端链路关闭
            future.channel().closeFuture().sync();
        } finally {
            //释放资源
            loopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        try {
            new TimeClient().connect(9999, "127.0.0.1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
