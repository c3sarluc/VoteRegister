/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author arthu
 */
public class Eleitor {
    private int    id;
    private String nome;
    private String nascimento;
    private String funcionario;
    private String email;
    private String telefone1;
    private String telefone2;
    private String voto;
    private String pleito;
    private String colaborador;
    private String endereco;
    private String bairro;
    private String zona;
    private String regiao;

    public Eleitor(String nome,     String nascimento, String funcionario, 
                   String email,    String telefone1,  String telefone2, 
                   String voto,     String pleito,     String colaborador, 
                   String endereco, String bairro,     String zona, 
                   String regiao,   int id)
    {
        this.id = id;
        this.nome = nome;
        this.nascimento = nascimento;
        this.funcionario = funcionario;
        this.email = email;
        this.telefone1 = telefone1;
        this.telefone2 = telefone2;
        this.voto = voto;
        this.pleito = pleito;
        this.colaborador = colaborador;
        this.endereco = endereco;
        this.bairro = bairro;
        this.zona = zona;
        this.regiao = regiao;
        
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getVoto() {
        return voto;
    }

    public void setVoto(String voto) {
        this.voto = voto;
    }

    public String getPleito() {
        return pleito;
    }

    public void setPleito(String pleito) {
        this.pleito = pleito;
    }

    public String getColaborador() {
        return colaborador;
    }

    public void setColaborador(String colaborador) {
        this.colaborador = colaborador;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }
    
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }

    @Override
    public String toString() {
        return "Eleitor{" + "id=" + id + ", nome=" + nome + ", nascimento=" + nascimento + ", funcionario=" + funcionario + ", email=" + email + ", telefone1=" + telefone1 + ", telefone2=" + telefone2 + ", voto=" + voto + ", pleito=" + pleito + ", colaborador=" + colaborador + ", endereco=" + endereco + ", bairro=" + bairro + ", zona=" + zona + ", regiao=" + regiao + '}';
    }
    
    
    
}
