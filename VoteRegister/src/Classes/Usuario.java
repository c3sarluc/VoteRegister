/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.Objects;

/**
 *
 * @author arthu
 */
public class Usuario {
    String login;
    String hashSenha;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    

    public Usuario(String login, String hashSenha, int id) {
        this.login = login;
        this.hashSenha = hashSenha;
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHashSenha() {
        return hashSenha;
    }

    public void setHashSenha(String hashSenha) {
        this.hashSenha = hashSenha;
    }



    
    
    
}
