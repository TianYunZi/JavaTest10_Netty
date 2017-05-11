package chaptereighth.pojo;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Admin on 2017/5/10.
 */
public class SubscribeReq implements Serializable {

    private static final Long serialVersionUid = 1L;

    private Long reqId;
    private String username;
    private char[] password;
    private String productNumber;
    private String phoneNumber;
    private String address;

    public Long getReqId() {
        return reqId;
    }

    public void setReqId(Long reqId) {
        this.reqId = reqId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SubscribeReq{" +
                "reqId=" + reqId +
                ", username='" + username + '\'' +
                ", password=" + Arrays.toString(password) +
                ", productNumber='" + productNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
