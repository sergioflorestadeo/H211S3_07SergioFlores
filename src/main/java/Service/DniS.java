/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import java.util.List;
import modelo.Cliente;

public class DniS {

    @SuppressWarnings("unchecked")
    public static boolean buscarDuplicado(List<Cliente> listadoDb, Integer dni) {
        
        if (listadoDb.contains(dni)) {
            System.out.println("El DNI ya esta en el listado");
            return true;
        } else {
            System.out.println("El DNI ha sido Registrado");
            return false;
        }
        
    }
    
}
