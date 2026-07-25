import java.sql.*;
import java.util.Scanner;

public class Student {

    static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    static final String USER = "root";
    static final String PASSWORD = "root123";

    static Scanner sc = new Scanner(System.in);

    public static void main(String args[]) {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con =
                    DriverManager.getConnection(URL, USER, PASSWORD);

            while (true) {

                System.out.println("\n===== MENU =====");
                System.out.println("1. Insert");
                System.out.println("2. Display");
                System.out.println("3. Update");
                System.out.println("4. Delete");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");

                int ch = sc.nextInt();

                switch (ch) {

                    case 1:
                        insert(con);
                        break;

                    case 2:
                        display(con);
                        break;

                    case 3:
                        update(con);
                        break;

                    case 4:
                        delete(con);
                        break;

                    case 5:
                        con.close();
                        System.out.println("Program Ended.");
                        System.exit(0);

                    default:
                        System.out.println("Invalid Choice");
                }
            }

        }

        catch(Exception e) {
            e.printStackTrace();
        }

    }

    static void insert(Connection con) throws Exception {

        System.out.print("Enter Roll No: ");
        int roll = sc.nextInt();

        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Marks: ");
        int marks = sc.nextInt();

        PreparedStatement ps =
                con.prepareStatement(
                        "insert into student values(?,?,?)");

        ps.setInt(1, roll);
        ps.setString(2, name);
        ps.setInt(3, marks);

        ps.executeUpdate();

        System.out.println("Record Inserted.");

    }

    static void display(Connection con) throws Exception {

        Statement st = con.createStatement();

        ResultSet rs =
                st.executeQuery("select * from student");

        System.out.println("\nRoll\tName\tMarks");

        while(rs.next()) {

            System.out.println(
                    rs.getInt(1)+"\t"+
                    rs.getString(2)+"\t"+
                    rs.getInt(3));

        }

    }

    static void update(Connection con) throws Exception {

        System.out.print("Enter Roll No: ");
        int roll = sc.nextInt();

        System.out.print("Enter New Marks: ");
        int marks = sc.nextInt();

        PreparedStatement ps =
                con.prepareStatement(
                        "update student set marks=? where roll=?");

        ps.setInt(1, marks);
        ps.setInt(2, roll);

        int r = ps.executeUpdate();

        if(r>0)
            System.out.println("Updated Successfully");
        else
            System.out.println("Record Not Found");

    }

    static void delete(Connection con) throws Exception {

        System.out.print("Enter Roll No: ");
        int roll = sc.nextInt();

        PreparedStatement ps =
                con.prepareStatement(
                        "delete from student where roll=?");

        ps.setInt(1, roll);

        int r = ps.executeUpdate();

        if(r>0)
            System.out.println("Deleted Successfully");
        else
            System.out.println("Record Not Found");

    }

}
