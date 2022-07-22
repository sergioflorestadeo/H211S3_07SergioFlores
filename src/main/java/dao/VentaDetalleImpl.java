/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Cliente;
import modelo.Subfamilia;
import modelo.VentaDetalle;

/**
 *
 * @author HP
 */
public class VentaDetalleImpl extends Conexion implements ICRUD<VentaDetalle> {

    @Override
    public void registrar(VentaDetalle ventadetalle) throws Exception {
        String sql = "Insert Into VENTADETALLE (CANTVENDET,IDVEN,IDSUBFAM) VALUES(?,?,?)";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, ventadetalle.getCANTVENDET());
            ps.setInt(2, ventadetalle.getVenta().getIDVEN());
            ps.setInt(3, ventadetalle.getSubfamilia().getIDSUBFAM());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en RegistrarD " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    public void registrarVenta(VentaDetalle ventadetalle) throws Exception {
        String sql = "insert into VENTA (FECHVEN, IDEMP,IDCLI,ESTVEN) values (getdate(),?,?,?)";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, ventadetalle.getEmpleado().getIDEMP());
            ps.setInt(2, ventadetalle.getCliente().getIdcli());
            ps.setString(3, ventadetalle.getESTVEN());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en RegistrarV " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    public int obtenerUltimoId() {
        try {

            PreparedStatement ps1 = this.conectar().prepareStatement("SELECT MAX(IDVEN) AS IDVENTA FROM VENTA");
            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
                return rs.getInt("IDVENTA");
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en obtenerId" + e.getMessage());
        }
        return -1;
    }

    public void registromultiple(List<VentaDetalle> listaVentaDetalle,
            int idVent) throws Exception {
        String sqlVentaDetalle = "INSERT INTO VENTADETALLE (IDSUBFAM,IDVEN,CANTVENDET) VALUES (?,?,?)";

        try {

            PreparedStatement ps = this.conectar().prepareStatement(sqlVentaDetalle);
            for (VentaDetalle ventadetalle : listaVentaDetalle) {
                int nuevoStock = ventadetalle.getSubfamilia().getSTOCKSUBFAM() - ventadetalle.getCANTVENDET();
                String sqlDescuento = "UPDATE  SUBFAMILIA SET STOCKSUBFAM = " + nuevoStock + " where IDSUBFAM = " + ventadetalle.getSubfamilia().getIDSUBFAM();
                PreparedStatement ps1 = this.conectar().prepareStatement(sqlDescuento);
                ps1.executeUpdate();
                ps1.close();
                ps.setInt(1, ventadetalle.getSubfamilia().getIDSUBFAM());
                ps.setInt(2, idVent);
                ps.setInt(3, ventadetalle.getCANTVENDET());
                ps.executeUpdate();
            }
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.cerrar();//si o si cerrar en caso funcione o no
        }
    }

    public VentaDetalle agregarFila(int idpro, int idcli) throws SQLException, Exception {
        System.out.println(idpro + idcli);
        VentaDetalle v = null;
        String sqlproducto = "SELECT IDSUBFAM,NOMSUBFAM,STOCKSUBFAM,PRESUBFAM FROM SUBFAMILIA WHERE IDSUBFAM = '" + idpro + "'";
        String sqlcliente = "select IDCLI,NOMCLI,APECLI FROM CLIENTE WHERE IDCLI='" + idcli + "'";
        System.out.println(sqlproducto);
        System.out.println(sqlcliente);
        try {
            PreparedStatement producto = this.conectar().prepareStatement(sqlproducto);
            PreparedStatement cliente = this.conectar().prepareStatement(sqlcliente);
            ResultSet rsp = producto.executeQuery();
            ResultSet rsc = cliente.executeQuery();
            while (rsp.next() && rsc.next()) {
                v = new VentaDetalle();
                Subfamilia p = new Subfamilia();
                p.setIDSUBFAM(rsp.getInt("IDSUBFAM"));
                p.setNOMSUBFAM(rsp.getString("NOMSUBFAM"));
                p.setPRESUBFAM(rsp.getDouble("PRESUBFAM"));
                p.setSTOCKSUBFAM(rsp.getInt("STOCKSUBFAM"));
                v.setSubfamilia(p);
                Cliente c = new Cliente();
                c.setIdcli(rsc.getInt("IDCLI"));
                c.setNombre(rsc.getString("NOMCLI"));
                c.setApellidos(rsc.getString("APECLI"));
                v.setCliente(c);
            }
            producto.close();
            cliente.close();
            rsp.close();
            rsc.close();
        } catch (Exception e) {
            System.out.println("Error en el nuevo metodo DetalleDAO " + e.getMessage());
            e.printStackTrace();
        } finally {
            this.cerrar();
            return v;
        }
    }

    @Override
    public void modificar(VentaDetalle ventadetalle) throws Exception {
       String sql = "update VENTADETALLE set CANTVENDET=?, SUBVENTDET=?, IDVEN=?, IDSUBFAM=? where IDVENDET=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, ventadetalle.getCANTVENDET());
            ps.setDouble(2, ventadetalle.getSubtotal());
            ps.setInt(3, ventadetalle.getVenta().getIDVEN());
            ps.setInt(4, ventadetalle.getSubfamilia().getIDSUBFAM());

            ps.setInt(5, ventadetalle.getIDVENDET());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en ModificarD " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    @Override
    public void eliminar(VentaDetalle ventadetalle) throws Exception {
        String sql = "delete from VENTADETALLE where IDVENDET=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, ventadetalle.getIDVENDET());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en EliminarD " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    @Override
    public void cambiarEstado(VentaDetalle ventadetalle) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<VentaDetalle> listar() throws Exception {
        List<VentaDetalle> listado = null;
        VentaDetalle ventadetalle;
        String sql = "SELECT CANTVENDET, SUBFAMILIA.IDSUBFAM,SUBFAMILIA.NOMSUBFAM,SUBFAMILIA.PRESUBFAM, (CANTVENDET*SUBFAMILIA.PRESUBFAM)AS SUBTOTAL,  \n"
                + "                CLIENTE.IDCLI,CLIENTE.NOMCLI,CLIENTE.APECLI  \n"
                + "                 FROM VENTADETALLE\n"
                + "                  INNER JOIN VENTA\n"
                + "                	ON VENTA.IDVEN = VENTADETALLE.IDVEN\n"
                + "                     INNER JOIN SUBFAMILIA\n"
                + "                     ON SUBFAMILIA.IDSUBFAM=VENTADETALLE.IDSUBFAM  \n"
                + "                     INNER JOIN CLIENTE  \n"
                + "                    ON CLIENTE.IDCLI = VENTA.IDCLI\n"
                + "                order by IDVENDET desc ";

        try {
            listado = new ArrayList();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                ventadetalle = new VentaDetalle();
                ventadetalle.setCANTVENDET(rs.getInt("CANTVENDET"));
                ventadetalle.setSubtotal(rs.getDouble("SUBTOTAL"));
                Subfamilia subfamilia = new Subfamilia();
                subfamilia.setIDSUBFAM(rs.getInt("IDSUBFAM"));
                subfamilia.setNOMSUBFAM(rs.getString("NOMSUBFAM"));
                subfamilia.setPRESUBFAM(rs.getDouble("PRESUBFAM"));
                ventadetalle.setSubfamilia(subfamilia);
                Cliente cliente = new Cliente();
                cliente.setIdcli(rs.getInt("IDCLI"));
                cliente.setNombre(rs.getString("NOMCLI"));
                cliente.setApellidos(rs.getString("APECLI"));
                ventadetalle.setCliente(cliente);
                listado.add(ventadetalle);
            }
            rs.close();
            st.close();

        } catch (Exception e) {
            System.out.println("Error en listarTodosD: " + e.getMessage());
        } finally {
            this.cerrar();
        }
        return listado;

    }

    @Override
    public List<VentaDetalle> listarTodos(char estado) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String obtenerIdProd(String cadenaProducto) throws SQLException, Exception {
        String sql = "select IDSUBFAM from SUBFAMILIA where concat(NOMSUBFAM, ', ', IDFAM , ', ',DESSUBFAM, ', ',PRESUBFAM) = ?";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, cadenaProducto);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("IDSUBFAM");
            }
            return rs.getString("IDSUBFAM");
        } catch (Exception e) {
            System.out.println("Error en obtenerIdProD " + e.getMessage());
            throw e;
        }
    }

    public List<String> autocompleteProducto(String consulta) throws SQLException, Exception {
        List<String> lista = new ArrayList<>();
        String sql = "select top 20 concat(NOMSUBFAM, ', ',PRESUBFAM) as PRODUCTODESC from SUBFAMILIA where NOMSUBFAM like ?";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, "%" + consulta + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("PRODUCTODESC"));
            }
        } catch (Exception e) {
            System.out.println("Error en autocompleteProductoD" + e.getMessage());
        }
        return lista;
    }

    public String obtenerIdVen(String cadenaVenta) throws SQLException, Exception {
        String sql = "select IDVEN from VENTA where concat(FECHVEN, ', ', IDCLI, ', ',IDVEN, ', ',TOTVENT) = ?";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, cadenaVenta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("IDVEN");
            }
            return rs.getString("IDVEN");
        } catch (Exception e) {
            System.out.println("Error en obtenerIdVenD " + e.getMessage());
            throw e;
        }
    }

    public List<String> autocompleteVenta(String consulta) throws SQLException, Exception {
        List<String> lista = new ArrayList<>();
        String sql = "select top 20 concat(FECHVEN, ', ', IDCLI, ', ',IDVEN, ', ',TOTVENT) as VENTADESC from VENTA where FECHVEN like ?";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, "%" + consulta + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("VENTADESC"));
            }
        } catch (Exception e) {
            System.out.println("Error en autocompleteVentaD" + e.getMessage());
        }
        return lista;
    }

}
