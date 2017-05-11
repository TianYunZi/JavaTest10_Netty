package chaptersixth.serializable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.time.Instant;

/**
 * Created by Admin on 2017/5/10.
 */
public class PerformTestUserInfo {

    public static void main(String[] args) throws IOException {
        UserInfo userInfo = new UserInfo();
        userInfo.buildUserID(100).buildUserName("欢迎来到Netty !");
        Long loop = 1000000L;
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        Instant startTime = Instant.now();
        for (int i = 0; i < loop; i++) {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(userInfo);
            objectOutputStream.flush();
            objectOutputStream.close();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
        }
        Instant endTime = Instant.now();
        System.out.println("JDK序列化花费时间：" + Duration.between(startTime, endTime).toMillis());
        System.out.println("----------------------------------------------------------------------------------");
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        startTime = Instant.now();
        for (int i = 0; i < loop; i++) {
            byte[] bytes = userInfo.codec(buffer);
        }
        endTime = Instant.now();
        System.out.println("二进制序列化花费时间：" + Duration.between(startTime, endTime).toMillis());
    }
}
