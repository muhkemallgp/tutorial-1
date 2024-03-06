package enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    WAITING_PAYMENT("WAITING_PAYMENT"),
    FAILED("FAILED"),
    SUCCESS("SUCCESS"),
    CANCELED("CANCELLED");;

    private final String value;

    private OrderStatus(String value) {
        this.value = value;
    }

    public static boolean contains (String param) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.getValue().equals(param)) {
                return true;
            }
        }
        return false;
    }
}