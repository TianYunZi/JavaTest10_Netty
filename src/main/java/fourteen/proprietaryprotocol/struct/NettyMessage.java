package fourteen.proprietaryprotocol.struct;

/**
 * Created by Admin on 2017/5/31.
 * Netty 消息定义表
 */
public final class NettyMessage {

    /**
     * 消息头定义.
     */
    private Header header;

    /**
     * 对于请求消息，它是方法的参数（作为示例，只支持携带一个参数）；
     * 对于响应消息，它是返回值.
     */
    private Object object;

    public final Header getHeader() {
        return header;
    }

    public final void setHeader(Header header) {
        this.header = header;
    }

    public final Object getObject() {
        return object;
    }

    public final void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "NettyMessage{" +
                "header=" + header +
                ", object=" + object +
                '}';
    }
}
