package xml2json;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class xml2json {
	public decide decider;
	public String path;

	public StringBuilder change(StringBuilder s, String prev, String text) {
		return s;
	}

	public xml2json(String path) {
		this.path = path;
		decider = new decide();
	}

	public String getJson() {
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

	public decide() {
		x = 0;
		y = 0;
		id = 0;
		producer = new Producer();
		cell = new StringBuilder();
		array = new ArrayList<Integer>();
	};

	public String getString(String path) {
		// 通过将文件中的xml标签读取进来，并在迭代器中遍历每个元素
		SAXReader reader = new SAXReader();
		try {
			document = reader.read(new File(path));
			// 通过document对象获取根节点bookstore
			Element root = document.getRootElement();
			// 通过element对象的elementIterator方法获取迭代器
			Iterator it = root.elementIterator();
			// 通过对根节点进行访问
			getNodes(root, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 添加 cells 头部和尾部
		String head = "{\"cells\": [";
		String trail = "]}";
		// 调用添加 链接 的方法
		addLink(0);
		cell.deleteCharAt(cell.lastIndexOf(","));
		cell.insert(0, head);
		cell.append(trail);
		
		return cell.toString();
	}

	/*
	 * @function 遍历节点的一个方法
	 * 
	 * @param node 表示需要访问的当前节点
	 *
	 * @param level 表示访问的当前节点的层次 第1层为基本层，2、3...之后的都表示嵌套图符里面的
	 * 
	 * @param 返回值void
	 */
	public void getNodes(Element node, int level) {
		boolean firstNode = true;

		// 当前节点是否是第一次连接，第一次连接需要连接上数据库，这时暂时也没有图符
		if (isFirstConnect && node.getName().toUpperCase() == "CONNECT") {
			isFirstConnect = false;
			Connect = node.getTextTrim();
			return;
		}
		// 清楚环境标签直接返回，没有这个图符，默认没有
		if (node.getName().toUpperCase() == "CLEAR") {
			return;
		}
		// 根据当前节点的标签进行匹配
		SearchNode(node, level);

		List<Element> listElement = node.elements();// 所有当前节点的list
		if (listElement.isEmpty()) {
		} else {
			for (Element e : listElement) {// 遍历该节点的子节点
				// 如果是由SQL_CASE包裹着的节点，层次不升高
				if (e.getParent().getName().toUpperCase().equals("SQL_CASE")) {
					getNodes(e, level);// 递归
				} else {
					getNodes(e, level + 1);// 递归
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
			analyseSQL(sValue, level);
			hasAdd = true;
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
		default:
			// 临时变量空间，考虑添加临时变量的位置
			// System.out.println("i dont konw this label "+sName);
		}
		// 由于有些标签里并没有添加图符，所以用一个boolean属性记录
		// 如果添加了，那么在存在图符id数组里添加这个Id值的记录
		if (hasAdd) {
			array.add((id - 1) + (1000 * level));
		}
	}

	/*
	 * @function 分析sql,根据sql生成对应的sql图符
	 * 
	 * @sValue 传入的是sql语句，需要对它进行拆分，看是什么操作
	 * 
	 * @level 当前图符应该在的层次
	 * 
	 * @return 返回值void
	 */
	public void analyseSQL(String sValue, int level) {
		String[] temp = sValue.trim().split(" ");
		switch (temp[0].toUpperCase()) {
		case "CREATE":
			switch (temp[1].toUpperCase()) {
			case "TABLE":

				break;
			case "USER":
				cell.append(producer.produce("sCreateUser", level).set("owner_id", (id++) + (1000 * level))
						.set("position_x", x += 80).set("position_y", y += 80).set("attrs_NEWUID", temp[2])
						.set("attrs_NEWPWD", temp[5]).set("attrs_type", TYPE).set("attrs_SETCONNECT", Connect)
						.set("parent_id", "parent_id" + (1000 * level + (id - 1))).getString());
				break;
			case "VIEW":

				break;
			default:
				// 留着以后解决
				System.err.println("cant recognize " + sValue);
			}
		case "SELECT":
		case "DROP":
		case "ALTER":
		}
	}

	// 通过这个方法对字符串中的一些值进行修改
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
			// id值为最后一个添加图符的时候
			if (array.size() - 1 == id) {
				changeValue("parent_id" + String.valueOf(array.get(id)), embeds);
				if (array.get(id) / 1000 != 1) {
					sons += String.valueOf(array.get(id)) + " ";
				}
				return;
			}
			// 同层次图符
			if ((array.get(id)) / 1000 == (array.get(id + 1) / 1000)) {
				sons += String.valueOf(array.get(id)) + " ";

				changeValue("parent_id" + String.valueOf(array.get(id)), embeds);

				cell.append(producer.produce("sLink", array.get(id) / 1000)
						.set("owner_id", String.valueOf(array.get(id)) + String.valueOf(array.get(id + 1)))
						.set("target_id", String.valueOf(array.get(id + 1)))
						.set("source_id", String.valueOf(array.get(id))).set("parent_id", String.valueOf(embeds))
						.getString());
			}
			// 进入嵌套
			if ((array.get(id)) / 1000 < (array.get(id + 1) / 1000)) { 

				changeValue("parent_id" + String.valueOf(array.get(id)), embeds);

				ssss.add(embeds);
				embeds = String.valueOf(array.get(id));
				sons += String.valueOf(array.get(id)) + " ";
				sonss.add(sons);
				sons = "";
				embeds = String.valueOf(array.get(id));
			}
			// 退出嵌套
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

	public Producer set(String prev, String rep) {
		int a = stringBuilder.indexOf(prev);
		if (a < 0) {
			return this;
		}
		stringBuilder.replace(a, a + prev.length(), rep);
		return this;
	}

}
