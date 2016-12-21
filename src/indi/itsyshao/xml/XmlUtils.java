package indi.itsyshao.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Branch;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * XML工具类
 * 
 * @author itsyshao
 */
public class XmlUtils {
	/**
	 * <p>
	 * 将任意的xml串转换成java.util.Map<String,Object>，以元素名作key，元素值作value 
	 * 注意：可以配置xml属性 type = list。将特定xml节点转为List
	 * </p>
	 * @param xml xml字符串
	 * @return Map<String, Object>
	 */
	public static HashMap<String, Object> xml2Map(String xml) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xml);
		} catch (DocumentException e1) {

		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> rootMap = new HashMap<String, Object>();
		if (doc == null)
			return map;
		Element root = doc.getRootElement();
		map.put(root.getName(), rootMap);
		for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
			Element e = (Element) iterator.next();
			List list = e.elements();
			if (list.size() > 0) {
				rootMap.put(e.getName(), dom2Map(e));
			} else
				rootMap.put(e.getName(), e.getText());
		}
		return map;
	}
	
	/**
	 * <p>
	 * 将java.util.Map<String,Object>转换成xml。map对象只支持string 和 map<string,object>对象
	 * </p>
	 * @param map map对象
	 * @return xml xml字符串
	 */
	public static String map2Xml(Map<String, Object> map, String rootName) {
		if (map == null) {
			return null;
		} else if (map.keySet().size() > 1 && StringUtils.isBlank(rootName)) {
			return null;
		}
		Document doc = DocumentHelper.createDocument();
		Branch branch = null;
		if (map.keySet().size() > 1) {
			branch = doc.addElement(rootName);
		} else {
			branch = doc;
		}
		for (String key : map.keySet()) {
			Object obj = map.get(key);
			if (obj == null)
				obj = "";
			if (obj instanceof String) {
				branch.addElement(key).setText(obj.toString());
			} else {
				Element el = branch.addElement(key);
				Map<String, Object> _map = (Map<String, Object>) obj;
				createChild(el, _map);
			}
		}
		return doc.asXML();
	}

	private static Object dom2Map(Element e) {
		Map map = new HashMap();
		List mapList = null;
		String type = e.attributeValue("type");
		if (StringUtils.isNotBlank(type) && "list".equals(type)) {
			mapList = new ArrayList();
		}
		List list = e.elements();
		if (list.size() >= 2) {
			Element element = (Element) list.get(0);
			for (int i = 1; i < list.size(); i++) {
				Element ele = (Element) list.get(i);
				if (ele.getName().equals(element.getName())) {
					mapList = new ArrayList();
					break;
				}
			}
		}

		if (mapList != null) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				if (iter.elements().size() > 0) {
					Object o = dom2Map(iter);
					mapList.add(o);
				} else {
					Map _map = new HashMap();
					_map.put(iter.getName(), iter.getText());
					mapList.add(_map);
				}
			}
			return mapList;
		} else {
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Element iter = (Element) list.get(i);
					if (iter.elements().size() > 0) {
						Object o = dom2Map(iter);
						map.put(iter.getName(), o);
					} else {
						map.put(iter.getName(), iter.getText());
					}
				}
			} else
				map.put(e.getName(), e.getText());
			return map;
		}
	}
	
	private static void createChild(Element pEl,Map<String,Object> map) {
		for(String key:map.keySet()) {
			Object obj = map.get(key);
			if (obj==null)obj="";
			if (obj instanceof String) {
				pEl.addElement(key).setText(obj.toString());
			}else {
				Element el = pEl.addElement(key);
				Map<String,Object> map2 = (Map<String,Object>)obj;
				createChild(el,map2);
			}
		}
	}
}
