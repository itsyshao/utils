package indi.itsyshao.xml;

import java.util.Map;

import junit.framework.TestCase;

public class XmlUtilsTest extends TestCase {

	public void testXml(){
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
						"<service>\n" + 
						"  <head>\n" + 
						"    <sender>test</sender>\n" + 
						"    <time>2016-11-07 11:41:54</time>\n" + 
						"  </head>\n" + 
						"  <data_info>\n" + 
						"    <nodes>\n" + 
						"      <node>\n" + 
						"        <objectid>UUID:d89test</objectid>\n" + 
						"        <routeseq>routeSeqName</routeseq>\n" + 
						"        <wayname>testName</wayname>\n" + 
						"      </node>\n" + 
						"    </nodes>\n" + 
						"  </data_info>\n" + 
						"</service>";
		Map<String,Object> map = XmlUtils.xml2Map(xml);
		System.out.println(map.toString());
		String _xml = XmlUtils.map2Xml(map, null);
		System.out.println(_xml);
	}
}
