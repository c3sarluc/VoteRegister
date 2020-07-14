/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConexaoBD;

import Entidades.Colaborador;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author aluno
 */
public class ColaboradorDAO {
    private final ConexaoSQLite conexaoSQLite;

    public ColaboradorDAO() {
        this.conexaoSQLite = new ConexaoSQLite();
    }

    public void criarTabela() {

        String sql = "CREATE TABLE IF NOT EXISTS tbl_colaborador"
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
                + "endereco text,"
                + "bairro text,"
                + "zona text,"
                + "regiao text,"
                + "observacao text,"
                + "secao text,"
                + "alcance text"
                + " );";

        //executando o sql de criar tabelas
        boolean conectou = false;

        try {
            conectou = this.conexaoSQLite.conectar();
            
            Statement stmt = this.conexaoSQLite.criarStatement();
            
            stmt.execute(sql);
            
            System.out.println("Tabela colaborador criada!");

        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela:");
            System.out.println(e);
        } finally {
            if(conectou){
                this.conexaoSQLite.desconectar();
            }
        }

    }
     
    public ArrayList<Colaborador> getColaboradores(){

        ResultSet resultSet = null;
        Statement statement = null;

        conexaoSQLite.conectar();

        String query = "SELECT * FROM tbl_colaborador;";

        statement = conexaoSQLite.criarStatement();
        
        ArrayList<Colaborador> Colaboradores = new ArrayList();

        try {
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Colaboradores.add(new Colaborador(
                        resultSet.getString("nome"), 
                        resultSet.getString("nascimento"), 
                        resultSet.getString("funcionario"), 
                        resultSet.getString("email"), 
                        resultSet.getString("telefone1"), 
                        resultSet.getString("telefone2"), 
                        resultSet.getString("voto"), 
                        resultSet.getString("pleito"), 
                        resultSet.getString("endereco"), 
                        resultSet.getString("bairro"), 
                        resultSet.getString("observacao"), 
                        resultSet.getString("zona"), 
                        resultSet.getString("regiao"), 
                        resultSet.getInt("id"),
                        resultSet.getString("secao"),
                        resultSet.getString("alcance"))); 
 
            }
            return Colaboradores;
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
    
    public ArrayList<String> getColaboradoresNames(){

        ResultSet resultSet = null;
        Statement statement = null;

        conexaoSQLite.conectar();

        String query = "SELECT nome FROM tbl_colaborador;";

        statement = conexaoSQLite.criarStatement();
        
        ArrayList<String> ColaboradoresNames = new ArrayList();

        try {
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                ColaboradoresNames.add(resultSet.getString("nome")); 
 
            }
            return ColaboradoresNames;
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
    
    

    public void insert(Colaborador colaborador) {
        String sql = "INSERT INTO tbl_colaborador ( nome ,nascimento,"+
                " funcionario, email, telefone1, telefone2, voto, pleito,"+
                " endereco, bairro, zona, regiao, observacao, secao, alcance)"+
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        boolean conectou = false;
        conectou = conexaoSQLite.conectar();
        try {
            PreparedStatement pstmt = this.conexaoSQLite.criarPreparedStatement(sql);
            pstmt.setString(1, colaborador.getNome());
            pstmt.setString(2, colaborador.getNascimento());
            pstmt.setString(3, colaborador.getFuncionario());
            pstmt.setString(4, colaborador.getEmail());
            pstmt.setString(5, colaborador.getTelefone1());
            pstmt.setString(6, colaborador.getTelefone2());
            pstmt.setString(7, colaborador.getVoto());
            pstmt.setString(8, colaborador.getPleito());
            pstmt.setString(9, colaborador.getEndereco());
            pstmt.setString(10, colaborador.getBairro());
            pstmt.setString(11, colaborador.getZona());
            pstmt.setString(12, colaborador.getRegiao());
            pstmt.setString(13, colaborador.getObservacao());
            pstmt.setString(14, colaborador.getSecao());
            pstmt.setString(15, colaborador.getAlcance());
            
            
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally{
            if(conectou){
                conexaoSQLite.desconectar();
            }
        }
    }
    
    public void delete(int id){
        
        Statement statement = null;
        
        conexaoSQLite.conectar();
        
        String sql = "Delete from tbl_colaborador where id = " + id + ";";
        
        statement = conexaoSQLite.criarStatement();
        
        try {
            statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e);
        }finally{
            conexaoSQLite.desconectar();
        }
    }

    public void update(Colaborador colaborador){
        String sql = 
                "UPDATE tbl_colaborador SET "
                + "nome = '" + colaborador.getNome()+ "', "
                + "nascimento = '" + colaborador.getNascimento() + "', "
                + "funcionario = '"+ colaborador.getFuncionario() +"', "
                + "email = '" + colaborador.getEmail() + "', "
                + "telefone1 = '" + colaborador.getTelefone1() + "', "
                + "telefone2 = '" + colaborador.getTelefone2() + "', "
                + "voto = '" + colaborador.getVoto() + "', "
                + "pleito = '" + colaborador.getPleito() + "', "
                + "endereco = '" + colaborador.getEndereco() + "', "
                + "bairro = '" + colaborador.getBairro() + "', "
                + "zona = '" + colaborador.getZona() + "', "
                + "regiao = '" + colaborador.getRegiao() + "', "
                + "observacao  = '" + colaborador.getObservacao() + "', "
                + "secao  = '" + colaborador.getSecao() + "', "
                + "alcance  = '" + colaborador.getAlcance() + "' "
                + "WHERE id = " + colaborador.getId() + ";";

        boolean conectou = false;
        conectou = conexaoSQLite.conectar();
        try {
            System.out.println(sql);
            PreparedStatement pstmt = this.conexaoSQLite.criarPreparedStatement(sql);
 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally{
            if(conectou){
                conexaoSQLite.desconectar();
            }
        }
    }
}
