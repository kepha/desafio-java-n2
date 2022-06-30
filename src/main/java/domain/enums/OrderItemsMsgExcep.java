package domain.enums;

public enum OrderItemsMsgExcep {
    NOTFOUND("OrderItems NOT FOUND");   

    private String msg;

    private OrderItemsMsgExcep(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }  
}
