/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author HP
 */
public class Familia {
    private Integer IDFAM;
    private String NOMFAM;
    private String DESFAM;
    
    public Familia(Integer IDFAM,String NOMFAM,String DESFAM){
        this.IDFAM=IDFAM;
        this.NOMFAM=NOMFAM;
        this.DESFAM=DESFAM;
    }

    public Familia() {
    }

    public Integer getIDFAM() {
        return IDFAM;
    }

    public void setIDFAM(Integer IDFAM) {
        this.IDFAM = IDFAM;
    }

    public String getNOMFAM() {
        return NOMFAM;
    }

    public void setNOMFAM(String NOMFAM) {
        this.NOMFAM = NOMFAM;
    }

    public String getDESFAM() {
        return DESFAM;
    }

    public void setDESFAM(String DESFAM) {
        this.DESFAM = DESFAM;
    }
}
