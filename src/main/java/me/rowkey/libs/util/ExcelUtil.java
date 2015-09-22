package me.rowkey.libs.util;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.*;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Author: Bryant Hang
 * Date: 15/1/21
 * Time: 下午2:39
 */
public class ExcelUtil {
    /**
     * 读取Excel文件
     *
     * @param file
     * @param keys
     * @param haveHead 是否有表头
     * @param reverse  是否从表尾开始读取
     * @return
     */
    public static List<Map<String, String>> readExcel(File file, String[] keys, boolean haveHead, boolean reverse) {
        assert keys != null;
        Workbook workbook = null;
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        try {
            workbook = Workbook.getWorkbook(file);
            Sheet rs = workbook.getSheet(0);

            int rowNum = rs.getRows();
            int toReadColumnNum = keys.length < rs.getColumns() ? keys.length : rs.getColumns();

            for (int row = haveHead ? 1 : 0; row < rowNum; row++) {
                //如果第一列为空，则此行记录无效
                String firstValue = rs.getCell(0, row).getContents();
                if (StringUtils.isBlank(firstValue)) {
                    continue;
                }
                Map<String, String> lineResult = new HashMap<String, String>();
                lineResult.put(keys[0], firstValue);

                for (int col = 1; col < toReadColumnNum; col++) {
                    lineResult.put(keys[col], rs.getCell(col, row).getContents());
                }

                resultList.add(lineResult);
            }
            if (reverse) {
                Collections.reverse(resultList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }

        return resultList;
    }

    /**
     * 创建Excel文件
     *
     * @param path
     * @param sheetName  工作表名称
     * @param dataTitles 表头标题
     * @param dataList   数据
     * @param keys       数据中的key
     */
    public static void createExcel(String path, String sheetName, String[] dataTitles, List<Map<String, String>> dataList, String keys[]) {
        WritableWorkbook workbook = null;
        try {
            File excelFile = new File(path);
            if (!excelFile.getParentFile().exists()) {
                excelFile.getParentFile().mkdirs();
            }
            workbook = Workbook.createWorkbook(excelFile);

            WritableSheet sheet = workbook.createSheet(sheetName, 0); // 添加第一个工作表
            sheet.getSettings().setDefaultColumnWidth(25); // 设置列的默认宽度
            //sheet.setRowView(2, false);

            // 表头样式
            WritableFont wf = new WritableFont(WritableFont.TIMES, 12,
                    WritableFont.BOLD, false);
            WritableCellFormat wcfTitle = new WritableCellFormat(wf);
            wcfTitle.setAlignment(Alignment.CENTRE);

            int curRow = 0;

            if (dataTitles != null && dataTitles.length > 0) {
                // 表头
                for (int i = 0; i < dataTitles.length; i++) {
                    // Label(列号,行号,内容,风格)
                    sheet.addCell(new Label(i, curRow, dataTitles[i], wcfTitle));
                }
                curRow++;
            }

            // 单元格样式
            wf = new WritableFont(WritableFont.createFont("宋体"), 11);
            WritableCellFormat wcf = new WritableCellFormat(wf); // 单元格定义
            wcf.setAlignment(Alignment.CENTRE); // 设置水平对齐方式
            wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 设置竖直对齐方式

            for (Map<String, String> data : dataList) {
                for (int i = 0; i < keys.length; i++) {
                    sheet.addCell(new Label(i, curRow, data.get(keys[i]), wcf));
                }

                curRow++;
            }

            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
