
package model;

public class Movimentacao {
    private String nomeprod;
    private String datamov;
    private double qtdmov;
    private String tipomov;

    public Movimentacao() {
    }

    public Movimentacao(String nomeprod, String datamov, double qtdmov, String tipomov) {
        this.nomeprod = nomeprod;
        this.datamov = datamov;
        this.qtdmov = qtdmov;
        this.tipomov = tipomov;
    }

    public String getNomeprod() {
        return nomeprod;
    }

    public void setNomeprod(String nomeprod) {
        this.nomeprod = nomeprod;
    }

    public String getDatamov() {
        return datamov;
    }

    public void setDatamov(String datamov) {
        this.datamov = datamov;
    }

    public double getQtdmov() {
        return qtdmov;
    }

    public void setQtdmov(double qtdmov) {
        this.qtdmov = qtdmov;
    }

    public String getTipomov() {
        return tipomov;
    }

    public void setTipomov(String tipomov) {
        this.tipomov = tipomov;
    }
    
}
