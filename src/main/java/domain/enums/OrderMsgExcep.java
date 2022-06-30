package domain.enums;

public enum OrderMsgExcep {
    NOTFOUND("Order NOT FOUND");

    private String msg;

    private OrderMsgExcep(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
