package jxl;

import java.io.File;
import java.io.IOException;

import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExeclUtil {
	private Sheet sheet = null;
	private Workbook book = null;
	private WritableWorkbook writeBook = null;
	private WritableSheet writeSheet = null;

	public boolean load(String path) {
		try {
			book = Workbook.getWorkbook(new File(path));
			sheet = book.getSheet(0);
		} catch (BiffException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean create(String path, String sheetName) {
		try {
			writeBook = Workbook.createWorkbook(new File(path));
			writeSheet = writeBook.createSheet(sheetName, 0);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean setTableTitle(String titles[]) {
		if (null == writeSheet)
			return false;
		for (int j = 0; j < titles.length; j++) {
			Label label = new Label(j, 0, titles[j]);
			try {
				writeSheet.addCell(label);
			} catch (RowsExceededException e) {
				e.printStackTrace();
				return false;
			} catch (WriteException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public boolean writeData(String data[][]) {
		try {
			for (int row = 0; row < data.length; row++) {
				for (int col = 0; col < data[row].length; col++) {
					Label label = new Label(col, row + 1, data[row][col]);
					writeSheet.addCell(label);
				}

			}
			writeBook.write();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void close() {
		if (null != book) {
			book.close();
		}
		if (null != writeBook) {
			try {
				writeBook.close();
			} catch (WriteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getCellData(int row, int col) {
		if (null == sheet) {
			return "";
		}
		Cell cell = sheet.getCell(col, row);
		return cell.getContents();
	}

	public int getRows() {
		if (null == sheet) {
			return -1;
		}
		return sheet.getRows();
	}

	public int getColumns() {
		if (null == sheet) {
			return -1;
		}
		return sheet.getColumns();
	}
}
