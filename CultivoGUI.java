import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class CultivoGUI extends JFrame {
    JTextField tipo, variedad, temporada;
    DefaultTableModel model;
    JTable table;

    public CultivoGUI() {
        setTitle("Gestión de Cultivos");
        setSize(600, 350);
        setLocationRelativeTo(null);

        tipo = new JTextField(); variedad = new JTextField(); temporada = new JTextField();
        JButton guardar = new JButton("Registrar Cultivo");
        guardar.addActionListener(e -> insertarCultivo());

        JPanel panel = new JPanel(new java.awt.GridLayout(4, 2));
        panel.add(new JLabel("Tipo:")); panel.add(tipo);
        panel.add(new JLabel("Variedad:")); panel.add(variedad);
        panel.add(new JLabel("Temporada:")); panel.add(temporada);
        panel.add(guardar);

        model = new DefaultTableModel(new String[]{"ID", "Tipo", "Variedad", "Temporada"}, 0);
        table = new JTable(model);
        add(panel, "North");
        add(new JScrollPane(table), "Center");

        cargarCultivos();
    }

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=agricultura;encrypt=false", "sa", "123456");
    }

    private void insertarCultivo() {
        String sql = "INSERT INTO Cultivo (CulTipo, CulVariedad, CulTemporada) VALUES (?, ?, ?)";
        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tipo.getText());
            ps.setString(2, variedad.getText());
            ps.setString(3, temporada.getText());
            ps.executeUpdate();
            cargarCultivos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void cargarCultivos() {
        model.setRowCount(0);
        try (Connection conn = conectar(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM Cultivo")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("IDCultivo"),
                        rs.getString("CulTipo"),
                        rs.getString("CulVariedad"),
                        rs.getString("CulTemporada")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar: " + e.getMessage());
        }
    }
}