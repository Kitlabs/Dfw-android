package krescent.com.dfwink.OrderEvent;

import java.util.List;

/**
 * Created by sys-roid on 20/11/17.
 */

public class OrderStatus {

    private String orderStatus;
    private List<String> customStatus;

    public OrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderStatus() {

    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setCustomOrder(List<String> status) {
        customStatus = status;
    }

    public List<String> getCustomOrder() {
        return customStatus;
    }
}
