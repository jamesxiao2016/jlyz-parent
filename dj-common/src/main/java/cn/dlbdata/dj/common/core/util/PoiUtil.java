package cn.dlbdata.dj.common.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.dlbdata.dj.common.core.web.vo.ExeclVo;

public class PoiUtil {
	/**
	 * 日志对象
	 */
	static Logger logger = LoggerFactory.getLogger(PoiUtil.class);

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public static List<List<Object>> readExecl(String xlsPath) {
		if (StringUtils.isEmpty(xlsPath)) {
			return null;
		}
		// 判断文件是否存在
		File file = new File(xlsPath);
		if (!file.exists()) {
			logger.error("文件不存在" + xlsPath);
			return null;
		}

		String postfix = xlsPath.substring(xlsPath.lastIndexOf(".") + 1);
		if ("xls".equalsIgnoreCase(postfix)) {
			return readExecl2003(xlsPath);
		} else if ("xlsx".equalsIgnoreCase(postfix)) {
			return readExecl2007(xlsPath, 0);
		}
		return null;
	}

	public static List<List<Object>> readExecl2003(String xlsPath) {
		List<List<Object>> result = new ArrayList<>();
		POIFSFileSystem fs = null;
		HSSFWorkbook wb = null;
		try {
			// 判断文件是否存在
			File file = new File(xlsPath);
			if (!file.exists()) {
				logger.error("文件不存在" + xlsPath);
				return result;
			}
			fs = new POIFSFileSystem(file);
			wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row = null;
			// 得到总行数
			int rowNum = sheet.getLastRowNum();
			List<Object> rowData = null;
			for (int i = 0; i <= rowNum; i++) {
				row = sheet.getRow(i);
				int colNum = row.getPhysicalNumberOfCells();
				rowData = new ArrayList<>();
				for (int j = 0; j <= colNum; j++) {
					rowData.add(getStringCellValue(row.getCell((short) j)));
				}

				result.add(rowData);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (wb != null) {
					wb.close();
				}
				if (fs != null) {
					fs.close();
				}
			} catch (IOException e) {
				// 忽略次异常
			}
		}

		return result;
	}

	public static List<List<Object>> readExecl2007(String xlsPath, int maxNum) {
		List<List<Object>> result = new ArrayList<>();
		XSSFWorkbook wb = null;
		try {
			// 判断文件是否存在
			File file = new File(xlsPath);
			if (!file.exists()) {
				logger.error("文件不存在" + xlsPath);
				return result;
			}
			wb = new XSSFWorkbook(file);
			int num = wb.getNumberOfSheets();
			System.out.println("sheetNum:" + num);
			if (num == 0) {
				return result;
			}
			if (maxNum > 0 && num > maxNum) {
				num = maxNum;
			}
			for (int ii = 0; ii < num; ii++) {
				XSSFSheet sheet = wb.getSheetAt(ii);
				XSSFRow row = null;
				// 得到总行数
				int rowNum = sheet.getLastRowNum();
				List<Object> rowData = null;
				for (int i = 0; i <= rowNum; i++) {
					row = sheet.getRow(i);
					int colNum = row.getPhysicalNumberOfCells();
					rowData = new ArrayList<>();
					for (int j = 0; j <= colNum; j++) {
						rowData.add(getStringCellValue(row.getCell((short) j)));
					}

					result.add(rowData);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (wb != null) {
					wb.close();
				}
			} catch (IOException e) {
				// 忽略次异常
			}
		}

		return result;
	}

	public static List<List<Object>> readToExecl2007(String xlsPath, int startSheet, int startRow) {
		List<List<Object>> result = new ArrayList<>();
		XSSFWorkbook wb = null;
		try {
			// 判断文件是否存在
			File file = new File(xlsPath);
			if (!file.exists()) {
				logger.error("文件不存在" + xlsPath);
				return result;
			}
			wb = new XSSFWorkbook(file);
			int num = wb.getNumberOfSheets();
			System.out.println("sheetNum:" + num);
			if (num == 0) {
				return result;
			}
			for (int ii = startSheet; ii < num; ii++) {
				XSSFSheet sheet = wb.getSheetAt(ii);
				XSSFRow row = null;
				// 得到总行数
				int rowNum = sheet.getLastRowNum();
				List<Object> rowData = null;
				for (int i = startRow; i <= rowNum; i++) {
					row = sheet.getRow(i);
					int start = row.getFirstCellNum();
					int colNum = row.getLastCellNum();
					rowData = new ArrayList<>();
					if (start != 0) {
						for (int m = 0; m < start; m++) {
							rowData.add("");
						}
					}
					for (int j = start; j < colNum; j++) {
						rowData.add(getStringCellValue(row.getCell((short) j)));
					}

					result.add(rowData);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (wb != null) {
					wb.close();
				}
			} catch (IOException e) {
				// 忽略次异常
			}
		}

		return result;
	}

	@SuppressWarnings("deprecation")
	private static String getStringCellValue(HSSFCell cell) {
		String strCell = "";
		if (cell == null) {
			return strCell;
		}
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			strCell = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = cell.getStringCellValue();
			break;
		}
		if (strCell != null) {
			strCell = strCell.trim();
		}
		return strCell;
	}

	@SuppressWarnings("deprecation")
	private static String getStringCellValue(XSSFCell cell) {
		String strCell = "";
		if (cell == null) {
			return strCell;
		}
		switch (cell.getCellType()) {
		case XSSFCell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case XSSFCell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
			}
			strCell = String.valueOf(cell.getNumericCellValue());
			break;
		case XSSFCell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case XSSFCell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = cell.getRawValue();
			break;
		}
		if (strCell != null) {
			strCell = strCell.trim().replace("#N/A", "");
		}
		return strCell;
	}

	@SuppressWarnings("resource")
	public static void writeToExecl(List<List<String>> dataList, String filePath) throws IOException {
		if (dataList == null || dataList.size() == 0) {
			return;
		}
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = (Sheet) wb.createSheet();

		// 每行的样式
		CellStyle cellstyle = wb.createCellStyle();
		cellstyle.setWrapText(true);// 指定当单元格内容显示不下时自动换行

		Row row = null;
		Cell cell = null;
		// 循环写入行数据
		for (int i = 0, rowCount = dataList.size(); i < rowCount; i++) {
			row = (Row) sheet.createRow(i);
			// row.setHeight((short) 440);
			List<String> rowData = dataList.get(i);
			for (int j = 0, count = rowData.size(); j < count; j++) {
				cell = row.createCell(j);
				cell.setCellValue(StringUtils.isEmpty(rowData.get(j)) ? "" : rowData.get(j));
				// cell.setCellStyle(cellstyle); // 样式，居中
				// sheet.autoSizeColumn(j, true);
			}
		}

		// 创建文件流
		OutputStream stream = new FileOutputStream(filePath);
		// 写入数据
		wb.write(stream);
		stream.flush();
		// 关闭文件流
		stream.close();
	}

	@SuppressWarnings("deprecation")
	public static void writeDataToExecl(String path, String fileName, String fileType, List<ExeclVo> dataList)
			throws IOException {
		Workbook wb = null;
		String excelPath = path + File.separator + fileName + "." + fileType;
		File file = new File(excelPath);
		Sheet sheet = null;
		// 创建工作文档对象
		if (!file.exists()) {
			if (fileType.equals("xls")) {
				wb = new HSSFWorkbook();

			} else if (fileType.equals("xlsx")) {
				wb = new XSSFWorkbook();
			} else {
				// throw new Exception("文件格式不正确");
			}
			// // 创建sheet对象
			// sheet = (Sheet) wb.createSheet("sheet1");
			// OutputStream outputStream = new FileOutputStream(excelPath);
			// wb.write(outputStream);
			// outputStream.flush();
			// outputStream.close();
		} else {
			if (fileType.equals("xls")) {
				wb = new HSSFWorkbook(new FileInputStream(file));
			} else if (fileType.equals("xlsx")) {
				wb = new XSSFWorkbook(excelPath);
			} else {
				// throw new Exception("文件格式不正确");
			}
		}
		// // 创建sheet对象
		// if (sheet == null) {
		// sheet = (Sheet) wb.createSheet("sheet1");
		// }

		// // 添加表头
		// Row row = (sheet).createRow(0);
		// Cell cell = row.createCell(0);
		// row.setHeight((short) 540);
		//
		// CellStyle style = wb.createCellStyle(); // 样式对象
		// // 设置单元格的背景颜色为淡蓝色
		// style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		//
		// style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
		// style.setAlignment(CellStyle.ALIGN_CENTER);// 水平
		// style.setWrapText(true);// 指定当单元格内容显示不下时自动换行
		//
		// cell.setCellStyle(style); // 样式，居中
		//
		// Font font = wb.createFont();
		// font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		// font.setFontName("宋体");
		// font.setFontHeight((short) 280);
		// style.setFont(font);
		// // 单元格合并
		// // 四个参数分别是：起始行，起始列，结束行，结束列
		// sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
		// sheet.autoSizeColumn(5200);

		// 表头样式
		CellStyle titleStyle = wb.createCellStyle();
		// 设置单元格的背景颜色为淡蓝色
		titleStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
		titleStyle.setAlignment(CellStyle.ALIGN_CENTER);// 水平
		titleStyle.setWrapText(true);// 指定当单元格内容显示不下时自动换行
		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		titleStyle.setFont(font);

		// 每行的样式
		CellStyle cellstyle = wb.createCellStyle();
		// 设置单元格的背景颜色为淡蓝色
		cellstyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
		cellstyle.setAlignment(CellStyle.ALIGN_CENTER);// 水平
		cellstyle.setWrapText(true);// 指定当单元格内容显示不下时自动换行

		int sheetIndex = 0;
		for (ExeclVo vo : dataList) {
			sheet = (Sheet) wb.createSheet(vo.getSheetName() == null ? "sheet" + sheetIndex++ : vo.getSheetName());

			Row row = null;
			Cell cell = null;
			row = sheet.createRow(0); // 创建第二行
			row.setHeight((short) 440);
			String[] titleRow = vo.getTitle();
			int[] titleWidth = vo.getTitleWidth();
			for (int i = 0, count = titleRow.length; i < count; i++) {
				cell = row.createCell(i);
				cell.setCellValue(titleRow[i]);
				cell.setCellStyle(titleStyle); // 样式，居中
				if (titleWidth != null && titleWidth.length > 0) {
					sheet.setColumnWidth(i, titleWidth[i] * 1000);
				}
			}

			List<List<String>> data = vo.getData();
			if (data == null) {
				continue;
			}
			// 循环写入行数据
			for (int i = 0; i < data.size(); i++) {
				row = (Row) sheet.createRow(i + 1);
				row.setHeight((short) 440);
				List<String> rowData = data.get(i);
				for (int j = 0, count = rowData.size(); j < count; j++) {
					cell = row.createCell(j);
					cell.setCellValue(StringUtils.isEmpty(rowData.get(j)) ? "-" : rowData.get(j));
					cell.setCellStyle(cellstyle); // 样式，居中
				}
			}
		}

		// 创建文件流
		OutputStream stream = new FileOutputStream(excelPath);
		// 写入数据
		wb.write(stream);
		stream.flush();
		// 关闭文件流
		stream.close();
	}

	//
	// /**
	// * 写入数据
	// *
	// * @param filePath
	// * @param data
	// */
	// public static void writeDataToExecl(String xlsPath, int sheetIdx,
	// List<List<String>> data) {
	// try {
	//
	// // 判断文件是否存在
	// File file = new File(xlsPath);
	// if (!file.exists()) {
	// log.error("文件不存在" + xlsPath);
	// return;
	// }
	// XSSFWorkbook wb = new XSSFWorkbook(file);
	// // XSSFSheet sheet = wb.createSheet("sheet1");
	// XSSFSheet sheet = wb.createSheet("sheet1");
	// // XSSFSheet sheet = wb.getSheetAt(sheetIdx);// 修改第一个sheet中的值
	//
	// // 如果每次重写，那么则从开始读取的位置写，否则果获取源文件最新的行。
	// // int lastRowNum = 0;
	// int count = 0;// 记录最新添加的行数
	// log.info("要添加的数据总条数为：" + data.size());
	// for (List<String> rowData : data) {
	// if (rowData == null)
	// continue;
	//
	// Row row = null;// 如果数据行已经存在，则获取后重写，否则自动创建新行。
	//
	// row = sheet.createRow(count);
	// // r2 = sheet.createRow(lastRowNum + t + 1);
	//
	// // 用于设定单元格样式
	// CellStyle cellStyle = wb.createCellStyle();
	// int i = 0;
	// for (String cellData : rowData) {
	// Cell cell = row.createCell(i);// 获取数据类型
	// cell.setCellType(CellType.STRING);
	// // cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	// cell.setCellValue(cellData);// 复制单元格的值到新的单元格
	// cell.setCellStyle(cellStyle);
	//
	// // cell = r2.createCell(i);// 获取数据类型
	// // cell.setCellType(CellType.STRING);
	// // // cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	// // cell.setCellValue(cellData);// 复制单元格的值到新的单元格
	// // cell.setCellStyle(cellStyle);
	// i++;
	// }
	//
	// count += 1;
	// }
	// // log.info("其中检测到重复条数为:" + (data.size() - t) + " ，追加条数为：" + t);
	//
	// try {
	// // 重新将数据写入Excel中
	// FileOutputStream outputStream = new FileOutputStream(xlsPath);
	// wb.write(outputStream);
	// outputStream.flush();
	// outputStream.close();
	// wb.close();
	// } catch (Exception e) {
	// log.error("写入Excel时发生错误！ ");
	// e.printStackTrace();
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	//
	// }
	// }
	//
	// /**
	// * 写入数据
	// *
	// * @param filePath
	// * @param data
	// */
	// public static void writeExeclDemo(String xlsPath, List<Map<String,
	// Object>> data) {
	// try {
	//
	// // 判断文件是否存在
	// File file = new File(xlsPath);
	// if (file.exists()) {
	// // file.delete();
	// } else {
	// // file.createNewFile();
	// }
	// HSSFWorkbook wb = new HSSFWorkbook();
	// HSSFSheet sheet = wb.createSheet("sheet1");
	// // Sheet sheet = wb.getSheetAt(0);// 修改第一个sheet中的值
	//
	// // 如果每次重写，那么则从开始读取的位置写，否则果获取源文件最新的行。
	// int lastRowNum = 0;
	// int t = 0;// 记录最新添加的行数
	// log.info("要添加的数据总条数为：" + data.size());
	// for (Map<String, Object> row : data) {
	// if (row == null)
	// continue;
	//
	// Row r, r2 = null;// 如果数据行已经存在，则获取后重写，否则自动创建新行。
	//
	// r = sheet.createRow(lastRowNum + t);
	// r2 = sheet.createRow(lastRowNum + t + 1);
	//
	// // 用于设定单元格样式
	// CellStyle cellStyle = wb.createCellStyle();
	// int i = 0;
	// for (Map.Entry<String, Object> cellData : row.entrySet()) {
	// Cell cell = r.createCell(i);// 获取数据类型
	// cell.setCellType(CellType.STRING);
	// // cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	// cell.setCellValue(cellData.getKey());// 复制单元格的值到新的单元格
	// cell.setCellStyle(cellStyle);
	//
	// cell = r2.createCell(i);// 获取数据类型
	// cell.setCellType(CellType.STRING);
	// // cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	// cell.setCellValue(cellData.getValue() == null ? "" :
	// cellData.getValue().toString());// 复制单元格的值到新的单元格
	// cell.setCellStyle(cellStyle);
	// i++;
	// }
	//
	// t += 2;
	// }
	// log.info("其中检测到重复条数为:" + (data.size() - t) + " ，追加条数为：" + t);
	//
	// try {
	// // 重新将数据写入Excel中
	// FileOutputStream outputStream = new FileOutputStream(xlsPath);
	// wb.write(outputStream);
	// outputStream.flush();
	// outputStream.close();
	// wb.close();
	// } catch (Exception e) {
	// log.error("写入Excel时发生错误！ ");
	// e.printStackTrace();
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	//
	// }
	// }

	public static void main(String[] args) {
		long begin = System.currentTimeMillis();
		List<List<Object>> data = readToExecl2007("d:\\DB\\test_data.xlsx", 1, 0);
		long end = System.currentTimeMillis();
		System.out.println(data.size() + "耗时:" + (end - begin));
		// for (int i = 0; i < data.size(); i++) {
		// List<Object> o = data.get(i);
		// {
		// for (Object oo : o)
		// System.out.print(oo + "--");
		// }
		// System.out.println(o.size());
		// }
		// String path = "d:\\";
		// List<List<String>> data = new ArrayList<>();
		// for (int i = 0; i < 10; i++) {
		// List<String> rowData = new ArrayList<>();
		// for (int j = 0; j < 5; j++) {
		// rowData.add(i + "cell" + j);
		// }
		// data.add(rowData);
		// }
		// String[] titleRow = new String[] { "专项资金名称", "单位名称", "机构代码", " 法人类型",
		// "企业规模", "平台查询结果", "负面记录数量", "评估结果" };
		// try {
		// writeDataToExecl(path, "template2", "xlsx", data, titleRow);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// writeDataToExecl(path, 0, data);
	}

	/**
	 * 复制文件
	 * 
	 * @param s
	 *            源文件
	 * @param t
	 *            目标文件
	 */
	public static void fileChannelCopy(File s, File t) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(s), 1024);
				out = new BufferedOutputStream(new FileOutputStream(t), 1024);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = in.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建文件
	 * 
	 * @param path
	 * @return
	 */
	public static File createNewFile(final String path) {
		File file = new File(path);

		String newPath = path.substring(0, path.indexOf(".xlsx")) + System.currentTimeMillis() + ".xlsx";
		File newFile = new File(newPath);
		try {
			newFile.createNewFile();

			fileChannelCopy(file, newFile);
		} catch (Exception e) {
			logger.error("createNewFile", e);
		}
		return newFile;
	}

	/**
	 * 删除文件
	 * 
	 * @param files
	 */
	public static void deleteFile(File... files) {
		for (File file : files) {
			if (file.exists()) {
				file.delete();
			}
		}
	}

	/**
	 * 导出文件
	 * 
	 * @param path
	 * @param data
	 * @return
	 */
//	public static File exportExcel(String path, List<SheetBean> data, String sheetName) {
//		if (null == path || null == data) {
//			return null;
//		}
//
//		File newFile = createNewFile(path);
//
//		InputStream is = null;
//		XSSFWorkbook workbook = null;
//		XSSFSheet sheet = null;
//		FileOutputStream fos = null;
//		Row row = null;
//		Cell cell = null;
//
//		try {
//			is = new FileInputStream(newFile);
//
//			workbook = new XSSFWorkbook(is);
//			workbook.setSheetName(0, sheetName);
//			// sheet页
//			for (int i = 0; i < data.size(); i++) {
//				SheetBean bean = data.get(i);
//				sheet = workbook.getSheetAt(0);
//
//				for (int j = 0; j < bean.getRowCount(); j++) {
//					List<String> rowData = bean.getRowData(j);
//					row = sheet.getRow(bean.getStartRow() + j);
//
//					if (null == row) {
//						row = sheet.createRow(bean.getStartRow() + j);
//					}
//
//					for (int k = 0; k < rowData.size(); k++) {
//						cell = row.getCell(bean.getStartCol() + k);
//
//						if (null == cell) {
//							cell = row.createCell(bean.getStartCol() + k);
//						}
//
//						cell.setCellValue(rowData.get(k));
//					}
//				}
//			}
//
//			fos = new FileOutputStream(newFile);
//			if (null != fos) {
//				workbook.write(fos);
//				fos.flush();
//				fos.close();
//			}
//		} catch (Exception e) {
//			log.error("exportExcel", e);
//		} finally {
//			try {
//				if (null != is) {
//					is.close();
//				}
//			} catch (Exception e) {
//				log.error("exportExcel", e);
//			}
//		}
//
//		return newFile;
//	}

	/**
	 * 项目考核Excel格式化
	 * 
	 * @param file
	 * @return
	 */
	public static File projectAnalyseFormat(File file) {
		if (null == file) {
			return null;
		}

		InputStream is = null;
		FileOutputStream fos = null;
		Workbook book = null;
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;

		try {
			is = new FileInputStream(file);
			book = new XSSFWorkbook(is);
			sheet = book.getSheetAt(0);

			String projName = null;
			int start = 0;
			for (int i = 1; i < sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i);

				if (null == row) {
					break;
				}

				cell = row.getCell(1);

				if (null == cell) {
					break;
				}

				if (null == projName) {
					if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
						projName = cell.getStringCellValue().trim();
						start = i;
					}
				} else {
					if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
						if (!projName.equals(cell.getStringCellValue().trim())) {
							// 只有一个的情况
							if (start != (i - 1)) {
								CellRangeAddress cra = new CellRangeAddress(start, i - 1, 1, 1);
								sheet.addMergedRegion(cra);
								sheet.getRow(start).createCell(1).setCellValue(projName);
							}

							projName = cell.getStringCellValue().trim();
							start = i;
						}
					}
				}
			}

			// 隐藏第一列
			sheet.setColumnWidth(0, 0);

			fos = new FileOutputStream(file);
			if (null != fos) {
				book.write(fos);
				fos.flush();
				fos.close();
			}
		} catch (IOException e) {
			logger.error("projectAnalyseFormat", e);
		} finally {
			try {
				if (null != is) {
					is.close();
				}
			} catch (Exception e) {
				logger.error("projectAnalyseFormat", e);
			}
		}

		return file;
	}
}
