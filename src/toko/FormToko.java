package toko;

import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FormToko extends JFrame {
    private String[] judul = {"Kode Barang", "Nama Barang", "Harga Barang", "Stock Barang"};
    DefaultTableModel df;
    JTable tab = new JTable();
    JScrollPane scp = new JScrollPane();
    JPanel pnl = new JPanel();
    JLabel lblKode = new JLabel("Kode Barang");
    JTextField txKode = new JTextField(20);
    JLabel lblNama = new JLabel("Nama Barang");
    JTextField txNama = new JTextField(20);
    JLabel lblHarga = new JLabel("Harga Barang");
    JTextField txHarga = new JTextField(10);
    JLabel lblStock = new JLabel("Stock Barang");
    JTextField txStock = new JTextField(10);
    JButton btadd = new JButton("Simpan");
    JButton btnew = new JButton("Baru");
    JButton btdel = new JButton("Hapus");
    JButton btedit = new JButton("Ubah");

    public FormToko() {
        super("Data Barang");
        setSize(460, 300);
        pnl.setLayout(null);
        pnl.add(lblKode);
        lblKode.setBounds(20, 10, 100, 20);
        pnl.add(txKode);
        txKode.setBounds(125, 10, 100, 20);
        pnl.add(lblNama);
        lblNama.setBounds(20, 33, 100, 20);
        pnl.add(txNama);
        txNama.setBounds(125, 33, 175, 20);
        pnl.add(lblHarga);
        lblHarga.setBounds(20, 56, 100, 20);
        pnl.add(txHarga);
        txHarga.setBounds(125, 56, 175, 20);
        pnl.add(lblStock);
        lblStock.setBounds(20, 79, 100, 20);
        pnl.add(txStock);
        txStock.setBounds(125, 79, 175, 20);

        pnl.add(btnew);
        btnew.setBounds(300, 10, 125, 20);
        btnew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnewAksi(e);
            }
        });
        pnl.add(btadd);
        btadd.setBounds(300, 33, 125, 20);
        btadd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btaddAksi(e);
            }
        });
        pnl.add(btedit);
        btedit.setBounds(300, 56, 125, 20);
        btedit.setEnabled(false);
        btedit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bteditAksi(e);
            }
        });
        pnl.add(btdel);
        btdel.setBounds(300, 79, 125, 20);
        btdel.setEnabled(false);
        btdel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btdelAksi(e);
            }
        });
        df = new DefaultTableModel(null, judul);
        tab.setModel(df);
        scp.getViewport().add(tab);
        tab.setEnabled(true);
        tab.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tabMouseClicked(evt);
            }
        });
        scp.setBounds(20, 110, 405, 130);
        pnl.add(scp);
        getContentPane().add(pnl);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    void loadData() {
        try {
            Connection cn = new connectDB().getConnect();
            Statement st = cn.createStatement();
            String sql = "SELECT * FROM tbl_barang";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String kode, nama, harga, stock;
                kode = rs.getString("Kode_barang");
                nama = rs.getString("Nama_barang");
                harga = rs.getString("harga_barang");
                stock = rs.getString("stock_barang");
                String[] data = {kode, nama, harga, stock};
                df.addRow(data);
            }
            rs.close();
            cn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void clearTable() {
        int numRow = df.getRowCount();
        for (int i = 0; i < numRow; i++) {
            df.removeRow(0);
        }
    }

    void clearTextField() {
        txKode.setText(null);
        txNama.setText(null);
        txHarga.setText(null);
        txStock.setText(null);
    }

    void simpanData(Toko B) {
        try {
            Connection cn = new connectDB().getConnect();
            Statement st = cn.createStatement();
            String sql = "INSERT INTO tbl_barang (Kode_barang, Nama_barang, harga_barang, stock_barang) " +
                    "VALUES ('" + B.getKode() + "', '" + B.getNama() + "', '" + B.getHarga() + "', '" + B.getStock() + "')";
            int result = st.executeUpdate(sql);
            cn.close();
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan",
                    "Info Proses", JOptionPane.INFORMATION_MESSAGE);
            String[] data = {B.getKode(), B.getNama(), B.getHarga(), B.getStock()};
            df.addRow(data);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void hapusData(String kode) {
        try {
            Connection cn = new connectDB().getConnect();
            Statement st = cn.createStatement();
            String sql = "DELETE FROM tbl_barang WHERE Kode_barang = '" + kode + "'";
            int result = st.executeUpdate(sql);
            cn.close();
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus", "Info Proses",
                    JOptionPane.INFORMATION_MESSAGE);
            df.removeRow(tab.getSelectedRow());
            clearTextField();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void ubahData(Toko B, String kode) {
        try {
            Connection cn = new connectDB().getConnect();
            Statement st = cn.createStatement();
            String sql = "UPDATE tbl_barang SET Kode_barang='" + B.getKode() + "', Nama_barang='" + B.getNama() + "', harga_barang='" + B.getHarga() + "', stock_barang='" + B.getStock() + "' WHERE Kode_barang='" + kode + "'";
            int result = st.executeUpdate(sql);
            cn.close();
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah", "Info Proses",
                    JOptionPane.INFORMATION_MESSAGE);
            clearTable();
            loadData();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void btnewAksi(ActionEvent evt) {
        clearTextField();
        btedit.setEnabled(false);
        btdel.setEnabled(false);
        btadd.setEnabled(true);
    }

    private void btaddAksi(ActionEvent evt) {
        Toko B = new Toko();
        B.setKode(txKode.getText());
        B.setNama(txNama.getText());
        B.setHarga(txHarga.getText());
        B.setStock(txStock.getText());
        simpanData(B);
    }

    private void btdelAksi(ActionEvent evt) {
        int selectedRow = tab.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Silakan pilih baris yang akan dihapus.");
            return;
        }
        int status = JOptionPane.showConfirmDialog(null, "Yakin data akan dihapus?",
                "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if (status == 0) {
            hapusData(tab.getValueAt(selectedRow, 0).toString());
        }
    }

    private void bteditAksi(ActionEvent evt) {
        int selectedRow = tab.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Silakan pilih baris yang akan diubah.");
            return;
        }
        Toko B = new Toko();
        B.setKode(txKode.getText());
        B.setNama(txNama.getText());
        B.setHarga(txHarga.getText());
        B.setStock(txStock.getText());
        ubahData(B, tab.getValueAt(selectedRow, 0).toString());
    }

    private void tabMouseClicked(MouseEvent evt) {
        int selectedRow = tab.getSelectedRow();
        if (selectedRow == -1) {
            return; // Tidak ada baris yang dipilih, keluar dari metode
        }
        btedit.setEnabled(true);
        btdel.setEnabled(true);
        btadd.setEnabled(false);
        String kode = tab.getValueAt(selectedRow, 0).toString();
        String nama = tab.getValueAt(selectedRow, 1).toString();
        String harga = tab.getValueAt(selectedRow, 2).toString();
        String stock = tab.getValueAt(selectedRow, 3).toString();
        txKode.setText(kode);
        txNama.setText(nama);
        txHarga.setText(harga);
        txStock.setText(stock);
    }

    public static void main(String[] args) {
        new FormToko().loadData();
    }
}
