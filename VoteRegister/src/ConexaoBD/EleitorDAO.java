/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConexaoBD;

import java.sql.SQLException;
import java.sql.Statement;
import Classes.Eleitor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author arthu
 */
public class EleitorDAO {
    private final ConexaoSQLite conexaoSQLite;

    public EleitorDAO(ConexaoSQLite pConexaoSQLite) {
        this.conexaoSQLite = pConexaoSQLite;
    }

    public void criarTabela() {

        String sql = "CREATE TABLE IF NOT EXISTS tbl_eleitor"
                + "( "
                + "id integer PRIMARY KEY,"
                + "nome text,"
                + "nascimento text,"
                + "funcionario text,"
                + "email text,"
                + "telefone1 text,"
                + "telefone2 text,"
                + "voto text,"
                + "pleito text,"
                + "colaborador text,"
                + "endereco text,"
                + "bairro text,"
                + "zona text,"
                + "regiao text"
                + " );";

        //executando o sql de criar tabelas
        boolean conectou = false;

        try {
            conectou = this.conexaoSQLite.conectar();
            
            Statement stmt = this.conexaoSQLite.criarStatement();
            
            stmt.execute(sql);
            
            System.out.println("Tabela eleitor criada!");

        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela:");
            System.out.println(e);
        } finally {
            if(conectou){
                this.conexaoSQLite.desconectar();
            }
        }

    }
    
    
    public ArrayList<Eleitor> getEleitores(){

        ResultSet resultSet = null;
        Statement statement = null;

        conexaoSQLite.conectar();

        String query = "SELECT * FROM tbl_eleitor;";

        statement = conexaoSQLite.criarStatement();
        
        ArrayList<Eleitor> Eleitores = new ArrayList();

        try {
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Eleitores.add(new Eleitor(
                        resultSet.getString("nome"), 
                        resultSet.getString("nascimento"), 
                        resultSet.getString("funcionario"), 
                        resultSet.getString("email"), 
                        resultSet.getString("telefone1"), 
                        resultSet.getString("telefone2"), 
                        resultSet.getString("voto"), 
                        resultSet.getString("pleito"), 
                        resultSet.getString("colaborador"), 
                        resultSet.getString("endereco"), 
                        resultSet.getString("bairro"), 
                        resultSet.getString("zona"), 
                        resultSet.getString("regiao"), 
                        resultSet.getInt("id"))); 
 
            }
            return Eleitores;
        } catch (SQLException e) {
            System.out.println("Erro misteriosos");
            return null;
        } finally {
            try {
                resultSet.close();
                statement.close();
                conexaoSQLite.desconectar();
            } catch (SQLException ex) {
                System.out.println("Erro misterioso de fechamentos");
            }
        }

    }

    public void insert(Eleitor eleitor) {
        String sql = "INSERT INTO tbl_eleitor ( nome ,nascimento,"+
                " funcionario, email, telefone1, telefone2, voto, pleito,"+
                " colaborador, endereco, bairro, zona, regiao)"+
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        boolean conectou = false;
        try {
            PreparedStatement pstmt = this.conexaoSQLite.criarPreparedStatement(sql);
            pstmt.setString(1, eleitor.getNome());
            pstmt.setString(2, eleitor.getNascimento());
            pstmt.setString(3, eleitor.getFuncionario());
            pstmt.setString(4, eleitor.getEmail());
            pstmt.setString(5, eleitor.getTelefone1());
            pstmt.setString(6, eleitor.getTelefone2());
            pstmt.setString(7, eleitor.getVoto());
            pstmt.setString(8, eleitor.getPleito());
            pstmt.setString(9, eleitor.getColaborador());
            pstmt.setString(10, eleitor.getEndereco());
            pstmt.setString(11, eleitor.getBairro());
            pstmt.setString(12, eleitor.getZona());
            pstmt.setString(13, eleitor.getRegiao());
            
            
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}



