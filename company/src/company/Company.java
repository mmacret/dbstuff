package company;

import java.sql.*;
import java.io.Console;

/**
 * Company.java
 * Purpose: Toy code that connects to an Oracle Database and performs some queries.
 * @author mmacret
 */
public class Company {

    private Connection conn;
    private PreparedStatement showQuery;
    private PreparedStatement underneathQuery;
    private ResultSet rs;

    /**
     * Constructor that initializes the database connection and prepares the queries.
     */
    public Company() throws SQLException {
        conn = DriverManager.getConnection("db url",
                "visitor",
                "password");

        String underneathSt = "SELECT e1.employee_id, e1.employee_name, e2.employee_name as manager_name, LEVEL "
                + "FROM employees e1, employees e2 "
                + "WHERE e1.employee_manager = e2.employee_id(+) AND LEVEL > 1"
                + "START WITH e1.employee_id = ? "
                + "CONNECT BY PRIOR e1.employee_id = e1.employee_manager ";

        String showSt = "SELECT * FROM EMPLOYEES";

        showQuery = conn.prepareStatement(showSt);
        underneathQuery = conn.prepareStatement(underneathSt);

    }

    /**
     * Close the connections to the Oracle Database.
     */
    public void closeConnection() throws SQLException {
        showQuery.close();
        underneathQuery.close();
        conn.close();
        rs.close();
    }

    /**
     * Show the list of employees.
     */
    public void showEmployees() throws SQLException {
        rs = showQuery.executeQuery();
        System.out.println("Employee Id | Name");
        while (rs.next()) {
            String id = rs.getString(1);
            String name = rs.getString(2);
            System.out.println(id + " | " + name);
        }
        System.out.println("--------------------");
    }

    /**
     * Show the list of employees underneath the employee Id "employeeId" in the management hierarchy.
     */
    public void underneathEmployee(int employeeId) throws SQLException {
        underneathQuery.setInt(1, employeeId);
        rs = underneathQuery.executeQuery();
        System.out.println("Employee Id | Name");
        while (rs.next()) {
            String id = rs.getString(1);
            String name = rs.getString(2);
            System.out.println(id + " | " + name);
        }
        System.out.println("--------------------");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        Console console = System.console();
        Company company = new Company();

        console.readLine("Press a key to see the employees list\n");
        company.showEmployees();
        System.out.println();

        boolean success = false;
        while (!success) {
            String employeeId = console.readLine("Select a employee id to show all the employees that are underneath them in the management hierarchy (q to quit): ");
            try {
                company.underneathEmployee(Integer.parseInt(employeeId));
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number\n");
            }
            if (employeeId.equals("q")) {
                success = true;
            }
        }

        company.closeConnection();
    }
}
