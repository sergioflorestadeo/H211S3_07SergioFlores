package controlador;

import Service.DniS;

import dao.ClienteImpl;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import modelo.Cliente;
import modelo.Ubigeo;
import Service.ReporteS;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;

@Named(value = "ClienteC")
@SessionScoped
public class ClienteC implements Serializable {

    // private static final long serialVersionUID = 1L;
    private Cliente cli;
    private Cliente climodificar;
    private ClienteImpl dao;
    private List<Cliente> listadoClientes;

    public ClienteC() {
        dao = new ClienteImpl();
        listadoClientes = new ArrayList<>();
        cli = new Cliente();
        climodificar = new Cliente();
    }

    public void obtenerCod() {
        System.out.println(cli.getUbigeo().getIdubi());
    }

    public void registrar() throws Exception {
        try {
            
            if (!DniS.buscarDuplicado(listadoClientes, cli.getDni())) {
                dao.registrar(cli);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Registrado con éxito"));
                limpiar();
                listar();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No", "DNI duplicado"));
            }
        } catch (Exception e) {
            System.out.println("Error en registrarC " + e.getMessage());
        }
    }

    public void modificar() throws Exception {
        try {
            dao.modificar(climodificar);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Modificado con éxito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en modificarC " + e.getMessage());
        }
    }

    public void eliminar(Cliente cli) throws Exception {
        try {
            System.out.println(cli.getIdcli());
            dao.eliminar(cli);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "OK", "Eliminado con éxito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en eliminarC " + e.getMessage());
        }
    }

    public void limpiar() {
        cli = new Cliente();
    }

//    @SuppressWarnings("unchecked")
    public void listar() {
        try {
            listadoClientes = dao.listar();
            for (Cliente listadoCliente : listadoClientes) {
                System.out.println(listadoClientes);

            }
        } catch (Exception e) {
            System.out.println("Error en listarC " + e.getMessage());
        }
    }

    @PostConstruct
    public void construir() {
        listar();
        listUbi();
    }

    public static void main(String[] args) {
        ClienteC C = new ClienteC();
        C.listar();
    }
    // métodos generados

    public ClienteImpl getDao() {
        return dao;
    }

    public void setDao(ClienteImpl dao) {
        this.dao = dao;
    }

    public List<Cliente> getListadoCli() {
        return listadoClientes;
    }

    public void setListadoCli(List<Cliente> listaClientes) {
        this.listadoClientes = listadoClientes;
    }
    private List<Ubigeo> LstUbi;
    private String ubi;

    public List<Ubigeo> getLstUbi() {
        return LstUbi;
    }

    public void setLstUbi(List<Ubigeo> LstUbi) {
        this.LstUbi = LstUbi;
    }

    public String getUbi() {
        return ubi;
    }

    public void setUbi(String Ubi) {
        this.ubi = Ubi;
    }

    public void listUbi() {
        try {
            LstUbi = dao.listUbi();

        } catch (Exception e) {
            ;
        }
    }

    public Cliente getCli() {
        return cli;
    }

    public void setCli(Cliente cli) {
        this.cli = cli;
    }

    public Cliente getClimodificar() {
        return climodificar;
    }

    public void setClimodificar(Cliente climodificar) {
        this.climodificar = climodificar;
    }
    
    
    //Metodo para invocar el reporte y enviarle los parametros si es que necesita
    public void verReportePdf() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, MalformedURLException {
        //Instancia hacia la clase reporteClientes        
        ReporteS rCliente = new ReporteS();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportes/Clientes.jasper");
        
        
        rCliente.getReportePdf(ruta);
        FacesContext.getCurrentInstance().responseComplete();
    }
   





    
    public void customizationOptions() {
        ExcelOptions xls = new ExcelOptions();
        xls.setFacetBgColor("#19C7FF");
        xls.setFacetFontSize("10");
        xls.setFacetFontColor("#FFFFFF");
        xls.setFacetFontStyle("BOLD");
        xls.setCellFontColor("#000000");
        xls.setCellFontSize("8");
        xls.setFontName("Verdana");

        PDFOptions pdf = new PDFOptions();
        pdf.setFacetBgColor("#19C7FF");
        pdf.setFacetFontColor("#000000");
        pdf.setFacetFontStyle("BOLD");
        pdf.setCellFontSize("12");
        pdf.setFontName("Courier");
        pdf.setOrientation(PDFOrientationType.LANDSCAPE);
    } 
}