package domain.enums;

public enum PaymentMsgExcep {
    NOTFOUND("Payment NOT FOUND"); 

    private String msg;

    private PaymentMsgExcep(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }  
}
