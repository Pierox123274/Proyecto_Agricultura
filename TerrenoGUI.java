import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class TerrenoGUI extends JFrame {
    JTextField nombre, tamano, ubicacion, tipo;
    DefaultTableModel model;
    JTable table;

    public TerrenoGUI() {
        setTitle("Gestión de Terrenos");
        setSize(700, 400);
        setLocationRelativeTo(null);

        nombre = new JTextField(); tamano = new JTextField();
        ubicacion = new JTextField(); tipo = new JTextField();

        JButton guardar = new JButton("Registrar Terreno");
        guardar.addActionListener(e -> insertarTerreno());

        JPanel panel = new JPanel(new java.awt.GridLayout(5, 2));
        panel.add(new JLabel("Nombre:")); panel.add(nombre);
        panel.add(new JLabel("Tamaño:")); panel.add(tamano);
        panel.add(new JLabel("Ubicación:")); panel.add(ubicacion);
        panel.add(new JLabel("Tipo:")); panel.add(tipo);
        panel.add(guardar);

        model = new DefaultTableModel(new String[]{"ID", "Nombre", "Tamaño", "Ubicación", "Tipo"}, 0);
        table = new JTable(model);
        add(panel, "North");
        add(new JScrollPane(table), "Center");

        cargarTerrenos();
    }

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=agricultura;encrypt=false", "sa", "123456");
    }

    private void insertarTerreno() {
        String sql = "INSERT INTO Terreno (terNombre, terTamaño, terUbicacion, terTipo) VALUES (?, ?, ?, ?)";
        try (Connection conn = conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre.getText());
            ps.setDouble(2, Double.parseDouble(tamano.getText()));
            ps.setString(3, ubicacion.getText());
            ps.setString(4, tipo.getText());
            ps.executeUpdate();
            cargarTerrenos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void cargarTerrenos() {
        model.setRowCount(0);
        try (Connection conn = conectar(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM Terreno")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("IDTerreno"),
                        rs.getString("terNombre"),
                        rs.getDouble("terTamaño"),
                        rs.getString("terUbicacion"),
                        rs.getString("terTipo")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar: " + e.getMessage());
        }
    }
}