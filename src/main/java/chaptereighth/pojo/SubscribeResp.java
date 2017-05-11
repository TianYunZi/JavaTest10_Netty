package chaptereighth.pojo;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/10.
 */
public class SubscribeResp implements Serializable {

    /**
     * 默认序列ID
     */
    private static final Long serialVersionUID = 1L;

    private Long subReqID;

    private int respCode;

    private String desc;

    public Long getSubReqID() {
        return subReqID;
    }

    public void setSubReqID(Long subReqID) {
        this.subReqID = subReqID;
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "SubscribeResp{" +
                "subReqID=" + subReqID +
                ", respCode=" + respCode +
                ", desc='" + desc + '\'' +
                '}';
    }
}
