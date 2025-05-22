import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class DetalleGUI extends JFrame {
    JTextField cultivoID, responsable, siembra, cosecha, obs;
    DefaultTableModel model;
    JTable table;

    public DetalleGUI() {
        setTitle("Detalle de Cultivo");
        setSize(700, 400);
        setLocationRelativeTo(null);

        cultivoID = new JTextField(); responsable = new JTextField();
        siembra = new JTextField(); cosecha = new JTextField(); obs = new JTextField();

        JButton guardar = new JButton("Registrar Detalle");
        guardar.addActionListener(e -> insertarDetalle());

        JPanel panel = new JPanel(new java.awt.GridLayout(6, 2));
        panel.add(new JLabel("ID Cultivo:")); panel.add(cultivoID);
        panel.add(new JLabel("Responsable:")); panel.add(responsable);
        panel.add(new JLabel("Fecha Siembra (YYYY-MM-DD):")); panel.add(siembra);
        panel.add(new JLabel("Fecha Cosecha (YYYY-MM-DD):")); panel.add(cosecha);
        panel.add(new JLabel("Observaciones:")); panel.add(obs);
        panel.add(guardar);

        model = new DefaultTableModel(new String[]{"ID", "Cultivo", "Responsable", "Siembra", "Cosecha", "Observaciones"}, 0);
        table = new JTable(model);
        add(panel, "North");
        add(new JScrollPane(table), "Center");

        cargarDetalles();
    }

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=agricultura;encrypt=false", "sa", "123456");
    }

    private void insertarDetalle() {
        String sql = "INSERT INTO DetalleCultivo (IDCultivo, DtcResponsable, DtcFechaSiembra, DtcFechaCosecha, DtcObservaciones) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(cultivoID.getText()));
            ps.setString(2, responsable.getText());
            ps.setDate(3, Date.valueOf(siembra.getText()));
            ps.setDate(4, Date.valueOf(cosecha.getText()));
            ps.setString(5, obs.getText());
            ps.executeUpdate();
            cargarDetalles();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void cargarDetalles() {
        model.setRowCount(0);
        try (Connection conn = conectar(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM DetalleCultivo")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("IDDetalleCultivo"),
                        rs.getInt("IDCultivo"),
                        rs.getString("DtcResponsable"),
                        rs.getDate("DtcFechaSiembra"),
                        rs.getDate("DtcFechaCosecha"),
                        rs.getString("DtcObservaciones")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar: " + e.getMessage());
        }
    }
}