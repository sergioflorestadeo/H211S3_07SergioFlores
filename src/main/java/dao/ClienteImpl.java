package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Statement;

import java.util.ArrayList;

import java.util.List;
import modelo.Cliente;
import modelo.Ubigeo;

public class ClienteImpl extends Conexion implements ICRUD<Cliente> {

    @Override
    public void registrar(Cliente cli) throws Exception {

        String sql = "insert into CLIENTE (NOMCLI,APECLI,DNICLI,TELFCLI,DIRECCLI,CODUBI,ESTCLI) values (?,?,?,?,?,?,'A')";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, cli.getNombre());
            
            ps.setString(2, cli.getApellidos());
            
            ps.setInt(3, cli.getDni());
           
            ps.setString(4, cli.getCelular());
            
            ps.setString(5, cli.getDireccion());
           
            ps.setString(6, cli.getUbigeo().getIdubi());
            
            

            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error al Ingresar Cliente: " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    // @Override
    public void modificar(Cliente cli) throws Exception {
        String sql = "update CLIENTE set NOMCLI=?,APECLI=?,DNICLI=?,TELFCLI=?,DIRECCLI=?,CODUBI=?  where IDCLI=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, cli.getNombre());
            ps.setString(2, cli.getApellidos());
            ps.setInt(3, cli.getDni());
            ps.setString(4, cli.getCelular());
            ps.setString(5, cli.getDireccion());
            ps.setString(6, cli.getUbigeo().getIdubi());
            
            ps.setInt(7, cli.getIdcli());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error al Modificar ClienteImpl: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Cliente cli) throws Exception {
        String sql = "UPDATE CLIENTE SET ESTCLI='I' WHERE IDCLI=?";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, cli.getIdcli());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en eliminarD" + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    @Override
    public List listar() throws Exception {
        List<Cliente> listado = null;
        Cliente cli;
        String sql = "select IDCLI,NOMCLI,APECLI,DNICLI,TELFCLI,DIRECCLI,UBIGEO.CODUBI,\n" +
"                UBIGEO.DEPUBI,UBIGEO.PROUBI,UBIGEO.DISUBI, ESTCLI from CLIENTE\n" +
"                INNER JOIN UBIGEO\n" +
"                ON UBIGEO.CODUBI = CLIENTE.CODUBI "
                + "where ESTCLI='A'"
                + "order by IDCLI desc";
        //"select c.CODPER,c.NOMPER,c.APEPER,c.DNIPER,c.CELPER,c.DIRECPER";
        // + " from Cliente c INNER JOIN Ubigeo u ON c.CODUBI=u.CODUBI";

        try {
            listado = new ArrayList();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cli = new Cliente();
                cli.setIdcli(rs.getInt("IDCLI"));
                cli.setNombre(rs.getString("NOMCLI"));
                cli.setApellidos(rs.getString("APECLI"));
                cli.setDni(rs.getInt("DNICLI"));
                cli.setCelular(rs.getString("TELFCLI"));
                cli.setDireccion(rs.getString("DIRECCLI"));
                Ubigeo ubigeo = new Ubigeo();
                ubigeo.setIdubi(rs.getString("CODUBI"));
                ubigeo.setDepubi(rs.getString("DEPUBI"));
                ubigeo.setProubi(rs.getString("PROUBI"));
                ubigeo.setDisubi(rs.getString("DISUBI"));
                cli.setUbigeo(ubigeo);
                cli.setEstado(rs.getString("ESTCLI"));
                listado.add(cli);
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

    //@Override
    //public void modificar(Persona obj) throws Exception {
    //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    //}
    //@Override
    //public void eliminar(Persona obj) throws Exception {
    //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    // }
    

    @Override
    public void cambiarEstado(Cliente obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    

    @Override
    public List<Cliente> listarTodos(char estado) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public List<Ubigeo> listUbi() throws Exception{
        List<Ubigeo> lista;
        ResultSet rs;
        Ubigeo ubigeo;
        try {
           ubigeo = new Ubigeo();
           String sql="SELECT CODUBI,DEPUBI,PROUBI,DISUBI FROM UBIGEO";
           PreparedStatement pr = this.conectar().prepareStatement(sql);
           rs = pr.executeQuery();
           lista = new ArrayList<>();
           while (rs.next()){
               ubigeo = new Ubigeo(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
               lista.add(ubigeo);
           }
        } catch (Exception e) {
            throw e;
        }
        return lista;
    }

}
