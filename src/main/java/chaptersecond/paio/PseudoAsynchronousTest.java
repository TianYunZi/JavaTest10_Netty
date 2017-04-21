package chaptersecond.paio;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Created by XJX on 2017/4/20.
 * <p>
 * 伪异步编程
 */
public class PseudoAsynchronousTest {

    @Test
    public void server() {
        int port = 9999;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("The time server is start in port : " + port);
            Socket socket = null;
            TimeServerHandlerExecutePool executePool = new TimeServerHandlerExecutePool(50, 10000);
            while (true) {
                socket = serverSocket.accept();
                final Socket finalSocket = socket;
                executePool.execute(() -> {
                    BufferedReader in = null;
                    PrintWriter out = null;
                    try {
                        in = new BufferedReader(new InputStreamReader(finalSocket.getInputStream()));
                        out = new PrintWriter(finalSocket.getOutputStream(), true);
                        String currentTime = null;
                        String body = null;
                        while (true) {
                            body = in.readLine();
                            if (body == null) {
                                break;
                            }
                            System.out.println("The time server receive order : " + body);
                            currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()
                            ).toString() : "BAD ORDER";
                            out.println(currentTime);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (out != null) {
                            out.close();
                            out = null;
                        }
                        if (finalSocket != null) {
                            try {
                                finalSocket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
