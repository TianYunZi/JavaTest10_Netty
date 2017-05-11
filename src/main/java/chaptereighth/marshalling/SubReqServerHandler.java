package chaptereighth.marshalling;

import chaptereighth.pojo.SubscribeReq;
import chapterseventh.pojo.SubscribeResp;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Admin on 2017/5/10.
 */
public class SubReqServerHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReq req = (SubscribeReq) msg;
        if ("Lilinfeng".equalsIgnoreCase(req.getUsername())) {
            System.out.println("服务端收到客户端请求：[" + req.toString() + "]");
            ctx.writeAndFlush(this.resp(req.getReqId()));
        }

    }

    private SubscribeResp resp(Long respId) {
        SubscribeResp resp = new SubscribeResp();
        resp.setSubReqID(respId);
        resp.setRespCode(0);
        resp.setDesc("Netty书籍订购成功，三天后发到指定地址");
        return resp;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
