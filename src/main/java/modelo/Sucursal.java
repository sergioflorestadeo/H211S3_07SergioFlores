/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author HP
 */
public class Sucursal {
    private Integer IDSUR;
    private String NOMSUR;
    private Ubigeo ubigeo= new Ubigeo();
    
    
    public Sucursal(Integer IDSUR,String NOMSUR,Ubigeo ubigeo){
        this.IDSUR=IDSUR;
        this.NOMSUR=NOMSUR;
        this.ubigeo=ubigeo;
    }

    public Sucursal() {
    }

    public Integer getIDSUR() {
        return IDSUR;
    }

    public void setIDSUR(Integer IDSUR) {
        this.IDSUR = IDSUR;
    }

    public String getNOMSUR() {
        return NOMSUR;
    }

    public void setNOMSUR(String NOMSUR) {
        this.NOMSUR = NOMSUR;
    }

    public Ubigeo getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(Ubigeo ubigeo) {
        this.ubigeo = ubigeo;
    }

}
