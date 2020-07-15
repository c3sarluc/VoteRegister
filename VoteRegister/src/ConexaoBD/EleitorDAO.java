 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConexaoBD;

import java.sql.SQLException;
import java.sql.Statement;
import Entidades.Eleitor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author arthu
 */
public class EleitorDAO {
    private final ConexaoSQLite conexaoSQLite;

    public EleitorDAO() {
        this.conexaoSQLite = new ConexaoSQLite();
    }
    
    private String addTildeOptions(String search) {
            return search.toLowerCase()
                             .replaceAll("[aáàäâã]", "\\[aáàäâã\\]")
                             .replaceAll("[eéèëê]", "\\[eéèëê\\]")
                             .replaceAll("[iíìî]", "\\[iíìî\\]")
                             .replaceAll("[oóòöôõ]", "\\[oóòöôõ\\]")
                             .replaceAll("[uúùüû]", "\\[uúùüû\\]")
                             .replace("*", "[*]")
                             .replace("?", "[?]");
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
                        resultSet.getString("observacao"), 
                        resultSet.getString("zona"), 
                        resultSet.getString("regiao"), 
                        resultSet.getInt("id"),
                        resultSet.getString("secao"),
                        resultSet.getString("alcance"))); 
 
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
                " colaborador, endereco, bairro, zona, regiao, observacao, secao, alcance)"+
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        boolean conectou = false;
        conectou = conexaoSQLite.conectar();
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
            pstmt.setString(14, eleitor.getObservacao());
            pstmt.setString(15, eleitor.getSecao());
            pstmt.setString(16, eleitor.getAlcance());
            
            
            
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
        
        String sql = "Delete from tbl_eleitor where id = " + id + " order by nome;";
        
        statement = conexaoSQLite.criarStatement();
        
        try {
            statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e);
        }finally{
            conexaoSQLite.desconectar();
        }
    }

    public void update(Eleitor eleitor){
        String sql = 
                "UPDATE tbl_eleitor SET "
                + "nome = '" + eleitor.getNome()+ "', "
                + "nascimento = '" + eleitor.getNascimento() + "', "
                + "funcionario = '"+ eleitor.getFuncionario() +"', "
                + "email = '" + eleitor.getEmail() + "', "
                + "telefone1 = '" + eleitor.getTelefone1() + "', "
                + "telefone2 = '" + eleitor.getTelefone2() + "', "
                + "voto = '" + eleitor.getVoto() + "', "
                + "pleito = '" + eleitor.getPleito() + "', "
                + "colaborador = '" + eleitor.getColaborador() + "', "
                + "endereco = '" + eleitor.getEndereco() + "', "
                + "bairro = '" + eleitor.getBairro() + "', "
                + "zona = '" + eleitor.getZona() + "', "
                + "regiao = '" + eleitor.getRegiao() + "', "
                + "observacao  = '" + eleitor.getObservacao() + "', "
                + "secao  = '" + eleitor.getSecao() + "', "
                + "alcance  = '" + eleitor.getAlcance() + "' "
                + "WHERE id = " + eleitor.getId() + ";";

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
    
    public ArrayList<Eleitor> searchEleitores(String search){
        
        search = addTildeOptions(search).toLowerCase();

        ResultSet resultSet = null;
        Statement statement = null;

        conexaoSQLite.conectar();

        String query = "SELECT * FROM tbl_eleitor where lower(nome) glob '*" + search + "*' order by nome;";
        
        System.out.println(query);

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
                        resultSet.getString("observacao"), 
                        resultSet.getString("zona"), 
                        resultSet.getString("regiao"), 
                        resultSet.getInt("id"),
                        resultSet.getString("secao"),
                        resultSet.getString("alcance"))); 
 
            }
            return Eleitores;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            try {
                statement.close();
                conexaoSQLite.desconectar();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }

    }
}



