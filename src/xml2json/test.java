package xml2json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class test {
	public static void main(String[] args) throws Exception {
		String path = "C:\\Users\\Dell\\Desktop\\tes3.xml";
		xml2json a = new xml2json(path,true);
		String x = a.getJson().toString();
		System.out.println(x);
	}
}
