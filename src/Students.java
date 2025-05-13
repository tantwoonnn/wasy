
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
import java.awt.event.MouseEvent;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author gianc
 */
public class Students extends javax.swing.JFrame {

    /**
     * Creates new form Students
     */
    public Students() {
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
    
    
    public void fetchAndEditGrades(String studentId) {
    try {
        // Query to fetch student information and grades
        String sql = "SELECT firstname, lastname, id_num, comprog, pe, conart, entrep, eapp, iii, backsubject FROM grades WHERE id_num = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, studentId); // Set the student ID

        ResultSet rs = pst.executeQuery();

        // Create combo box for quarter selection
        String[] quarters = {"1st Quarter", "2nd Quarter"};
        JComboBox<String> quarterComboBox = new JComboBox<>(quarters);

        // Create text fields to edit the grades
        JTextField firstnameField = new JTextField();
        JTextField lastnameField = new JTextField();
        JTextField idNumField = new JTextField(studentId); // pre-fill with passed ID
        JTextField comprogField = new JTextField();
        JTextField peField = new JTextField();
        JTextField conartField = new JTextField();
        JTextField entrepField = new JTextField();
        JTextField eappField = new JTextField();
        JTextField iiiField = new JTextField();
        JTextField backsubjectField = new JTextField();

        boolean recordExists = false;

        if (rs.next()) {
            // Populate fields with existing data if found
            firstnameField.setText(rs.getString("firstname"));
            lastnameField.setText(rs.getString("lastname"));
            idNumField.setText(rs.getString("id_num"));
            comprogField.setText(rs.getString("comprog"));
            peField.setText(rs.getString("pe"));
            conartField.setText(rs.getString("conart"));
            entrepField.setText(rs.getString("entrep"));
            eappField.setText(rs.getString("eapp"));
            iiiField.setText(rs.getString("iii"));
            backsubjectField.setText(rs.getString("backsubject"));
            recordExists = true;
        }

        // Build the panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
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
        panel.add(new JLabel("Backsubject: (leave blank if none)"));
        panel.add(backsubjectField);

        int result = JOptionPane.showConfirmDialog(null, panel, recordExists ? "Edit Grades" : "Insert New Grades", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            // Get selected quarter
            String selectedQuarter = (String) quarterComboBox.getSelectedItem();

            // Get entered values
            String updatedFirstname = firstnameField.getText();
            String updatedLastname = lastnameField.getText();
            String updatedIdNum = idNumField.getText();
            String updatedComprog = comprogField.getText();
            String updatedPe = peField.getText();
            String updatedConart = conartField.getText();
            String updatedEntrep = entrepField.getText();
            String updatedEapp = eappField.getText();
            String updatedIii = iiiField.getText();
            String updatedBacksubject = backsubjectField.getText();

            if (recordExists) {
                // Update existing record
                String updateSql = "UPDATE grades SET firstname = ?, lastname = ?, comprog = ?, pe = ?, conart = ?, entrep = ?, eapp = ?, iii = ?, backsubject = ? WHERE id_num = ?";
                PreparedStatement updatePst = con.prepareStatement(updateSql);
                updatePst.setString(1, updatedFirstname);
                updatePst.setString(2, updatedLastname);
                updatePst.setString(3, updatedComprog);
                updatePst.setString(4, updatedPe);
                updatePst.setString(5, updatedConart);
                updatePst.setString(6, updatedEntrep);
                updatePst.setString(7, updatedEapp);
                updatePst.setString(8, updatedIii);
                updatePst.setString(9, updatedBacksubject);
                updatePst.setString(10, updatedIdNum);

                int rowsUpdated = updatePst.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Grades successfully updated for " + selectedQuarter + "!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update grades.");
                }
            } else {
                // Insert new record
                String insertSql = "INSERT INTO grades (firstname, lastname, id_num, comprog, pe, conart, entrep, eapp, iii, backsubject) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertPst = con.prepareStatement(insertSql);
                insertPst.setString(1, updatedFirstname);
                insertPst.setString(2, updatedLastname);
                insertPst.setString(3, updatedIdNum);
                insertPst.setString(4, updatedComprog);
                insertPst.setString(5, updatedPe);
                insertPst.setString(6, updatedConart);
                insertPst.setString(7, updatedEntrep);
                insertPst.setString(8, updatedEapp);
                insertPst.setString(9, updatedIii);
                insertPst.setString(10, updatedBacksubject);

                int rowsInserted = insertPst.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(null, "Grades successfully added for " + selectedQuarter + "!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add grades.");
                }
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error editing/inserting grades: " + e.getMessage());
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
        editGradesMenuItem = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField4 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        viewGradesMenuItem.setText("View Grades");
        viewGradesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewGradesMenuItemActionPerformed(evt);
            }
        });
        jPopupMenu1.add(viewGradesMenuItem);

        editGradesMenuItem.setText("Edit Grade");
        editGradesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editGradesMenuItemActionPerformed(evt);
            }
        });
        jPopupMenu1.add(editGradesMenuItem);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 760, 410));

        jTextField4.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jTextField4.setText("Search");
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });
        getContentPane().add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 760, 30));

        jButton1.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Delete");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 490, 270, 40));

        jButton2.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 0, 0));
        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 510, 70, 60));

        jButton3.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 0, 0));
        jButton3.setText("Report");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 490, 270, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Clark College of Science and Technology.png"))); // NOI18N
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here
    try {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    int selectedIndex = jTable1.getSelectedRow();

    if (selectedIndex != -1) {
        // get SIDNUM from the selected row (column 2)
        String sidnum = model.getValueAt(selectedIndex, 2).toString();

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/tregister", "root", "");
        PreparedStatement pst = con.prepareStatement("DELETE FROM saccounts WHERE SIDNUM = ?");
        pst.setString(1, sidnum);
        pst.executeUpdate();

        JOptionPane.showMessageDialog(null, "Record Deleted");

        // Remove row from table model too
        model.removeRow(selectedIndex);

    } else {
        JOptionPane.showMessageDialog(null, "Please select a row to delete.");
    }

} catch (Exception e) {
    JOptionPane.showMessageDialog(null, e.getMessage());
}
       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        new Teacher().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
         int selectedRow = jTable1.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(null, "Please select a row first.");
        return;
    }

    String name = jTable1.getValueAt(selectedRow, 1).toString();

    JTextField nameField = new JTextField(15);
    nameField.setText(name);
    nameField.setEditable(false);

    String[] focusOptions = {"Classroom", "Annex", "Other"};
    JComboBox<String> focusCombo = new JComboBox<>(focusOptions);

    JTextField reasonField = new JTextField(15);
    JTextField timeField = new JTextField(10);
    JTextField roomField = new JTextField(10);

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.add(new JLabel("Name:"));
    panel.add(nameField);
    panel.add(new JLabel("Where it's Focused:"));
    panel.add(focusCombo);
    panel.add(new JLabel("Reason:"));
    panel.add(reasonField);
    panel.add(new JLabel("Time to Call:"));
    panel.add(timeField);
    panel.add(new JLabel("Room:"));
    panel.add(roomField);

    int result = JOptionPane.showConfirmDialog(null, panel, 
             "Enter Report Details", JOptionPane.OK_CANCEL_OPTION);

    if (result == JOptionPane.OK_OPTION) {
        String focus = focusCombo.getSelectedItem().toString();
        String reason = reasonField.getText();
        String timeToCall = timeField.getText();
        String room = roomField.getText();

        // Optional: Validate inputs
        if (reason.isEmpty() || timeToCall.isEmpty() || room.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please complete all fields.");
            return;
        }

        // Insert into database
        try {
            String sql = "INSERT INTO reports (name, focus, reason, time_to_call, room) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, name);
            pst.setString(2, focus);
            pst.setString(3, reason);
            pst.setString(4, timeToCall);
            pst.setString(5, room);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Report successfully submitted!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    }//GEN-LAST:event_jButton3ActionPerformed

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

    private void editGradesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editGradesMenuItemActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();
    if (selectedRow >= 0) {
        String studentId = jTable1.getValueAt(selectedRow, 2).toString(); // adjust if id_num is not in column 2
        fetchAndEditGrades(studentId);
    } else {
        JOptionPane.showMessageDialog(null, "Please select a student row first.");
    }
    }//GEN-LAST:event_editGradesMenuItemActionPerformed

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
            java.util.logging.Logger.getLogger(Students.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Students.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Students.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Students.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Students().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem editGradesMenuItem;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JMenuItem viewGradesMenuItem;
    // End of variables declaration//GEN-END:variables
}
