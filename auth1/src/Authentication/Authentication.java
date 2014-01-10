package Authentication;

import java.sql.*;
import java.io.Console;

/**
 * Company.java
 * Purpose: Toy code that connects to an Oracle Database and performs some queries.
 * @author mmacret
 */
public class Authentication {

    private Connection conn;
    

    /**
     * Constructor that initializes the database connection and prepares the queries.
     */
    public Authentication()  {
        

    }

    
    /**
     * Authenticate the connections to the Oracle Database.
     */
    public boolean openConnection(String login, String pw) {
        try {
            conn = DriverManager.getConnection("db url",
                     login,
                     pw);
            System.out.println("Authentication successful");
            return true;
        } catch (SQLException ex) {
            System.out.println("Authentication failure");
           return false;
        }
    }
    
    /**
     * Close the connections to the Oracle Database.
     */
    public void closeConnection() throws SQLException {
        conn.close();
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
            success = auth.openConnection(login,pw);
        }

        auth.closeConnection();
    }
}
