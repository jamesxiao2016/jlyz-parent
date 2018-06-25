package cn.dlbdata.dj.common.core.util.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	/**
	 * 替换Excel模板文件内容
	 * 
	 * @param datas
	 *            文档数据
	 * @param sourceFilePath
	 *            Excel模板文件路径
	 * @param targetFilePath
	 *            Excel生成文件路径
	 */
	public static boolean replaceModel(List<ExcelReplaceDataVO> datas,
			String sourceFilePath, String targetFilePath) {
		boolean bool = true;
		if (StringUtils.isEmpty(sourceFilePath)) {
			return false;
		}
		// 判断文件是否存在
		File file = new File(sourceFilePath);
		if (!file.exists()) {
			return false;
		}

		String postfix = sourceFilePath.substring(sourceFilePath.lastIndexOf(".") + 1);
		if ("xls".equalsIgnoreCase(postfix)) {
			try {
				POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(
						sourceFilePath));
				HSSFWorkbook wb = new HSSFWorkbook(fs);
				HSSFSheet sheet = wb.getSheetAt(0);
				for (ExcelReplaceDataVO data : datas) {
					// 获取单元格内容
					HSSFRow row = sheet.getRow(data.getRow());
					HSSFCell cell = row.getCell((short) data.getColumn());
					String str = cell.getStringCellValue();
					// 写入单元格内容
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(str);
				}
				// 输出文件
				FileOutputStream fileOut = new FileOutputStream(targetFilePath);
				wb.write(fileOut);
				fileOut.close();
			} catch (Exception e) {
				bool = false;
				e.printStackTrace();
			}
		} else if ("xlsx".equalsIgnoreCase(postfix)) {
			try {
				XSSFWorkbook wb =  new XSSFWorkbook(file);
				XSSFSheet sheet = wb.getSheetAt(0);
				for (ExcelReplaceDataVO data : datas) {
					// 获取单元格内容
					XSSFRow row = sheet.getRow(data.getRow());
					XSSFCell cell = row.getCell((short) data.getColumn());
					String str = data.getValue();
					// 写入单元格内容
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue(str);
				}
				// 输出文件
				FileOutputStream fileOut = new FileOutputStream(targetFilePath);
				wb.write(fileOut);
				fileOut.close();
			} catch (Exception e) {
				bool = false;
				e.printStackTrace();
			}
		}
		return bool;
	}
}
