import java.sql.*;

public class Preparedstmt {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/testdb?";
        String user = "testuser";
        String password = "testpass"; 

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to Database
            Connection con = DriverManager.getConnection(url, user, password);

            // a. Create Student table
            String createTable = "CREATE TABLE IF NOT EXISTS Student ("
                               + "RollNo INT PRIMARY KEY, "
                               + "Name VARCHAR(50), "
                               + "Address VARCHAR(100))";
            con.createStatement().executeUpdate(createTable);
            System.out.println("Table created successfully.");

            // Insert initial records using Statement (for setup only)
            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT IGNORE INTO Student VALUES (1, 'Ravi', 'Hyderabad')");
            stmt.executeUpdate("INSERT IGNORE INTO Student VALUES (2, 'Sita', 'Chennai')");
            stmt.executeUpdate("INSERT IGNORE INTO Student VALUES (3, 'Kiran', 'Bangalore')");
            System.out.println("Initial records inserted.");

            // b. Display content using PreparedStatement
            System.out.println("\nInitial Records:");
            displayRecords(con);

            // c. Insert 2 records using PreparedStatement
            String insertSQL = "INSERT INTO Student (RollNo, Name, Address) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = con.prepareStatement(insertSQL);

            insertStmt.setInt(1, 4);
            insertStmt.setString(2, "Meena");
            insertStmt.setString(3, "Pune");
            insertStmt.executeUpdate();

            insertStmt.setInt(1, 5);
            insertStmt.setString(2, "Ramesh");
            insertStmt.setString(3, "Mumbai");
            insertStmt.executeUpdate();

            System.out.println("Two new records inserted.");

            // d. Update one record using PreparedStatement
            String updateSQL = "UPDATE Student SET Address = ? WHERE RollNo = ?";
            PreparedStatement updateStmt = con.prepareStatement(updateSQL);
            updateStmt.setString(1, "Delhi");
            updateStmt.setInt(2, 2);
            updateStmt.executeUpdate();
            System.out.println("One record updated.");

            // e. Delete one record using PreparedStatement
            String deleteSQL = "DELETE FROM Student WHERE RollNo = ?";
            PreparedStatement deleteStmt = con.prepareStatement(deleteSQL);
            deleteStmt.setInt(1, 3);
            deleteStmt.executeUpdate();
            System.out.println("One record deleted.");

            // f. Display updated content using PreparedStatement
            System.out.println("\nFinal Records:");
            displayRecords(con);

            // Close connection
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Function to display all records using PreparedStatement
    public static void displayRecords(Connection con) throws SQLException {
        String selectSQL = "SELECT * FROM Student";
        PreparedStatement selectStmt = con.prepareStatement(selectSQL);
        ResultSet rs = selectStmt.executeQuery();

        System.out.println("RollNo\tName\tAddress");
        while (rs.next()) {
            int roll = rs.getInt("RollNo");
            String name = rs.getString("Name");
            String address = rs.getString("Address");
            System.out.println(roll + "\t" + name + "\t" + address);
        }
    }
}
