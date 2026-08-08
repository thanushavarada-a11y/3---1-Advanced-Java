import java.sql.*;

public class UResultSet {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/testdb";
        String user = "testuser";
        String password = "testpass";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    url, user, password);

            Statement st = con.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = st.executeQuery(
                    "SELECT * FROM Student");

            // Delete last row
            if (rs.last()) {
                rs.deleteRow();
                System.out.println(
                    "Last student record deleted successfully.");
            }

            // Insert new row
            rs.moveToInsertRow();

            rs.updateInt("RollNo", 105);
            rs.updateString("Name", "John Doe");
            rs.updateString("Address", "Hyderabad");

            rs.insertRow();

            System.out.println(
                "New student record inserted successfully.");

            // Display all records
            ResultSet rs2 = st.executeQuery(
                    "SELECT * FROM Student");

            System.out.println("\nUpdated Student Records:");
            System.out.println("RollNo\tName\t\tAddress");
            System.out.println("--------------------------------");

            while (rs2.next()) {

                System.out.println(
                    rs2.getInt("RollNo") + "\t" +
                    rs2.getString("Name") + "\t\t" +
                    rs2.getString("Address"));
            }

            rs2.close();
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
