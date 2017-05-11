package chaptereighth.marshalling;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by Admin on 2017/5/10.
 * Netty使用Marshalling对POJO序列化
 */
public class SubReqServer {

    private void bind(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 100).handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                    ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                    ch.pipeline().addLast(new SubReqServerHandler());
                }
            });

            //绑定端口，同步等待成功
            ChannelFuture future = bootstrap.bind(port).sync();
            //等待服务端监听端口关闭
            future.channel().closeFuture().sync();
        } finally {
            //释放资源
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        try {
            new SubReqServer().bind(9999);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
