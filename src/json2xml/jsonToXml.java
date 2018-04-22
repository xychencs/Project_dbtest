package json2xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class jsonToXml {

	// 如果是空，则用Its_null 代替
	private static String Its_null;

	// 文件的保存路径
	private static String Path;

	// 临时参数
	Document document;
	private JSONObject json;

	// 生成的用户、视图、表单独保存，将会在ClearEnvironment函数中清除
	private static ArrayList<String> USER = new ArrayList();
	private static ArrayList<String> VIEW = new ArrayList();
	private static ArrayList<String> TABLE = new ArrayList();

	// 默认构造函数
	jsonToXml() {
		jsonToXml();
	};

	// 输入Json对象
	jsonToXml(JSONObject json) {
		this.json = json;
		jsonToXml();
	};

	jsonToXml(String s) throws Exception {
		String decode = URLDecoder.decode(s, "utf-8");
		JSONObject js = new JSONObject(decode);
		this.json = js;
		jsonToXml();
	};

	// 输入Json对象、文件生成路径、空值设置
	jsonToXml(JSONObject json, String path, String text) {
		this.json = json;
		this.Its_null = text;
		this.Path = path;
		jsonToXml();
	}

	private void jsonToXml() {
		Its_null = "#Null#";
		Path = "C:\\Users\\Dell\\Desktop\\tes4.xml";
	}

	/*
	 * 查询json数据中类型为Search,值为key的对象,并返回该对象
	 *
	 */
	private JSONObject FindBySearch(String search, String key) throws JSONException {
		JSONArray jsonArray = json.getJSONArray("cells");
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String value = jsonObject.getString(search);
			if (value.equals(key)) {
				return jsonObject;
			}
		}
		return null;
	}

	private void ClearEnvironment(Element root) {
		if ((!USER.isEmpty()) || (!TABLE.isEmpty()) || (!VIEW.isEmpty())) { // 清除环境
			String connn = "0";
			Element CLEAR = root.addElement("CLEAR");
			Element SETCONNECTID = CLEAR.addElement("SETCONNECTID");
			SETCONNECTID.setText(getText(connn));
			Element SQL_CASE = CLEAR.addElement("SQL_CASE");
			if (!USER.isEmpty()) {
				for (int i = 0; i < USER.size(); i++) {
					String user = USER.get(i);
					user = "DROP USER " + user + " CASCADE;";
					Element SQL = SQL_CASE.addElement("SQL");
					SQL.setText(getText(user));
				}
			}
			USER.clear();
			if (!TABLE.isEmpty()) {
				for (int i = 0; i < TABLE.size(); i++) {
					String table = TABLE.get(i);
					table = "DROP TABLE " + table + " CASCADE;";
					Element SQL = SQL_CASE.addElement("SQL");
					SQL.setText(getText(table));
				}
			}
			TABLE.clear();
			if (!VIEW.isEmpty()) {
				for (int i = 0; i < TABLE.size(); i++) {
					String view = VIEW.get(i);
					view = "DROP VIEW " + view + " CASCADE;";
					Element SQL = SQL_CASE.addElement("SQL");
					SQL.setText(getText(view));
				}
			}
			VIEW.clear();
		}
	};

	private Element GenerateXmlRoot() {
		// 创建Document对象
		document = DocumentHelper.createDocument();
		// 创建根节点
		Element root = DocumentHelper.createElement("SQLTEST");
		document.setRootElement(root);
		System.out.println("1" + root.getText() + "1");
		String uid = "SYSDBA";
		String pwd = "SYSDBA";
		String sql = "dm.jdbc.driver.DmDriver";
		String address = "jdbc:dm://222.20.73.234:5236";
		String con = "0";

		Element UID = root.addElement("USERID");
		UID.setText(getText(uid));
		Element PWD = root.addElement("PASSWD");
		PWD.setText(getText(pwd));
		Element PROVIDER = root.addElement("PROVIDER");
		PROVIDER.setText(getText(sql));
		Element URL = root.addElement("URL");
		URL.setText(getText(address));
		Element CONNECT = root.addElement("CONNECT");
		CONNECT.setText(getText(con));
		return root;
	}

	private void print(Document document) throws Exception {
		OutputFormat format = new OutputFormat(); // 创建OutputFormat对象
		// format的ExpandEmptyElements属性默认是false
		format.setExpandEmptyElements(true);
		format.setEncoding("utf-8");
		// format.setSuppressDeclaration(true);
		format.setIndent(true); // 设置是否缩进
		format.setIndentSize(2);
		format.setNewlines(true);
		format.setTrimText(true);
		format.setPadText(true);
		// 获取输出流
		OutputStream out = new FileOutputStream(Path);
		// 创建XMLWriter
		XMLWriter xmlWriter = new XMLWriter(out, format);

		// 写出xml文档
		xmlWriter.write(document);
		// String s = document.asXML().replaceAll("><", ">\r\n<");
		//
		// System.out.println(s);
		xmlWriter.flush();
		xmlWriter.close();

		File localFile = new File(Path);
		// 写文件到本地
		// 读取文件内容
		BufferedReader br = null;

		// 构造BufferedReader对象
		br = new BufferedReader(new InputStreamReader(new FileInputStream(Path), "utf-8"));// (new FileReader(path1));

		String line = null;
		String content = "";
		while ((line = br.readLine()) != null) {

			// 将文本打印到控制台
			content = content + line + "\r\n";

		}
		br.close();
		// 获取content后删除存到服务器的文件
		if (localFile.exists()) {
			// localFile.delete();
		}
		System.out.println(content);

	}

	/*
	 * 通过查询数据库获得相应的json文件
	 */
	private void getJsonBySql() {

		try {
			Class.forName("com.mysql.jdbc.Driver");

			// 数据库连接url
			String url = "jdbc:mysql://localhost:3306/dbtest";
			// 获取数据库连接
			Connection conn = DriverManager.getConnection(url, "root", "123456");
			// sql语句
			String sql = "select * from table_visualcase where title = '0.3.1.1.1.1.1.4.'";
			String result;
			String decode;
			// 创建PreparedStatement对象
			Statement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				result = rs.getString("content");
				decode = URLDecoder.decode(result, "utf-8");
				System.out.println(decode);
				json = new JSONObject(decode);
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * 判断 XML的值是否为空 返回填入的String
	 */
	public static String getText(String tempString) {

		if (!tempString.equals("")) {
			return tempString;
		}
		return Its_null;
	}

	/*
	 * 插入可嵌套图标
	 */
	public static Element Nest(JSONObject jsonObject, Element root) throws JSONException { // 嵌套函数调用
		String type = jsonObject.getString("type");
		if (type.equals("basic.For")) { // For循环5
			Element FOR = root.addElement("FOR");
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			JSONObject TIME = attrs.getJSONObject("FORTIMES");
			String funname = TIME.getString("text");
			Element co = FOR.addElement("FORTIMES");
			co.setText(getText(funname));
			// JSONObject EXP=attrs.getJSONObject("TYPE");
			// funname=EXP.getString("text");
			// co= FOR.addElement("TYPE");
			// co.setText(funname);
			// Element NERROR= FOR.addElement("NERROR");
			// JSONObject SQL=attrs.getJSONObject("NERROR");
			// funname=SQL.getString("text");
			// NERROR.setText(funname);
			return FOR;
		} else if (type.equals("basic.MultipleThread")) { // 多线程控制
			Element MORETHREAD = root.addElement("MORETHREAD");
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			/*
			 * JSONObject LOCK=attrs.getJSONObject("LOCK"); String
			 * funname=LOCK.getString("text"); Element co= MORETHREAD.addElement("LOCK");
			 * co.setText(funname);
			 */
			JSONObject NOTIFY = attrs.getJSONObject("NOTIFY");
			String funname = NOTIFY.getString("text");
			Element co = MORETHREAD.addElement("NOTIFY");
			co.setText(getText(funname));
			JSONObject WAIT = attrs.getJSONObject("WAIT");
			funname = WAIT.getString("text");
			co = MORETHREAD.addElement("WAIT");
			co.setText(getText(funname));
			return MORETHREAD;
		} else if (type.equals("devs.ConditionalBranch")) { // 判断分支
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			JSONObject IF = attrs.getJSONObject("IF");
			type = IF.getString("text");
			Element TYPE = root.addElement("IF");
			TYPE.setText(getText(type));
			JSONObject SUCPRINT = attrs.getJSONObject("SUCPRINT");
			type = SUCPRINT.getString("text");
			Element ELSE = root.addElement("ELSE");
			TYPE = ELSE.addElement("SUCPRINT");
			TYPE.setText(getText(type));
			return ELSE;
		} else if (type.equals("basic.Lock")) { // 锁对象
			Element LOCK = root.addElement("LOCK");
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			JSONObject VALNAM = attrs.getJSONObject("VALNAM");
			String funname = VALNAM.getString("text");
			Element co = LOCK.addElement("VALNAM");
			co.setText(getText(funname));
			/*
			 * JSONObject WAIT=attrs.getJSONObject("WAIT"); funname=WAIT.getString("text");
			 * co= LOCK.addElement("WAIT"); co.setText(funname); JSONObject
			 * NOTIFY=attrs.getJSONObject("NOTIFY"); funname=NOTIFY.getString("text"); co=
			 * LOCK.addElement("NOTIFY"); co.setText(funname);
			 */
			return LOCK;
		}
		return root;
	}

	/*
	 * 插入基本图标
	 */
	public static void Basic(JSONObject jsonObject, Element root) throws JSONException { // 非嵌套函数调用
		String type = jsonObject.getString("type");
		if (type.equals("basic.CreateUser")) { // 创建用户1
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			JSONObject CONN = attrs.getJSONObject("SETCONNECT");
			String con = CONN.getString("text");
			Element SETCONNECTID = root.addElement("SETCONNECTID");
			SETCONNECTID.setText(getText(con));
			Element SQL_CASE = root.addElement("SQL_CASE");
			JSONObject RESULT = attrs.getJSONObject("TYPE");
			type = RESULT.getString("text");
			Element TYPE = SQL_CASE.addElement("TYPE");
			TYPE.setText(getText(type));
			JSONObject NEWUID = attrs.getJSONObject("NEWUID");
			String user = NEWUID.getString("text");
			USER.add(user);
			JSONObject NEWPWD = attrs.getJSONObject("NEWPWD");
			String pwd = NEWPWD.getString("text");
			Element SQL = SQL_CASE.addElement("SQL");
			String str = "CREATE USER " + user + " IDENTIFIED BY " + pwd + ";";
			SQL.setText(getText(str));
		} else if (type.equals("basic.UserLogin")) { // 用户登录2
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			JSONObject RESULT = attrs.getJSONObject("TYPE");
			type = RESULT.getString("text");
			Element TYPE = root.addElement("TYPE");
			TYPE.setText(getText(type));
			JSONObject NEWUID = attrs.getJSONObject("UID");
			String user = NEWUID.getString("text");
			JSONObject NEWPWD = attrs.getJSONObject("PWD");
			String pwd = NEWPWD.getString("text");
			Element USERID = root.addElement("USERID");
			USERID.setText(getText(user));
			Element PASSWD = root.addElement("PASSWD");
			PASSWD.setText(getText(pwd));

			JSONObject SETCONNECT = attrs.getJSONObject("CONNECT");
			System.out.println();
			String setcon = SETCONNECT.getString("text");
			Element CONN = root.addElement("CONNECT");
			CONN.setText(getText(setcon));
		} else if (type.equals("basic.Table")) { // 表操作
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			JSONObject CONN = attrs.getJSONObject("SETCONNECT");
			String con = CONN.getString("text");
			Element SETCONNECTID = root.addElement("SETCONNECTID");
			SETCONNECTID.setText(getText(con));
			Element SQL_CASE = root.addElement("SQL_CASE");
			JSONObject RESULT = attrs.getJSONObject("TYPE");
			type = RESULT.getString("text");
			Element TYPE = SQL_CASE.addElement("TYPE");
			TYPE.setText(getText(type));
			JSONObject SQL = attrs.getJSONObject("SQL");
			type = SQL.getString("text");
			TYPE = SQL_CASE.addElement("SQL");
			TYPE.setText(getText(type));
		} else if (type.equals("basic.RunImport")) { // 运行外部扩展包程序5
			Element RUNIMPORT = root.addElement("RUNIMPORT");
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			JSONObject JARNAME = attrs.getJSONObject("JARNAME");
			String jarname = JARNAME.getString("text");
			Element co = RUNIMPORT.addElement("JARNAME");
			co.setText(getText(jarname));
			JSONObject CLASSNAME = attrs.getJSONObject("CLASSNAME");
			String classname = CLASSNAME.getString("text");
			co = RUNIMPORT.addElement("CLASSNAME");
			co.setText(getText(classname));
			JSONObject FUNNAME = attrs.getJSONObject("FUNNAME");
			String funname = FUNNAME.getString("text");
			co = RUNIMPORT.addElement("FUNNAME");
			co.setText(getText(funname));
			JSONObject ARG = attrs.getJSONObject("ARG");
			String avg = ARG.getString("text");
			co = RUNIMPORT.addElement("ARG");
			co.setText(getText(avg));
		} else if (type.equals("basic.RunRemote")) { // 远程过程调用7
			Element RUNREMOTE = root.addElement("RUNREMOTE");
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			JSONObject FUNNAME = attrs.getJSONObject("FUNNAME");
			String funname = FUNNAME.getString("text");
			Element co = RUNREMOTE.addElement("FUNNAME");
			co.setText(getText(funname));
			JSONObject ARG = attrs.getJSONObject("ARG");
			String avg = ARG.getString("text");
			co = RUNREMOTE.addElement("ARG");
			co.setText(getText(avg));
			JSONObject VALNAME = attrs.getJSONObject("VALNAME");
			avg = VALNAME.getString("text");
			Element ret = RUNREMOTE.addElement("RET");
			co = ret.addElement("VALNAME");
			co.setText(getText(avg));
		} else if (type.equals("basic.RemoteCMD")) { // 远程命令调用13
			Element RUNREMOTE = root.addElement("RUNREMOTE");
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			JSONObject LINUX = attrs.getJSONObject("LINUX");
			String funname = LINUX.getString("text");
			Element co = RUNREMOTE.addElement("LINUX");
			co.setText(getText(funname));
			JSONObject WINDOWS = attrs.getJSONObject("WINDOWS");
			funname = WINDOWS.getString("text");
			Element coo = RUNREMOTE.addElement("WINDOWS");
			coo.setText(getText(funname));
			JSONObject RET = attrs.getJSONObject("RET");
			funname = RET.getString("text");
			Element co2 = RUNREMOTE.addElement("RET");
			co = co2.addElement("VALNAME");
			co.setText(getText(funname));
		} else if (type.equals("basic.View")) { // 操作视图
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			JSONObject CONN = attrs.getJSONObject("SETCONNECT");
			String con = CONN.getString("text");
			Element SETCONNECTID = root.addElement("SETCONNECT");
			SETCONNECTID.setText(getText(con));
			JSONObject RESULT = attrs.getJSONObject("TYPE");
			type = RESULT.getString("text");
			Element SQL_CASE = root.addElement("SQL_CASE");
			Element TYPE = SQL_CASE.addElement("TYPE");
			TYPE.setText(getText(type));
			JSONObject SQL = attrs.getJSONObject("SQL");
			type = SQL.getString("text");
			TYPE = SQL_CASE.addElement("SQL");
			TYPE.setText(getText(type));
		} else if (type.equals("basic.Trans")) { // 设置事务隔离性6
			Element SETTRANS = root.addElement("SETTRANS");
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			JSONObject SQL = attrs.getJSONObject("SQL");
			String avg = SQL.getString("text");
			Element SQL_CASE = SETTRANS.addElement("SQL_CASE");
			Element TYPE = SQL_CASE.addElement("SQL");
			TYPE.setText(getText(avg));
			JSONObject BEGINTRANS = attrs.getJSONObject("BEGINTRANS");
			String funname = BEGINTRANS.getString("text");
			Element co = SETTRANS.addElement("BEGINTRANS");
			co.setText(getText(funname));
			JSONObject ENDTRANS = attrs.getJSONObject("ENDTRANS");
			funname = ENDTRANS.getString("text");
			co = SETTRANS.addElement("ENDTRANS");
			co.setText(getText(funname));
		} else if (type.equals("basic.GetVal")) { // 申请一个全局变量8
			Element GETVAL = root.addElement("GETVAL");
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			JSONObject VALNAME = attrs.getJSONObject("VALNAME");
			String funname = VALNAME.getString("text");
			Element co = GETVAL.addElement("VALNAME");
			co.setText(getText(funname));
			JSONObject VAL = attrs.getJSONObject("VAL");
			funname = VAL.getString("text");
			co = GETVAL.addElement("VAL");
			co.setText(getText(funname));
		} else if (type.equals("basic.SetVal")) { // 使用一个全局变量9
			Element SETVAL = root.addElement("SETVAL");
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			JSONObject VALNAME = attrs.getJSONObject("VALNAME");
			String funname = VALNAME.getString("text");
			Element co = SETVAL.addElement("VALNAME");
			co.setText(getText(funname));
			JSONObject VAL = attrs.getJSONObject("VAL");
			funname = VAL.getString("text");
			co = SETVAL.addElement("VAL");
			co.setText(getText(funname));
		} else if (type.equals("basic.Interact")) { // 人机交互10
			Element CTRLMSG = root.addElement("CTRLMSG");
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			JSONObject CTRLTYPE = attrs.getJSONObject("CTRLTYPE");
			String funname = CTRLTYPE.getString("text");
			Element co = CTRLMSG.addElement("CTRLTYPE");
			co.setText(getText(funname));
			JSONObject ARG = attrs.getJSONObject("ARG");
			funname = ARG.getString("text");
			co = CTRLMSG.addElement("ARG");
			co.setText(getText(funname));
			Element RET = CTRLMSG.addElement("RET");
			JSONObject SQL = attrs.getJSONObject("CH");
			RET.setText(getText(funname));
		} else if (type.equals("basic.SetTrans")) { // 设置数据库隔离级别15
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			JSONObject SETTRANS = attrs.getJSONObject("SETTRANS");
			String funname = SETTRANS.getString("text");
			Element co = root.addElement("SETTRANS");
			co.setText(getText(funname));
		} else if (type.equals("basic.Sleep")) { // 线程挂起16
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			JSONObject SLEEP = attrs.getJSONObject("SLEEP");
			String funname = SLEEP.getString("text");
			Element co = root.addElement("SLEEP");
			co.setText(getText(funname));
		} else if (type.equals("basic.Select")) { // 查询数据库结果集17
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			JSONObject SETCONNECTID = attrs.getJSONObject("SETCONNECT");
			String funname = SETCONNECTID.getString("text");
			Element co = root.addElement("SETCONNECTID");
			co.setText(getText(funname));
			JSONObject RESULT = attrs.getJSONObject("TYPE");
			type = RESULT.getString("text");
			Element SQL_CASE = root.addElement("SQL_CASE");
			Element TYPE = SQL_CASE.addElement("TYPE");
			TYPE.setText(getText(type));
			JSONObject SQL = attrs.getJSONObject("SQL");
			type = SQL.getString("text");

			if (type.length() > 0) {
				if (type.charAt(type.length() - 1) != ';') {
					type = type + ";";
				}
			}

			TYPE = SQL_CASE.addElement("SQL");
			TYPE.setText(getText(type));
			JSONObject COLUMN = attrs.getJSONObject("COLUMN");

			funname = COLUMN.getString("text");
			Element RESULT_ = SQL_CASE.addElement("RESULT");

			// 添加对应查询结果
			add_column(RESULT_, funname);

			// Element RECORD=RESULT_.addElement("RECORD");
			// Element co_=RECORD.addElement("COLUMN");
			// co_.setText(getText(funname));

		} else if (type.equals("basic.CreateTable")) { // 创建表
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			JSONObject CONN = attrs.getJSONObject("SETCONNECT");
			String con = CONN.getString("text");
			Element SETCONNECTID = root.addElement("SETCONNECTID");
			SETCONNECTID.setText(getText(con));
			Element SQL_CASE = root.addElement("SQL_CASE");
			JSONObject TYPE = attrs.getJSONObject("TYPE");
			String funname = TYPE.getString("text");
			Element co = SQL_CASE.addElement("TYPE");
			co.setText(getText(funname));
			Element SQL = SQL_CASE.addElement("SQL");
			JSONObject TableName = attrs.getJSONObject("TABLENAME");
			String tablename = TableName.getString("text");
			TABLE.add(tablename);
			JSONObject Columns = attrs.getJSONObject("COLUMNS");
			String columns = Columns.getString("text");
			String table = "create table " + tablename + "(" + columns + ");";
			SQL.setText(getText(table));
		} else if (type.equals("basic.Print")) { // 打印输出
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			JSONObject SUCPRINT = attrs.getJSONObject("SUCPRINT");
			String funname = SUCPRINT.getString("text");
			Element co = root.addElement("SUCPRINT");
			co.setText(getText(funname));
			JSONObject ERRPRINT = attrs.getJSONObject("ERRPRINT");
			funname = ERRPRINT.getString("text");
			co = root.addElement("ERRPRINT");
			co.setText(getText(funname));
		} else if (type.equals("basic.CreateView")) { // 创建视图
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			JSONObject CONN = attrs.getJSONObject("SETCONNECT");
			String con = CONN.getString("text");
			Element SETCONNECTID = root.addElement("SETCONNECTID");
			SETCONNECTID.setText(getText(con));
			Element SQL_CASE = root.addElement("SQL_CASE");
			JSONObject RESULT = attrs.getJSONObject("TYPE");
			type = RESULT.getString("text");
			Element TYPE = SQL_CASE.addElement("TYPE");
			TYPE.setText(getText(type));
			JSONObject ViewName = attrs.getJSONObject("VIEWNAME");
			String viewname = ViewName.getString("text");
			VIEW.add(viewname);
			JSONObject Columns = attrs.getJSONObject("CONTENTS");
			String columns = Columns.getString("text");
			Element SQL = SQL_CASE.addElement("SQL");
			String str = "CREATE VIEW " + viewname + " AS " + columns;
			SQL.setText(getText(str));
		} else if (type.equals("basic.Delete")) { // 删除操作23
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			JSONObject CONN = attrs.getJSONObject("SETCONNECT");
			String con = CONN.getString("text");
			Element SETCONNECTID = root.addElement("SETCONNECTID");
			SETCONNECTID.setText(getText(con));
			Element SQL_CASE = root.addElement("SQL_CASE");
			JSONObject RESULT = attrs.getJSONObject("TYPE");
			type = RESULT.getString("text");
			if (!type.equals("")) {
				Element TYPE = SQL_CASE.addElement("TYPE");
				TYPE.setText(getText(type));
			}
			JSONObject VIEW = attrs.getJSONObject("DELETEVIEW");
			String view = VIEW.getString("text");
			if (!view.equals("")) {
				view = "DROP VIEW " + view + " CASCADE;";
				Element SQL = SQL_CASE.addElement("SQL");
				SQL.setText(getText(view));
			}
			JSONObject DELETETABLE = attrs.getJSONObject("DELETETABLE");
			String table = DELETETABLE.getString("text");
			if (!table.equals("")) {
				JSONObject WHERE = attrs.getJSONObject("WHERE");
				String where = WHERE.getString("text");
				if (where.equals("")) {
					table = "DROP TABLE " + table + " CASCADE;";
					Element SQL = SQL_CASE.addElement("SQL");
					SQL.setText(getText(table));
				} else {
					table = "DELETE FROM " + table + " WHERE " + where;
					Element SQL = SQL_CASE.addElement("SQL");
					SQL.setText(getText(table));
				}
			}
		} else if (type.equals("basic.Insert")) { // 插入操作
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			JSONObject CONN = attrs.getJSONObject("SETCONNECT");
			String con = CONN.getString("text");
			Element SETCONNECTID = root.addElement("SETCONNECTID");
			SETCONNECTID.setText(getText(con));
			Element SQL_CASE = root.addElement("SQL_CASE");
			JSONObject RESULT = attrs.getJSONObject("TYPE");
			type = RESULT.getString("text");
			Element TYPE = SQL_CASE.addElement("TYPE");
			TYPE.setText(getText(type));
			JSONObject TableName = attrs.getJSONObject("INSERTTARGET");
			String viewname = TableName.getString("text");
			JSONObject Columns = attrs.getJSONObject("VALUES");
			String columns = Columns.getString("text");
			Element SQL = SQL_CASE.addElement("SQL");
			String str = "insert into " + viewname + " values(" + columns + ")";
			SQL.setText(getText(str));
		} else if (type.equals("basic.Update")) { // 更新操作
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			JSONObject CONN = attrs.getJSONObject("SETCONNECT");
			String con = CONN.getString("text");
			Element SETCONNECTID = root.addElement("SETCONNECTID");
			SETCONNECTID.setText(getText(con));
			Element SQL_CASE = root.addElement("SQL_CASE");
			JSONObject RESULT = attrs.getJSONObject("TYPE");
			type = RESULT.getString("text");
			Element TYPE = SQL_CASE.addElement("TYPE");
			TYPE.setText(getText(type));
			JSONObject TableName = attrs.getJSONObject("UPDATETARGET");
			String viewname = TableName.getString("text");
			JSONObject Columns = attrs.getJSONObject("SET");
			String columns = Columns.getString("text");
			JSONObject whe = attrs.getJSONObject("WHERE");
			String where = whe.getString("text");
			Element SQL = SQL_CASE.addElement("SQL");
			String str = "update " + viewname + " set " + columns + "" + " where " + where;
			SQL.setText(getText(str));
		} else if (type.equals("basic.AlterPwd")) { // 修改用户密码
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			JSONObject USER = attrs.getJSONObject("USER");
			String user = USER.getString("text");
			JSONObject PWD = attrs.getJSONObject("PWD");
			String pwd = PWD.getString("text");
			JSONObject CONN = attrs.getJSONObject("SETCONNECTID");
			String con = CONN.getString("text");
			Element SETCONNECTID = root.addElement("SETCONNECTID");
			SETCONNECTID.setText(getText(con));
			Element SQL_CASE = root.addElement("SQL_CASE");
			JSONObject RESULT = attrs.getJSONObject("TYPE");
			type = RESULT.getString("text");
			Element TYPE = SQL_CASE.addElement("TYPE");
			TYPE.setText(getText(type));
			Element SQL = SQL_CASE.addElement("SQL");
			String str = "ALTER " + user + " IDENTIFIED BY " + pwd + ";";
			SQL.setText(getText(str));
		} else if (type.equals("basic.Grant")) { // 授予用户对表或者视图权限
			JSONObject attrs = jsonObject.getJSONObject("attrs");
			JSONObject TABLE = attrs.getJSONObject("TABLE");
			String table = TABLE.getString("text");
			JSONObject VIEW = attrs.getJSONObject("VIEW");
			String view = VIEW.getString("text");
			if ((!table.equals("")) || (!view.equals(""))) {
				JSONObject CONN = attrs.getJSONObject("SETCONNECTID");
				String con = CONN.getString("text");
				Element SETCONNECTID = root.addElement("SETCONNECTID");
				SETCONNECTID.setText(getText(con));
				Element SQL_CASE = root.addElement("SQL_CASE");
				JSONObject RESULT = attrs.getJSONObject("TYPE");
				type = RESULT.getString("text");
				Element TYPE = SQL_CASE.addElement("TYPE");
				TYPE.setText(getText(type));
				JSONObject USER = attrs.getJSONObject("USER");
				String user = USER.getString("text");
				JSONObject OPERATION = attrs.getJSONObject("OPERATION");
				String operation = OPERATION.getString("text");
				Element SQL = SQL_CASE.addElement("SQL");
				if (!table.equals("")) {
					String str = "GRANT " + operation + " " + table + " to " + user + ";";
					SQL.setText(getText(str));
				} else if (!view.equals("")) {
					String str = "GRANT " + operation + " " + view + " to " + user + ";";
				}
			}
		}

	}

	/*
	 * 查表需要特殊处理的方法
	 */
	private static void add_column(Element e, String text) {
		if (text.equals("null")) {
			return;
		}
		String row[] = text.split(";;");
		for (int i = 0; i < row.length; i++) {
			Element column = e.addElement("RECORD");
			String list[] = row[i].split(",,");
			for (int j = 0; j < list.length; j++) {
				column.addElement("COLUMN").setText(getText(list[j]));
			}
		}
	}

	public static String getIts_null() {
		return Its_null;
	}

	public static void setIts_null(String its_null) {
		Its_null = its_null;
	}

	public static String getPath() {
		return Path;
	}

	public static void setPath(String path) {
		Path = path;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	/*
	 * @function 找到数组中第一个id
	 * 
	 * @param ids[] 传入的Id数组
	 * 
	 * @return String
	 */
	private String getFirst() throws Exception {
		JSONArray jsonArray = json.getJSONArray("cells");
		String id = null;
		boolean Find = false;
		while (!Find) {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String type = jsonObject.getString("type");
				if (type.equals("link") && !jsonObject.has("parent")) {
					JSONObject targetObject = jsonObject.getJSONObject("target");
					String targetid = targetObject.getString("id");
					if (targetid == id || id == null) {
						JSONObject sourceObject = jsonObject.getJSONObject("source");
						String sourceid = sourceObject.getString("id");
						id = sourceid;
						break;
					}
				}
				if (i == jsonArray.length() - 1) {
					Find = true;
				}
			}
		}
		return id;
	}

	private String getFirst(String array[]) throws Exception {
		if (array.length == 0) {
			return null;
		}
		if (array.length == 1) {
			return array[0];
		}
		String id = null;
		boolean Find = false;
		while (!Find) {
			for (int i = 0; i < array.length; i++) {
				JSONObject jsonObject = FindBySearch("id", array[i]);
				String type = jsonObject.getString("type");
				if (type.equals("link")) {
					JSONObject targetObject = jsonObject.getJSONObject("target");
					String targetid = targetObject.getString("id");
					if (targetid == id || id == null) {
						JSONObject sourceObject = jsonObject.getJSONObject("source");
						String sourceid = sourceObject.getString("id");
						id = sourceid;
						break;
					}
				}
				if (i == array.length - 1) {
					Find = true;
				}
			}
		}
		return id;
	}

	/*
	 * function 查找图符的下一个图符的id
	 *
	 * @param id
	 *
	 * @return 返回下一个图符的id或者null
	 * 
	 */
	private String findNextById(String id) throws Exception {
		JSONArray jsonArray = json.getJSONArray("cells");
		boolean Find = false;
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String type = jsonObject.getString("type");
			if (type.equals("link")) {
				JSONObject sourceObject = jsonObject.getJSONObject("source");
				String sourceid = sourceObject.getString("id");
				if (sourceid.equals(id)) {
					JSONObject targetObject = jsonObject.getJSONObject("target");
					String targetid = targetObject.getString("id");
					id = targetid;
					Find = true;
					break;
				}
			}
		}
		if (Find) {
			return id;
		} else {
			return null;
		}
	}

	public void generate(String id, Element n) throws Exception {
		if (id == null) {
			return;
		}
		if (FindBySearch("id", id).has("embeds")) {
			Element e = Nest(FindBySearch("id", id), n);
			generate(getFirst(getEmbeds(id)), e);
			generate(findNextById(id), n);
		}
		if (!FindBySearch("id", id).has("embeds")) {
			Basic(FindBySearch("id", id), n);
			generate(findNextById(id), n);
		}
	}

	public String[] getEmbeds(String id) throws JSONException {
		JSONArray jsonArray = json.getJSONArray("cells");
		String e[] = null;

		JSONObject jsonObject = FindBySearch("id", id);
		String type = jsonObject.getString("type");
		if (!type.equals("link") && jsonObject.has("embeds")) {
			String embeds = jsonObject.getString("embeds");
			System.out.println(embeds);
			embeds = embeds.substring(1, embeds.length() - 1);
			e = embeds.split(",");
		}
		for (int i = 0; i < e.length; i++) {
			e[i] = e[i].substring(1, e[i].length() - 1);
		}
		return e;
	}

	public void getXml() throws Exception {
		getJsonBySql();
		Element root = GenerateXmlRoot();
		generate(getFirst(), root);
		ClearEnvironment(root);
		print(document);
	}
}
