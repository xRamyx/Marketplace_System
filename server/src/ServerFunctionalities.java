import org.json.simple.JSONObject;

import java.util.Vector;

public class ServerFunctionalities {
    public JSONObject login(JSONObject credentials){
        String s;
        DB_Manipulations dbm = new DB_Manipulations();
        JSONObject res = new JSONObject();
        String user = (String) credentials.get("user");
        String pass = (String) credentials.get("pass");
        s = dbm.compareUser(user, pass);
        res.put("access", s);
        return res;
    }

    public synchronized JSONObject signUp(JSONObject info){
        String s;
        DB_Manipulations dbm = new DB_Manipulations();
        JSONObject res = new JSONObject();
        String fname = (String) info.get("fname");
        String mname = (String) info.get("mname");
        String lname = (String) info.get("lname");
        String uname = (String) info.get("username");
        String pass = (String) info.get("pass");
        String passv = (String) info.get("passv");
        float blnc = Float.parseFloat((String) info.get("blnc"));
        if(!pass.equals(passv)){
            res.put("create", "v");
        }else{
            User user = new User(fname, mname, lname, uname, pass, blnc);
            s = dbm.insertUser(user);
            res.put("create", s);
        }
        return res;
    }
    
    public JSONObject getHistory(JSONObject history){
        JSONObject result = new JSONObject();
        DB_Manipulations dbm = new DB_Manipulations();
        String user = (String) history.get("user");
        result = dbm.retrieveTrans(user);
        return result;
    }
    
    /*PurchaseItems function gets the string of item,qty pairs that are to be purchased from
     * the cart , divides them into two JSON files indexed with tags starting from 1
     * passes those files + username to the database */
    public synchronized JSONObject purchaseItems(JSONObject itemList){
        String list,result,uname;
        JSONObject s=new JSONObject();
        DB_Manipulations dbm = new DB_Manipulations();
        list=(String) itemList.get("itemsList");
        uname=(String) itemList.get("username");

        String temp = "";
        JSONObject items=new JSONObject();
        JSONObject quantaties=new JSONObject();
        //get the (item,quantity) pairs into the vector
        Vector<String> vec = new Vector<String>();
        for (int i = 0; i < list.length(); i++) {
            temp += list.charAt(i);
            if (list.charAt(i) == '\n') {
                vec.add(temp);
                temp = "";
            }
        }
        vec.add(temp);
        temp="";
        String itemname = "";
        String qty="";
        for (int i = 0; i <vec.size(); i++) {
            //parse item name and qty
            for (int j = 0; j < vec.elementAt(i).length(); j++) {
                if (vec.elementAt(i).charAt(j) != ',') {
                    temp += vec.elementAt(i).charAt(j);
                } else {
                    itemname = temp;
                    temp = "";
                }
            }
            qty = temp;
            temp="";
            //give items and their quantities ids form 1
            items.put(String.valueOf(i+1),itemname);
            quantaties.put(String.valueOf(i+1),qty);
        }
        result=dbm.commitPurchase(uname,items,quantaties,vec.size());
        s.put("purchase",result);
        return s;
    }

    public JSONObject getAllProducts(){
        JSONObject s = new JSONObject();
        DB_Manipulations dbm = new DB_Manipulations();
        s = dbm.getProducts();
        return s;
    }
    public JSONObject show_user_info(JSONObject user){
        JSONObject s=new JSONObject();
        DB_Manipulations dbm = new DB_Manipulations();
        String uname = (String) user.get("uname");
        s=dbm.retrieveUserInfo(uname);

        return s;
    }

 }
