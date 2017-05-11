package chapterfifth.delimiter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Admin on 2017/5/8.
 */
public class EchoServerHandler extends SimpleChannelInboundHandler {

    private int counter = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("总计数" + ++counter + "次；收到客户端：[" + body + "]");
        String delimiter = "$_";
        StringBuffer buffer = new StringBuffer();
        buffer.append(body).append(delimiter);
        ByteBuf echo = Unpooled.copiedBuffer(buffer.toString().getBytes());
        ctx.writeAndFlush(echo);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();//释放资源
    }
}
