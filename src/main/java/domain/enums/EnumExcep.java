package domain.enums;

public enum EnumExcep {
    NOTFOUND(404L), BADREQUEST(400L);

    private Long status;

    public Long getStatus() {
        return status;
    }   

    private EnumExcep(Long status) {
        this.status = status;
    }
}
