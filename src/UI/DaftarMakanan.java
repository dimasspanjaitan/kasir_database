/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author PANJAITAN
 */
public class DaftarMakanan extends javax.swing.JFrame {

    public ArrayList nama = new ArrayList();
    private String idCount = "";
    
    private void TampilkanData(){
        DefaultTableModel model = new DefaultTableModel();
        
        model.addColumn("No. ");
        model.addColumn("Nama");
        model.addColumn("Harga (Rp)");
        model.addColumn("");
        
        try{
            int no = 1;
//            String sql = "SELECT * FROM makanan";
//            java.sql.Connection conn = (Connection)Konfig.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);

            java.sql.ResultSet res = MySQL.GetData("SELECT * FROM makanan");
            
            while(res.next()){
                model.addRow(new Object[]{no++, res.getString(2), res.getString(3), res.getString(1)});
            }
            tabelMakanan.setModel(model);
            tabelMakanan.getColumnModel().getColumn(3).setMaxWidth(0);       
            tabelMakanan.getColumnModel().getColumn(3).setMinWidth(0);
        }catch(SQLException e){
            System.out.println("Error : " + e.getMessage());
        }
    }
    
    private boolean cekKosong(boolean makanan){
        boolean hasil = true;
        if(txtTambahNamaMakanan.getText().isEmpty() || txtTambahHargaMakanan.getText().isEmpty() == true){
            JOptionPane.showMessageDialog(null, "Nama atau Harga Makanan Harus Diisi!");
            hasil=true;
        }
        return false;
    }
    
    private boolean cekDataTable(String data){
        boolean hasil = nama.contains(data);
        return hasil;
    }
    
    private void Tambah(){
        if(cekKosong(true) == false){
            String menuItem = txtTambahNamaMakanan.getText();
            
            if(cekDataTable(menuItem) == false){
                nama.add(menuItem);
                try{
                String sql = "INSERT INTO makanan (nama,harga) VALUES ('"+txtTambahNamaMakanan.getText()+"','"+txtTambahHargaMakanan.getText()+"')";
                java.sql.Connection conn = (Connection)Konfig.configDB();
                java.sql.PreparedStatement pstm = conn.prepareStatement(sql);
                pstm.execute();
                JOptionPane.showMessageDialog(null, "Berhasil disimpan");
                TampilkanData();
                Hapus();
                }catch(HeadlessException | SQLException e){
                     JOptionPane.showMessageDialog(this, e.getMessage());
                }
            }else{
                try{
                    String sql = "UPDATE makanan SET harga='"+txtTambahHargaMakanan.getText()+"' WHERE id='"+idCount+"'";
                    java.sql.Connection conn = (Connection)Konfig.configDB();
                    java.sql.PreparedStatement pstm = conn.prepareStatement(sql);
                    pstm.execute();
                    JOptionPane.showMessageDialog(null, "Update data berhasil ");
                    TampilkanData();
                }catch(HeadlessException | SQLException e){
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }
            }
        }
    }
    
    private void Hapus(){
        txtTambahNamaMakanan.setText(null);
        txtTambahHargaMakanan.setText(null);
    }
    
    public void Tema(){
        try{
            UIManager.setLookAndFeel(new AluminiumLookAndFeel());
        }catch(UnsupportedLookAndFeelException ex){
            ex.printStackTrace();
        }
    }
    
    /**
     * Creates new form DaftarMakanan
     */
    public DaftarMakanan() {
        initComponents();
        ImageIcon imageicon = new ImageIcon("src/Image/logo.png");
        setIconImage(imageicon.getImage());
        TampilkanData();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnSimpanMakanan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelMakanan = new javax.swing.JTable();
        btnBatalMakanan = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTambahNamaMakanan = new javax.swing.JTextField();
        txtTambahHargaMakanan = new javax.swing.JTextField();
        btnHapus = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Daftar Makanan");

        btnSimpanMakanan.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnSimpanMakanan.setText("Simpan");
        btnSimpanMakanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanMakananActionPerformed(evt);
            }
        });

        tabelMakanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabelMakanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelMakananMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelMakanan);

        btnBatalMakanan.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnBatalMakanan.setText("Batal / Clear");
        btnBatalMakanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalMakananActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Tambahkan Menu Makanan");

        jLabel3.setText("Nama Makanan");

        jLabel4.setText("Harga Makanan");

        txtTambahHargaMakanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTambahHargaMakananActionPerformed(evt);
            }
        });

        btnHapus.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(18, 18, 18))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(16, 16, 16)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTambahHargaMakanan)
                                    .addComponent(txtTambahNamaMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnBatalMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSimpanMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addGap(117, 117, 117)))
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(214, 214, 214)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnHapus)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTambahNamaMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTambahHargaMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBatalMakanan)
                    .addComponent(btnSimpanMakanan))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanMakananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanMakananActionPerformed
        // TODO add your handling code here:
        Tambah();
    }//GEN-LAST:event_btnSimpanMakananActionPerformed

    private void btnBatalMakananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalMakananActionPerformed
        // TODO add your handling code here:
        Hapus();
    }//GEN-LAST:event_btnBatalMakananActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        int baris = tabelMakanan.getSelectedRow();
        String id2 = tabelMakanan.getValueAt(baris, 3).toString();
        try{
            String sql = "DELETE FROM makanan WHERE id='"+id2+"'";
            System.out.println(sql);
            java.sql.Connection conn = (Connection)Konfig.configDB();
            java.sql.PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.execute();     
            JOptionPane.showMessageDialog(null, "Hapus Data Berhasil");
            Hapus();
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        TampilkanData();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tabelMakananMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelMakananMouseClicked
        // TODO add your handling code here:
        int baris = tabelMakanan.rowAtPoint(evt.getPoint());
        String nama2 = tabelMakanan.getValueAt(baris, 1).toString();
        txtTambahNamaMakanan.setText(nama2);
        String harga2 = tabelMakanan.getValueAt(baris, 2).toString();
        txtTambahHargaMakanan.setText(harga2);
        
        idCount = tabelMakanan.getValueAt(baris, 3).toString();
    }//GEN-LAST:event_tabelMakananMouseClicked

    private void txtTambahHargaMakananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTambahHargaMakananActionPerformed
        // TODO add your handling code here:
        Tambah();
    }//GEN-LAST:event_txtTambahHargaMakananActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DaftarMakanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DaftarMakanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DaftarMakanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DaftarMakanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
        Login frame = new Login();
        frame.Tema();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DaftarMakanan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatalMakanan;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpanMakanan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelMakanan;
    private javax.swing.JTextField txtTambahHargaMakanan;
    private javax.swing.JTextField txtTambahNamaMakanan;
    // End of variables declaration//GEN-END:variables
}
