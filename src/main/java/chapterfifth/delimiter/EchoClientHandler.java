package chapterfifth.delimiter;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.logging.Logger;

/**
 * Created by Admin on 2017/5/8.
 */
public class EchoClientHandler extends SimpleChannelInboundHandler {

    private static final Logger LOGGER = Logger.getLogger(EchoClientHandler.class.getName());

    private int counter;

    private static final String ECHO_REQ = "Hi, Lilinfeng. Welcome to Netty.$_";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOGGER.info("这是第" + ++counter + "次；接收服务器：[" + msg + "]");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 100; i++) {
            ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
