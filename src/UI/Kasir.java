/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
/**
 *
 * @author PANJAITAN
 */
public class Kasir extends javax.swing.JFrame {
    
    public ArrayList id = new ArrayList();
    public ArrayList menu = new ArrayList();
    public ArrayList harga = new ArrayList();
    public ArrayList jumlah = new ArrayList();
    public ArrayList subtotal = new ArrayList();
    private String idCount = "";
    private boolean pilihMakanan;
    
    private void setMakanan(){
        try{
            java.sql.ResultSet res = MySQL.GetData("SELECT * FROM makanan");
            while(res.next()){
                cbMakanan.addItem(res.getString("nama"));
            }
        }catch(SQLException e){
            System.out.println("Error : " + e.getMessage());
        }
        txtHargaMakanan.setText(null);
    }
    
    private void setMinuman(){
        try{
            java.sql.ResultSet res = MySQL.GetData("SELECT * FROM minuman");
            while(res.next()){
                cbMinuman.addItem(res.getString("nama"));
            }
        }catch(SQLException e){
            System.out.println("Error : " + e.getMessage());
        }
        txtHargaMinuman.setText(null);
    }
    
//    private void setBayar(){
//        Object[] ob = new Object[10];
//        try{
//            java.sql.ResultSet res = MySQL.GetData("SELECT * FROM transaksi_detail_view ORDER BY transaksi_id DESC LIMIT 1");
//            while(res.next()){
//                ob[1] = res.getString(2);
//            }
//            
////            while(res2.next()){
////                
////            }
//        }catch(SQLException e){
//            System.out.println("Error : " + e.getMessage());
//        }
//        java.sql.ResultSet res2 = MySQL.GetData("SELECT * FROM transaksi_detail_view WHERE transaksi_id='"+ob[1]+"' ");
//        JOptionPane.showMessageDialog(null, "Menu : " + res2.getString("menu"), "Terima Kasih sudah Makan di Tempat Kami :) \n", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/Image/logo.png"));
//        
//    }
    
    private void setTable(){
        int total=0;
        double pajak=0;
        int bayar=0;
        
        DefaultTableModel tbl = new DefaultTableModel();
        
        tbl.addColumn("Menu");
        tbl.addColumn("Harga (Rp.)");
        tbl.addColumn("Jumlah");
        tbl.addColumn("Sub Total (Rp.)");
        tbl.addColumn("");
        
        try{
            java.sql.ResultSet res = MySQL.GetData("SELECT * FROM transaksi_sementara");
            while(res.next()){
                total += Integer.parseInt(res.getString(5));
                tbl.addRow(new Object[]{res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(1)});
            }
            tabelData.setModel(tbl);
            tabelData.getColumnModel().getColumn(4).setMaxWidth(0);       
            tabelData.getColumnModel().getColumn(4).setMinWidth(0);
        }catch(SQLException e){
            System.out.println("Error : " + e.getMessage());
        }
        
//        for(int n=0; n<menu.size(); n++){
//            total = total + ;
//        }
        
        pajak = total * 0.03;
        bayar = (int) (total + pajak);
        
        tabelData.setModel(tbl);
        txtTotal.setText("" + total);
        txtPajak.setText("" + pajak);
        txtTotalBayar.setText("" + bayar);
    }
    
    private boolean cekKosong(boolean makanan){
        boolean hasil = true;
        if(makanan == true){
            if(txtJumlahMakanan.getText().isEmpty() == true){
                JOptionPane.showMessageDialog(null, "Jumlah Makanan Harus Diisi!");
                hasil=true;
            }
        }else{
            if(txtJumlahMinuman.getText().isEmpty() == true){
                JOptionPane.showMessageDialog(null, "Jumlah Minuman Harus Diisi!");
            }
        }
        return false;
    }
    
    private boolean cekDataTable(String data){
        boolean hasil = menu.contains(data);
//        if(hasil==true) JOptionPane.showMessageDialog(null, "Menu Sudah Ditambahkan Sebelumnya.");
        return hasil;
    }
    
    private void tambahMakanan(){
        pilihMakanan = true;
        
        if(cekKosong(true) == false){
            String menuItem = cbMakanan.getSelectedItem().toString();
            if(cekDataTable(menuItem)==false){
                id.add("");
                menu.add(menuItem);
                harga.add(txtHargaMakanan.getText());
                jumlah.add(txtJumlahMakanan.getText());
                subtotal.add(txtSubTotalMakanan.getText());

                try{
                    String sql = "INSERT INTO transaksi_sementara (nama,harga,jumlah,sub_total) VALUES ('"+menuItem+"','"+txtHargaMakanan.getText()+"','"+txtJumlahMakanan.getText()+"','"+txtSubTotalMakanan.getText()+"')"; 
                    java.sql.Connection conn = (Connection)Konfig.configDB();
                    java.sql.PreparedStatement pstm = conn.prepareStatement(sql);
                    pstm.execute();
                    setTable();
                }catch(HeadlessException | SQLException e){
                    JOptionPane.showMessageDialog(this, e);
                }
            }else{
                try{
                    String sql = "UPDATE transaksi_sementara SET jumlah='"+txtJumlahMakanan.getText()+"',sub_total='"+txtSubTotalMakanan.getText()+"' WHERE id='"+idCount+"'";
                    java.sql.Connection conn = (Connection)Konfig.configDB();
                    java.sql.PreparedStatement pstm = conn.prepareStatement(sql);
                    pstm.execute();
                    setTable();
                }catch(HeadlessException | SQLException e){
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }
            }
        }
        cbMakanan.setSelectedIndex(0);
        txtHargaMakanan.setText(null);
        txtJumlahMakanan.setText(null);
        txtSubTotalMakanan.setText(null);
    }
    
    private void tambahMinuman(){
        pilihMakanan = false;
        
        if(cekKosong(false) == false){
            String menuItem = cbMinuman.getSelectedItem().toString();
            if(cekDataTable(menuItem)==false){
                id.add("");
                menu.add(menuItem);
                harga.add(txtHargaMinuman.getText());
                jumlah.add(txtJumlahMinuman.getText());
                subtotal.add(txtSubTotalMinuman.getText());

                try{
                    String sql = "INSERT INTO transaksi_sementara (nama,harga,jumlah,sub_total) VALUES ('"+menuItem+"','"+txtHargaMinuman.getText()+"','"+txtJumlahMinuman.getText()+"','"+txtSubTotalMinuman.getText()+"')"; 
                    java.sql.Connection conn = (Connection)Konfig.configDB();
                    java.sql.PreparedStatement pstm = conn.prepareStatement(sql);
                    pstm.execute();
                    setTable();
                }catch(HeadlessException | SQLException e){
                    JOptionPane.showMessageDialog(this, e);
                }
            }else{
                try{
                    String sql = "UPDATE transaksi_sementara SET jumlah='"+txtJumlahMinuman.getText()+"',sub_total='"+txtSubTotalMinuman.getText()+"' WHERE id='"+idCount+"'";
                    java.sql.Connection conn = (Connection)Konfig.configDB();
                    java.sql.PreparedStatement pstm = conn.prepareStatement(sql);
                    pstm.execute();
                    JOptionPane.showMessageDialog(null, "Edit Data Berhasil");
                    setTable();
                }catch(HeadlessException | SQLException e){
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }
            }
        }
        cbMinuman.setSelectedIndex(0);
        txtHargaMinuman.setText(null);
        txtJumlahMinuman.setText(null);
        txtSubTotalMinuman.setText(null);
    }
    
    private void hapusData(){
        try{
            String sql = "DELETE FROM transaksi_sementara";
            java.sql.Connection conn = (Connection)Konfig.configDB();
            java.sql.PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.execute();
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        
        setTable();
    }
    
    public void Tema(){
        try{
            UIManager.setLookAndFeel(new AluminiumLookAndFeel());
        }catch(UnsupportedLookAndFeelException ex){
            ex.printStackTrace();
        }
    }
    
    /**
     * Creates new form Kasir
     */
    public Kasir(){
        initComponents();
        ImageIcon imageicon = new ImageIcon("src/Image/logo.png");
        setIconImage(imageicon.getImage());
        setMakanan();
        setMinuman();
        setTable();
        hapusData();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        content = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbMakanan = new javax.swing.JComboBox<>();
        txtHargaMakanan = new javax.swing.JLabel();
        txtJumlahMakanan = new javax.swing.JTextField();
        txtSubTotalMakanan = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelData = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JLabel();
        txtPajak = new javax.swing.JLabel();
        txtTotalBayar = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnBayar = new javax.swing.JButton();
        txtJumlahBayar = new javax.swing.JTextField();
        cbMinuman = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtHargaMinuman = new javax.swing.JLabel();
        txtJumlahMinuman = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtSubTotalMinuman = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        menuKasir = new javax.swing.JMenu();
        menuTransaksi = new javax.swing.JMenuItem();
        menuDaftar = new javax.swing.JMenu();
        daftarMakanan = new javax.swing.JMenuItem();
        daftarMinuman = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();
        clear = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        content.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
        jLabel1.setText("Rumah Makan diKey");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Menu Makanan :");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Harga :    Rp.");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Jumlah :");

        cbMakanan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " - PILIH -" }));
        cbMakanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMakananActionPerformed(evt);
            }
        });

        txtHargaMakanan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtHargaMakanan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        txtJumlahMakanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtJumlahMakananKeyTyped(evt);
            }
        });

        txtSubTotalMakanan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        tabelData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tabelData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelDataMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelData);
        if (tabelData.getColumnModel().getColumnCount() > 0) {
            tabelData.getColumnModel().getColumn(0).setMinWidth(0);
        }

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Total :    Rp.");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Pajak (3%) :    Rp.");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Total Bayar :    Rp.");

        txtTotal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtPajak.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtTotalBayar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Jumlah Bayar :    Rp.");

        btnBayar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnBayar.setText("Bayar");
        btnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarActionPerformed(evt);
            }
        });

        cbMinuman.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " - PILIH -" }));
        cbMinuman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMinumanActionPerformed(evt);
            }
        });

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Menu Minuman : ");

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Harga :    Rp.");

        txtHargaMinuman.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtHargaMinuman.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        txtJumlahMinuman.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtJumlahMinumanKeyTyped(evt);
            }
        });

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Jumlah :");

        txtSubTotalMinuman.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout contentLayout = new javax.swing.GroupLayout(content);
        content.setLayout(contentLayout);
        contentLayout.setHorizontalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentLayout.createSequentialGroup()
                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contentLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(contentLayout.createSequentialGroup()
                                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPajak, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtJumlahBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTotalBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(contentLayout.createSequentialGroup()
                                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(contentLayout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtHargaMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(contentLayout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(contentLayout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtSubTotalMakanan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtJumlahMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(contentLayout.createSequentialGroup()
                                        .addGap(136, 136, 136)
                                        .addComponent(txtSubTotalMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(contentLayout.createSequentialGroup()
                                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cbMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(contentLayout.createSequentialGroup()
                                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtHargaMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(contentLayout.createSequentialGroup()
                                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtJumlahMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(contentLayout.createSequentialGroup()
                        .addGap(219, 219, 219)
                        .addComponent(jLabel1)))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        contentLayout.setVerticalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(44, 44, 44)
                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contentLayout.createSequentialGroup()
                        .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cbMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtHargaMakanan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtJumlahMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(txtSubTotalMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contentLayout.createSequentialGroup()
                        .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(cbMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtHargaMinuman, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16))
                        .addGap(18, 18, 18)
                        .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtJumlahMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(txtSubTotalMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contentLayout.createSequentialGroup()
                        .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPajak, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(contentLayout.createSequentialGroup()
                        .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(txtTotalBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtJumlahBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(btnBayar)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        desktopPane.add(content);
        content.setBounds(0, 0, 730, 610);

        menuKasir.setMnemonic('f');
        menuKasir.setText("Kasir");

        menuTransaksi.setMnemonic('o');
        menuTransaksi.setText("Transaksi");
        menuTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTransaksiActionPerformed(evt);
            }
        });
        menuKasir.add(menuTransaksi);

        menuBar.add(menuKasir);

        menuDaftar.setMnemonic('e');
        menuDaftar.setText("Daftar");

        daftarMakanan.setMnemonic('t');
        daftarMakanan.setText("Makanan");
        daftarMakanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                daftarMakananActionPerformed(evt);
            }
        });
        menuDaftar.add(daftarMakanan);

        daftarMinuman.setMnemonic('y');
        daftarMinuman.setText("Minuman");
        daftarMinuman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                daftarMinumanActionPerformed(evt);
            }
        });
        menuDaftar.add(daftarMinuman);

        menuBar.add(menuDaftar);

        menuEdit.setText("Edit");

        clear.setText("Clear");
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });
        menuEdit.add(clear);

        menuBar.add(menuEdit);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void daftarMakananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_daftarMakananActionPerformed
        // TODO add your handling code here:
        new DaftarMakanan().show();
    }//GEN-LAST:event_daftarMakananActionPerformed

    private void daftarMinumanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_daftarMinumanActionPerformed
        // TODO add your handling code here:
        new DaftarMinuman().show();
    }//GEN-LAST:event_daftarMinumanActionPerformed

    private void cbMakananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMakananActionPerformed
        // TODO add your handling code here:
        try{
            java.sql.ResultSet res = MySQL.GetData("SELECT * FROM makanan WHERE nama='"+cbMakanan.getSelectedItem()+"'");
            
            while(res.next()){
                Object[] ob = new Object[3];
                ob[2] = res.getString(3);
                
                txtHargaMakanan.setText((String) ob[2]);
            }
        }catch(SQLException e){
            System.out.println("Error : " + e.getMessage());
        }
        txtJumlahMakanan.setText(null);
        txtSubTotalMakanan.setText(null);
    }//GEN-LAST:event_cbMakananActionPerformed

    private void txtJumlahMakananKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJumlahMakananKeyTyped
        // TODO add your handling code here:
        try{
            int hargaMakanan = Integer.parseInt(txtHargaMakanan.getText());
            int jumlahMakanan = Integer.parseInt(txtJumlahMakanan.getText());
            
            txtSubTotalMakanan.setText("" + (hargaMakanan*jumlahMakanan));
            
            tambahMakanan();
        }catch(NumberFormatException e){
            txtSubTotalMakanan.setText(null);
        }
    }//GEN-LAST:event_txtJumlahMakananKeyTyped

    private void btnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarActionPerformed
        // TODO add your handling code here:
        int jumlahBayar = Integer.parseInt(txtJumlahBayar.getText());
        int totalBayar = Integer.parseInt(txtTotalBayar.getText());
        
//        txtKembalian.setText("" + (jumlahBayar-totalBayar));
        
        Date todaysDate = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String kode = df.format(todaysDate);
        String sql = "INSERT INTO transaksi (kode,jumlah_bayar,pajak) VALUES ('"+kode+"','"+txtJumlahBayar.getText()+"','"+txtPajak.getText()+"')";
        MySQL.Insert(sql);
        
        java.sql.ResultSet transaksi = MySQL.GetData("SELECT * FROM transaksi ORDER BY id DESC LIMIT 1");
        
        String transaksiId = null;
        try {
            while(transaksi.next()){
                transaksiId = transaksi.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Kasir.class.getName()).log(Level.SEVERE, null, ex);
        }
        int kolo= tabelData.getRowCount();
        for(int i=0; i<tabelData.getRowCount(); i++){
            String nama2 = tabelData.getValueAt(i, 0).toString();
            String harga2 = tabelData.getValueAt(i, 1).toString();
            String jumlah2 =  tabelData.getValueAt(i, 2).toString();
            String subTotal2 = tabelData.getValueAt(i, 3).toString();

            String sql_detail = "INSERT INTO transaksi_detail (transaksi_id,menu,harga,jumlah,sub_total) VALUES ('"+transaksiId+"','"+nama2+"','"+harga2+"','"+jumlah2+"','"+subTotal2+"')"; 
            MySQL.Insert(sql_detail);
        }
        
        JOptionPane.showMessageDialog(null, "Kembalian    : Rp." + (jumlahBayar-totalBayar), "Terima Kasih :)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/Image/logo.png"));
        
        MySQL.Delete("DELETE FROM transaksi_sementara");
        setTable();
        txtJumlahBayar.setText(null);
        idCount = "";
    }//GEN-LAST:event_btnBayarActionPerformed

    private void tabelDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDataMouseClicked
        // TODO add your handling code here:
        int baris = tabelData.rowAtPoint(evt.getPoint());
        if(pilihMakanan==true){
            String namaMakanan = tabelData.getValueAt(baris, 0).toString();
            cbMakanan.setSelectedItem(namaMakanan);

            String jumlahMakanan = tabelData.getValueAt(baris, 2).toString();
            txtJumlahMakanan.setText(jumlahMakanan);

            idCount = tabelData.getValueAt(baris, 4).toString();
        }else{
            String namaMinuman = tabelData.getValueAt(baris, 0).toString();
            cbMinuman.setSelectedItem(namaMinuman);

            String jumlahMinuman = tabelData.getValueAt(baris, 2).toString();
            txtJumlahMinuman.setText(jumlahMinuman);

            idCount = tabelData.getValueAt(baris, 4).toString();
        }
    }//GEN-LAST:event_tabelDataMouseClicked

    private void menuTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuTransaksiActionPerformed
        // TODO add your handling code here:
        new Transaksi().show();
    }//GEN-LAST:event_menuTransaksiActionPerformed

    private void cbMinumanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMinumanActionPerformed
        // TODO add your handling code here:
        try{
            java.sql.ResultSet res = MySQL.GetData("SELECT * FROM minuman WHERE nama='"+cbMinuman.getSelectedItem()+"'");
            
            while(res.next()){
                Object[] ob = new Object[3];
                ob[2] = res.getString(3);
                
                txtHargaMinuman.setText((String) ob[2]);
            }
        }catch(SQLException e){
            System.out.println("Error : " + e.getMessage());
        }
        txtJumlahMinuman.setText(null);
        txtSubTotalMinuman.setText(null);
    }//GEN-LAST:event_cbMinumanActionPerformed

    private void txtJumlahMinumanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJumlahMinumanKeyTyped
        // TODO add your handling code here:
        try{
            int hargaMinuman = Integer.parseInt(txtHargaMinuman.getText());
            int jumlahMinuman = Integer.parseInt(txtJumlahMinuman.getText());
            
            txtSubTotalMinuman.setText("" + (hargaMinuman*jumlahMinuman));
            
            tambahMinuman();
        }catch(NumberFormatException e){
            txtSubTotalMinuman.setText(null);
        }
    }//GEN-LAST:event_txtJumlahMinumanKeyTyped

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
        // TODO add your handling code here:
        try{
            String sql = "DELETE FROM transaksi_sementara";
            java.sql.Connection conn = (Connection)Konfig.configDB();
            java.sql.PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.execute();     
            JOptionPane.showMessageDialog(null, "Hapus Data Berhasil");
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        
        setTable();
        cbMakanan.setSelectedIndex(0);
        txtHargaMakanan.setText(null);
        txtJumlahMakanan.setText(null);
        txtSubTotalMakanan.setText(null);
        
        txtTotal.setText(null);
        txtPajak.setText(null);
        txtTotalBayar.setText(null);
        txtJumlahBayar.setText(null);
    }//GEN-LAST:event_clearActionPerformed

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
            java.util.logging.Logger.getLogger(Kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
        Login frame = new Login();
        frame.Tema();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Kasir().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBayar;
    private javax.swing.JComboBox<String> cbMakanan;
    private javax.swing.JComboBox<String> cbMinuman;
    private javax.swing.JMenuItem clear;
    private javax.swing.JPanel content;
    private javax.swing.JMenuItem daftarMakanan;
    private javax.swing.JMenuItem daftarMinuman;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuDaftar;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenu menuKasir;
    private javax.swing.JMenuItem menuTransaksi;
    private javax.swing.JTable tabelData;
    private javax.swing.JLabel txtHargaMakanan;
    private javax.swing.JLabel txtHargaMinuman;
    private javax.swing.JTextField txtJumlahBayar;
    private javax.swing.JTextField txtJumlahMakanan;
    private javax.swing.JTextField txtJumlahMinuman;
    private javax.swing.JLabel txtPajak;
    private javax.swing.JLabel txtSubTotalMakanan;
    private javax.swing.JLabel txtSubTotalMinuman;
    private javax.swing.JLabel txtTotal;
    private javax.swing.JLabel txtTotalBayar;
    // End of variables declaration//GEN-END:variables

}
