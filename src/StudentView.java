
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;  
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author gianc
 */
public class StudentView extends javax.swing.JFrame {

    /**
     * Creates new form StudentView
     */
    public StudentView() {
        initComponents();
        this.dispose();
        setSize(947, 586);
        this.setUndecorated(true);
        setLocationRelativeTo(null); // Centers the frame
        this.setVisible(true);
        loadTableData();
        try {
            Connection();
        } catch (SQLException ex) {
            Logger.getLogger(TeacherRegister.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Connection con;
        Statement state;
        PreparedStatement pst;
    
    private static final String DbaseName = "tregister";
    private static final String DbaseDriver = "com.mysql.cj.jdbc.Driver";
    private static final String DbaseUrl = "jdbc:mysql://localhost:3306/"+DbaseName;
    private static final String DbaseUsername = "root";
    private static final String DbasePassword = "";
    
    public void Connection () throws SQLException{
        try {
            Class.forName(DbaseDriver);
            con = DriverManager.getConnection(DbaseUrl, DbaseUsername, DbasePassword);
            state = con.createStatement();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TeacherRegister.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void loadTableData() {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

    try {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/tregister", "root", "");
        String sql = "SELECT * FROM saccounts";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            String fname = rs.getString("SFIRSTN");
            String lname = rs.getString("SLASTN");
            String idnum = rs.getString("SIDNUM");
            String section = rs.getString("SECTION");
            model.addRow(new Object[]{fname, lname, idnum, section});
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
}
    public void fetchAndDisplayGrades(String studentId) {
    try {
        // Query to fetch student information and grades
        String sql = "SELECT firstname, lastname, id_num, comprog, pe, conart, entrep, eapp, iii FROM grades WHERE id_num = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, studentId); // Set the student ID

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            // Extract student information and grades
            String name = rs.getString("firstname") + " " + rs.getString("lastname");
            String id = rs.getString("id_num");
            String grades = "ComProg: " + rs.getString("comprog") + "\n" +
                            "PE: " + rs.getString("pe") + "\n" +
                            "ConArt: " + rs.getString("conart") + "\n" +
                            "Entrep: " + rs.getString("entrep") + "\n" +
                            "EApp: " + rs.getString("eapp") + "\n" +
                            "III: " + rs.getString("iii");

            JOptionPane.showMessageDialog(null, "Student ID: " + id + "\nName: " + name + "\n\n" + grades,
                                          "Grades for " + name, JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No grades found for student ID: " + studentId);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error fetching grades: " + e.getMessage());
    }
    }
    public void fetchAndViewGrades(String studentId) {
    try {
        String sql = "SELECT firstname, lastname, id_num, comprog, pe, conart, entrep, eapp, iii, backsubject FROM grades WHERE id_num = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, studentId);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            // Quarter selection dropdown
            String[] quarters = {"1st Quarter", "2nd Quarter"};
            JComboBox<String> quarterComboBox = new JComboBox<>(quarters);

            // Text fields (disabled for viewing only)
            JTextField firstnameField = new JTextField(rs.getString("firstname"));
            firstnameField.setEnabled(false);
            JTextField lastnameField = new JTextField(rs.getString("lastname"));
            lastnameField.setEnabled(false);
            JTextField idNumField = new JTextField(rs.getString("id_num"));
            idNumField.setEnabled(false);
            JTextField comprogField = new JTextField(rs.getString("comprog"));
            comprogField.setEnabled(false);
            JTextField peField = new JTextField(rs.getString("pe"));
            peField.setEnabled(false);
            JTextField conartField = new JTextField(rs.getString("conart"));
            conartField.setEnabled(false);
            JTextField entrepField = new JTextField(rs.getString("entrep"));
            entrepField.setEnabled(false);
            JTextField eappField = new JTextField(rs.getString("eapp"));
            eappField.setEnabled(false);
            JTextField iiiField = new JTextField(rs.getString("iii"));
            iiiField.setEnabled(false);
            JTextField backsubjectField = new JTextField(rs.getString("backsubject"));
            backsubjectField.setEnabled(false);

            // Panel layout
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            // Add fields to panel
            panel.add(new JLabel("Select Quarter:"));
            panel.add(quarterComboBox);
            panel.add(new JLabel("First Name:"));
            panel.add(firstnameField);
            panel.add(new JLabel("Last Name:"));
            panel.add(lastnameField);
            panel.add(new JLabel("Student ID:"));
            panel.add(idNumField);
            panel.add(new JLabel("ComProg:"));
            panel.add(comprogField);
            panel.add(new JLabel("PE:"));
            panel.add(peField);
            panel.add(new JLabel("ConArt:"));
            panel.add(conartField);
            panel.add(new JLabel("Entrep:"));
            panel.add(entrepField);
            panel.add(new JLabel("EApp:"));
            panel.add(eappField);
            panel.add(new JLabel("III:"));
            panel.add(iiiField);
            panel.add(new JLabel("Backsubject:"));
            panel.add(backsubjectField);

            // Show dialog with view-only fields
            JOptionPane.showMessageDialog(null, panel, "View Grades", JOptionPane.INFORMATION_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(null, "No record found for student ID: " + studentId);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        viewGradesMenuItem = new javax.swing.JMenuItem();
        jTextField4 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        viewGradesMenuItem.setText("View Grades");
        viewGradesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewGradesMenuItemActionPerformed(evt);
            }
        });
        jPopupMenu1.add(viewGradesMenuItem);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField4.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jTextField4.setText("Search");
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });
        getContentPane().add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 760, 30));

        jTable1.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "First Name", "Last Name", "ID Number", "Section"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable1MouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 760, 480));

        jButton1.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Home");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 550, 290, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Untitled design (5).png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        // TODO add your handling code here:
        DefaultTableModel ob = (DefaultTableModel) jTable1.getModel();
        TableRowSorter<DefaultTableModel> obj=new TableRowSorter<>(ob);
        jTable1.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(jTextField4.getText()));
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased
        // TODO add your handling code here:
        if (evt.isPopupTrigger()) {
            int row = jTable1.rowAtPoint(evt.getPoint());
            if (row >= 0 && row < jTable1.getRowCount()) {
                jTable1.setRowSelectionInterval(row, row);
            }
            jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jTable1MouseReleased

    private void viewGradesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewGradesMenuItemActionPerformed
        // TODO add your handling code here:
         int selectedRow = jTable1.getSelectedRow();
    if (selectedRow >= 0) {
        String studentId = jTable1.getValueAt(selectedRow, 2).toString();
        fetchAndViewGrades(studentId);
    } else {
        JOptionPane.showMessageDialog(null, "Please select a student row first.");
    }
    }//GEN-LAST:event_viewGradesMenuItemActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new StudentHome().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(StudentView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JMenuItem viewGradesMenuItem;
    // End of variables declaration//GEN-END:variables
}
