package domain.enums;

public enum CustomerMsgExcep {
    NOTFOUND("Customer NOT FOUND"), 
    ALREADYEXIST("Customer ALREADY EXIST"), 
    ENABLED(" Customer NOT ENABLED");

    private String msg;

    private CustomerMsgExcep(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }  
}
