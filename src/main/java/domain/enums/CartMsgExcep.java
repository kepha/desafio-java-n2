package domain.enums;


public enum CartMsgExcep {
    NOTFOUND("Cart NOT FOUND"), 
    ALREADYEXIST("There is already an active cart for this customer");

    private String msg;

    private CartMsgExcep(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }  
}
