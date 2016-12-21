package indi.itsyshao.json;

import org.json.JSONObject;
import org.json.XML;

/**
 * JSON工具类
 * 
 * @author itsyshao
 */
public class JsonUtils {

	/**
	 * 将xml字符串转换为JSON字符串
	 * 
	 * @param xml
	 * @return JSON字符串
	 */
	public static String xml2json(String xml) {
		String rs = xml;
		try {
			rs = XML.toJSONObject(xml).toString();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 将JSON字符串转换为XML字符串
	 * 
	 * @param jsonString
	 * @return XML字符串
	 */
	public static String json2xml(String json) {
		String rs = json;
		try {
			rs = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>".concat(XML.toString(new JSONObject(json)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

}
