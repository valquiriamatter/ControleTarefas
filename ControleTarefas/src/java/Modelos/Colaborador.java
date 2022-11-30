package Modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.Conexao;
 
public class Colaborador {

    private int    idcolaborador;
    private String nomecolaborador;

    public boolean IncluirColaborador() {
        String sql  = "insert into colaborador ";
               sql += " (nomecolaborador) " ;
               sql += " values(?); ";
        Connection con = Conexao.conectar();

        try {
             PreparedStatement stm = con.prepareStatement(sql);
             stm.setString(1, this.nomecolaborador);
             stm.execute();         
        } 
        catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage() +sql);
            return false;
        }
        return true;
    }  

    public boolean alterarColaborador() {
        Connection con = Conexao.conectar();
        String sql  = "update colaborador ";
               sql += "set nomecolaborador = ?, ";
              sql +=" where idColaborador = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, this.nomecolaborador );
            stm.setInt(2, this.idcolaborador);
            stm.execute();           
        } 
        catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
            return false;
        }        
        return true;
    }

    public boolean excluirColaborador() {
        Connection con = Conexao.conectar();
        String sql = "delete from colaborador ";
               sql +=" where idcolaborador = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, this.idcolaborador);
            stm.execute();           
        } 
        catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
            return false;
        }        
        return true;
       }
    
    public List<Colaborador> consultaColaborador() {
        List<Colaborador> lista = new ArrayList<>();
        Connection con = Conexao.conectar();
        String sql  = "select idcolaborador,nomecolaborador ";
               sql += "from colaborador ";
               sql += "order by 2";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Colaborador colab = new Colaborador();
                colab.setIdcolaborador(rs.getInt("idcolaborador"));
                colab.setNomecolaborador(rs.getString("nomecolaborador"));
                lista.add(colab);
            }
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        return lista;
        }

    public Colaborador consultarColaborador(int pIdColaborador) {
        Connection con = Conexao.conectar();
        String sql  = "select idcolaborador,nomeColaborador ";
               sql += "from colaborador ";
               sql += "where idcolaborador = ?";
        Colaborador colab = null;
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, pIdColaborador);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                colab = new Colaborador();
                colab.setIdcolaborador(rs.getInt("idcolaborador"));
                colab.setNomecolaborador(rs.getString("nomecolaborador"));
            }
        } 
        catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        return colab;
    }

    public int getIdcolaborador() {
        return idcolaborador;
    }

    public void setIdcolaborador(int idcolaborador) {
        this.idcolaborador = idcolaborador;
    }

    public String getNomecolaborador() {
        return nomecolaborador;
    }

    public void setNomecolaborador(String nomecolaborador) {
        this.nomecolaborador = nomecolaborador;
    }
}