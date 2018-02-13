package krescent.com.dfwink.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sys-roid on 30/11/17.
 */

public class GetOrderCustomStatus {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("msg_details")
    @Expose
    private String msgDetails;
    @SerializedName("used_status")
    @Expose
    private List<String> usedStatus = null;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgDetails() {
        return msgDetails;
    }

    public void setMsgDetails(String msgDetails) {
        this.msgDetails = msgDetails;
    }

    public List<String> getUsedStatus() {
        return usedStatus;
    }

    public void setUsedStatus(List<String> usedStatus) {
        this.usedStatus = usedStatus;
    }
}
