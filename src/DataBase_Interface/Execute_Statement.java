package DataBase_Interface;

import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONObject;

import SQL_Support.SQL_Commands;

public class Execute_Statement {
	private SQL_Commands sql;

	public Execute_Statement() {
		// TODO Auto-generated constructor stub
		Database_Auth admin = new Database_Auth();
		sql = new SQL_Commands(admin.admin(), admin.pass(), admin.dataBase());
	}

	public void Insert(JSONObject obj, String view) {
		try {
			Entity entity = new Entity();
			System.out.println(entity);
			JSONObject[] obj_array = entity.Split_Json_Objects(obj, view);
			if (!Possible(obj_array)) {
				throw new  Exception ("Insert could not be performed");
			}
			for (JSONObject object : obj_array) {
				object.remove("Status");
				sql.Insert(object);
			} 
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Error in Insert Execute_Statement");
		}
	}
	public void Delete(JSONObject obj, String view) {
		try {
			Entity entity = new Entity();
			JSONObject[] obj_array = entity.Split_Json_Objects(obj, view);
			
			if (!Possible(obj_array)) {
				throw new  Exception ("Insert could not be performed");
			}
			
			for (JSONObject object : obj_array) {
				object.remove("Status");
				sql.Delete(object);
			}
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Error in Delete Execute_Statement");
		}
	}
	public void Update(JSONObject what ,  JSONObject where , String view)  {
		try {
			
			Entity entity = new Entity();
			JSONObject[] what_array = entity.Split_Json_Objects(what, view);
			JSONObject[] where_array = entity.Split_Json_Objects(where, view);
		   
			if (!Possible(where_array)) {
				throw new  Exception ("Update could not be performed");
			
			}
			for (int i = 0  ; i<where_array.length; i++) {
				where_array[i].remove("Status");
				what_array[i].remove("Status");
				sql.Update(what_array[i]  , where_array[i]);
			}
			
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Error in Update Execute_Statement");
		}
	}
	public ArrayList<JSONObject> Read(JSONObject obj, String type) {
		try {
			Get_Read_Query read = new Get_Read_Query();
			String required_Query = read.get(obj, type);
			return (ArrayList<JSONObject>) sql.Read(required_Query, read.values , read.features);
			
			
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Error in Read Execute_Statement");
		}
		return null;
			
		
	}
	
	private boolean Possible(JSONObject[] obj) {
		for (JSONObject object : obj) {
			if (object.get("Status").equals("NOT OK")) {
				return false;
			}
		}
		return true;
	}
}
