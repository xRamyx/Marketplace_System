import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;


public class DB_Manipulations {

    // adding users
    public String insertUser(User user) {
        String result = "Ok";
        String SQL1 = "SELECT user_name "
                + "FROM user";
        String SQL2 = "INSERT INTO user(fname,mname,lname,user_name,pass,balance) "
                + "VALUES(?,?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/marketplace", "root", "123456789");
             PreparedStatement pstmt = connection.prepareStatement(SQL2)) {

            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(SQL1);

            while(resultSet.next()){
                String uname = resultSet.getString("user_name");

                if((uname.equals(user.getUserName()))){
                    result = "Choose Another";
                    break;
                }
            }

            if(result.equals("Ok")){
                pstmt.setString(1, user.getFName());
                pstmt.setString(2, user.getMName());
                pstmt.setString(3, user.getLName());
                pstmt.setString(4, user.getUserName());
                pstmt.setString(5, user.getUserPass());
                pstmt.setFloat(6, user.getBalance());

                pstmt.executeUpdate();
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    // comparing users
    public String compareUser(String user, String pass) {
        String result = "Denied";
        String SQL = "SELECT user_name, pass "
                + "FROM user";

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/marketplace", "root", "123456789");
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(SQL);

            while(resultSet.next()){
                String uname = resultSet.getString("user_name");
                String password = resultSet.getString("pass");

                if((user.equals(uname)) && (pass.equals(password))){
                    result = "Granted";
                    break;
                }else{
                    result = "Denied";
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }
}