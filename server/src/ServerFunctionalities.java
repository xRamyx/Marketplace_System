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

 }
