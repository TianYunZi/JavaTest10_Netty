package chaptersecond.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by XJX on 2017/4/22.
 */
public class MultiplexerTimeServer implements Callable {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private AtomicBoolean stop = new AtomicBoolean(false);

    public MultiplexerTimeServer(int port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The time server is start in port : " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        this.stop = new AtomicBoolean(true);
    }

    @Override
    public Object call() {
        while (!stop.get()) {
            try {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectedKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null)
                                key.channel().close();
                        }
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        // 多路复用器关闭后，所有注册在上面的Channel和Pipe等资源都会被自动去注册并关闭，所以不需要重复释放资源
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            //处理接入新消息的请求
            if (key.isAcceptable()) {
                //接入新的连接
                ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                SocketChannel socketChannel = serverChannel.accept();
                socketChannel.configureBlocking(false);
                //在选择器注册新连接
                socketChannel.register(selector, SelectionKey.OP_READ);
            }

            //读数据
            if (key.isReadable()) {
                SocketChannel socketChannel = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                if (socketChannel.read(buffer) > 0) {
                    buffer.flip();
                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                    String body = new String(bytes, "utf-8");
                    System.out.println("The time server receive order : " + body);
                    String currentTime = "读取时间：" + LocalDateTime.now(ZoneId.of("Asia/Shanghai")).format(DateTimeFormatter
                            .ofPattern("yyyy-MM-dd " +
                                    "HH:mm:ss"));
                    doWrite(socketChannel, currentTime);
                } else if (socketChannel.read(buffer) < 0) {
                    key.cancel();
                    socketChannel.close();
                }
            }
        }
    }

    private void doWrite(SocketChannel channel, String response) throws IOException {
        if (response != null && response.length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            buffer.put(bytes);
            buffer.flip();
            channel.write(buffer);
        }
    }
}
