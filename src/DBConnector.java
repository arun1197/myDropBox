import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Don on 5/17/2017 AD.
 */
public class DBConnector extends HashSalt {

    private static Connection connection;
    public static ResultSet resultSet;

    //checks the login credentials
    public static boolean checkLogin(String username, String password) throws Exception {
        boolean pass = true;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("RDS_HOST","RDS_USERNAME","RDS_PASSWORD");
            String query = "SELECT username,password FROM users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                if(resultSet.getString(1).equals(username) &&
                        checkPassword(password,resultSet.getString(2))){
                    pass = true;
                } else {
                    pass = false;
                }
            }
        } finally {
            close();
            return pass;
        }
    }

    //check whether user exist or not.
    public static boolean userExist(String username){
        boolean exist = false;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("RDS_HOST","RDS_USERNAME","RDS_PASSWORD");
            String query = "SELECT username FROM users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                exist = true;
            }

        }
        finally {
            close();
            return exist;
        }
    }

    //Creates a connection to database and adds user to db when "newuser" command is used
    public static void addUser(String username, String password) {
        try {
            // create a mysql database connection
            Class.forName("com.mysql.jdbc.Driver");
            HashSalt hashSalt = new HashSalt();
            connection = DriverManager.getConnection("RDS_HOST","RDS_USERNAME","RDS_PASSWORD");
            String query = "INSERT INTO YOUR_TABLE_NAME_GOES_HERE (username, password) VALUES(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2,hashSalt.hashPassword(password));
            preparedStatement.executeUpdate();
            connection.close();

        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }


    private static void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
        }
    }
}
