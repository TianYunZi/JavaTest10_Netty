package chaptersixth.serializable;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Created by Admin on 2017/5/9.
 */
public class UserInfo implements Serializable {
    private static final long serialVersionId = 1L;

    private String userName;
    private Integer userId;

    public UserInfo() {
    }

    public UserInfo buildUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserInfo buildUserID(Integer userID) {
        this.userId = userID;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public byte[] codec() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] bytes = this.userName.getBytes();
        buffer.putInt(bytes.length);
        buffer.put(bytes);
        buffer.putInt(this.userId);
        buffer.flip();
        bytes = null;
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }

    public byte[] codec(ByteBuffer buffer) {
        buffer.clear();
        byte[] bytes = this.userName.getBytes();
        buffer.putInt(bytes.length);
        buffer.put(bytes);
        buffer.putInt(this.userId);
        buffer.flip();
        bytes = null;
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }
}
