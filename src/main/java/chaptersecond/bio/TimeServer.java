package chaptersecond.bio;

import utils.ExecutorServiceSingleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;

/**
 * Created by XJX on 2017/3/17.
 */
public class TimeServer {
    public static void main(String[] args) {
        int port = 9999;
        ExecutorService executorService = ExecutorServiceSingleton.getInstance();
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                //采用默认值
            }
        }
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("The time server is start in port : " + port);
            Socket socket = null;
            while (true) {
                socket = serverSocket.accept();
                final Socket finalSocket = socket;
                executorService.execute(() -> {
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
            executorService.shutdown();
            if (serverSocket != null) {
                System.out.println("The time server close");
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
