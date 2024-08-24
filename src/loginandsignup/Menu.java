/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package loginandsignup;






import com.toedter.calendar.JDateChooser;
import javax.swing.JFrame;
import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
/**
 *
 * @author Choeurn chantha
 */
public class Menu extends javax.swing.JFrame {

    /**
     * Creates new form Menu
     */
    public Menu() {
        initComponents();
        IconSetter.setFrameIcon(this, "logo1.png");
        setTitle("Inventory Managament Systems");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        BarChart bar = new BarChart(jPanel10,jTable2);
        DesignTable table2 = new DesignTable(jTable2);
        DesignTableemployee table3 = new DesignTableemployee(jTable3);
        DesignTablePruduct tableProduct = new DesignTablePruduct(jTable1);
        DesignTableStock tableStock = new DesignTableStock(jTable4);
        DesignTableCategory tablecate = new DesignTableCategory(jTable5);
        DesignTableSupplier tablesupp = new DesignTableSupplier(jTable6);
        Gettext text = new Gettext();
        RowSelectionHandler selectionHandler = new RowSelectionHandler(jTable2, jTextField2, jTextField3, jTextField4, jDateChooser1);
        RowSelectionHandlerEmployee selecteEmployee = new RowSelectionHandlerEmployee(jTable3, jTextPane1, jComboBox1, jTextPane3, jTextPane4);
        RowSelectionHandlerProduct rowHandler = new RowSelectionHandlerProduct(jTable1, jTextPane6, jTextPane7, jTextPane8, jTextPane9);       
        RowSelectionHandlerProduct rowHandlerSupplier = new RowSelectionHandlerProduct(jTable6,jTextPane11,jTextPane12,jTextPane13,jTextPane14);
        RowSelectionHandlerCategory rowHandlerCat = new RowSelectionHandlerCategory(jTable5,jTextPane16,jTextPane26);
        RowSelectionHandlerStock rowHandlerStock = new RowSelectionHandlerStock(jTable4, jTextPane21, jTextPane22, jDateChooser2, jTextPane24);

        countProduct();
        countstock();
        countEmployee();
        design();
        setTotal();
    }
// set icon
public class IconSetter {
    public static void setFrameIcon(JFrame frame, String iconPath) {
        
        // Load the icon image
        URL iconURL = frame.getClass().getResource(iconPath);
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            frame.setIconImage(icon.getImage());
        } else {
            System.err.println("Icon not found at: " + iconPath);
        }
    }
}

public class Gettext{
    Gettext(){
        int count = jTable2.getRowCount();;
        String str = String.valueOf(count);
        label1.setText(str);
    }
}   
public void design(){
    Font font = new Font("Arial", Font.PLAIN, 14);
        jCalendar1.setFont(font);

        // Set the background color for the JCalendar
        jCalendar1.setBackground(Color.LIGHT_GRAY);

        // Customizing the day chooser (inside the JCalendar)
        jCalendar1.getDayChooser().setBackground(Color.WHITE);
        jCalendar1.getDayChooser().setFont(font);

        // Customizing the month chooser (inside the JCalendar)
        jCalendar1.getMonthChooser().setBackground(Color.WHITE);
        jCalendar1.getMonthChooser().setFont(font);

        // Customizing the year chooser (inside the JCalendar)
        jCalendar1.getYearChooser().setBackground(Color.WHITE);
        jCalendar1.getYearChooser().setFont(font);

        // Optional: Customizing the border for a cohesive look
        jCalendar1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jCalendar1.getDayChooser().setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jCalendar1.getMonthChooser().setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jCalendar1.getYearChooser().setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
public class BarChart {

    public BarChart(JPanel panel, JTable table) {
        // Assign passed JPanel and JTable to local variables

        // Create Dataset
        CategoryDataset dataset = createDataset();

        // Create chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Bar Chart Dashboard", // Chart title
                "Name", // X-Axis Label
                "Quantity", // Y-Axis Label
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(jPanel10.getWidth(), jPanel10.getHeight()));
        jPanel10.setLayout(new java.awt.BorderLayout());
        jPanel10.add(chartPanel, java.awt.BorderLayout.CENTER);
        jPanel10.validate();
    }

    public CategoryDataset createDataset() {
        // Fetch the row count from the database
        int sale = getRowCountFromDatabase();
        int product = getRowCountFromDatabaseProduct();
        int stock = getRowCountFromDatabaseStock();
        int employee = getRowCountFromDatabaseEmployee();
        System.out.println("Row count (sale): " + sale); // Debug statement

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(sale, "Marks", "Sale");
        dataset.addValue(product, "Marks", "Products");
        dataset.addValue(stock, "Marks", "Stock");
        dataset.addValue(employee, "Marks", "Employee");

        return dataset;
    }

    private int getRowCountFromDatabase() {
        String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String username = "root";
        String password = "12345";
        String query = "SELECT COUNT(*) AS rowcount FROM Sale";

        try (java.sql.Connection connection = DriverManager.getConnection(url, username, password);
             java.sql.Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                return resultSet.getInt("rowcount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; // Return 0 if there's an issue
    }
    private int getRowCountFromDatabaseProduct() {
        String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String username = "root";
        String password = "12345";
        String query = "SELECT COUNT(*) AS rowcount FROM Product2";

        try (java.sql.Connection connection = DriverManager.getConnection(url, username, password);
             java.sql.Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                return resultSet.getInt("rowcount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; // Return 0 if there's an issue
    }
    private int getRowCountFromDatabaseStock() {
        String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String username = "root";
        String password = "12345";
        String query = "SELECT COUNT(*) AS rowcount FROM Stock";

        try (java.sql.Connection connection = DriverManager.getConnection(url, username, password);
             java.sql.Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                return resultSet.getInt("rowcount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; // Return 0 if there's an issue
    }
    private int getRowCountFromDatabaseEmployee() {
        String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String username = "root";
        String password = "12345";
        String query = "SELECT COUNT(*) AS rowcount FROM Employee";

        try (java.sql.Connection connection = DriverManager.getConnection(url, username, password);
             java.sql.Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                return resultSet.getInt("rowcount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; // Return 0 if there's an issue
    }
}

// Table Employee
public class DesignTableemployee {
    private DefaultTableModel model;
    private JTable jTable1;

    public DesignTableemployee(JTable table) {
        this.jTable1 = table;
        initTable();
        loadData();
    }

    private void initTable() {
        // Initialize table model
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Sex");       
        model.addColumn("Position");
        model.addColumn("Salary");
  

        jTable1.setModel(model);

        // Set the table font size
        jTable1.setFont(new Font("Serif", Font.PLAIN, 23));
        jTable1.setRowHeight(50);

        // Ensure row selection is enabled
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add a listener to detect row selection changes
        jTable1.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int selectedRow = jTable1.getSelectedRow();
            }
        });

        // Customize the table header
        JTableHeader header = jTable1.getTableHeader();
        header.setFont(new Font("Serif", Font.BOLD, 25));
        header.setBackground(new Color(0, 225, 225));
        header.setOpaque(true);
        header.setForeground(Color.BLACK);
        jTable1.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(Color.RED); // Color for selected row
                } else {
                    c.setBackground(row % 2 == 0 ? new Color(255, 204, 204) : Color.WHITE); // Alternating row colors
                }
                return c;
            }
        });
    }
    private void loadData() {
        String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String user = "root";
        String password = "12345";

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            try (java.sql.Connection con = DriverManager.getConnection(url, user, password)) {
                String query = "SELECT * FROM Employee"; // Adjust query as needed
                try (PreparedStatement pstmt = con.prepareStatement(query)) {
                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            int id = rs.getInt("employee_id");
                            String product = rs.getString("name");
                            String sex = rs.getString("sex");
                            String position = rs.getString("position");
                            int salary = rs.getInt("salary");
                            model.addRow(new Object[]{id, product, sex, position, salary});
                        }
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to establish connection to the database.");
            e.printStackTrace();
        }
    }
}
// Update Employee
public void updateDataInEmployee(int id) {
        String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String user = "root";
        String password = "12345";

        String name = jTextPane1.getText();
        String sex = (String) jComboBox1.getSelectedItem();
        String position = jTextPane3.getText();
        int salary = Integer.parseInt(jTextPane4.getText());

        try (java.sql.Connection con = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE Employee SET name = ?, sex = ?, position = ?, salary = ? WHERE employee_id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, name);
                pstmt.setString(2, sex);
                pstmt.setString(3, position);
                pstmt.setInt(4, salary);
                pstmt.setInt(5, id);
                
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Updated row with ID: " + id);
                } else {
                    System.out.println("No row found with ID: " + id);
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to update row in database.");
            e.printStackTrace();
        }
    }



public void refreshTable(String table,JTable jTable) {
    String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
    String user = "root";
    String password = "12345";

    try (java.sql.Connection con = DriverManager.getConnection(url, user, password)) {
        String query = "SELECT * FROM "+table;
        try (PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            // Assuming you have a method to create a table model from the ResultSet
            jTable.setModel(buildTableModel(rs));
        }
    } catch (SQLException e) {
        System.err.println("Failed to refresh table data.");
        e.printStackTrace();
    }
}
// refresh product
public void refreshpro(String table, JTable jTable) {
    String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
    String user = "root";
    String password = "12345";

    try (java.sql.Connection con = DriverManager.getConnection(url, user, password)) {
        String query = "SELECT p.product_id, p.name, c.type AS category_type, s.supplier_name, p.Price " +
                       "FROM " + table + " p " +
                       "JOIN Category c ON p.category_id = c.category_id " +
                       "JOIN Supplier s ON p.supplier_id = s.supplier_id";

        try (PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            // Assuming you have a method to create a table model from the ResultSet
            jTable.setModel(buildTableModel(rs));
        }
    } catch (SQLException e) {
        System.err.println("Failed to refresh table data.");
        e.printStackTrace();
    }
}

// Employee get seleted
public class RowSelectionHandlerEmployee {
    private JTable table;
    private JTextPane nameField;
    private JComboBox<String> comboBox;
    private JTextPane positionField;
    private JTextPane salaryField;

    public RowSelectionHandlerEmployee(JTable table, JTextPane nameField, JComboBox<String> comboBox, JTextPane positionField, JTextPane salaryField) {
        this.table = table;
        this.nameField = nameField;
        this.comboBox = comboBox;
        this.positionField = positionField;
        this.salaryField = salaryField;

        // Add a listener to handle row selection
        this.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    populateFields();
                }
            }
        });
    }

    private void populateFields() {
    int selectedRow = table.getSelectedRow();
    if (selectedRow != -1) {
        // Get data from the selected row
        String name = (String) table.getValueAt(selectedRow, 1); // Assuming 2nd column is name
        String sex = (String) table.getValueAt(selectedRow, 2);  // Assuming 3rd column is sex
        String position = (String) table.getValueAt(selectedRow, 3); // Assuming 4th column is position

        // Handle salary which could be Integer or Double
        Object salaryObj = table.getValueAt(selectedRow, 4); // Assuming 5th column is salary
        double salary;
        if (salaryObj instanceof Integer) {
            salary = ((Integer) salaryObj).doubleValue();
        } else if (salaryObj instanceof Double) {
            salary = (Double) salaryObj;
        } else {
            throw new ClassCastException("Unexpected data type for salary.");
        }

        // Populate the comboBox with sex
        comboBox.setSelectedItem(sex);

        // Populate the input fields
        nameField.setText(name);
        positionField.setText(position);

        // Formatting salary with two decimal places
        DecimalFormat df = new DecimalFormat("#.##");
        salaryField.setText(df.format(salary));
    } else {
        System.out.println("No row selected.");
    }
}
}
// Get Product selected
public class RowSelectionHandlerProduct {
    private JTable table;
    private JTextPane textPaneName;
    private JTextPane textPaneType;
    private JTextPane textPaneSupplierName;
    private JTextPane textPanePrice;

    public RowSelectionHandlerProduct(JTable table, JTextPane textPaneName, JTextPane textPaneType, JTextPane textPaneSupplierName, JTextPane textPanePrice) {
        this.table = table;
        this.textPaneName = textPaneName;
        this.textPaneType = textPaneType;
        this.textPaneSupplierName = textPaneSupplierName;
        this.textPanePrice = textPanePrice;

        // Add a listener to handle row selection
        this.table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                populateFields();
            }
        });
    }

    private void populateFields() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Get data from the selected row with appropriate type casting and null checking
            Object nameObj = table.getValueAt(selectedRow, 1); // Assuming 2nd column is name
            Object typeObj = table.getValueAt(selectedRow, 2);  // Assuming 3rd column is type
            Object supplierNameObj = table.getValueAt(selectedRow, 3); // Assuming 4th column is supplier name
            Object priceObj = table.getValueAt(selectedRow, 4);  // Assuming 5th column is price

            // Populate the input fields if values are not null
            textPaneName.setText(nameObj != null ? nameObj.toString() : "");
            textPaneType.setText(typeObj != null ? typeObj.toString() : "");
            textPaneSupplierName.setText(supplierNameObj != null ? supplierNameObj.toString() : "");
            textPanePrice.setText(priceObj != null ? priceObj.toString() : "");
        } else {
            System.out.println("No row selected.");
        }
    }
}

// Get data to input field table Category
public class RowSelectionHandlerCategory {
    private JTable table;
    private JTextPane textPaneType;
    private JTextPane textPaneDescription;

    public RowSelectionHandlerCategory(JTable table, JTextPane textPaneType, JTextPane textPaneDescription) {
        this.table = table;
        this.textPaneType = textPaneType;
        this.textPaneDescription = textPaneDescription;

        // Add a listener to handle row selection
        this.table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                populateFields();
            }
        });
    }

    private void populateFields() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Get data from the selected row with appropriate type casting and null checking
            Object typeObj = table.getValueAt(selectedRow, 1);  // Assuming 2nd column is Type of Product
            Object descriptionObj = table.getValueAt(selectedRow, 2);  // Assuming 3rd column is Description

            // Populate the input fields if values are not null
            textPaneType.setText(typeObj != null ? typeObj.toString() : "");
            textPaneDescription.setText(descriptionObj != null ? descriptionObj.toString() : "");
        } else {
            System.out.println("No row selected.");
        }
    }
}


// refresh from stock
public void refreshStock(JTable jTable) {
    String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
    String user = "root";
    String password = "12345";

    try (java.sql.Connection con = DriverManager.getConnection(url, user, password)) {
        // Query to get data from the Stock table, joining Product2 and Supplier
        String query = "SELECT st.stock_id, p.name AS product_name, st.qty, st.last_update, s.supplier_name " +
                       "FROM Stock st " +
                       "JOIN Product2 p ON st.product_id = p.product_id " +
                       "JOIN Supplier s ON st.supplier_id = s.supplier_id";

        try (PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            // Set the table model with the result set
            jTable.setModel(buildTableModel(rs));
        }
    } catch (SQLException e) {
        System.err.println("Failed to refresh table data.");
        e.printStackTrace();
    }
}
// Get data to input table Stock
public class RowSelectionHandlerStock {
    private JTable table;
    private JTextPane textPaneProductName;
    private JTextPane textPaneQty;
    private JDateChooser dateChooserLastUpdate;
    private JTextPane textPaneSupplierName;

    public RowSelectionHandlerStock(JTable table, JTextPane textPaneProductName, JTextPane textPaneQty, JDateChooser dateChooserLastUpdate, JTextPane textPaneSupplierName) {
        this.table = table;
        this.textPaneProductName = textPaneProductName;
        this.textPaneQty = textPaneQty;
        this.dateChooserLastUpdate = dateChooserLastUpdate;
        this.textPaneSupplierName = textPaneSupplierName;

        // Add a listener to handle row selection
        this.table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                populateFields();
            }
        });
    }

    private void populateFields() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Get data from the selected row with appropriate type casting and null checking
            Object productNameObj = table.getValueAt(selectedRow, 1);  // Assuming 2nd column is Product Name
            Object qtyObj = table.getValueAt(selectedRow, 2);  // Assuming 3rd column is Qty
            Object lastUpdateObj = table.getValueAt(selectedRow, 3);  // Assuming 4th column is Last Update
            Object supplierNameObj = table.getValueAt(selectedRow, 4);  // Assuming 5th column is Supplier Name

            // Populate the input fields if values are not null
            textPaneProductName.setText(productNameObj != null ? productNameObj.toString() : "");
            textPaneQty.setText(qtyObj != null ? qtyObj.toString() : "");
            textPaneSupplierName.setText(supplierNameObj != null ? supplierNameObj.toString() : "");

            // Handle date conversion for JDateChooser
            if (lastUpdateObj != null) {
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(lastUpdateObj.toString());
                    dateChooserLastUpdate.setDate(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                dateChooserLastUpdate.setDate(null);
            }
        } else {
            System.out.println("No row selected.");
        }
    }
}



// Table sale
public class DesignTable {
    private DefaultTableModel model;
    private JTable jTable1;

    public DesignTable(JTable table) {
        this.jTable1 = table;
        initTable();
        loadData();
    }

    private void initTable() {
        // Initialize table model
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Products");
        model.addColumn("Qty");
        model.addColumn("Price");
        model.addColumn("Date");
        model.addColumn("Total");

        jTable1.setModel(model);

        // Set the table font size
        jTable1.setFont(new Font("Serif", Font.PLAIN, 23));
        jTable1.setRowHeight(50);

        // Ensure row selection is enabled
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add a listener to detect row selection changes
        jTable1.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int selectedRow = jTable1.getSelectedRow();
            }
        });

        // Customize the table header
        JTableHeader header = jTable1.getTableHeader();
        header.setFont(new Font("Serif", Font.BOLD, 25));
        header.setBackground(new Color(0, 225, 225));
        header.setOpaque(true);
        header.setForeground(Color.BLACK);
        jTable1.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(Color.RED); // Color for selected row
                } else {
                    c.setBackground(row % 2 == 0 ? new Color(255, 204, 204) : Color.WHITE); // Alternating row colors
                }
                return c;
            }
        });
    }
    private void loadData() {
        String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String user = "root";
        String password = "12345";

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            try (java.sql.Connection con = DriverManager.getConnection(url, user, password)) {
                String query = "SELECT * FROM Sale"; // Adjust query as needed
                try (PreparedStatement pstmt = con.prepareStatement(query)) {
                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            int id = rs.getInt("ID");
                            String product = rs.getString("Products");
                            int qty = rs.getInt("Qty");
                            double price = rs.getDouble("Price");
                            String date = rs.getString("Date");
                            double total = rs.getDouble("Total");
                            model.addRow(new Object[]{id, product, qty, price, date, total});
                        }
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to establish connection to the database.");
            e.printStackTrace();
        }
    }
}
public void deleteDataFromDatabase(int id ,String table, String delete) {
    String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
    String user = "root";
    String password = "12345";

    try (java.sql.Connection con = DriverManager.getConnection(url, user, password)) {
        String query = "DELETE FROM "+table+" WHERE "+delete+" = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Deleted row with ID: " + id);
        }
    } catch (SQLException e) {
        System.err.println("Failed to delete row from database.");
        e.printStackTrace();
    }
}
public void addDataToDatabase(String product, int qty, double price, String date) {
        String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String user = "root";
        String password = "12345";

        double total = price * qty; // Calculate the total

        try (java.sql.Connection con = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO Sale (Products, Qty, Price, Date, Total) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, product);
                pstmt.setInt(2, qty);
                pstmt.setDouble(3, price);
                pstmt.setString(4, date);
                pstmt.setDouble(5, total);
                pstmt.executeUpdate();
                System.out.println("Added row with Product: " + product);

                // Retrieve the generated ID
                try (java.sql.ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        addRowToTable(generatedId, product, qty, price, date, total);
                    } else {
                        throw new SQLException("Creating product failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to add row to database.");
            e.printStackTrace();
        }
    }
private void addRowToTable(int id, String product, int qty, double price, String date, double total) {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.addRow(new Object[]{id, product, qty, price, date, total});
}
public void searchByName(String productName) {
    String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
    String user = "root";
    String password = "12345";
    String query = "SELECT * FROM Sale WHERE Products LIKE ?";

    try (java.sql.Connection con = DriverManager.getConnection(url, user, password);
         PreparedStatement pstmt = con.prepareStatement(query)) {

        pstmt.setString(1, "%" + productName + "%");
        try (ResultSet rs = pstmt.executeQuery()) {
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0); // Clear existing rows

            while (rs.next()) { // Fetch all matching results
                int id = rs.getInt("ID");
                String product = rs.getString("Products");
                int qty = rs.getInt("Qty");
                double price = rs.getDouble("Price");
                String date = rs.getString("Date");
                double total = rs.getDouble("Total");

                model.addRow(new Object[]{id, product, qty, price, date, total});
            }
        }
    } catch (SQLException e) {
        System.err.println("Failed to search data from the database.");
        e.printStackTrace();
    }
}
 public double getSumOfTotal() {
        String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String user = "root";
        String password = "12345";
        String query = "SELECT SUM(Total) AS total_sum FROM Sale";

        double sum = 0.0;

        try (java.sql.Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                sum = rs.getDouble("total_sum");
            }
        } catch (SQLException e) {
            System.err.println("Failed to calculate sum from database.");
            e.printStackTrace();
        }

        return sum;
    }
public void setTotal(){
    double total = getSumOfTotal();
    String str = String.valueOf(total);
    label5.setText("Total Sales : "+str);
}
// update
public void updateDataInDatabase(int id) {
    String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
    String user = "root";
    String password = "12345";

    String product = jTextField2.getText();
    int qty = Integer.parseInt(jTextField3.getText());
    double price = Double.parseDouble(jTextField4.getText());
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String dateOfBirth = dateFormat.format(jDateChooser1.getDate());
    double total = price * qty;

    try (java.sql.Connection con = DriverManager.getConnection(url, user, password)) {
        String query = "UPDATE Sale SET Products = ?, Qty = ?, Price = ?, Date = ?, Total = ? WHERE ID = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, product);
            pstmt.setInt(2, qty);
            pstmt.setDouble(3, price);
            pstmt.setString(4, dateOfBirth);
            pstmt.setDouble(5, total);
            pstmt.setInt(6, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Updated row with ID: " + id);
            } else {
                System.out.println("No row found with ID: " + id);
            }
        }
    } catch (SQLException e) {
        System.err.println("Failed to update row in database.");
        e.printStackTrace();
    }
}
// Get sale to field

public class RowSelectionHandler {
    private JTable table;
    private JTextField nameField;
    private JTextField qtyField;
    private JTextField priceField;
    private JDateChooser dateChooser;

    public RowSelectionHandler(JTable table, JTextField nameField, JTextField qtyField, JTextField priceField, JDateChooser dateChooser) {
        this.table = table;
        this.nameField = nameField;
        this.qtyField = qtyField;
        this.priceField = priceField;
        this.dateChooser = dateChooser;

        // Add a listener to handle row selection
        this.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    populateFields();
                }
            }
        });
    }

    private void populateFields() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Get data from the selected row with appropriate type casting and null checking
            Object nameObj = table.getValueAt(selectedRow, 1); // Assuming 2nd column is name
            Object qtyObj = table.getValueAt(selectedRow, 2);  // Assuming 3rd column is qty
            Object priceObj = table.getValueAt(selectedRow, 3); // Assuming 4th column is price
            Object dateObj = table.getValueAt(selectedRow, 4);  // Assuming 5th column is date

            // Populate the input fields if values are not null
            nameField.setText(nameObj != null ? nameObj.toString() : "");
            qtyField.setText(qtyObj != null ? qtyObj.toString() : "");
            priceField.setText(priceObj != null ? priceObj.toString() : "");

            // Handle date parsing
            if (dateObj != null) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = dateFormat.parse(dateObj.toString());
                    dateChooser.setDate(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                    dateChooser.setDate(null);
                }
            } else {
                dateChooser.setDate(null);
            }
        } else {
            System.out.println("No row selected.");
        }
    }
}

// Get data product selected







public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
    java.sql.ResultSetMetaData metaData = rs.getMetaData();

    // Names of columns
    Vector<String> columnNames = new Vector<>();
    int columnCount = metaData.getColumnCount();
    for (int column = 1; column <= columnCount; column++) {
        columnNames.add(metaData.getColumnName(column));
    }

    // Data of the table
    Vector<Vector<Object>> data = new Vector<>();
    while (rs.next()) {
        Vector<Object> vector = new Vector<>();
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            vector.add(rs.getObject(columnIndex));
        }
        data.add(vector);
    }

    return new DefaultTableModel(data, columnNames);
}
// update
// add employee
public void addDataToEmployee(String name, String sex, String position, int salary) {
        String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String user = "root";
        String password = "12345";


        try (java.sql.Connection con = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO Employee (name, sex, position, salary) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, name);
                pstmt.setString(2, sex);
                pstmt.setString(3, position );
                pstmt.setInt(4, salary);
                pstmt.executeUpdate();

                // Retrieve the generated ID
                try (java.sql.ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        addRowEmployee(generatedId, name, sex, position, salary);
                    } else {
                        throw new SQLException("Creating product failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to add row to database.");
            e.printStackTrace();
        }
    }
private void addRowEmployee(int id,String name, String sex,  String position,int salary) {
        DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
        model.addRow(new Object[]{id, name, sex, position, salary});
}
public void countEmployee(){
    int count = jTable3.getRowCount();;
    String str = String.valueOf(count);
    label8.setText(str);
}
public void searchByNameEmployee(String employeeName) {
    String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
    String user = "root";
    String password = "12345";
    String query = "SELECT * FROM Employee WHERE name LIKE ?";

    try (java.sql.Connection con = DriverManager.getConnection(url, user, password);
         PreparedStatement pstmt = con.prepareStatement(query)) {

        pstmt.setString(1, "%" + employeeName + "%");
        try (ResultSet rs = pstmt.executeQuery()) {
            DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
            model.setRowCount(0); // Clear existing rows

            while (rs.next()) { // Fetch all matching results
                int id = rs.getInt("employee_id");
                String em_name = rs.getString("name");
                String sex = rs.getString("sex");
                String position = rs.getString("position");
                int salary = rs.getInt("salary");

                model.addRow(new Object[]{id, em_name, sex, position, salary});
            }
        }
    } catch (SQLException e) {
        System.err.println("Failed to search data from the database.");
        e.printStackTrace();
    }
}
// Table Product
public class DesignTablePruduct {
    private DefaultTableModel model;
    private JTable jTable1;
    

    public DesignTablePruduct(JTable table) {
        this.jTable1 = table;
        initTable();
        loadData();
    }

    private void initTable() {
        // Initialize table model
        model = new DefaultTableModel();
        model.addColumn("Product ID");
        model.addColumn("Name");
        model.addColumn("Type");
        model.addColumn("Supplier Name");
        model.addColumn("Price");

        jTable1.setModel(model);

        // Set the table font size
        jTable1.setFont(new Font("Serif", Font.PLAIN, 23));
        jTable1.setRowHeight(50);

        // Ensure row selection is enabled
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        // Customize the table header
        JTableHeader header = jTable1.getTableHeader();
        header.setFont(new Font("Serif", Font.BOLD, 25));
        header.setBackground(new Color(0, 225, 225));
        header.setOpaque(true);
        header.setForeground(Color.BLACK);

        // Customize cell rendering
        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(Color.RED); // Color for selected row
                } else {
                    c.setBackground(row % 2 == 0 ? new Color(255, 204, 204) : Color.WHITE); // Alternating row colors
                }
                return c;
            }
        });
    }

    private void loadData() {
        String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String user = "root";
        String password = "12345";

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            try (java.sql.Connection con = DriverManager.getConnection(url, user, password)) {
                // SQL query to join Product2, Category, and Supplier tables
                String query = "SELECT p.product_id, p.name, c.type, s.supplier_name, p.price " +
                               "FROM Product2 p " +
                               "JOIN Category c ON p.category_id = c.category_id " +
                               "JOIN Supplier s ON p.supplier_id = s.supplier_id";

                try (PreparedStatement pstmt = con.prepareStatement(query);
                     ResultSet rs = pstmt.executeQuery()) {

                    // Clear existing data
                    model.setRowCount(0);

                    while (rs.next()) {
                        int id = rs.getInt("product_id");
                        String name = rs.getString("name");
                        String categoryName = rs.getString("type");
                        String supplierName = rs.getString("supplier_name");
                        int price = rs.getInt("price");

                        model.addRow(new Object[]{id, name, categoryName, supplierName, price});
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to establish connection to the database.");
            e.printStackTrace();
        }
    }
}
// count product
public void countProduct(){
    int count = jTable1.getRowCount();;
    String str = String.valueOf(count);
    label9.setText(str);
}
// Add product
public void addDataToProduct(String name, String type, String supplierName, int price) {
    String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
    String user = "root";
    String password = "12345";

    try (java.sql.Connection con = DriverManager.getConnection(url, user, password)) {
        // Fetch category_id based on the type (category name)
        String fetchCategoryIdQuery = "SELECT category_id FROM Category WHERE type = ?";
        int categoryId = -1;

        try (PreparedStatement pstmt = con.prepareStatement(fetchCategoryIdQuery)) {
            pstmt.setString(1, type);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    categoryId = rs.getInt("category_id");
                } else {
                    JOptionPane.showMessageDialog(null, "Category not found: " + type);
                }
            }
        }

        // Fetch supplier_id based on the supplier name
        String fetchSupplierIdQuery = "SELECT supplier_id FROM Supplier WHERE supplier_name = ?";
        int supplierId = -1;

        try (PreparedStatement pstmt = con.prepareStatement(fetchSupplierIdQuery)) {
            pstmt.setString(1, supplierName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    supplierId = rs.getInt("supplier_id");
                } else {
                    JOptionPane.showMessageDialog(null, "Supplier not found: " + supplierName);
                }
            }
        }

        // Insert the product into the Product2 table
        String insertQuery = "INSERT INTO Product2 (name, category_id, supplier_id, Price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, categoryId);
            pstmt.setInt(3, supplierId);
            pstmt.setInt(4, price);
            pstmt.executeUpdate();

            // Retrieve the generated ID and add the row to the table
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    addRowProduct(generatedId, name, type, supplierName, price);
                } else {
                    throw new SQLException("Creating product failed, no ID obtained.");
                }
            }
        }
    } catch (SQLException e) {
        System.err.println("Failed to add row to the database.");
        e.printStackTrace();
    }
}

private void addRowProduct(int id, String name, String type, String supplierName, int price) {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.addRow(new Object[]{id, name, type, supplierName, price});
}
    // Update product
public void updateDataInProduct(int id) {
    String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
    String user = "root";
    String password = "12345";

    // Retrieve values from text panes
    String name = jTextPane6.getText();
    String type = jTextPane7.getText();
    String supplierName = jTextPane8.getText();
    int price;
    try {
        price = Integer.parseInt(jTextPane9.getText());
    } catch (NumberFormatException e) {
        System.err.println("Invalid price provided.");
        return;
    }

    if (id <= 0) {
        System.err.println("Invalid ID provided.");
        return;
    }

    try (java.sql.Connection con = DriverManager.getConnection(url, user, password)) {
        // Fetch category_id based on the type (category name)
        String fetchCategoryIdQuery = "SELECT category_id FROM Category WHERE type = ?";
        int categoryId = -1;

        try (PreparedStatement pstmt = con.prepareStatement(fetchCategoryIdQuery)) {
            pstmt.setString(1, type);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    categoryId = rs.getInt("category_id");
                } else {
                    JOptionPane.showMessageDialog(null, "Category not found: " + type);
                }
            }
        }

        // Fetch supplier_id based on the supplier name
        String fetchSupplierIdQuery = "SELECT supplier_id FROM Supplier WHERE supplier_name = ?";
        int supplierId = -1;

        try (PreparedStatement pstmt = con.prepareStatement(fetchSupplierIdQuery)) {
            pstmt.setString(1, supplierName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    supplierId = rs.getInt("supplier_id");
                } else {
                    JOptionPane.showMessageDialog(null, "Supplier not found: " + supplierName);
                }
            }
        }

        // Update the product in the Product2 table
        String updateQuery = "UPDATE Product2 SET name = ?, category_id = ?, supplier_id = ?, Price = ? WHERE product_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(updateQuery)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, categoryId);
            pstmt.setInt(3, supplierId);
            pstmt.setInt(4, price);
            pstmt.setInt(5, id);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Updated row with ID: " + id);
                refreshpro("Product2",jTable1);
                // Update the row in the JTable
                updateRowProduct(id, name, type, supplierName, price);
            } else {
                System.out.println("No row found with ID: " + id);
            }
        }
    } catch (SQLException e) {
        System.err.println("Failed to update row in the database.");
        e.printStackTrace();
    }
}

private void updateRowProduct(int id, String name, String type, String supplierName, int price) {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    for (int i = 0; i < model.getRowCount(); i++) {
        if ((int) model.getValueAt(i, 0) == id) {
            model.setValueAt(name, i, 1);            // Update the Name column
            model.setValueAt(type, i, 2);            // Update the Type column
            model.setValueAt(supplierName, i, 3);    // Update the Supplier Name column
            model.setValueAt(price, i, 4);           // Update the Price column
            model.fireTableRowsUpdated(i, i);        // Refresh the specific row
            break;
        }
    }
}


// Search product
public void searchBynameProducts(String productName) {
        String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String user = "root";
        String password = "12345";
        String query = "SELECT * FROM Product2 WHERE name LIKE ? LIMIT 1"; // LIMIT 1 to ensure only one result

        try (java.sql.Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, "%" + productName + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setRowCount(0); // Clear existing rows

                if (rs.next()) { // Only fetch the first result
                    int id = rs.getInt("product_id");
                    String pro_name = rs.getString("name");
                    int cate_id = rs.getInt("category_id");
                    int sup_id = rs.getInt("supplier_id");
                    int price = rs.getInt("Price");

                    model.addRow(new Object[]{id, pro_name, cate_id, sup_id, price});
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to search data from database.");
            e.printStackTrace();
        }
    }

// Table DesignTableStock

public class DesignTableStock {
    private DefaultTableModel model;
    private JTable jTable1;

    public DesignTableStock(JTable table) {
        this.jTable1 = table;
        initTable();
        loadData();
    }

    private void initTable() {
        // Initialize table model
        model = new DefaultTableModel();
        model.addColumn("Stock ID");
        model.addColumn("Product Name");
        model.addColumn("Qty");
        model.addColumn("Last Update");
        model.addColumn("Supplier Name");

        jTable1.setModel(model);

        // Set the table font size
        jTable1.setFont(new Font("Serif", Font.PLAIN, 23));
        jTable1.setRowHeight(50);

        // Ensure row selection is enabled
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Customize the table header
        JTableHeader header = jTable1.getTableHeader();
        header.setFont(new Font("Serif", Font.BOLD, 25));
        header.setBackground(new Color(0, 225, 225));
        header.setOpaque(true);
        header.setForeground(Color.BLACK);
        jTable1.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(Color.RED); // Color for selected row
                } else {
                    c.setBackground(row % 2 == 0 ? new Color(255, 204, 204) : Color.WHITE); // Alternating row colors
                }
                return c;
            }
        });
    }

    private void loadData() {
        String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String user = "root";
        String password = "12345";

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            try (java.sql.Connection con = DriverManager.getConnection(url, user, password)) {
                // SQL query to join Stock, Product2, and Supplier tables
                String query = "SELECT st.stock_id, p.name, st.qty, st.last_update, s.supplier_name " +
                               "FROM Stock st " +
                               "JOIN Product2 p ON st.product_id = p.product_id " +
                               "JOIN Supplier s ON st.supplier_id = s.supplier_id";

                try (PreparedStatement pstmt = con.prepareStatement(query)) {
                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            int stockId = rs.getInt("stock_id");
                            String productName = rs.getString("name");
                            int qty = rs.getInt("qty");
                            String lastUpdate = rs.getString("last_update");
                            String supplierName = rs.getString("supplier_name");
                            model.addRow(new Object[]{stockId, productName, qty, lastUpdate, supplierName});
                        }
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to establish connection to the database.");
            e.printStackTrace();
        }
    }
}

// Add stock
public void addDataToStock(String proName, int qty, String date, String supName) {
    String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
    String user = "root";
    String password = "12345";

    try (java.sql.Connection con = DriverManager.getConnection(url, user, password)) {
        // Fetch product_id based on the product name
        String fetchProductIdQuery = "SELECT product_id FROM Product2 WHERE name = ?";
        int productId = -1;

        try (PreparedStatement pstmt = con.prepareStatement(fetchProductIdQuery)) {
            pstmt.setString(1, proName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    productId = rs.getInt("product_id");
                } else {
                    JOptionPane.showMessageDialog(null, "Product not found: " + proName);
                    return; // Exit the method if product is not found
                }
            }
        }

        // Fetch supplier_id based on the supplier name
        String fetchSupplierIdQuery = "SELECT supplier_id FROM Supplier WHERE supplier_name = ?";
        int supplierId = -1;

        try (PreparedStatement pstmt = con.prepareStatement(fetchSupplierIdQuery)) {
            pstmt.setString(1, supName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    supplierId = rs.getInt("supplier_id");
                } else {
                    JOptionPane.showMessageDialog(null, "Supplier not found: " + supName);
                    return; // Exit the method if supplier is not found
                }
            }
        }

        // Insert the stock information into the Stock table
        String insertQuery = "INSERT INTO Stock (product_id, qty, last_update, supplier_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, productId);
            pstmt.setInt(2, qty);
            pstmt.setString(3, date);
            pstmt.setInt(4, supplierId);
            pstmt.executeUpdate();

            // Retrieve the generated ID and add the row to the table
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    addRowStock(generatedId, proName, qty, date, supName);
                } else {
                    throw new SQLException("Creating stock entry failed, no ID obtained.");
                }
            }
        }
    } catch (SQLException e) {
        System.err.println("Failed to add row to the database.");
        e.printStackTrace();
    }
}

private void addRowStock(int id, String proName, int qty, String date, String supName) {
    DefaultTableModel model = (DefaultTableModel) jTable4.getModel();
    model.addRow(new Object[]{id, proName, qty, date, supName});
}

    // update stock
public void updateDataInStock(int id) {
    String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
    String user = "root";
    String password = "12345";

    // Get the values from the UI components
    String productName = jTextPane21.getText().trim();
    String qtyText = jTextPane22.getText().trim();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String lastUpdate = (jDateChooser2.getDate() != null) ? dateFormat.format(jDateChooser2.getDate()) : null;
    String supplierName = jTextPane24.getText().trim();

    // Validate and parse the quantity
    int qty = 0;
    try {
        qty = Integer.parseInt(qtyText);
    } catch (NumberFormatException e) {
        System.err.println("Invalid quantity format: " + qtyText);
        return; // Exit if quantity format is invalid
    }

    try (java.sql.Connection con = DriverManager.getConnection(url, user, password)) {
        // Fetch product_id based on product name
        String fetchProductIdQuery = "SELECT product_id FROM Product2 WHERE name = ?";
        int proId = -1;
        try (PreparedStatement pstmt = con.prepareStatement(fetchProductIdQuery)) {
            pstmt.setString(1, productName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    proId = rs.getInt("product_id");
                } else {
                    JOptionPane.showMessageDialog(null, "Product not found: " + productName);
                    return; // Exit if product is not found
                }
            }
        }

        // Fetch supplier_id based on supplier name
        String fetchSupplierIdQuery = "SELECT supplier_id FROM Supplier WHERE supplier_name = ?";
        int supId = -1;
        try (PreparedStatement pstmt = con.prepareStatement(fetchSupplierIdQuery)) {
            pstmt.setString(1, supplierName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    supId = rs.getInt("supplier_id");
                } else {
                    JOptionPane.showMessageDialog(null, "Supplier not found: " + supplierName);
                    return; // Exit if supplier is not found
                }
            }
        }

        // Update the stock entry
        String updateQuery = "UPDATE Stock SET product_id = ?, qty = ?, last_update = ?, supplier_id = ? WHERE stock_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(updateQuery)) {
            pstmt.setInt(1, proId);
            pstmt.setInt(2, qty);
            pstmt.setString(3, lastUpdate);
            pstmt.setInt(4, supId);
            pstmt.setInt(5, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Updated row with ID: " + id);
            } else {
                System.out.println("No row found with ID: " + id);
            }
        }
    } catch (SQLException e) {
        System.err.println("Failed to update row in database.");
        e.printStackTrace();
    }
}

// Search stock
public void searchByNameStock(String productName) {
    String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
    String user = "root";
    String password = "12345";

    // Adjust query to search by product name
    String query = "SELECT st.stock_id, p.name AS product_name, st.qty, st.last_update, s.supplier_name " +
                   "FROM Stock st " +
                   "JOIN Product2 p ON st.product_id = p.product_id " +
                   "JOIN Supplier s ON st.supplier_id = s.supplier_id " +
                   "WHERE p.name LIKE ?"; // Use LIKE for partial matching

    try (java.sql.Connection con = DriverManager.getConnection(url, user, password);
         PreparedStatement pstmt = con.prepareStatement(query)) {

        // Set the parameter for product name with wildcard for partial matching
        pstmt.setString(1, "%" + productName + "%");

        try (ResultSet rs = pstmt.executeQuery()) {
            DefaultTableModel model = (DefaultTableModel) jTable4.getModel();
            model.setRowCount(0); // Clear existing rows

            // Populate the table with search results
            while (rs.next()) {
                int id = rs.getInt("stock_id");
                String name = rs.getString("product_name");
                int qty = rs.getInt("qty");
                String lastUpdate = rs.getString("last_update");
                String supplierName = rs.getString("supplier_name");

                model.addRow(new Object[]{id, name, qty, lastUpdate, supplierName});
            }
        }
    } catch (SQLException e) {
        System.err.println("Failed to search data from the database.");
        e.printStackTrace();
    }
}


// Table DesignTableCategory
public class DesignTableCategory {
    private DefaultTableModel model;
    private JTable jTable1;

    public DesignTableCategory(JTable table) {
        this.jTable1 = table;
        initTable();
        loadData();
    }

    private void initTable() {
        // Initialize table model
        model = new DefaultTableModel();
        model.addColumn("Category ID");
        model.addColumn("Type of Product");
        model.addColumn("Description");

        jTable1.setModel(model);

        // Set the table font size
        jTable1.setFont(new Font("Serif", Font.PLAIN, 23));
        jTable1.setRowHeight(50);

        // Ensure row selection is enabled
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Customize the table header
        JTableHeader header = jTable1.getTableHeader();
        header.setFont(new Font("Serif", Font.BOLD, 25));
        header.setBackground(new Color(0, 225, 225));
        header.setOpaque(true);
        header.setForeground(Color.BLACK);

        // Customize cell rendering
        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(Color.RED); // Color for selected row
                } else {
                    c.setBackground(row % 2 == 0 ? new Color(255, 204, 204) : Color.WHITE); // Alternating row colors
                }
                return c;
            }
        });
    }

    private void loadData() {
        String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String user = "root";
        String password = "12345";

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            try (java.sql.Connection con = DriverManager.getConnection(url, user, password)) {
                String query = "SELECT * FROM Category"; // Adjust query as needed
                try (PreparedStatement pstmt = con.prepareStatement(query)) {
                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            int id = rs.getInt("category_id");
                            String type = rs.getString("type");
                            String description = rs.getString("description");
                            model.addRow(new Object[]{id, type, description});
                        }
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to establish connection to the database.");
            e.printStackTrace();
        }
    }

}
// add category
public void addDataToCategory(String name, String description) {
        String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String user = "root";
        String password = "12345";

        try (java.sql.Connection con = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO Category (type, description) VALUES (?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, name);
                pstmt.setString(2, description);
                pstmt.executeUpdate();

                // Retrieve the generated ID
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        addRowCategory(generatedId, name, description);
                    } else {
                        throw new SQLException("Creating product failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to add row to database.");
            e.printStackTrace();
        }
    }

    private void addRowCategory(int id, String name, String description) {
        DefaultTableModel model = (DefaultTableModel) jTable5.getModel();
        model.addRow(new Object[]{id, name, name, description});
    }
 // update category
public void updateDataInCategory(int id) {
    String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
    String user = "root";
    String password = "12345";

    String type = jTextPane16.getText();
    String des = jTextPane26.getText();

    try (java.sql.Connection con = DriverManager.getConnection(url, user, password)) {
        String query = "UPDATE Category SET type = ?, description = ? WHERE category_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, type);
            pstmt.setString(2, des);
            pstmt.setInt(3, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Updated row with ID: " + id);
            } else {
                System.out.println("No row found with ID: " + id);
            }
        }
    } catch (SQLException e) {
        System.err.println("Failed to update row in database.");
        e.printStackTrace();
    }
}
// Search Category
public void searchBynameCategory(String type) {
    String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
    String user = "root";
    String password = "12345";
    String query = "SELECT * FROM Category WHERE type LIKE ? LIMIT 1"; // LIMIT 1 to ensure only one result

    try (java.sql.Connection con = DriverManager.getConnection(url, user, password);
         PreparedStatement pstmt = con.prepareStatement(query)) {

        pstmt.setString(1, "%" + type + "%");
        try (ResultSet rs = pstmt.executeQuery()) {
            DefaultTableModel model = (DefaultTableModel) jTable5.getModel();
            model.setRowCount(0); // Clear existing rows

            if (rs.next()) { // Only fetch the first result
                int id = rs.getInt("category_id");
                String categoryType = rs.getString("type");
                String description = rs.getString("description");
                model.addRow(new Object[]{id, categoryType, description});
            }
        }
    } catch (SQLException e) {
        System.err.println("Failed to search data from database.");
        e.printStackTrace();
    }
}

 // Count stock 
public void countstock(){
    int count = jTable4.getRowCount();;
    String str = String.valueOf(count);
    label7.setText(str);
}

// Table DesignTableSupplier
public class DesignTableSupplier {
    private DefaultTableModel model;
    private JTable jTable1;


    public DesignTableSupplier(JTable table) {
        this.jTable1 = table;
        initTable();
        loadData();
    }

    private void initTable() {
        // Initialize table model
        model = new DefaultTableModel();
        model.addColumn("Supplier ID");
        model.addColumn("Supplier Name");
        model.addColumn("Contact Name");
        model.addColumn("Contact TEL");
        model.addColumn("Address");

        jTable1.setModel(model);

        // Set the table font size
        jTable1.setFont(new Font("Serif", Font.PLAIN, 23));
        jTable1.setRowHeight(50);

        // Ensure row selection is enabled
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add a listener to detect row selection changes

        // Customize the table header
        JTableHeader header = jTable1.getTableHeader();
        header.setFont(new Font("Serif", Font.BOLD, 25));
        header.setBackground(new Color(0, 225, 225));
        header.setOpaque(true);
        header.setForeground(Color.BLACK);

        // Customize cell rendering
        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(Color.RED); // Color for selected row
                } else {
                    c.setBackground(row % 2 == 0 ? new Color(255, 204, 204) : Color.WHITE); // Alternating row colors
                }
                return c;
            }
        });
    }

    private void loadData() {
        String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String user = "root";
        String password = "12345";

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            try (java.sql.Connection con = DriverManager.getConnection(url, user, password)) {
                String query = "SELECT * FROM Supplier"; // Adjust query as needed
                try (PreparedStatement pstmt = con.prepareStatement(query)) {
                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            int id = rs.getInt("supplier_id");
                            String supName = rs.getString("supplier_name");
                            String conName = rs.getString("contact_name");
                            String conTel = rs.getString("contact_tel");
                            String address = rs.getString("address");
                            model.addRow(new Object[]{id, supName, conName, conTel, address});
                        }
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to establish connection to the database.");
            e.printStackTrace();
        }
    }
}
// add Supplier
public void addDataToSupplier(String sup_name, String con_name, String con_tel, String address) {
        String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String user = "root";
        String password = "12345";

        try (java.sql.Connection con = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO Supplier (supplier_name, contact_name, contact_tel, address) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, sup_name);
                pstmt.setString(2, con_name);
                pstmt.setString(3, con_tel);
                pstmt.setString(4, address);
                pstmt.executeUpdate();

                // Retrieve the generated ID
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        addRowSupplier(generatedId, sup_name, con_name, con_tel, address);
                    } else {
                        throw new SQLException("Creating stock entry failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to add row to database.");
            e.printStackTrace();
        }
    }
private void addRowSupplier(int id, String sup_name, String con_name, String con_tel, String address) {
        DefaultTableModel model = (DefaultTableModel) jTable6.getModel();
        model.addRow(new Object[]{id,  sup_name, con_name, con_tel, address});
}
// Update supplier
public void updateDataInSupplier(int id) {
    String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
    String user = "root";
    String password = "12345";

    String sup_name = jTextPane11.getText();
    String con_name = jTextPane12.getText();
    String con_tel = jTextPane13.getText();
    String address = jTextPane14.getText();

    try (java.sql.Connection con = DriverManager.getConnection(url, user, password)) {
        String query = "UPDATE Supplier SET supplier_name = ?, contact_name = ?, contact_tel = ?, address = ? WHERE supplier_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, sup_name);
            pstmt.setString(2, con_name);
            pstmt.setString(3, con_tel);
            pstmt.setString(4, address);
            pstmt.setInt(5, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Updated row with ID: " + id);
            } else {
                System.out.println("No row found with ID: " + id);
            }
        }
    } catch (SQLException e) {
        System.err.println("Failed to update row in database.");
        e.printStackTrace();
    }
}
// Search supplier
public void searchBySupplierName(String name) {
    String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
    String user = "root";
    String password = "12345";
    String query = "SELECT * FROM Supplier WHERE supplier_name LIKE ?"; // Using LIKE for partial matches

    try (java.sql.Connection con = DriverManager.getConnection(url, user, password);
         PreparedStatement pstmt = con.prepareStatement(query)) {

        // Set the parameter with wildcard for partial matching
        pstmt.setString(1, "%" + name + "%");

        try (ResultSet rs = pstmt.executeQuery()) {
            DefaultTableModel model = (DefaultTableModel) jTable6.getModel();
            model.setRowCount(0); // Clear existing rows before populating with new results

            // Fetch and add rows for all results that match the name
            while (rs.next()) {
                int id = rs.getInt("supplier_id");
                String supName = rs.getString("supplier_name");
                String conName = rs.getString("contact_name");
                String conTel = rs.getString("contact_tel");
                String address = rs.getString("address");

                // Add row to the table model
                model.addRow(new Object[]{id, supName, conName, conTel, address});
            }
        }
    } catch (SQLException e) {
        System.err.println("Failed to search data from the database.");
        e.printStackTrace();
    }
}


// Change password
public void changePasswordAndName(String oldUsername, String oldPassword, String newUsername, String newPassword) {
    String url = "jdbc:mysql://localhost:3306/Project_java?zeroDateTimeBehavior=CONVERT_TO_NULL";
    String user = "root";
    String password = "12345";
    String getPasswordAndUsernameQuery = "SELECT pass, name FROM login WHERE name = ?";
    String updatePasswordAndUsernameQuery = "UPDATE login SET name = ?, pass = ? WHERE name = ?";

    try (java.sql.Connection con = DriverManager.getConnection(url, user, password);
         PreparedStatement getPassAndUserStmt = con.prepareStatement(getPasswordAndUsernameQuery);
         PreparedStatement updatePassAndUserStmt = con.prepareStatement(updatePasswordAndUsernameQuery)) {

        // Step 1: Validate the old username and old password
        getPassAndUserStmt.setString(1, oldUsername);
        try (ResultSet rs = getPassAndUserStmt.executeQuery()) {
            if (rs.next()) {
                String storedPassword = rs.getString("pass");
                String storedUsername = rs.getString("name");

                // Check if the stored username and password match the provided old values
                if (!storedUsername.equals(oldUsername) || !storedPassword.equals(oldPassword)) {
                    System.err.println("The old username or password is incorrect.");
                    return; // Exit the method if the old username or password is incorrect
                }
            } else {
                JOptionPane.showMessageDialog(null, "The username does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                return;// Exit the method if the user is not found
            }
        }

        // Step 2: Update the username and password if the old values were correct
        updatePassAndUserStmt.setString(1, newUsername);
        updatePassAndUserStmt.setString(2, newPassword);
        updatePassAndUserStmt.setString(3, oldUsername);
        int rowsUpdated = updatePassAndUserStmt.executeUpdate();
        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(null, "Username and password updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.err.println("Failed to update the username and password.");
        }

    } catch (SQLException e) {
        System.err.println("An error occurred while accessing the database.");
        e.printStackTrace();
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

        roboto1 = new efectos.Roboto();
        customUI1 = new necesario.CustomUI();
        dateChooserPanelBeanInfo1 = new com.toedter.calendar.demo.DateChooserPanelBeanInfo();
        jCalendarTheme1 = new com.toedter.plaf.JCalendarTheme();
        xYShapeRenderer1 = new org.jfree.chart.renderer.xy.XYShapeRenderer();
        compassPlot1 = new org.jfree.chart.plot.CompassPlot();
        datasetGroup1 = new org.jfree.data.general.DatasetGroup();
        abstractOverlay1 = new org.jfree.chart.panel.AbstractOverlay();
        arcDialFrame1 = new org.jfree.chart.plot.dial.ArcDialFrame();
        areaRenderer1 = new org.jfree.chart.renderer.category.AreaRenderer();
        arcDialFrame2 = new org.jfree.chart.plot.dial.ArcDialFrame();
        barRenderer1 = new org.jfree.chart.renderer.category.BarRenderer();
        popupMenu1 = new java.awt.PopupMenu();
        popupMenu2 = new java.awt.PopupMenu();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        buttonGroup7 = new javax.swing.ButtonGroup();
        buttonGroup8 = new javax.swing.ButtonGroup();
        buttonGroup9 = new javax.swing.ButtonGroup();
        buttonGroup10 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel12 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        label4 = new java.awt.Label();
        label9 = new java.awt.Label();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        label3 = new java.awt.Label();
        label8 = new java.awt.Label();
        jPanel8 = new javax.swing.JPanel();
        label2 = new java.awt.Label();
        label7 = new java.awt.Label();
        jPanel9 = new javax.swing.JPanel();
        label1 = new java.awt.Label();
        label6 = new java.awt.Label();
        jCalendar1 = new com.toedter.calendar.JCalendar();
        jPanel10 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        label5 = new java.awt.Label();
        jTextField4 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton14 = new javax.swing.JButton();
        jTextField6 = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jButton35 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane3 = new javax.swing.JTextPane();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPane4 = new javax.swing.JTextPane();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextPane5 = new javax.swing.JTextPane();
        jButton16 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton36 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextPane6 = new javax.swing.JTextPane();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTextPane7 = new javax.swing.JTextPane();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTextPane8 = new javax.swing.JTextPane();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTextPane9 = new javax.swing.JTextPane();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTextPane10 = new javax.swing.JTextPane();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton37 = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane22 = new javax.swing.JScrollPane();
        jTextPane16 = new javax.swing.JTextPane();
        jLabel9 = new javax.swing.JLabel();
        jButton24 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jScrollPane26 = new javax.swing.JScrollPane();
        jTextPane19 = new javax.swing.JTextPane();
        jButton27 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane32 = new javax.swing.JScrollPane();
        jTextPane26 = new javax.swing.JTextPane();
        jButton38 = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jButton28 = new javax.swing.JButton();
        jScrollPane27 = new javax.swing.JScrollPane();
        jTextPane20 = new javax.swing.JTextPane();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane25 = new javax.swing.JScrollPane();
        jTextPane21 = new javax.swing.JTextPane();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane28 = new javax.swing.JScrollPane();
        jTextPane22 = new javax.swing.JTextPane();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jButton29 = new javax.swing.JButton();
        jScrollPane30 = new javax.swing.JScrollPane();
        jTextPane24 = new javax.swing.JTextPane();
        jButton30 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jButton39 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        jTextPane11 = new javax.swing.JTextPane();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane18 = new javax.swing.JScrollPane();
        jTextPane12 = new javax.swing.JTextPane();
        jScrollPane19 = new javax.swing.JScrollPane();
        jTextPane13 = new javax.swing.JTextPane();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane20 = new javax.swing.JScrollPane();
        jTextPane14 = new javax.swing.JTextPane();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jScrollPane31 = new javax.swing.JScrollPane();
        jTextPane25 = new javax.swing.JTextPane();
        jButton32 = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        jButton33 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jButton34 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();

        popupMenu1.setLabel("popupMenu1");

        popupMenu2.setLabel("popupMenu2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(1280, 720));

        jPanel1.setBackground(new java.awt.Color(54, 56, 62));
        jPanel1.setAlignmentX(0.1F);

        jPanel3.setBackground(new java.awt.Color(245, 245, 245));

        jTabbedPane2.setBackground(new java.awt.Color(102, 51, 255));
        jTabbedPane2.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane2.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        jTabbedPane2.setMinimumSize(new java.awt.Dimension(84, 245));

        jPanel12.setBackground(new java.awt.Color(0, 51, 51));
        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel4.setBackground(new java.awt.Color(255, 0, 255));

        label4.setFont(new java.awt.Font("High Tower Text", 0, 36)); // NOI18N
        label4.setForeground(new java.awt.Color(0, 51, 153));
        label4.setText("Products");

        label9.setFont(new java.awt.Font("High Tower Text", 0, 48)); // NOI18N
        label9.setForeground(new java.awt.Color(0, 51, 153));
        label9.setText("20");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(0, 204, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(255, 153, 0));

        label3.setFont(new java.awt.Font("High Tower Text", 0, 36)); // NOI18N
        label3.setForeground(new java.awt.Color(0, 51, 153));
        label3.setText("Employees");

        label8.setFont(new java.awt.Font("High Tower Text", 0, 48)); // NOI18N
        label8.setForeground(new java.awt.Color(0, 51, 153));
        label8.setText("20");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(label3, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(0, 255, 204));

        label2.setFont(new java.awt.Font("High Tower Text", 0, 36)); // NOI18N
        label2.setForeground(new java.awt.Color(0, 51, 153));
        label2.setText("Stock");

        label7.setFont(new java.awt.Font("High Tower Text", 0, 48)); // NOI18N
        label7.setForeground(new java.awt.Color(0, 51, 153));
        label7.setText("20");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(81, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );

        jPanel9.setBackground(new java.awt.Color(153, 255, 153));
        jPanel9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        label1.setFont(new java.awt.Font("High Tower Text", 0, 48)); // NOI18N
        label1.setForeground(new java.awt.Color(0, 51, 153));
        label1.setText("20");

        label6.setFont(new java.awt.Font("High Tower Text", 0, 36)); // NOI18N
        label6.setForeground(new java.awt.Color(0, 51, 153));
        label6.setText("Sales");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(83, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(63, 63, 63)
                    .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(75, Short.MAX_VALUE)))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(55, Short.MAX_VALUE)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 79, Short.MAX_VALUE)))
        );

        jCalendar1.setBackground(new java.awt.Color(0, 255, 153));
        jCalendar1.setDecorationBackgroundColor(new java.awt.Color(153, 255, 51));
        jCalendar1.setSundayForeground(new java.awt.Color(153, 0, 153));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 493, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(384, 390, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jCalendar1, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(103, 103, 103))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCalendar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(104, 104, 104))
        );

        jTabbedPane2.addTab("Dash Board   ", jPanel12);

        jPanel13.setBackground(new java.awt.Color(51, 51, 44));

        jTextField2.setBackground(new java.awt.Color(204, 255, 176));
        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(0, 0, 204));
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 255, 0));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-employee-24.png"))); // NOI18N
        jLabel15.setText("Name");

        jTextField3.setBackground(new java.awt.Color(204, 255, 176));
        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(0, 0, 204));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 255, 0));
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-quantity-24.png"))); // NOI18N
        jLabel16.setText("Qty");

        jPanel21.setBackground(new java.awt.Color(0, 255, 153));

        label5.setFont(new java.awt.Font("NiDA Sowanaphum", 1, 30)); // NOI18N
        label5.setForeground(new java.awt.Color(102, 0, 102));
        label5.setText("Total Sales : ");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(101, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
        );

        jTextField4.setBackground(new java.awt.Color(204, 255, 176));
        jTextField4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(0, 0, 204));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 255, 0));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-price-24.png"))); // NOI18N
        jLabel17.setText("Price");

        jButton8.setBackground(new java.awt.Color(255, 51, 0));
        jButton8.setFont(new java.awt.Font("Sitka Small", 1, 18)); // NOI18N
        jButton8.setForeground(new java.awt.Color(0, 0, 204));
        jButton8.setText("Delete");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton13.setBackground(new java.awt.Color(0, 204, 0));
        jButton13.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton13.setForeground(new java.awt.Color(204, 0, 0));
        jButton13.setText("Add");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 255, 0));
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-date-24.png"))); // NOI18N
        jLabel18.setText("Date");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(jTable2);

        jButton14.setBackground(new java.awt.Color(153, 0, 153));
        jButton14.setFont(new java.awt.Font("Sitka Small", 1, 18)); // NOI18N
        jButton14.setForeground(new java.awt.Color(0, 204, 204));
        jButton14.setText("Update");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jTextField6.setBackground(new java.awt.Color(51, 255, 255));
        jTextField6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextField6.setForeground(new java.awt.Color(0, 0, 204));

        jButton15.setBackground(new java.awt.Color(55, 100, 174));
        jButton15.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton15.setForeground(new java.awt.Color(255, 255, 255));
        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-search-24.png"))); // NOI18N
        jButton15.setText("Search");
        jButton15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jDateChooser1.setBackground(new java.awt.Color(204, 255, 176));
        jDateChooser1.setForeground(new java.awt.Color(204, 255, 176));
        jDateChooser1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        jButton35.setBackground(new java.awt.Color(255, 255, 0));
        jButton35.setFont(new java.awt.Font("Sitka Small", 1, 18)); // NOI18N
        jButton35.setForeground(new java.awt.Color(0, 0, 204));
        jButton35.setText("Reset");
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jButton13)
                .addGap(30, 30, 30)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(jButton8)
                .addGap(18, 18, 18)
                .addComponent(jButton35)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField4))
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 628, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(126, 126, 126))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(55, 55, 55)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(83, 83, 83))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)))
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Sale   ", jPanel13);

        jPanel14.setBackground(new java.awt.Color(51, 51, 44));
        jPanel14.setForeground(new java.awt.Color(0, 0, 204));

        jTextPane1.setBackground(new java.awt.Color(204, 255, 176));
        jTextPane1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextPane1.setForeground(new java.awt.Color(0, 0, 204));
        jScrollPane1.setViewportView(jTextPane1);

        jTextPane3.setBackground(new java.awt.Color(204, 255, 176));
        jTextPane3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextPane3.setForeground(new java.awt.Color(0, 0, 204));
        jScrollPane3.setViewportView(jTextPane3);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 255, 0));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-permanent-job-24.png"))); // NOI18N
        jLabel6.setText("Position");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 255, 0));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-employee-24.png"))); // NOI18N
        jLabel7.setText("Name");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 255, 0));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-gender-24.png"))); // NOI18N
        jLabel12.setText("Gender");

        jButton4.setBackground(new java.awt.Color(255, 0, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 255, 204));
        jButton4.setText("UPDATE");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(102, 255, 102));
        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 0, 51));
        jButton5.setText("ADD");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(255, 102, 102));
        jButton7.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton7.setForeground(new java.awt.Color(102, 0, 102));
        jButton7.setText("DELETE");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 255, 0));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-price-24.png"))); // NOI18N
        jLabel13.setText("Salary");

        jTextPane4.setBackground(new java.awt.Color(204, 255, 176));
        jTextPane4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextPane4.setForeground(new java.awt.Color(0, 0, 204));
        jScrollPane4.setViewportView(jTextPane4);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(jTable3);

        jTextPane5.setBackground(new java.awt.Color(51, 255, 255));
        jTextPane5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextPane5.setForeground(new java.awt.Color(0, 0, 204));
        jScrollPane8.setViewportView(jTextPane5);

        jButton16.setBackground(new java.awt.Color(55, 100, 174));
        jButton16.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton16.setForeground(new java.awt.Color(255, 255, 255));
        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-search-24.png"))); // NOI18N
        jButton16.setText("Search");
        jButton16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jComboBox1.setBackground(new java.awt.Color(204, 255, 176));
        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(102, 0, 102));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));

        jButton36.setBackground(new java.awt.Color(255, 255, 102));
        jButton36.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton36.setForeground(new java.awt.Color(0, 0, 204));
        jButton36.setText("RESET");
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel14Layout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel14Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(40, 40, 40)))
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(38, 38, 38)))
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 612, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(93, 93, 93))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                            .addComponent(jComboBox1))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(74, 74, 74)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30)
                        .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(118, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Employee", jPanel14);

        jPanel15.setBackground(new java.awt.Color(51, 51, 44));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jTable1);

        jTextPane6.setBackground(new java.awt.Color(204, 255, 176));
        jTextPane6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextPane6.setForeground(new java.awt.Color(0, 0, 204));
        jScrollPane9.setViewportView(jTextPane6);

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 255, 0));
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/items.png"))); // NOI18N
        jLabel19.setText("Name");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 255, 0));
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-supplies-24.png"))); // NOI18N
        jLabel20.setText("Type");

        jTextPane7.setBackground(new java.awt.Color(204, 255, 176));
        jTextPane7.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextPane7.setForeground(new java.awt.Color(0, 0, 204));
        jScrollPane10.setViewportView(jTextPane7);

        jTextPane8.setBackground(new java.awt.Color(204, 255, 176));
        jTextPane8.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextPane8.setForeground(new java.awt.Color(0, 0, 204));
        jScrollPane11.setViewportView(jTextPane8);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 255, 0));
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-supplier-24.png"))); // NOI18N
        jLabel21.setText("Supplier Name");

        jTextPane9.setBackground(new java.awt.Color(204, 255, 176));
        jTextPane9.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextPane9.setForeground(new java.awt.Color(0, 0, 204));
        jScrollPane12.setViewportView(jTextPane9);

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 255, 0));
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-price-24.png"))); // NOI18N
        jLabel22.setText("Price");

        jTextPane10.setBackground(new java.awt.Color(51, 255, 255));
        jTextPane10.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextPane10.setForeground(new java.awt.Color(0, 0, 204));
        jScrollPane13.setViewportView(jTextPane10);

        jButton17.setBackground(new java.awt.Color(55, 100, 174));
        jButton17.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton17.setForeground(new java.awt.Color(255, 255, 255));
        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-search-24.png"))); // NOI18N
        jButton17.setText("Search");
        jButton17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton18.setBackground(new java.awt.Color(153, 0, 153));
        jButton18.setFont(new java.awt.Font("Sitka Small", 1, 18)); // NOI18N
        jButton18.setForeground(new java.awt.Color(0, 204, 204));
        jButton18.setText("Update");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jButton19.setBackground(new java.awt.Color(0, 204, 0));
        jButton19.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton19.setForeground(new java.awt.Color(204, 0, 0));
        jButton19.setText("Add");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jButton20.setBackground(new java.awt.Color(255, 51, 0));
        jButton20.setFont(new java.awt.Font("Sitka Small", 1, 18)); // NOI18N
        jButton20.setForeground(new java.awt.Color(0, 0, 204));
        jButton20.setText("Delete");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jButton37.setBackground(new java.awt.Color(255, 255, 0));
        jButton37.setFont(new java.awt.Font("Sitka Small", 1, 18)); // NOI18N
        jButton37.setForeground(new java.awt.Color(0, 0, 204));
        jButton37.setText("Reset");
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addContainerGap(54, Short.MAX_VALUE)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(96, 96, 96))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(130, 130, 130))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(100, 100, 100))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(98, 98, 98))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton19)
                        .addGap(32, 32, 32)
                        .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton20)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(jButton37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 721, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(86, 86, 86))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(76, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Products", jPanel15);

        jPanel16.setBackground(new java.awt.Color(51, 51, 44));

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane15.setViewportView(jTable5);

        jPanel18.setBackground(new java.awt.Color(51, 51, 44));
        jPanel18.setForeground(new java.awt.Color(0, 255, 204));

        jTextPane16.setBackground(new java.awt.Color(204, 255, 176));
        jTextPane16.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextPane16.setForeground(new java.awt.Color(0, 0, 204));
        jScrollPane22.setViewportView(jTextPane16);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 255, 0));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/items.png"))); // NOI18N
        jLabel9.setText("Type of Product");

        jButton24.setBackground(new java.awt.Color(255, 0, 255));
        jButton24.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton24.setForeground(new java.awt.Color(0, 255, 204));
        jButton24.setText("UPDATE");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        jButton25.setBackground(new java.awt.Color(102, 255, 102));
        jButton25.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton25.setForeground(new java.awt.Color(255, 0, 51));
        jButton25.setText("ADD");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jButton26.setBackground(new java.awt.Color(255, 102, 102));
        jButton26.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton26.setForeground(new java.awt.Color(102, 0, 102));
        jButton26.setText("DELETE");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });

        jTextPane19.setBackground(new java.awt.Color(51, 255, 255));
        jTextPane19.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextPane19.setForeground(new java.awt.Color(0, 0, 204));
        jScrollPane26.setViewportView(jTextPane19);

        jButton27.setBackground(new java.awt.Color(55, 100, 174));
        jButton27.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton27.setForeground(new java.awt.Color(255, 255, 255));
        jButton27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-search-24.png"))); // NOI18N
        jButton27.setText("Search");
        jButton27.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 255, 0));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-content-24.png"))); // NOI18N
        jLabel10.setText("Description");

        jTextPane26.setBackground(new java.awt.Color(204, 255, 176));
        jTextPane26.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextPane26.setForeground(new java.awt.Color(0, 0, 204));
        jScrollPane32.setViewportView(jTextPane26);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(135, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123)
                .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86))
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane32, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(103, 103, 103)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel18Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel18Layout.createSequentialGroup()
                            .addGap(14, 14, 14)
                            .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane32, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(181, Short.MAX_VALUE))
        );

        jButton38.setBackground(new java.awt.Color(255, 255, 0));
        jButton38.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton38.setForeground(new java.awt.Color(102, 0, 102));
        jButton38.setText("RESET");
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jButton38, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 290, Short.MAX_VALUE)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 636, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel16Layout.createSequentialGroup()
                    .addGap(0, 11, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 12, Short.MAX_VALUE)))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(120, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton38, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(180, 180, 180))
            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel16Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane2.addTab("Category   ", jPanel16);

        jPanel17.setBackground(new java.awt.Color(51, 51, 44));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane14.setViewportView(jTable4);

        jButton28.setBackground(new java.awt.Color(55, 100, 174));
        jButton28.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton28.setForeground(new java.awt.Color(255, 255, 255));
        jButton28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-search-24.png"))); // NOI18N
        jButton28.setText("Search");
        jButton28.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });

        jTextPane20.setBackground(new java.awt.Color(51, 255, 255));
        jTextPane20.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextPane20.setForeground(new java.awt.Color(0, 0, 204));
        jScrollPane27.setViewportView(jTextPane20);

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 255, 0));
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/items.png"))); // NOI18N
        jLabel28.setText("Product Name");

        jTextPane21.setBackground(new java.awt.Color(204, 255, 176));
        jTextPane21.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextPane21.setForeground(new java.awt.Color(0, 0, 204));
        jScrollPane25.setViewportView(jTextPane21);

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 255, 0));
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-quantity-24.png"))); // NOI18N
        jLabel29.setText("Quantity");

        jTextPane22.setBackground(new java.awt.Color(204, 255, 176));
        jTextPane22.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextPane22.setForeground(new java.awt.Color(0, 0, 204));
        jScrollPane28.setViewportView(jTextPane22);

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 255, 0));
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-date-24.png"))); // NOI18N
        jLabel30.setText("Date of  Update");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 255, 0));
        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-supplier-24.png"))); // NOI18N
        jLabel31.setText("Supplier Name");

        jButton29.setBackground(new java.awt.Color(0, 204, 0));
        jButton29.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton29.setForeground(new java.awt.Color(204, 0, 0));
        jButton29.setText("Add");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        jTextPane24.setBackground(new java.awt.Color(204, 255, 176));
        jTextPane24.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextPane24.setForeground(new java.awt.Color(0, 0, 204));
        jScrollPane30.setViewportView(jTextPane24);

        jButton30.setBackground(new java.awt.Color(153, 0, 153));
        jButton30.setFont(new java.awt.Font("Sitka Small", 1, 18)); // NOI18N
        jButton30.setForeground(new java.awt.Color(0, 204, 204));
        jButton30.setText("Update");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });

        jButton31.setBackground(new java.awt.Color(255, 51, 0));
        jButton31.setFont(new java.awt.Font("Sitka Small", 1, 18)); // NOI18N
        jButton31.setForeground(new java.awt.Color(0, 0, 204));
        jButton31.setText("Delete");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        jDateChooser2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jButton39.setBackground(new java.awt.Color(255, 255, 0));
        jButton39.setFont(new java.awt.Font("Sitka Small", 1, 18)); // NOI18N
        jButton39.setText("Reset");
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jDateChooser2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane28, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                    .addComponent(jScrollPane25, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                    .addComponent(jScrollPane30))
                                .addGap(45, 45, 45))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(67, 67, 67))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addContainerGap(22, Short.MAX_VALUE)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(70, 70, 70))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(66, 66, 66))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(72, 72, 72))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel17Layout.createSequentialGroup()
                                        .addComponent(jButton29)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(jButton31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))))
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 726, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(95, 95, 95))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane28, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane30, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(117, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Stock   ", jPanel17);

        jPanel11.setBackground(new java.awt.Color(51, 51, 44));

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane16.setViewportView(jTable6);

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 255, 0));
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-supplier-24.png"))); // NOI18N
        jLabel23.setText("Supplier Name");

        jTextPane11.setBackground(new java.awt.Color(204, 255, 176));
        jTextPane11.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextPane11.setForeground(new java.awt.Color(0, 0, 204));
        jScrollPane17.setViewportView(jTextPane11);

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 255, 0));
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-employee-24.png"))); // NOI18N
        jLabel24.setText("Contact Name");

        jTextPane12.setBackground(new java.awt.Color(204, 255, 176));
        jTextPane12.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextPane12.setForeground(new java.awt.Color(0, 0, 204));
        jScrollPane18.setViewportView(jTextPane12);

        jTextPane13.setBackground(new java.awt.Color(204, 255, 176));
        jTextPane13.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextPane13.setForeground(new java.awt.Color(0, 0, 204));
        jScrollPane19.setViewportView(jTextPane13);

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 255, 0));
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-call-24.png"))); // NOI18N
        jLabel25.setText("Contact Tel");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 255, 0));
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-address-24.png"))); // NOI18N
        jLabel26.setText("Address");

        jTextPane14.setBackground(new java.awt.Color(204, 255, 176));
        jTextPane14.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextPane14.setForeground(new java.awt.Color(0, 0, 204));
        jScrollPane20.setViewportView(jTextPane14);

        jButton21.setBackground(new java.awt.Color(0, 204, 0));
        jButton21.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton21.setForeground(new java.awt.Color(204, 0, 0));
        jButton21.setText("Add");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jButton22.setBackground(new java.awt.Color(153, 0, 153));
        jButton22.setFont(new java.awt.Font("Sitka Small", 1, 18)); // NOI18N
        jButton22.setForeground(new java.awt.Color(0, 204, 204));
        jButton22.setText("Update");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jButton23.setBackground(new java.awt.Color(255, 51, 0));
        jButton23.setFont(new java.awt.Font("Sitka Small", 1, 18)); // NOI18N
        jButton23.setForeground(new java.awt.Color(0, 0, 204));
        jButton23.setText("Delete");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jTextPane25.setBackground(new java.awt.Color(51, 255, 255));
        jTextPane25.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextPane25.setForeground(new java.awt.Color(0, 0, 204));
        jScrollPane31.setViewportView(jTextPane25);

        jButton32.setBackground(new java.awt.Color(55, 100, 174));
        jButton32.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton32.setForeground(new java.awt.Color(255, 255, 255));
        jButton32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-search-24.png"))); // NOI18N
        jButton32.setText("Search");
        jButton32.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });

        jButton40.setBackground(new java.awt.Color(255, 255, 0));
        jButton40.setFont(new java.awt.Font("Sitka Small", 1, 18)); // NOI18N
        jButton40.setText("Reset");
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(30, 30, 30))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(76, 76, 76))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(66, 66, 66))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(72, 72, 72))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                .addComponent(jButton21)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton40, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(17, 17, 17)
                                        .addComponent(jButton23)))
                                .addGap(12, 12, 12)))))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jScrollPane31, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 777, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(95, 95, 95))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton40, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(117, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Supplier", jPanel11);

        jPanel19.setBackground(new java.awt.Color(51, 51, 44));

        jButton33.setBackground(new java.awt.Color(255, 0, 51));
        jButton33.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton33.setForeground(new java.awt.Color(0, 255, 204));
        jButton33.setText("Exit Program");
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 255, 0));
        jLabel4.setText("Change Password");

        jTextField1.setBackground(new java.awt.Color(204, 255, 255));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(0, 0, 255));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 255, 204));
        jLabel5.setText("Old Name");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 255, 204));
        jLabel8.setText("Old Password");

        jTextField7.setBackground(new java.awt.Color(204, 255, 255));
        jTextField7.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextField7.setForeground(new java.awt.Color(0, 0, 255));
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 255, 204));
        jLabel11.setText("New Password");

        jTextField8.setBackground(new java.awt.Color(204, 255, 255));
        jTextField8.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextField8.setForeground(new java.awt.Color(0, 0, 255));
        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 255, 204));
        jLabel14.setText("New Name  ");

        jTextField9.setBackground(new java.awt.Color(204, 255, 255));
        jTextField9.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jTextField9.setForeground(new java.awt.Color(0, 0, 255));
        jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField9ActionPerformed(evt);
            }
        });

        jButton34.setBackground(new java.awt.Color(51, 255, 51));
        jButton34.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton34.setForeground(new java.awt.Color(255, 0, 0));
        jButton34.setText("Change Password");
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(367, 367, 367)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(462, 462, 462)
                        .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton33))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                                .addGap(142, 142, 142)
                                .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(112, 112, 112))
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel8))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(272, 272, 272))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField1))
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(47, 47, 47)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(83, 83, 83)
                .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
                .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(172, 172, 172))
        );

        jTabbedPane2.addTab("Settings           ", jPanel19);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(802, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 817, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/logo2.png"))); // NOI18N

        jLabel1.setBackground(new java.awt.Color(54, 56, 62));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(143, 181, 255));
        jLabel1.setText("Inventory Management");

        jButton1.setBackground(new java.awt.Color(55, 100, 174));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/home1.png"))); // NOI18N
        jButton1.setText("      Dash Board");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(55, 100, 174));
        jButton2.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/sale1.png"))); // NOI18N
        jButton2.setText("                 Sale");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(55, 100, 174));
        jButton3.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-employee-24.png"))); // NOI18N
        jButton3.setText("        Employee");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(55, 100, 174));
        jButton6.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-supplies-24.png"))); // NOI18N
        jButton6.setText("        Category");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(54, 56, 62));
        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(143, 181, 255));
        jLabel3.setText("System");

        jButton9.setBackground(new java.awt.Color(55, 100, 174));
        jButton9.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/items.png"))); // NOI18N
        jButton9.setText("       Products   ");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(55, 100, 174));
        jButton10.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-stock-24.png"))); // NOI18N
        jButton10.setText("            Stock");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(55, 100, 174));
        jButton11.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-supplier-24.png"))); // NOI18N
        jButton11.setText("      Supplier");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setBackground(new java.awt.Color(55, 100, 174));
        jButton12.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loginandsignup/icons8-settings-24.png"))); // NOI18N
        jButton12.setText("       Settings");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(51, 255, 204));
        jPanel2.setPreferredSize(new java.awt.Dimension(278, 2));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 278, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(101, 101, 101)
                                .addComponent(jLabel3))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(112, 112, 112)
                                .addComponent(jLabel2))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 688, Short.MAX_VALUE)
        );

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTabbedPane2.setSelectedIndex(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jTabbedPane2.setSelectedIndex(1);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jTabbedPane2.setSelectedIndex(2);
// TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
       jTabbedPane2.setSelectedIndex(4);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        jTabbedPane2.setSelectedIndex(3);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        jTabbedPane2.setSelectedIndex(5);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        jTabbedPane2.setSelectedIndex(6);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        jTabbedPane2.setSelectedIndex(7);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        String name = jTextPane5.getText();
        searchByNameEmployee(name);
        if (name.isEmpty()) {
            DesignTableemployee table3 = new DesignTableemployee(jTable3);
        } else {
          searchByNameEmployee(name); // Search by name if not empty
        }
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable3.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) jTable3.getValueAt(selectedRow, 0);
            deleteDataFromDatabase(id,"Employee","employee_id");
            ((DefaultTableModel) jTable3.getModel()).removeRow(selectedRow);
        } else {
            System.out.println("No row selected.");
        }
        countEmployee();
        BarChart bar = new BarChart(jPanel10,jTable3); 
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable3.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) jTable3.getValueAt(selectedRow, 0);
            updateDataInEmployee(id);
            refreshTable("Employee",jTable3);  // Call refreshTable method directly
        } else {
            System.out.println("No row selected.");
        }
        jTextPane1.setText("");
        jComboBox1.setSelectedIndex(-1);
        jTextPane3.setText("");
        jTextPane4.setText("");
        countEmployee();
        BarChart bar = new BarChart(jPanel10,jTable3);
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        String name = jTextField6.getText();
        searchByName(name);
        if (name.isEmpty()) {
            DesignTable table2 = new DesignTable(jTable2);
        } else {
            searchByName(name); // Search by name if not empty
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable2.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) jTable2.getValueAt(selectedRow, 0);
            updateDataInDatabase(id);
            refreshTable("Sale",jTable2);  // Call refreshTable method directly
        } else {
            System.out.println("No row selected.");
        }
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jDateChooser1.setDate(null);
        setTotal();
        Gettext text = new Gettext();
        BarChart bar = new BarChart(jPanel10, jTable2);
        RowSelectionHandler selectionHandler = new RowSelectionHandler(jTable2, jTextField2, jTextField3, jTextField4, jDateChooser1);
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        String name = jTextField2.getText();
        String strqty = jTextField3.getText();
        String strprice = jTextField4.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateOfBirth = dateFormat.format(jDateChooser1.getDate());
        int qty = Integer.parseInt(strqty);
        int price = Integer.parseInt(strprice);
        addDataToDatabase(name,qty,price,dateOfBirth);
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jDateChooser1.setDate(null);
        setTotal();
        Gettext text = new Gettext();
        BarChart bar = new BarChart(jPanel10,jTable2);
        RowSelectionHandler selectionHandler = new RowSelectionHandler(jTable2, jTextField2, jTextField3, jTextField4, jDateChooser1);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        int selectedRow = jTable2.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) jTable2.getValueAt(selectedRow, 0);
            deleteDataFromDatabase(id,"Sale","ID");
            ((DefaultTableModel) jTable2.getModel()).removeRow(selectedRow);
        } else {
            System.out.println("No row selected.");
        }
        setTotal();
        Gettext text = new Gettext();
        BarChart bar = new BarChart(jPanel10,jTable2);
        RowSelectionHandler selectionHandler = new RowSelectionHandler(jTable2, jTextField2, jTextField3, jTextField4, jDateChooser1);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String name = jTextPane1.getText();
        String sex = (String) jComboBox1.getSelectedItem();
        String position = jTextPane3.getText();
        String strsalary = jTextPane4.getText();
        int salary = Integer.parseInt(strsalary);
        addDataToEmployee(name,sex,position,salary);
        jTextPane1.setText("");
        jComboBox1.setSelectedIndex(-1);
        jTextPane3.setText("");
        jTextPane4.setText("");
        countEmployee();
        BarChart bar = new BarChart(jPanel10,jTable3);   
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        String name = jTextPane10.getText();
        searchBynameProducts(name);
        if (name.isEmpty()) {
            DesignTablePruduct tableProduct = new DesignTablePruduct(jTable1);
        } else {
          searchBynameProducts(name); // Search by name if not empty
        }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) jTable1.getValueAt(selectedRow, 0);
            updateDataInProduct(id);
            refreshpro("Product2",jTable1);  // Call refreshTable method directly
        } else {
            System.out.println("No row selected.");
        }
        jTextPane6.setText("");
        jTextPane7.setText("");
        jTextPane8.setText("");
        jTextPane9.setText("");
        countProduct();
        BarChart bar = new BarChart(jPanel10,jTable3);
        refreshpro("Product2",jTable1);
        DesignTablePruduct tableProduct = new DesignTablePruduct(jTable1);
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        //addproduct
        String name = jTextPane6.getText();
        String type = jTextPane7.getText();
        String sup_name = jTextPane8.getText();
        String strprice = jTextPane9.getText();
        int price = Integer.parseInt(strprice);
        addDataToProduct(name,type, sup_name, price);
        jTextPane6.setText("");
        jTextPane7.setText("");
        jTextPane8.setText("");
        jTextPane9.setText("");
        countProduct();
        BarChart bar = new BarChart(jPanel10,jTable2);  
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) jTable1.getValueAt(selectedRow, 0);
            deleteDataFromDatabase(id,"Product2","product_id");
            ((DefaultTableModel) jTable1.getModel()).removeRow(selectedRow);
        } else {
            System.out.println("No row selected.");
        }
        countProduct();
        BarChart bar = new BarChart(jPanel10,jTable1);
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:
        String sup_name = jTextPane11.getText();
        String con_name = jTextPane12.getText();
        String con_tel = jTextPane13.getText();
        String address = jTextPane14.getText();
        addDataToSupplier(sup_name, con_name, con_tel, address);
        jTextPane11.setText("");
        jTextPane12.setText("");
        jTextPane13.setText("");
        jTextPane14.setText("");        
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable6.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) jTable6.getValueAt(selectedRow, 0);
            updateDataInSupplier(id);
            refreshTable("Supplier",jTable6);  // Call refreshTable method directly
        } else {
            System.out.println("No row selected.");
        }
        jTextPane11.setText("");
        jTextPane12.setText("");
        jTextPane13.setText("");
        jTextPane14.setText("");
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        // TODO add your handling code here:
        // update supplier
        int selectedRow = jTable4.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) jTable4.getValueAt(selectedRow, 0);
            updateDataInSupplier(id);
            refreshTable("Stock",jTable4);  // Call refreshTable method directly
        } else {
            System.out.println("No row selected.");
        }
        jTextPane21.setText("");
        jTextPane22.setText("");
        jDateChooser2.setDate(null);
        jTextPane24.setText(""); 
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable5.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) jTable5.getValueAt(selectedRow, 0);
            updateDataInCategory(id);
            refreshTable("Category",jTable5);  // Call refreshTable method directly
        } else {
            System.out.println("No row selected.");
        }
        jTextPane16.setText("");
        jTextPane26.setText("");
        
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        String name = jTextPane16.getText();
        String description = jTextPane26.getText();
        addDataToCategory(name,description);
        jTextPane16.setText("");
        jTextPane26.setText("");        
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        int selectedRow = jTable5.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) jTable5.getValueAt(selectedRow, 0);
            deleteDataFromDatabase(id,"Category","category_id");
            ((DefaultTableModel) jTable5.getModel()).removeRow(selectedRow);
        } else {
            System.out.println("No row selected.");
        }
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        // TODO add your handling code here:
        String name = jTextPane19.getText();
            searchBynameCategory(name);
        if (name.isEmpty()) {
            DesignTableCategory tablecate = new DesignTableCategory(jTable5);
        } else {
          searchBynameCategory(name);// Search by name if not empty
        }
        
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        // TODO add your handling code here:
        String name = jTextPane20.getText();
        searchByNameStock(name);
        if (name.isEmpty()) {
        DesignTableStock tableStock = new DesignTableStock(jTable4);
        } else {
            searchByNameStock(name); // Search by name if not empty
        }
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        String pro_name = jTextPane21.getText();
        String strqty = jTextPane22.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(jDateChooser2.getDate());
        String sup_name = jTextPane24.getText();
        int qty = Integer.parseInt(strqty);
        addDataToStock(pro_name, qty, date, sup_name);
        jTextPane21.setText("");
        jTextPane22.setText("");
        jDateChooser2.setDate(null);
        jTextPane24.setText("");
        countstock();
        BarChart bar = new BarChart(jPanel10,jTable4);
    }//GEN-LAST:event_jButton29ActionPerformed

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        // TODO add your handling code here:
        // update stock
        int selectedRow = jTable4.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) jTable4.getValueAt(selectedRow, 0);
            updateDataInStock(id);
            refreshStock(jTable4);  // Call refreshTable method directly
        } else {
            System.out.println("No row selected.");
        }
        jTextPane21.setText("");
        jTextPane22.setText("");
        jDateChooser2.setDate(null);
        jTextPane24.setText("");        
    }//GEN-LAST:event_jButton30ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable4.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) jTable4.getValueAt(selectedRow, 0);
            deleteDataFromDatabase(id,"Category","category_id");
            ((DefaultTableModel) jTable4.getModel()).removeRow(selectedRow);
        } else {
            System.out.println("No row selected.");
        }
        countstock();
        BarChart bar = new BarChart(jPanel10,jTable4);
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        // TODO add your handling code here:
        String name = jTextPane25.getText();

        // Check if the name is empty
        if (name.isEmpty()) {
            // If empty, reset the table or show all suppliers
            DesignTableSupplier tablesupp = new DesignTableSupplier(jTable6); // Reinitialize the table to show all suppliers
        } else {
            // If not empty, search by the supplier name
            searchBySupplierName(name);
        }
    }//GEN-LAST:event_jButton32ActionPerformed

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton33ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        String oldUsername = jTextField1.getText();
        String oldPassword = jTextField7.getText();
        String newUsername = jTextField9.getText();
        String newPassword = jTextField8.getText();
        changePasswordAndName(oldUsername, oldPassword, newUsername,newPassword);
        jTextField1.setText("");
        jTextField7.setText("");
        jTextField9.setText("");
        jTextField8.setText("");
    }//GEN-LAST:event_jButton34ActionPerformed

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed
        // TODO add your handling code here:
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jDateChooser1.setDate(null);
    }//GEN-LAST:event_jButton35ActionPerformed

    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton36ActionPerformed
        // TODO add your handling code here:
        jTextPane1.setText("");
        jComboBox1.setSelectedIndex(-1);
        jTextPane3.setText("");
        jTextPane4.setText("");
    }//GEN-LAST:event_jButton36ActionPerformed

    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
        // TODO add your handling code here:
        jTextPane6.setText("");
        jTextPane7.setText("");
        jTextPane8.setText("");
        jTextPane9.setText("");
    }//GEN-LAST:event_jButton37ActionPerformed

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        // TODO add your handling code here:
        jTextPane16.setText("");
        jTextPane26.setText("");
    }//GEN-LAST:event_jButton38ActionPerformed

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton39ActionPerformed
        // TODO add your handling code here:
        jTextPane21.setText("");
        jTextPane22.setText("");
        jDateChooser2.setDate(null);
        jTextPane24.setText("");
    }//GEN-LAST:event_jButton39ActionPerformed

    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed
        // TODO add your handling code here:
        jTextPane11.setText("");
        jTextPane12.setText("");
        jTextPane13.setText("");
        jTextPane14.setText(""); 
    }//GEN-LAST:event_jButton40ActionPerformed

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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jfree.chart.panel.AbstractOverlay abstractOverlay1;
    private org.jfree.chart.plot.dial.ArcDialFrame arcDialFrame1;
    private org.jfree.chart.plot.dial.ArcDialFrame arcDialFrame2;
    private org.jfree.chart.renderer.category.AreaRenderer areaRenderer1;
    private org.jfree.chart.renderer.category.BarRenderer barRenderer1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup10;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.ButtonGroup buttonGroup7;
    private javax.swing.ButtonGroup buttonGroup8;
    private javax.swing.ButtonGroup buttonGroup9;
    private org.jfree.chart.plot.CompassPlot compassPlot1;
    private necesario.CustomUI customUI1;
    private org.jfree.data.general.DatasetGroup datasetGroup1;
    private com.toedter.calendar.demo.DateChooserPanelBeanInfo dateChooserPanelBeanInfo1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private com.toedter.calendar.JCalendar jCalendar1;
    private com.toedter.plaf.JCalendarTheme jCalendarTheme1;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane28;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane30;
    private javax.swing.JScrollPane jScrollPane31;
    private javax.swing.JScrollPane jScrollPane32;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane10;
    private javax.swing.JTextPane jTextPane11;
    private javax.swing.JTextPane jTextPane12;
    private javax.swing.JTextPane jTextPane13;
    private javax.swing.JTextPane jTextPane14;
    private javax.swing.JTextPane jTextPane16;
    private javax.swing.JTextPane jTextPane19;
    private javax.swing.JTextPane jTextPane20;
    private javax.swing.JTextPane jTextPane21;
    private javax.swing.JTextPane jTextPane22;
    private javax.swing.JTextPane jTextPane24;
    private javax.swing.JTextPane jTextPane25;
    private javax.swing.JTextPane jTextPane26;
    private javax.swing.JTextPane jTextPane3;
    private javax.swing.JTextPane jTextPane4;
    private javax.swing.JTextPane jTextPane5;
    private javax.swing.JTextPane jTextPane6;
    private javax.swing.JTextPane jTextPane7;
    private javax.swing.JTextPane jTextPane8;
    private javax.swing.JTextPane jTextPane9;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private java.awt.Label label5;
    private java.awt.Label label6;
    private java.awt.Label label7;
    private java.awt.Label label8;
    private java.awt.Label label9;
    private java.awt.PopupMenu popupMenu1;
    private java.awt.PopupMenu popupMenu2;
    private efectos.Roboto roboto1;
    private org.jfree.chart.renderer.xy.XYShapeRenderer xYShapeRenderer1;
    // End of variables declaration//GEN-END:variables
    }
