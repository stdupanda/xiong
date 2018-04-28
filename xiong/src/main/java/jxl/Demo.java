package jxl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import jxl.read.biff.BiffException;

public class Demo {

	/**
	 * 生成 excel 文件
	 * @param users
	 * @return 生成的excel文件路径
	 */
	@Test
	public String genExcel(List<Object> users) {
		ExeclUtil execl = new ExeclUtil();
		String filename = "data/1.xls";

		String name = System.getProperty("hcecloud.root") + filename;
		execl.create(name, "sheet页名");
		String[] titles = new String[70];
		titles[0] = "用户ID";
		titles[1] = "imei号";
		titles[2] = "订单号";
		execl.setTableTitle(titles);

		String[][] totalStr = new String[users.size() + 1][8];
		int index = 0;
		for (Object v : users) {
			totalStr[index][0] = "1" + v.hashCode();
			totalStr[index][1] = "2";
			totalStr[index][2] = "3";

			index++;
		}
		execl.writeData(totalStr);
		execl.close();
		return filename;
	}

	@Test
	public void test() throws BiffException, IOException {
		File file = new File("g:/1.xls");
		Workbook workbook = Workbook.getWorkbook(file);
		System.out.println(workbook.hashCode());
	}
}
