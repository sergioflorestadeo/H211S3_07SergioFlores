/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import dao.Conexion;
import static dao.Conexion.cnx;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class ReporteS extends Conexion{
    
    public void getReportePdf(String ruta) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        //Se definen los parametros si es que el reporte necesita
        Map<String, Object> parameter = new HashMap<String, Object>();
        try {
            File file = new File(ruta);

            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\"CLIENTES.PDF\";");
            httpServletResponse.setHeader("Cache-Control", "no-cache");
            httpServletResponse.setHeader("Pragma", "no-cache");
            httpServletResponse.setDateHeader("Expires", 0);
            httpServletResponse.setContentType("application/PDF");

            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(file.getPath());
          //  parameter.put("idinf", numeroinforme);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, this.conectar());

            JRExporter jrExporter = null;
            jrExporter = new JRPdfExporter();
            jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, httpServletResponse.getOutputStream());

            if (jrExporter != null) {
                try {
                    jrExporter.exportReport();
                } catch (JRException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Conexion.cnx != null) {
                try {
                    cnx.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
