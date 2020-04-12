package utility;

import entity.PurchaseEntity.OrderStatus;

public class OrderSelectorForm {
    private Long orderId;
    private OrderStatus status;

    public Long getOrderId() {
        return orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
