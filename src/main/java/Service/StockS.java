/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

/**
 *
 * @author HP
 */
public class StockS {
     @SuppressWarnings("unchecked")
    public static boolean validaStock (int stock , int Cantidad ) {
        
        if (stock == 0 || stock < Cantidad ) {
            System.out.println("No hay  stock");
            return true;
        } else {
            System.out.println("Hay stock");
            return false;
        }
        
    }
}
