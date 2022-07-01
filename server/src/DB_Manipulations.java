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
    
     public JSONObject getProducts(){
        String SQL = "SELECT name , category , qty , price ,img FROM product";
        String name , image,category;
        Float price;
        int qty;
        int idx=0;
        JSONObject result=new JSONObject();
        try {
            Statement myStmt;
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/marketplace", "root", "123456789");
            myStmt = connection.createStatement();
            ResultSet resultSet = myStmt.executeQuery(SQL);

            while(resultSet.next()){
                name=resultSet.getString("name");
                price=resultSet.getFloat("price");
                qty=resultSet.getInt("qty");
                image=resultSet.getString("img");
                category=resultSet.getString("category");
                //store first name , qty , price , image , category with tags n0 , q0 , p0 , i0 , c0
                result.put("n"+String.valueOf(idx),name);
                result.put("q"+String.valueOf(idx),String.valueOf(qty));
                result.put("p"+String.valueOf(idx),String.valueOf(price));
                result.put("i"+String.valueOf(idx),image);
                result.put("c"+String.valueOf(idx),category);
                idx++;
            }
            result.put("size",String.valueOf(idx));

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public synchronized String commitPurchase(String uname, JSONObject item, JSONObject qty,int size) {
        String result = "";
        Vector<Integer> productId = new Vector<Integer>();
        float total_price=0;
        float oldBalance=0;
        float newBalance=0;
        int userid=0;
        int transactionID=0;
        String SQL1 = "SELECT price , product.id FROM product WHERE product.name = ?";
        String SQL2 = "SELECT balance , user.id FROM user WHERE user.user_name = ?";
        String SQL3 = "UPDATE user SET balance = balance - ? WHERE user_name = ?";
        String SQL4 = "UPDATE product SET qty = qty - ? WHERE product.name = ?";
        String SQL5 = "INSERT INTO transaction(balance_before,balance_after,user_id) VALUES(?,?,?)";
        String SQL6 = "INSERT INTO purchased_prod(qty,trans_id,product_id) VALUES(?,?,?)";
        String SQL7 = "SELECT qty FROM product WHERE product.name=? ";
        try {
            PreparedStatement myStmt;
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/marketplace", "root", "123456789");
            ResultSet resultSet;
            //calculate total price of cart compare it to the balance to abort if insufficient
            for (int i =1 ; i<=size ; i++) {
                myStmt = connection.prepareStatement(SQL1);
                myStmt.setString(1, (String) item.get(String.valueOf(i)));
                resultSet = myStmt.executeQuery();
                while (resultSet.next()) {
                    total_price+=((resultSet.getFloat("price"))*(Float.valueOf((String) qty.get(String.valueOf(i)))));
                    productId.add((Integer)resultSet.getInt("id"));
                }
            }
            myStmt = connection.prepareStatement(SQL2);
            myStmt.setString(1, uname);
            resultSet = myStmt.executeQuery();
            while (resultSet.next()) {
                oldBalance=(resultSet.getFloat("balance"));
                userid=resultSet.getInt("id");
            }
            if(oldBalance<total_price){
                result="insufficientBalance";
                return result;
            }

            //Check item is in stock
            for (int i =1 ; i<=size ; i++) {
                myStmt = connection.prepareStatement(SQL7);
                myStmt.setString(1, (String) item.get(String.valueOf(i)));
                ResultSet rs=myStmt.executeQuery();
                rs.next();
                if(rs.getFloat("qty") <= 0 || rs.getFloat("qty")<Float.valueOf((String) qty.get(String.valueOf(i)))){
                    result="NoItemsInStock";
                    return result;
                }
            }

            //update balance = balance - total cart price
            myStmt = connection.prepareStatement(SQL3);
            myStmt.setFloat(1, total_price);
            myStmt.setString(2, uname);
            myStmt.executeUpdate();

            //get the new balance
            myStmt = connection.prepareStatement(SQL2);
            myStmt.setString(1, uname);
            resultSet = myStmt.executeQuery();
            while (resultSet.next()) {
                newBalance=(resultSet.getFloat("balance"));
            }
            //Decrease quantity from items
            for (int i =1 ; i<=size ; i++) {
                myStmt = connection.prepareStatement(SQL4);
                myStmt.setString(1,(String) qty.get(String.valueOf(i)));
                myStmt.setString(2, (String) item.get(String.valueOf(i)));
                myStmt.executeUpdate();
            }
            //insert into transaction
            myStmt = connection.prepareStatement(SQL5,PreparedStatement.RETURN_GENERATED_KEYS);
            myStmt.setFloat(1, oldBalance);
            myStmt.setFloat(2, newBalance);
            myStmt.setInt(3, userid);
            myStmt.executeUpdate();
            resultSet  = myStmt.getGeneratedKeys();
            resultSet.next();
            transactionID=resultSet.getInt(1);

            //insert purchased_items
            for(int i =1 ; i<=size; i++){
                myStmt = connection.prepareStatement(SQL6);
                myStmt.setString(1, (String) qty.get(String.valueOf(i)));
                myStmt.setInt(2, transactionID);
                myStmt.setInt(3, productId.elementAt(i-1));
                myStmt.executeUpdate();
            }



        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        result="Done";
        return result;
    }
}
