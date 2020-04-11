package utility;

public class OrderForm {
    private String deliveryAddress;
    private Integer deliveryYear;
    private Integer deliveryMonth;
    private Integer deliveryDay;

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public Integer getDeliveryYear() {
        return deliveryYear;
    }

    public Integer getDeliveryMonth() {
        return deliveryMonth;
    }

    public Integer getDeliveryDay() {
        return deliveryDay;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setDeliveryYear(Integer deliveryYear) {
        this.deliveryYear = deliveryYear;
    }

    public void setDeliveryMonth(Integer deliveryMonth) {
        this.deliveryMonth = deliveryMonth;
    }

    public void setDeliveryDay(Integer deliveryDay) {
        this.deliveryDay = deliveryDay;
    }
}
