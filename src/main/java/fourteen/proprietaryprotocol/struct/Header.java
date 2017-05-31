package fourteen.proprietaryprotocol.struct;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 2017/5/31.
 * 消息头定义
 */
public final class Header {

    /**
     * Netty 消息的校验码，它由三部分组成：
     * 1）0xABEF：固定值，表明该消息是 Netty 协议消息，2 个字节；
     * 2）主版本号：1～255，1 个字节；
     * 3）次版本号：1～255，1 个字节。
     * crcCode = 0x.
     */
    private int crcCode = 0xabef0101;

    /**
     * 消息长度，整个消息，包括消息头和消息体.
     */
    private int length;

    /**
     * 集群节点内全局唯一，由会话 ID 生成器生成.
     */
    private long sessionId;

    /**
     * 0：业务请求消息；
     * 1：业务响应消息；
     * 2：业务 ONE WAY 消息（既是请求又是响应消息）；
     * 3：握手请求消息；
     * 4：握手应答消息；
     * 5：心跳请求消息；
     * 6：心跳应答消息.
     */
    private byte type;

    /**
     * 消息优先级：0～255.
     */
    private byte priority;

    /**
     * 附件
     * 可选字段，用于扩展消息头.
     */
    private Map<String, Object> attachment = new HashMap<>();

    public final int getCrcCode() {
        return crcCode;
    }

    public final void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public final int getLength() {
        return length;
    }

    public final void setLength(int length) {
        this.length = length;
    }

    public final long getSessionId() {
        return sessionId;
    }

    public final void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public final byte getType() {
        return type;
    }

    public final void setType(byte type) {
        this.type = type;
    }

    public final byte getPriority() {
        return priority;
    }

    public final void setPriority(byte priority) {
        this.priority = priority;
    }

    public final Map<String, Object> getAttachment() {
        return attachment;
    }

    public final void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "Header{" +
                "crcCode=" + crcCode +
                ", length=" + length +
                ", sessionId=" + sessionId +
                ", type=" + type +
                ", priority=" + priority +
                ", attachment=" + attachment +
                '}';
    }
}
