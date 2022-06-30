package domain.enums;

public enum ProductMsgExcep {
    NOTFOUND("Product NOT FOUND");

    private String msg;

    private ProductMsgExcep(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }  
}
