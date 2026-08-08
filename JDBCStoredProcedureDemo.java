import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

public class JDBCStoredProcedureDemo {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/employee_db";
        String user = "javauser";
        String password = "password@123";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            System.out.println("Database connected successfully.");

            // 1. Call insert_employee procedure
            CallableStatement insertStmt =
                    conn.prepareCall("{call insert_employee(?, ?, ?)}");

            insertStmt.setInt(1, 102);
            insertStmt.setString(2, "John Doe");
            insertStmt.setDouble(3, 55000.00);

            insertStmt.execute();

            System.out.println("Employee record inserted successfully.");

            insertStmt.close();

            // 2. Call get_salary_by_id procedure
            CallableStatement getSalaryStmt =
                    conn.prepareCall("{call get_salary_by_id(?, ?)}");

            getSalaryStmt.setInt(1, 102);

            getSalaryStmt.registerOutParameter(2, Types.DECIMAL);

            getSalaryStmt.execute();

            double salary = getSalaryStmt.getDouble(2);

            System.out.println(
                "Salary for Employee ID 102 is: " + salary
            );

            getSalaryStmt.close();

        } catch (SQLException e) {

            e.printStackTrace();

        }
    }
}
