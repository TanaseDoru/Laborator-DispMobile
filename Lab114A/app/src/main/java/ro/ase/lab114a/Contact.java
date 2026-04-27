package ro.ase.lab114a;

public class Contact {

    private String idc;
    private String numec;
    private String telefonc;

    public String getIdc() {
        return idc;
    }

    public void setIdc(String idc) {
        this.idc = idc;
    }

    public String getNumec() {
        return numec;
    }

    public void setNumec(String numec) {
        this.numec = numec;
    }

    public String getTelefonc() {
        return telefonc;
    }

    public void setTelefonc(String telefonc) {
        this.telefonc = telefonc;
    }

    public Contact(String idc, String numec, String telefonc) {
        this.idc = idc;
        this.numec = numec;
        this.telefonc = telefonc;
    }
}
