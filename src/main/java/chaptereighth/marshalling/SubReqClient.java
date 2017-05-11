package chaptereighth.marshalling;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by Admin on 2017/5/10.
 * Netty使用Marshalling对POJO序列化
 */
public class SubReqClient {

    private void connect(String host, int port) throws InterruptedException {
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loopGroup).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                    ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                    ch.pipeline().addLast(new SubReqClientHandler());
                }
            });

            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } finally {
            loopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        try {
            new SubReqClient().connect("127.0.0.1", 9999);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
