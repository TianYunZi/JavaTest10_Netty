package thirteen.file;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * Created by XJX on 2017/5/28.
 */
public class FileServerHandler extends SimpleChannelInboundHandler<String> {

    private static final String CR = System.getProperty("line.separator");//Windows下是/r/n，Linux是/n

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        File file = new File(msg);
        if (file.exists()) {
            ctx.writeAndFlush("not a file: " + file + CR);
            return;
        }
        ctx.write(file + " " + file.length() + CR);
        try (RandomAccessFile accessFile = new RandomAccessFile(msg, "r")) {
            FileRegion region = new DefaultFileRegion(accessFile.getChannel(), 0, accessFile.length());
            ctx.write(region);
            ctx.writeAndFlush(CR);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
