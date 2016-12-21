package indi.itsyshao.json;
import junit.framework.TestCase;

public class JsonUtilsTest extends TestCase{

	public void testJson(){
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
		System.out.println(JsonUtils.xml2json(xml));
		System.out.println(JsonUtils.json2xml(JsonUtils.xml2json(xml)));
	}
}
