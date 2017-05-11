package chapterseventh.serializable;

import chapterseventh.pojo.SubscribeReq;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.logging.Logger;

/**
 * Created by Admin on 2017/5/10.
 */
public class SubReqClientHandler extends SimpleChannelInboundHandler {

    private static final Logger LOGGER = Logger.getLogger(SubReqClientHandler.class.getName());

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOGGER.info("收到服务器端响应：[" + msg + "]");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (long i = 0; i < 1000000L; i++) {
            ctx.writeAndFlush(this.req(i));
        }
    }

    private SubscribeReq req(Long i) {
        SubscribeReq req = new SubscribeReq();
        req.setAddress("南京市雨花台区软件大道101号华为基地");
        req.setPhoneNumber("138xxxxxxxxx");
        req.setProductNumber("Netty 最佳实践和原理分析");
        req.setPassword("123456".toCharArray());
        req.setReqId(i);
        req.setUsername("Lilinfeng");
        return req;
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
