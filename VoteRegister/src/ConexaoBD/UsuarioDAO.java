/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConexaoBD;

import Entidades.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author arthu
 */
public class UsuarioDAO {
    private final ConexaoSQLite conexaoSQLite;

    public UsuarioDAO() {
        this.conexaoSQLite = new ConexaoSQLite();
    }

    public void criarTabela() {

        String sql = "CREATE TABLE IF NOT EXISTS tbl_usuario"
                + "( "
                + "id integer PRIMARY KEY, "
                + "login text, "
                + "hashSenha text"
                + " );";

        //executando o sql de criar tabelas
        boolean conectou = false;

        try {
            conectou = this.conexaoSQLite.conectar();
            
            Statement stmt = this.conexaoSQLite.criarStatement();
            
            stmt.execute(sql);
            
            System.out.println("Tabela usuario criada!");

        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela:");
            System.out.println(e);
        } finally {
            if(conectou){
                this.conexaoSQLite.desconectar();
            }
        }

    }
     
    public ArrayList<Usuario> getEleitores(){

        ResultSet resultSet = null;
        Statement statement = null;

        conexaoSQLite.conectar();

        String query = "SELECT * FROM tbl_usuario;";

        statement = conexaoSQLite.criarStatement();
        
        ArrayList<Usuario> Usuarios = new ArrayList();

        try {
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Usuarios.add(new Usuario(
                        resultSet.getString("nome"), 
                        resultSet.getString("nascimento"), 
                        resultSet.getInt("id"))); 
 
            }
            return Usuarios;
        } catch (SQLException e) {
            System.out.println("Erro misteriosos");
            System.out.println(e);
            return null;
        } finally {
            try {
                resultSet.close();
                statement.close();
                conexaoSQLite.desconectar();
            } catch (SQLException ex) {
                System.out.println("Erro misterioso de fechamentos");
                System.out.println(ex);
            }
        }

    }

    public void insert(Usuario usuario) {
        String sql = "INSERT INTO tbl_usuario ( login , hashSenha) VALUES (?,?)";

        boolean conectou = false;
        try {
            conectou = this.conexaoSQLite.conectar();
            PreparedStatement pstmt = this.conexaoSQLite.criarPreparedStatement(sql);
            pstmt.setString(1, usuario.getLogin());
            pstmt.setString(2, String.valueOf(usuario.getHashSenha().hashCode()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally{
            if(conectou){
                this.conexaoSQLite.desconectar();
            }
        }
    }

    public int checkUser(String login, String hashSenha){
        ResultSet resultSet = null;
        Statement pstmt = null;

        conexaoSQLite.conectar();
        pstmt = conexaoSQLite.criarStatement();
        
        String query = "Select * From tbl_usuario where login = '" + login + "' and hashSenha = '" + String.valueOf(hashSenha.hashCode()) + "';";

        
        ArrayList<Usuario> Usuarios = new ArrayList();

        try {

            resultSet = pstmt.executeQuery(query);

            while (resultSet.next()) {
                Usuarios.add(new Usuario(
                        resultSet.getString("login"), 
                        resultSet.getString("hashSenha"), 
                        resultSet.getInt("id"))); 
 
            }
            resultSet.close();
            pstmt.close();
            return Usuarios.size();
        } catch (SQLException e) {
            System.out.println("Erro misteriosos");
            System.out.println(e);
            return 0;
        } finally {
            conexaoSQLite.desconectar();
        }
    }

}
