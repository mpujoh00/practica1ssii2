/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import DAO.CategoriasDAO;
import DAO.TrabajadorDAO;
import clases.Categorias;
import clases.Empresas;
import java.util.Scanner;
import clases.Trabajadorbbdd;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author maybeitsmica
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        // petición de los datos
        
        System.out.println("Introduzca el NIF del empleado:");
        
        Scanner sc = new Scanner(System.in);
        String NIFemp = sc.nextLine();
        
        // consulta los datos
        
        TrabajadorDAO tDAO = new TrabajadorDAO();
        
        Trabajadorbbdd trabajador = tDAO.obtenerTrabajador(NIFemp);
          
        // muestra los datos
        
        if(trabajador == null){
            
            System.out.println("El trabajador no ha sido encontrado");
            return;
        }
        else{
            System.out.println("---------------------------------------------------------------");
            System.out.println("Nombre: "+ trabajador.getNombre());
            System.out.println("Apellidos: "+ trabajador.getApellido1() +" "+ trabajador.getApellido2());
            System.out.println("NIF: "+ trabajador.getNifnie());
            System.out.println("Categoría: "+ trabajador.getCategorias().getNombreCategoria());
            System.out.println("Empresa: "+ trabajador.getEmpresas().getNombre());
        }
        
        // incremento del salario base del resto de categorías
        
        Categorias categoriaTrabajador = trabajador.getCategorias();
        
        CategoriasDAO cDAO = new CategoriasDAO();
   
        List categorias = cDAO.obtenerCategorias();
        
        for(int i=0; i<categorias.size(); i++){
            
            Categorias cat = (Categorias)categorias.get(i);
            
            if(cat.getIdCategoria() != categoriaTrabajador.getIdCategoria()){
                
                cDAO.aumentaSalario(cat);                              
            }
        }
        
        // eliminación de las nóminas y trabajadores de la misma empresa que el trabajador
        
        Empresas empresa = trabajador.getEmpresas();
        
        Set trabs = empresa.getTrabajadorbbdds();
        List trabajadoresEmpresa = new ArrayList();
        trabajadoresEmpresa.addAll(trabs);
        
        for(int i=0; i<trabajadoresEmpresa.size(); i++){
            
            Trabajadorbbdd trab = (Trabajadorbbdd)trabajadoresEmpresa.get(i);
                        
            if(trab.getIdTrabajador() != trabajador.getIdTrabajador()){
                
                tDAO.borraTrabajador(trab.getNifnie());
            }
        }
        
    }
        
}
