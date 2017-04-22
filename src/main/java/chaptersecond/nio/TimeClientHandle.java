package chaptersecond.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by XJX on 2017/4/22.
 */
public class TimeClientHandle implements Runnable {

    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private AtomicBoolean stop = new AtomicBoolean(false);

    public TimeClientHandle(String host, int port) {
        this.host = host == null ? "127.0.0.1" : host;
        this.port = port;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see Runnable#run()
     */
    @Override
    public void run() {
        try {
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!this.stop.get()) {
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                selectionKeys.forEach(e -> {
                    try {
                        handlerInput(e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        if (e != null) {
                            e.cancel();
                            if (e.channel() != null) {
                                try {
                                    e.channel().close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            }
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
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
    }

    private void handlerInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            //判断是否连接成功
            if (key.isConnectable()) {
                if (socketChannel.finishConnect()) {
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    doWrite(socketChannel);
                }
            }

            if (key.isReadable()) {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                if (socketChannel.read(buffer) > 0) {
                    buffer.flip();
                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                    String body = new String(bytes, "utf-8");
                    System.out.println("Now is" + body);
                    this.stop.set(true);
                } else if (socketChannel.read(buffer) < 0) {
                    //对端链路关闭
                    key.cancel();
                    socketChannel.close();
                }
            }
        }
    }

    private void doConnect() throws IOException {
        // 如果直接连接成功，则注册到多路复用器上，发送请求消息，读应答
        if (socketChannel.connect(new InetSocketAddress(host, port))) {
            socketChannel.register(selector, SelectionKey.OP_READ);
            doWrite(socketChannel);
        } else {
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }

    private void doWrite(SocketChannel socketChannel) throws IOException {
        byte[] bytes = "查询时间命令".getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        socketChannel.write(buffer);
        if (!buffer.hasRemaining()) {
            System.out.println("发送服务器成功");
        }
    }
}
