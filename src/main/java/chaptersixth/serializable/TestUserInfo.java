package chaptersixth.serializable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by Admin on 2017/5/9.
 */
public class TestUserInfo {

    public static void main(String[] args) throws IOException {
        UserInfo info = new UserInfo();
        info.buildUserID(100).buildUserName("Welcome to Netty");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(info);
        objectOutputStream.flush();
        objectOutputStream.close();
        byte[] bytes = byteArrayOutputStream.toByteArray();
        System.out.println("JDK序列化字节码长度：" + bytes.length);
        System.out.println("-------------------------------------");
        System.out.println("字节数组序列化长度：" + info.codec().length);
    }
}
