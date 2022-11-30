package Modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import utils.Conexao;

public class Tarefa {

    private int idTarefa;
    private int idColaborador;
    private String descrTarefa;
    private Timestamp dataHoraInicio;
    private Timestamp dataHoraFim;
    private String statusTarefa;
    private String prioridadeTarefa;
    
    public boolean incluirTarefa() {
        String sql  = "insert into tarefa ";
               sql += "(idcolaborador, descrtarefa, idprioridade, sysdate, ";
               sql += " null, statustarefa) ";
               sql += "values(?,?,?,?,?,?,?,?);";
        Connection con = Conexao.conectar();
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt      (1,this.idColaborador);
            stm.setString   (2,this.descrTarefa);
            stm.setString   (3,this.prioridadeTarefa);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            String datahorainicio = dtf.format(LocalDateTime.now());
            
            stm.setTimestamp(4,Timestamp.valueOf(datahorainicio));
            stm.setTimestamp(5,this.dataHoraFim);
            stm.setString   (6,this.statusTarefa);
            stm.execute();
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean alterarTarefa() {
        Connection con = Conexao.conectar();
        String  sql  = "update tarefa ";
                sql += "set descrtarefa      = ?, ";
                sql += "    prioridadetarefa = ?, ";
                sql += "    statustarefa     = ?  ";
                sql += "where idtarefa = ? ";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1,this.descrTarefa);
            stm.setString(2,this.prioridadeTarefa);
            stm.setString(3, this.statusTarefa);
            stm.setInt(4,this.idTarefa);
            stm.execute();
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean excluirTarefa() {
        Connection con = Conexao.conectar();
        String  sql  = "delete from tarefa ";
                sql += "where idtarefa = ? ";
        try {
            PreparedStatement stm = con.prepareStatement(sql);          
            stm.setInt(1, this.idTarefa);
            stm.execute();
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
            return false;
        }
        return true;
    }
    
    public List<Tarefa> consultaGeralTarefas() {
        List<Tarefa> lista = new ArrayList<>();
        Connection con = Conexao.conectar();
        String  sql  = "SELECT idtarefa,idcolaborador, descrtarefa,    ";
                sql += "prioridadetarefa, datahorainicio, datahorafim,  ";
                sql += "statustarefa ";
                sql += "from tarefa ";
                sql += "order by idtarefa";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Tarefa tarefas = new Tarefa();
                tarefas.setIdTarefa(rs.getInt("idtarefa"));
                tarefas.setIdColaborador(rs.getInt("idcolaborador"));
                tarefas.setDescrTarefa(rs.getString("descrtarefa"));
                tarefas.setPrioridadeTarefa(rs.getString("prioridadetarefa"));
                tarefas.setDataHoraInicio(rs.getTimestamp("datahorainicio"));
                tarefas.setDataHoraFim(rs.getTimestamp("datahorafim"));
                tarefas.setStatusTarefa(rs.getString("statustarefa"));
                lista.add(tarefas);
            }
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        return lista;
    }

    public Tarefa consultarTarefa(int pCodTarefa) {
        Connection con = Conexao.conectar();
       String   sql  = "SELECT idcolaborador, descrtarefa, prioridadetarefa, ";
                sql += " datahorainicio, datahorafim, statustarefa ";
                sql += "where idtarefa = ? ";
                sql += "from tarefa ";
                sql += "order by idtarefa";
        Tarefa tarefas = null;
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, pCodTarefa);          
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                tarefas = new Tarefa();
                tarefas.setIdColaborador(rs.getInt("idcolaborador"));
                tarefas.setDescrTarefa(rs.getString("descrtarefa"));
                tarefas.setPrioridadeTarefa(rs.getString("prioridadetarefa"));
                tarefas.setDataHoraInicio(rs.getTimestamp("datahorainicio"));
                tarefas.setDataHoraFim(rs.getTimestamp("datahorafim"));
                tarefas.setStatusTarefa(rs.getString("statustarefa"));
                tarefas.setIdTarefa(rs.getInt("idtarefa"));
            }
        } 
        catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        return tarefas;
    }

    /*   AREA DE CONSULTAS ESPECIFICAS  */
    
       /* tarefas do colaborador **/
    
    public List<Tarefa> consultarTarefasColab(int pCodColaborador)  {
        List<Tarefa> lista = new ArrayList<>();
        Connection con = Conexao.conectar();
        String  sql  = "SELECT idtarefa, descrtarefa, prioridadetarefa, ";
                sql += " datahorainicio, datahorafim, statustarefa ";
                sql += "where idcolaborador = ? ";
                sql += "from tarefa ";
                sql += "order by idtarefa";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1,pCodColaborador );
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Tarefa tarefas = new Tarefa();
                tarefas.setIdTarefa(rs.getInt("idtarefa"));
                tarefas.setDescrTarefa(rs.getString("descrtarefa"));
                tarefas.setPrioridadeTarefa(rs.getString("prioridadetarefa"));
                tarefas.setDataHoraInicio(rs.getTimestamp("datahorainicio"));
                tarefas.setDataHoraFim(rs.getTimestamp("datahorafim"));
                tarefas.setStatusTarefa(rs.getString("statustarefa"));
                lista.add(tarefas);
            }
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        return lista;
    } 
    // area de getters e setters

    public int getIdTarefa() {
        return idTarefa;
    }

    public void setIdTarefa(int idTarefa) {
        this.idTarefa = idTarefa;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getDescrTarefa() {
        return descrTarefa;
    }

    public void setDescrTarefa(String descrTarefa) {
        this.descrTarefa = descrTarefa;
    }

    public Timestamp getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(Timestamp dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public Timestamp getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(Timestamp dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public String getStatusTarefa() {
        return statusTarefa;
    }

    public void setStatusTarefa(String statusTarefa) {
        this.statusTarefa = statusTarefa;
    }

    public String getPrioridadeTarefa() {
        return prioridadeTarefa;
    }

    public void setPrioridadeTarefa(String prioridadeTarefa) {
        this.prioridadeTarefa = prioridadeTarefa;
    }

  
    
}