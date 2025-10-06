/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 *Classe criada para fazer conexão do banco de dados com 
 * sistema em java swing
 * @author Marina Souza
 */
public class Conexao {
    public Connection getConexao(){
    try{
        Connection conn= DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/p1_sistema_compra_venda?useTimezone=true&serverTimezone=UTC",
    "root","root");
    System.out.println("Conexão realizada com sucesso! ");
    return conn;
        
    }
    catch(Exception e){
        System.out.println("Erro ao conectar no BD"+e.getMessage());
        return null;
        
    }
       
    }
    
}
