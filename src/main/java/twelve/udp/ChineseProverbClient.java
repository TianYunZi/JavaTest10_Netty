package twelve.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * Created by Admin on 2017/5/27.
 */
public class ChineseProverbClient {

    public static void run(int port) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ChineseProverbClientHandler());
            Channel channel = bootstrap.bind(0).sync().channel();
            // 向网段内的所有机器广播UDP消息
            channel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("谚语字典查询?", CharsetUtil.UTF_8), new
                    InetSocketAddress("255.255.255.255", port))).sync();
            if (!channel.closeFuture().await(15000)) {
                System.out.println("查询超时!");
            }
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        try {
            run(9999);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
