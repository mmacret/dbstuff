package authentication;

import java.sql.*;
import java.io.Console;

/**
 * Company.java
 * Purpose: Toy code that connects to an Oracle Database and performs some queries.
 * @author mmacret
 */
public class Authentication {

    private Connection conn;
    private PreparedStatement connectQuery;
    private ResultSet rs;

    /**
     * Constructor that initializes the database connection and prepares the queries.
     */
    public Authentication() throws SQLException {
        conn = DriverManager.getConnection("db url",
                     "user",
                     "pw");

        String query = "SELECT * FROM USERS WHERE USER_LOGIN = ? AND USER_PASSWORD = ?";

        connectQuery = conn.prepareStatement(query);
    }

    /**
     * Close the connections to the Oracle Database.
     */
    public void closeConnection() throws SQLException {
        connectQuery.close();
        conn.close();
        rs.close();
    }

    

    /**
     * Authenticate an user.
     */
    public boolean authenticate(String login, String pw) throws SQLException {
        connectQuery.setString(1, login);
        connectQuery.setString(2, pw);
        rs = connectQuery.executeQuery();
        boolean success = rs.next();
        
        if (!success) System.out.println("Authentication failed");
        
        return success;
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        Console console = System.console();
        
        String login ;
        String pw;
        Authentication auth = new Authentication();

        boolean success = false;
        while (!success) {
            login  = console.readLine("Enter login: ");
            pw  = console.readLine("Enter password: ");
            success = auth.authenticate(login,pw);
        }
        System.out.println("Authentication successful");
        auth.closeConnection();
    }
}
