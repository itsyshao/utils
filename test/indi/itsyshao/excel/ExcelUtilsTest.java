package indi.itsyshao.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import junit.framework.TestCase;

public class ExcelUtilsTest extends TestCase {

	public void testListToExcel() throws Exception {
		OutputStream out = new FileOutputStream(new File("D:/test.xls"));
		List<Person> list = new ArrayList<Person>();

		Person p1 = new Person();
		p1.setName("张三");
		p1.setSex("男");
		p1.setAge(20);
		p1.setJob("程序猿");
		list.add(p1);

		Person p2 = new Person();
		p2.setName("李四");
		p2.setSex("女");
		p2.setAge(18);
		p2.setJob("设计师");
		list.add(p2);

		LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
		fieldMap.put("name", "姓名");
		fieldMap.put("sex", "性别");
		fieldMap.put("age", "年龄");
		fieldMap.put("job", "工作");

		String sheetName = "人员清单";
		ExcelUtils.listToExcel(list, fieldMap, sheetName, out);
	}

	public void testExcelToList() throws Exception {
		InputStream in = new FileInputStream(new File("D:/test.xls"));
		List<Person> list = new ArrayList<Person>();

		LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
		fieldMap.put("姓名", "name");
		fieldMap.put("性别", "sex");
		fieldMap.put("年龄", "age");
		fieldMap.put("工作", "job");

		String sheetName = "人员清单";
		String[] uniqueFields = new String[] { "姓名", "年龄" };
		list = ExcelUtils.excelToList(in, sheetName, Person.class, fieldMap, uniqueFields);
		System.out.println(list.size());
	}

}
