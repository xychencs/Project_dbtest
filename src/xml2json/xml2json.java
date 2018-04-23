package xml2json;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.dom.DOMNodeHelper.EmptyNodeList;
import org.dom4j.io.SAXReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class xml2json {
	public decide decider;
	public String path;

	public StringBuilder change(StringBuilder s, String prev, String text) {
		return s;
	}

	public xml2json(String path,boolean clear) {
		this.path = path;
		decider = new decide(clear);
	}

	public String getJson() throws Exception {
		return decider.getString(path);
	}
}

class decide {
	public int x;
	public int y;
	public String TYPE;
	public String USERID;
	public String PASSWD;
	public String Connect;
	public Document document;
	public Producer producer;
	public StringBuilder cell;
	public int id;
	public boolean isFirstConnect = true;
	public ArrayList<Integer> array;
	public String ForTimes;
	public boolean Clear ;
	public Element ele;
	
	public decide(boolean clear) {
		Clear = clear;
		x = 0;
		y = 0;
		id = 0;
		producer = new Producer();
		cell = new StringBuilder();
		array = new ArrayList<Integer>();
	};

	public String getString(String path) throws Exception {
		// ͨ�����ļ��е�xml��ǩ��ȡ���������ڵ������б���ÿ��Ԫ��
		SAXReader reader = new SAXReader();
		try {
			document = reader.read(new File(path));
			// ͨ��document�����ȡ���ڵ�bookstore
			Element root = document.getRootElement();
			// ͨ��element�����elementIterator������ȡ������
			Iterator it = root.elementIterator();
			// ͨ���Ը��ڵ���з���
			getNodes(root, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ��� cells ͷ����β��
		String head = "{\"cells\": [";
		String trail = "]}";
		// ������� ���� �ķ���
		addLink(0);
		cell.deleteCharAt(cell.lastIndexOf(","));
		cell.insert(0, head);
		cell.append(trail);
		
		if(Clear) {
			JSONObject js;
			JSONObject json = new JSONObject(cell.toString());
			JSONArray arr = json.getJSONArray("cells");
			for (int i = 0; i < arr.length(); i++) {
				js = (JSONObject) arr.get(i);
				if (js.has("parent") && js.get("parent").equals("")) {
					js.put("parent", (Object) null);
				}
			}
			String sss = String.valueOf(json);
			return sss;
		}
		
		return cell.toString();
	}

	/*
	 * @function �����ڵ��һ������
	 * 
	 * @param node ��ʾ��Ҫ���ʵĵ�ǰ�ڵ�
	 *
	 * @param level ��ʾ���ʵĵ�ǰ�ڵ�Ĳ�� ��1��Ϊ�����㣬2��3...֮��Ķ���ʾǶ��ͼ�������
	 * 
	 * @param ����ֵvoid
	 */
	public void getNodes(Element node, int level) {
		boolean firstNode = true;

		// ��ǰ�ڵ��Ƿ��ǵ�һ�����ӣ���һ��������Ҫ���������ݿ⣬��ʱ��ʱҲû��ͼ��
		if (isFirstConnect && node.getName().toUpperCase() == "CONNECT") {
			isFirstConnect = false;
			Connect = node.getTextTrim();
			return;
		}
		// ���������ǩֱ�ӷ��أ�û�����ͼ����Ĭ��û��
		if (node.getName().toUpperCase() == "CLEAR") {
			return;
		}
		// ���ݵ�ǰ�ڵ�ı�ǩ����ƥ��
		SearchNode(node, level);
		List<Element> listElement = node.elements();// ���е�ǰ�ڵ��list
		if (listElement.isEmpty()) {
		} else {
			for (Element e : listElement) {// �����ýڵ���ӽڵ�
				// �������SQL_CASE�����ŵĽڵ㣬��β�����
				String name = e.getName().toUpperCase();
				if (name.equals("RESULT")) {
					continue;
				}
				if (name.equals("SETVAL") || name.equals("GETVAL")) {
					SearchNode(e, level+1);
					continue;
				}
				if (e.getParent().getName().toUpperCase().equals("SQL_CASE")) {
					getNodes(e, level);// �ݹ�
				} else {
					getNodes(e, level + 1);// �ݹ�
				}
			}
		}
	}

	public void SearchNode(Element attr, int level) {
		String sName = attr.getName().toUpperCase();
		String sValue = attr.getTextTrim();
		boolean hasAdd = false;
		switch (sName) {
		case "USERID":
			USERID = sValue;
			break;
		case "PASSWD":
			PASSWD = sValue;
			break;
		case "TYPE":
			TYPE = sValue;
			break;
		case "CONNECT":
			cell.append(producer.produce("sUserLogin", level).set("owner_id", (id++) + (1000 * level))
					.set("position_x", x += 80).set("position_y", y += 80).set("attrs_UID", USERID)
					.set("attrs_PWD", PASSWD).set("attrs_TYPE", TYPE).set("attrs_CONNECT", sValue)
					.set("parent_id", "parent_id" + (1000 * level + (id - 1))).getString());
			hasAdd = true;
			break;
		case "SQL":
			ele = attr;
			analyseSQL(sValue, level);
			break;
		case "SQL_CASE":
			break;
		case "FORTIMES":
			ForTimes = sValue;
			String changeString = "attrs_FORTIMES" + (1000 * (level - 1) + (id - 1));
			int index = cell.indexOf(changeString);
			cell.replace(index, index + changeString.length(), sValue);
			break;
		case "FOR":
			cell.append(producer.produce("sFor", level).set("owner_id", (id++) + (1000 * (level)))
					.set("position_x", x += 80).set("position_y", y += 80)
					.set("attrs_FORTIMES", "attrs_FORTIMES" + (1000 * (level) + (id - 1)))
					.set("parent_id", "parent_id" + (1000 * level + (id - 1)))
					.set("embeds_id", "embeds_id" + (1000 * level + (id - 1))).getString());
			hasAdd = true;
			break;
		case "SETVAL":
			cell.append(producer.produce("sSetVal", level).set("owner_id", (id++) + (1000 * (level)))
					.set("position_x", x += 80).set("position_y", y += 80)
					.set("attrs_VALNAME", attr.element("VALNAME").getText())
					.set("attrs_VAL", attr.element("VAL").getText())
					.set("parent_id", "parent_id" + (1000 * level + (id - 1))).getString());
			hasAdd = true;
			break;
			
		case "GETVAL":
			cell.append(producer.produce("sGetVal", level).set("owner_id", (id++) + (1000 * (level)))
					.set("position_x", x += 80).set("position_y", y += 80)
					.set("attrs_VALNAME", attr.element("VALNAME").getText())
					.set("attrs_VAL", attr.element("VAL").getText())
					.set("parent_id", "parent_id" + (1000 * level + (id - 1))).getString());
			hasAdd = true;
			break;
		default:
			// ��ʱ�����ռ䣬���������ʱ������λ��
			// System.out.println("i dont konw this label "+sName);
		}
		// ������Щ��ǩ�ﲢû�����ͼ����������һ��boolean���Լ�¼
		// �������ˣ���ô�ڴ���ͼ��id������������Idֵ�ļ�¼
		if (hasAdd) {
			array.add((id - 1) + (1000 * level));
		}
	}

	/*
	 * @function ����sql,����sql���ɶ�Ӧ��sqlͼ��
	 * 
	 * @sValue �������sql��䣬��Ҫ�������в�֣�����ʲô����
	 * 
	 * @level ��ǰͼ��Ӧ���ڵĲ��
	 * 
	 * @return ����ֵvoid
	 */
	public void analyseSQL(String sValue, int level) {
		String[] temp = sValue.trim().split(" ");
		String[] b;
		boolean hasAdd = true;
		switch (temp[0].toUpperCase()) {
		case "CREATE":
			switch (temp[1].toUpperCase()) {
			case "TABLE":
				sValue = sValue.replaceFirst("\\(", " (");
				System.out.println(sValue);
				sValue = sValue.replaceAll("\\s{2,}", " ");
				System.out.println(sValue);
				b = sValue.split("(?<!\\(.{0,100})\\s");
				cell.append(producer.produce("sCreateTable", level).set("owner_id", (id++) + (1000 * level))
						.set("position_x", x += 80).set("position_y", y += 80).set("attrs_TABLENAME", temp[2])
						.set("COLUMNS", b[3]).set("attrs_TYPE", TYPE).set("attrs_SETCONNECT", Connect)
						.set("parent_id", "parent_id" + (1000 * level + (id - 1))).getString());
				break;
			case "USER":
				cell.append(producer.produce("sCreateUser", level).set("owner_id", (id++) + (1000 * level))
						.set("position_x", x += 80).set("position_y", y += 80).set("attrs_NEWUID", temp[2])
						.set("attrs_NEWPWD", temp[5]).set("attrs_TYPE", TYPE).set("attrs_SETCONNECT", Connect)
						.set("parent_id", "parent_id" + (1000 * level + (id - 1))).getString());
				break;
			case "VIEW":
				cell.append(producer.produce("sCreateView", level).set("owner_id", (id++) + (1000 * level))
						.set("position_x", x += 80).set("position_y", y += 80).set("attrs_VIEWNAME", temp[2])
						.set("attrs_CONTENTS", temp[5]).set("attrs_TYPE", TYPE).set("attrs_SETCONNECT", Connect)
						.set("parent_id", "parent_id" + (1000 * level + (id - 1))).getString());
				break;
			default:
				// �����Ժ���
				hasAdd = false;
				System.err.println("cant recognize " + sValue);
				break;
			}
			break;
		case "SELECT":
			//ȷ���Ƿ��н���Ƚ�
			StringBuilder result = new StringBuilder("");
			if(ele.getParent().element("RESULT")!=null) {
				List<Element> listElement_RECORD = ele.getParent().element("RESULT").elements();
				for (Element e : listElement_RECORD) {
					List<Element> listElement_Column = e.elements();
					for (Element eColumn : listElement_Column) {
						result.append(eColumn.getText());
						result.append(",,");
					}
					result.append(";;");
				}
			}else {
				System.out.println("no need compare result");
			}
			
			cell.append(producer.produce("sSelect", level).set("owner_id", (id++) + (1000 * level))
					.set("position_x", x += 80).set("position_y", y += 80)
					.set("attrs_COLUMN", result)
					.set("attrs_SQL",sValue)
					.set("attrs_TYPE", TYPE)
					.set("parent_id", "parent_id" + (1000 * level + (id - 1)))
					.getString());
			
			
//			System.out.println(ele.getParent().element("RESULT").element("RECORD").element("COLUMN").getText());
			break;
		case "DROP":
			break;
		case "ALTER": //�޸�����  ALTER userName IDENTIFIED BY username 
			cell.append(producer.produce("sAlterPwd", level).set("owner_id", (id++) + (1000 * level))
					.set("position_x", x += 80).set("position_y", y += 80).set("attrs_USER", temp[1])
					.set("attrs_PWD", temp[4]).set("attrs_TYPE", TYPE)
					.set("parent_id", "parent_id" + (1000 * level + (id - 1))).getString());
			break;
			
		case "INSERT":
			b= sValue.split("(?<=\\w|\\))(\\s)(?=\\(|\\w)");
//			System.out.println(b[i]);
			String table_name;
			if(b.length == 5) {
				table_name = b[2];
			}else {
				table_name = b[2] + b[3];
			}
			cell.append(producer.produce("sInsert", level).set("owner_id", (id++) + (1000 * level))
					.set("position_x", x += 80).set("position_y", y += 80)
					.set("attrs_INSERTTARGET",table_name).set("attrs_VALUES", b[b.length-1])
					.set("attrs_TYPE", TYPE)
					.set("parent_id", "parent_id" + (1000 * level + (id - 1))).getString());
			break;
			
		case "GRANT":
			
			break;
			
		case "UPDATE":
			b = sValue.split("((?<=WHERE|SET|UPDATE)(\\s)+)|((\\s)+(?=WHERE|SET))");
			if(b.length != 6) {
				System.err.println("SQL cant be recognized :" + sValue);
				return;
			}
			cell.append(producer.produce("sInsert", level).set("owner_id", (id++) + (1000 * level))
					.set("position_x", x += 80).set("position_y", y += 80)
					.set("attrs_UPDATETARGET",b[1]).set("attrs_SET_", b[3])
					.set("attrs_WHERE", b[5])
					.set("attrs_TYPE", TYPE)
					.set("parent_id", "parent_id" + (1000 * level + (id - 1))).getString());
			break;
		default:
			// �����Ժ���
			hasAdd = false;
			System.err.println("SQL cant be recognized :" + sValue);
			break;
		}
		if (hasAdd) {
			array.add((id - 1) + (1000 * level));
		}
	}

	// ͨ������������ַ����е�һЩֵ�����޸�
	public void changeValue(String prev, String rep) {
		int index = cell.indexOf(prev);
		cell.replace(index, index + prev.length(), rep);
	}

	@SuppressWarnings("rawtypes")
	public void addLink(int id) {
		LinkedList ssss = new LinkedList();
		LinkedList sonss = new LinkedList();
		String embeds = ""; // parent
		String sons = "";
		while (array.size() - 1 >= id) {
			// idֵΪ���һ�����ͼ����ʱ��
			if (array.size() - 1 == id) {
				changeValue("parent_id" + String.valueOf(array.get(id)), embeds);
				if (array.get(id) / 1000 != 1) {
					sons += String.valueOf(array.get(id)) + " ";
				}
				return;
			}
			// ͬ���ͼ��
			if ((array.get(id)) / 1000 == (array.get(id + 1) / 1000)) {
				sons += String.valueOf(array.get(id)) + " ";

				changeValue("parent_id" + String.valueOf(array.get(id)), embeds);

				cell.append(producer.produce("sLink", array.get(id) / 1000)
						.set("owner_id", String.valueOf(array.get(id)) + String.valueOf(array.get(id + 1)))
						.set("target_id", String.valueOf(array.get(id + 1)))
						.set("source_id", String.valueOf(array.get(id))).set("parent_id", String.valueOf(embeds))
						.getString());
			}
			// ����Ƕ��
			if ((array.get(id)) / 1000 < (array.get(id + 1) / 1000)) { 

				changeValue("parent_id" + String.valueOf(array.get(id)), embeds);

				ssss.add(embeds);
				embeds = String.valueOf(array.get(id));
				sons += String.valueOf(array.get(id)) + " ";
				sonss.add(sons);
				sons = "";
				embeds = String.valueOf(array.get(id));
			}
			// �˳�Ƕ��
			if ((array.get(id)) / 1000 > (array.get(id + 1) / 1000)) { 
				sons += String.valueOf(array.get(id)) + " ";

				changeValue("parent_id" + String.valueOf(array.get(id)), embeds);

				int k = (array.get(id)) / 1000 - (array.get(id + 1) / 1000);
				for (int i = 0; i < k - 1; i++) {
					changeValue("embeds_id" + embeds, getSons(sons));
					embeds = (String) ssss.pollLast();
					sons = (String) sonss.pollLast();
				}
				changeValue("embeds_id" + embeds, getSons(sons));
				cell.append(producer.produce("sLink", (array.get(id + 1) / 1000))
						.set("owner_id", String.valueOf(embeds) + String.valueOf(array.get(id + 1)))
						.set("target_id", String.valueOf(array.get(id + 1))).set("source_id", String.valueOf(embeds))
						.set("parent_id", String.valueOf(embeds = (String) ssss.pollLast())).getString());
				sons = (String) sonss.pollLast();
			}
			id++;
		}
	}

	public String getSons(String son) {
		String sons = son.trim();
		String[] s = sons.split("\\s");
		StringBuilder embeds_sons = new StringBuilder("");

		embeds_sons.append("\"" + s[0] + "\"");
		if (s.length == 1) {
			return embeds_sons.toString();
		}

		for (int i = 1; i < s.length; i++) {
			embeds_sons.append(",\"" + s[i] + "\"" + ",");
			embeds_sons.append("\"" + s[i - 1] + s[i] + "\"");
		}

		return embeds_sons.toString();
	}

	
	
	
	
}

class Producer implements StaticString {

	private static StringBuilder stringBuilder;

	public Producer() {

	}

	public Producer produce(String Type, int id) {
		switch (Type) {
		case "sLink":
			if (id == 1) {
				stringBuilder = new StringBuilder(sLink);
			} else {
				stringBuilder = new StringBuilder(sLink_2);
			}
			return this;
		case "sCreateTable":
			stringBuilder = new StringBuilder(sCreateTable);
			break;
		case "sUserLogin":
			stringBuilder = new StringBuilder(sUserLogin);
			break;
		case "sCreateUser":
			stringBuilder = new StringBuilder(sCreateUser);
			break;
		case "sCreateView":
			stringBuilder = new StringBuilder(sCreateView);
			break;
		case "sInsert":
			stringBuilder = new StringBuilder(sInsert);
			break;
		case "sDelete":
			stringBuilder = new StringBuilder(sDelete);
			break;
		case "sUpdate":
			stringBuilder = new StringBuilder(sUpdate);
			break;
		case "sSelect":
			stringBuilder = new StringBuilder(sSelect);
			break;
		case "sTrans":
			stringBuilder = new StringBuilder(sTrans);
			break;
		case "sSetTrans":
			stringBuilder = new StringBuilder(sSetTrans);
			break;
		case "sGrant":
			stringBuilder = new StringBuilder(sGrant);
			break;
		case "sFor":
			stringBuilder = new StringBuilder(sFor);
			break;
		case "sSetVal":
			stringBuilder = new StringBuilder(sSetVal);
			break;
		case "sGetVal":
			stringBuilder = new StringBuilder(sGetVal);
			break;
		default:
			return this;
		}
		if (id == 1) {
			set("imageSize", Visable);
			set("closed_id", 1);
		} else {
			set("imageSize", unVisable);
			set("closed_id", 0);
		}
		return this;
	}

	public String getString() {
		stringBuilder.append(",");
		return stringBuilder.toString();
	}

	public String addComma() {
		stringBuilder.append(",");
		return stringBuilder.toString();
	}

	public Producer set(String prev, int rep) {
		return set(prev, String.valueOf(rep));
	}

	public Producer set(String prev, StringBuilder rep) {
		return set(prev, String.valueOf(rep));
	}
	
	public Producer set(String prev, String rep) {
		int a = stringBuilder.indexOf(prev);
		if (a < 0) {
			return this;
		}
		stringBuilder.replace(a, a + prev.length(), rep);
		return this;
	}

}
