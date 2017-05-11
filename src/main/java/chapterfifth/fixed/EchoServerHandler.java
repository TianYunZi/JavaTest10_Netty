package chapterfifth.fixed;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Admin on 2017/5/9.
 */
public class EchoServerHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("收到客户端消息：" + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();//异常退出，释放资源
    }
}
