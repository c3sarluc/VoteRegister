/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConexaoBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author bruno
 */
public class ConexaoSQLite {

    private Connection conexao;

    /**
     * Conecta a um banco de dados (cria o banco se ele nÃ£o existir)
     *
     * @return
     */
    public boolean conectar() {

        try {

            String url = "jdbc:sqlite:banco_de_dados/banco_sqlite.db";

            this.conexao = DriverManager.getConnection(url);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }

        System.out.println("conectou!!!");
        
        return true;
    }

    public boolean desconectar() {

        try {
            if (this.conexao.isClosed() == false) {
                this.conexao.close();
            }

        } catch (SQLException e) {

            System.err.println(e.getMessage());
            return false;
        }
        System.out.println("desconectou!!!");
        return true;

    }
    
        /**
     * Criar os statements para nossos sqls serem executados
     *
     * @return
     */
    public Statement criarStatement() {
        try {
            return this.conexao.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
    
    
    public PreparedStatement criarPreparedStatement(String sql) {
        try {
            return this.conexao.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
    
    

    public Connection getConexao() {
        return this.conexao;
    }

}

