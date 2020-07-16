/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import Entidades.Eleitor;
import ConexaoBD.EleitorDAO;
import java.util.ArrayList;
import java.util.function.Consumer;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Label;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

/**
 *
 * @author julio
 */
public class TelaGerarPDF extends javax.swing.JInternalFrame {
    ArrayList<Eleitor> eleitores;
    
    ArrayList<JTextField> textsEleitor = new ArrayList<>();
    
    String[] optsAlcance = {"", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
    ArrayList<JComboBox> alcanceComboBoxes = new ArrayList<>();
    
    ArrayList<JTextField> textsColaborador = new ArrayList<>();

    String[] optsBairro = { "", "BARRA DE JANGADA", "ALTO DA UNIÃO", "ALTO DO CEMITERIO", "ARITANA", "BORBOREMA", "BULHÕES", "CAJÁ", "CAJUEIRO SECO ", "CANDEIAS ", "CASCATA ", "CAVALEIRO", "CENTRO ", "COLÔNIA DOS PADRES ", "COMPORTA", "CORREGO DA BATALHA", "CORREGO DA ROSA", "CURADO", "CURADO I", "CURADO II", "CURADO III", "CURADO IV", "CURCURANA", "DISTRITO INDUSTRIAL", "DOIS CARNEIROS", "DUAS UNAS", "ENGENHO CORVETA", "ENGENHO MACUJÉ", "ENGENHO MEGALP", "ENGENHO PALMEIRA", "ENGENHO SACUPEMA", "ENGENHO VELHO", "ENTRE RIOS", "FORA DO MUNICÍPIO", "FLORIANO", "GAMELEIRA", "GENERAL DERBY", "GOIABEIRA", "GUARARAPES", "JABOATÃO CENTRO", "JARDIM JORDÃO", "JARDIM PIEDADE", "JARDIM SANTO ANDRÉ", "LORETO ", "LOTE 19 ", "LOTE 31 ", "LOTE 56 ", "LOTE 92 ", "LOTEAMENTO BOLA DE OURO ", "LOTEAMENTO COVA DA ONÇA ", "LOTEAMENTO EDMAR DE OLIVEIRA ", "LOTEAMENTO GRANDE RECIFE ", "LOTEAMENTO SANTA HELENA ", "LOTEAMENTO SANTA INÊS ", "LOTEAMENTO SANTA JOANA ", "LOTEAMENTO SÃO CRISTOVÃO ", "LOTEAMENTO SÃO FRANCISCO ", "LOTEAMENTO SONHO VERDE ", "LOTEAMENTO VILA RICA ", "MANASSU ", "MARCOS FREIRE ", "MASSANGANA ", "MASSARANDUBA ", "MOEDA DE BRONZE ", "MURIBECA ", "MURIBEQUINHA ", "PARQUE SANTANA ", "PIEDADE ", "PORTA LARGA ", "PRAIAS ", "PRAZERES ", "RIO DAS VELHAS ", "SANTANA ", "SANTO ALEIXO ", "SANTO ANDRÉ ", "SÃO JOSÉ ", "SOCORRO ", "SOTAVE ", "SUCUPIRA ", "TERRA MAR ", "TRÊS CARNEIROS ", "UR-10", "UR-11 ", "UR-6 ", "VARGEM FRIA ", "VILA MARIO GOUVEIA ", "VILA NESTLÉ ", "VILA RICA ", "VISTA ALEGRE ", "ZUMBI DO PACHECO"};
    ArrayList<JComboBox> bairroComboBoxes = new ArrayList<>();
    
    String[] optsZona = { "", "11", "101", "118", "147" };
    ArrayList<JComboBox> zonaComboBoxes = new ArrayList<>();
    
    private String addTildeOptions(String search) {
            return search.toLowerCase()
                             .replaceAll("[aáàäâã]", "\\[aáàäâãAÁÃÀÂ\\]")
                             .replaceAll("[eéèëê]", "\\[eéèëêEÉẼÈÊ\\]")
                             .replaceAll("[iíìî]", "\\[iíìîIÌÍĨÎ\\]")
                             .replaceAll("[oóòöôõ]", "\\[oóòöôõÔÕÓÒ\\]")
                             .replaceAll("[uúùüû]", "\\[uúùüûÚUÙÛŨ\\]")
                             .replace("*", "[*]")
                             .replace("?", "[?]");
    }
    
    
    private String getQueryCB(ArrayList<JComboBox> comboBox, String tbl) {
        int idx = 0;
        String query = "( ";
        for (JComboBox cbAlcance: comboBox){
            if(!cbAlcance.getSelectedItem().toString().trim().equals("")){
                if(idx > 0){
                query += " or ";
            }
            query += "lower(" + tbl + ") glob '*" + addTildeOptions(cbAlcance.getSelectedItem().toString()) + "*'"; 
            idx++;
            }
        }
        query += " )";
        if(query.equals("(  )")){
            return "";
        }
        return query;
    }
    
    private String agregarQueries(String[] Queries){
        int idx = 0;
        String queryCompose = "";
        for (String query: Queries){
            System.out.println(query);
            if(!query.equals("")){
                if(idx > 0){
                    queryCompose += " and ";
                }
                queryCompose += " " + query; 
                idx++;
            }
        }
        return queryCompose;
    }
    
    private String getQueryTF(ArrayList<JTextField> textFields, String tbl) {
        int idx = 0;
        String query = "( ";
        for (JTextField tf: textFields){
            if(!tf.getText().trim().equals("")){
                if(idx > 0){
                query += " or ";
            }
            query += "lower(" + tbl + ") glob '*" + addTildeOptions(tf.getText()) + "*'"; 
            idx++;
            }
        }
        query += " )";
        if(query.equals("(  )")){
            return "";
        }
        return query;
    }
    
    private void addFiltros(){
        JTextField txEleitor = new JTextField();
        PanelFilters.add(txEleitor);
        textsEleitor.add(txEleitor);
        
        JComboBox comboBoxAlcance = new JComboBox();
        DefaultComboBoxModel modelAlcance = new DefaultComboBoxModel(optsAlcance);
        comboBoxAlcance.setModel(modelAlcance);
        PanelFilters.add(comboBoxAlcance);
        alcanceComboBoxes.add(comboBoxAlcance);
        
        JTextField txColaborador = new JTextField();
        PanelFilters.add(txColaborador);
        textsColaborador.add(txColaborador);
        
        JComboBox comboBoxBairro = new JComboBox();
        DefaultComboBoxModel modelBairro = new DefaultComboBoxModel(optsBairro);
        comboBoxBairro.setModel(modelBairro);
        PanelFilters.add(comboBoxBairro);
        bairroComboBoxes.add(comboBoxBairro);
        
        JComboBox comboBoxZona = new JComboBox();
        DefaultComboBoxModel modelZona = new DefaultComboBoxModel(optsZona);
        comboBoxZona.setModel(modelZona);
        PanelFilters.add(comboBoxZona);
        zonaComboBoxes.add(comboBoxZona);
        
    }
    
    
    public TelaGerarPDF() {
        initComponents();
        
        DefaultTableModel dtmEleitores = (DefaultTableModel) jCadastro.getModel();
        
        EleitorDAO eleitorDAO = new EleitorDAO();
        
        eleitores = eleitorDAO.getEleitores();
        
        eleitores.stream().forEach(s -> { 
            Object[] dados = {
                              s.getNome(), s.getNascimento(), s.getFuncionario(),
                              s.getEmail(),s.getTelefone1(),s.getTelefone2(),
                              s.getVoto(),s.getPleito(),s.getAlcance(),s.getColaborador(),
                              s.getEndereco(),s.getBairro(),s.getSecao(),s.getObservacao(),s.getZona(), s.getRegiao()};
            dtmEleitores.addRow(dados);
        });
        
        jCadastro.setModel(dtmEleitores);
        
        PanelFilters.add(new Label("Eleitor"));
        PanelFilters.add(new Label("Alcance"));
        PanelFilters.add(new Label("Colaborador"));
        PanelFilters.add(new Label("Bairro"));
        PanelFilters.add(new Label("Zona"));
        

        addFiltros();
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jCadastro = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        ScrollFilters = new javax.swing.JScrollPane();
        PanelFilters = new javax.swing.JPanel();
        btAddFiltro = new javax.swing.JButton();
        btFiltrar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Gerar PDF");

        jCadastro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Data de Nascimento", "Funcionário", "E-mail", "Telefone - 1", "Telefone - 2", "Voto", "Pleito", "Alcance", "Colaborador", "Endereço", "Bairro", "Seção", "Observação", "Zona", "Região"
            }
        ));
        jScrollPane1.setViewportView(jCadastro);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtros", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        PanelFilters.setLayout(new java.awt.GridLayout(0, 5, 4, 4));
        ScrollFilters.setViewportView(PanelFilters);

        btAddFiltro.setText("+ Adicionar Filtro");
        btAddFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddFiltroActionPerformed(evt);
            }
        });

        btFiltrar.setText("Filtrar");
        btFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFiltrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btAddFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 985, Short.MAX_VALUE)
                        .addComponent(btFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(ScrollFilters))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(ScrollFilters, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btAddFiltro)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btFiltrar)
                        .addContainerGap())))
        );

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pdf.png"))); // NOI18N
        jButton1.setText(" Gerar PDF");
        jButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(672, 672, 672))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1309, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed


        String path="";
        JFileChooser j= new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x=j.showSaveDialog(this);
              
        path=j.getSelectedFile().getPath();
        System.out.println(path);

        Document doc = new Document ();
        
        try {
            String so = String.valueOf( System.getProperty("os.name") );
            if (so.equals("Linux")){
                PdfWriter.getInstance(doc, new FileOutputStream(path+"/relatorio.pdf"));
            }else{
                PdfWriter.getInstance(doc, new FileOutputStream(path+"\\relatorio.pdf"));
            }
            
            doc.open();
            
            Consumer<Eleitor> consumer = s -> { 
                
                
                try {   
                    doc.add(new Paragraph("Nome: "+s.getNome()));
                    doc.add(new Paragraph("Data de Nascimento: " + s.getNascimento() + "  Funcionario: " + s.getFuncionario()));
                    doc.add(new Paragraph("E-mail: " + s.getEmail()+ "  Telefone 1: " + s.getTelefone1() + "  Telefone 2: " +  s.getTelefone2()));
                    doc.add(new Paragraph("Voto: " + s.getVoto() + "  Pleito: " + s.getPleito()+"  Alcance: " + s.getAlcance() + "  Colaborador: " + s.getColaborador()));
                    doc.add(new Paragraph("Endereço: " + s.getEndereco() + "  Bairro: " + s.getBairro()  ));
                    doc.add(new Paragraph("Seção: "+s.getSecao() + "  Zona: " + s.getZona() + "  Regional: " + s.getRegiao()));
                    doc.add(new Paragraph("Observação: " + s.getObservacao()));
                    doc.add(new Paragraph("_____________________________________________________________________________"));

                } catch (DocumentException ex) {
                    Logger.getLogger(TelaGerarPDF.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            };
            eleitores.stream().forEach(consumer);
            
            
        } catch (FileNotFoundException | DocumentException ex) {
            Logger.getLogger(TelaGerarPDF.class.getName()).log(Level.SEVERE, null, ex);
        }

        doc.close();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btAddFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddFiltroActionPerformed
        // TODO add your handling code here:
        addFiltros();
    }//GEN-LAST:event_btAddFiltroActionPerformed

    private void btFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFiltrarActionPerformed
        // TODO add your handling code here:
        
        
        EleitorDAO eleitorDao = new EleitorDAO();
        String[] queries = {
            getQueryTF(textsEleitor, "nome"), 
            getQueryTF(textsColaborador, "colaborador"),
            getQueryCB(alcanceComboBoxes, "alcance"),
            getQueryCB(bairroComboBoxes, "bairro"),
            getQueryCB(zonaComboBoxes, "zona")
        };
        String query = agregarQueries(queries);
        System.out.println(query);
                
        DefaultTableModel dtmEleitores = (DefaultTableModel) jCadastro.getModel();
        
        dtmEleitores.setRowCount(0);
        
        ArrayList<Eleitor>  eleitoresReturn = eleitorDao.getEleitores(query);
        eleitoresReturn.stream().forEach(s -> { 
            Object[] dados = {
                              s.getNome(), s.getNascimento(), s.getFuncionario(),
                              s.getEmail(),s.getTelefone1(),s.getTelefone2(),
                              s.getVoto(),s.getPleito(),s.getAlcance(),s.getColaborador(),
                              s.getEndereco(),s.getBairro(),s.getSecao(),s.getObservacao(),s.getZona(), s.getRegiao()};
            dtmEleitores.addRow(dados);
        });
        
        this.eleitores = eleitoresReturn;
        
        jCadastro.setModel(dtmEleitores);
    }//GEN-LAST:event_btFiltrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelFilters;
    private javax.swing.JScrollPane ScrollFilters;
    private javax.swing.JButton btAddFiltro;
    private javax.swing.JButton btFiltrar;
    private javax.swing.JButton jButton1;
    private javax.swing.JTable jCadastro;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
