package xyz.util;


import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import xyz.exception.MyExceptionForXyz;
import xyz.filter.JSON;


public class ExcelTool {
    public static boolean createExcelForList(List<List<Object>> dataList , Object[][] titleList ,
                                             String fileName) {
        HSSFWorkbook wb = new HSSFWorkbook();
        // 设置字体
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short)20); // 字体高度
        font.setColor(HSSFFont.COLOR_RED); // 字体颜色
        font.setFontName("黑体"); // 字体
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
        font.setItalic(true); // 是否使用斜体
        HSSFSheet sheet = wb.createSheet();
        HSSFRow row = sheet.createRow((int)0);
        // 用于表头样式
        HSSFCellStyle style = wb.createCellStyle();
        // 左右居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 上下对齐
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 自动换行
        style.setWrapText(true);
        // 用于数据样式
        HSSFCellStyle style1 = wb.createCellStyle();
        // 左右居中
        style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        // 上下对齐
        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 自动换行
        style1.setWrapText(true);
        // 设置Excel行标
        HSSFCell cell = null;
        for (int i = 0; i < titleList.length; i++ ) {
            cell = row.createCell(i);
            cell.setCellValue(new HSSFRichTextString(JSON.toJson(titleList[i][0])));
            // 设置列宽
            int linWidth = 10;
            if (titleList[i].length > 1) {
                linWidth = Integer.parseInt((JSON.toJson(titleList[i][1])));
            }
            sheet.setColumnWidth(i, (short)(linWidth * 1.3 * 256));
            cell.setCellStyle(style);
        }
        HSSFCell cell1 = null;
        for (int i = 0; i < dataList.size(); i++ ) {
            // 动态创建行
            row = sheet.createRow((int)i + 1);
            List<Object> tt = dataList.get(i);
            // 用于比较列高大小
            int rowCount = 1;
            for (int z = 0; z < tt.size(); z++ ) {
                // 这里来创建每个单元格
                cell1 = row.createCell(z);
                String out = JSON.toJson(tt.get(z));
                if (out != null) {
                    int countT = out.split("\r\n").length;
                    if (countT > rowCount) {
                        rowCount = countT;
                    }
                }
                cell1.setCellValue(new HSSFRichTextString(out));
                cell1.setCellStyle(style1);
            }
            // 设置每个行的高
            row.setHeight((short)(rowCount * 300));
        }
        try {
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            // 获取项目绝对路径
            String file_real_path = request.getSession().getServletContext().getRealPath(
                "tempFile");
            // File file = new File(file_real_path);
            // FileTool.deleteChildFolder(file);
            String path = file_real_path + "\\" + fileName;
            System.out.println(file_real_path);
            FileOutputStream fout = new FileOutputStream(path);// "D:\\test.xls"
            wb.write(fout);
            fout.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 调用此方法将产生合并单元格的现象，要求第一个单元格必须是合并关键字段
     */
    public static boolean createExcelForJoinForList(List<List<Object>> dataList ,
                                                    Object[][] titleList , short[] fieldCounts ,
                                                    String fileName) {
        HSSFWorkbook wb = new HSSFWorkbook();
        // 设置字体
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short)20); // 字体高度
        font.setColor(HSSFFont.COLOR_RED); // 字体颜色
        font.setFontName("黑体"); // 字体
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
        font.setItalic(true); // 是否使用斜体
        HSSFSheet sheet = wb.createSheet();
        HSSFRow row = sheet.createRow((int)0);
        // 用于表头样式
        HSSFCellStyle style = wb.createCellStyle();
        // 左右居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 上下对齐
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 自动换行
        style.setWrapText(true);
        // 用于数据样式
        HSSFCellStyle style1 = wb.createCellStyle();
        // 左右居中
        style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        // 上下对齐
        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 自动换行
        style1.setWrapText(true);
        // 设置Excel行标
        HSSFCell cell = null;
        for (int i = 0; i < titleList.length; i++ ) {
            cell = row.createCell(i);
            cell.setCellValue(new HSSFRichTextString(JSON.toJson(titleList[i][0])));
            // 设置列宽
            int linWidth = Integer.parseInt((JSON.toJson(titleList[i][1])));
            sheet.setColumnWidth(i, (short)(linWidth * 1.3 * 256));
            cell.setCellStyle(style);
        }
        Object groupStr = dataList.get(0).get(0);
        int count = 0;
        HSSFCell cell1 = null;
        for (int i = 0; i < dataList.size(); i++ ) {
            // 动态创建行
            row = sheet.createRow((int)i + 1);
            List<Object> tt = dataList.get(i);
            {
                if (!tt.get(0).equals(groupStr)) {
                    if (count > 1) {
                        for (short t = 0; t < fieldCounts.length; t++ ) {
                            short j = fieldCounts[t];
                            sheet.addMergedRegion(new CellRangeAddress(i + 1 - count, i, j, j));
                        }
                    }
                    count = 1;
                }
                else {
                    count++ ;
                }
                groupStr = tt.get(0);
            }
            // 用于比较列高大小
            int rowCount = 1;
            for (int z = 0; z < tt.size(); z++ ) {
                // 这里来创建每个单元格
                cell1 = row.createCell(z);
                String out = JSON.toJson(tt.get(z));
                if (out != null) {
                    boolean flag2 = false;
                    for (short ppp : fieldCounts) {
                        if (ppp == z) {
                            flag2 = true;
                        }
                    }
                    if (!(count > 1 && flag2)) {
                        int countT = out.split("\r\n").length;
                        if (countT > rowCount) {
                            rowCount = countT;
                        }
                    }
                }
                cell1.setCellValue(new HSSFRichTextString(out));
                cell1.setCellStyle(style1);
            }
            // 设置每个行的高
            row.setHeight((short)(rowCount * 300));
        }
        if (count > 1) {
            for (short t = 0; t < fieldCounts.length; t++ ) {
                short j = fieldCounts[t];
                sheet.addMergedRegion(new CellRangeAddress(dataList.size() + 1 - count,
                    dataList.size(), j, j));
            }
        }
        try {
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            // 获取项目绝对路径
            String file_real_path = request.getSession().getServletContext().getRealPath(
                "tempFile");
            // File file = new File(file_real_path);
            // FileTool.deleteChildFolder(file);
            String path = file_real_path + "\\" + fileName;
            System.out.println(file_real_path);
            FileOutputStream fout = new FileOutputStream(path);// "D:\\test.xls"
            wb.write(fout);
            fout.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 下载excel
     * 
     * @param fileName
     * @param dataList
     * @param titleList
     * @param fieldCounts
     * @return boolean
     * @author :huying
     * @date : 2016-6-27下午3:10:47
     */
    public static boolean getExcel(String fileName , List<List<Object>> dataList ,
                                   Object[][] titleList , short[] fieldCounts) {
        System.out.println("进入。。。");
        HSSFWorkbook wb = new HSSFWorkbook();
        // 设置字体
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short)20); // 字体高度
        font.setColor(HSSFFont.COLOR_RED); // 字体颜色
        font.setFontName("黑体"); // 字体
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
        font.setItalic(true); // 是否使用斜体
        HSSFSheet sheet = wb.createSheet();
        HSSFRow row = sheet.createRow((int)0);
        // 用于表头样式
        HSSFCellStyle style = wb.createCellStyle();
        // 左右居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 上下对齐
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 自动换行
        style.setWrapText(true);
        // 用于数据样式
        HSSFCellStyle style1 = wb.createCellStyle();
        // 左右居中
        style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        // 上下对齐
        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 自动换行
        style1.setWrapText(true);
        // 设置Excel行标
        HSSFCell cell = null;
        for (int i = 0; i < titleList.length; i++ ) {
            cell = row.createCell(i);
            cell.setCellValue(new HSSFRichTextString(JSON.toJson(titleList[i][0])));
            // 设置列宽
            int linWidth = Integer.parseInt((JSON.toJson(titleList[i][1])));
            sheet.setColumnWidth(i, (short)(linWidth * 1.3 * 256));
            cell.setCellStyle(style);
        }
        Object groupStr = dataList.get(0).get(0);
        int count = 0;
        HSSFCell cell1 = null;

        int size = dataList.size();
        if (size >= 65536) {
            throw new MyExceptionForXyz("数据量巨大，请缩减查询范围！");
        }
        else if (size > 1000) {
            try {
                Thread.sleep(1000);
                System.out.println("下载excel中途等待中......");
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < dataList.size(); i++ ) {
            if (i % 2000 == 0) {
                try {
                    Thread.sleep(1000);
                    System.out.println("==========================");
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 动态创建行
            row = sheet.createRow((int)i + 1);
            List<Object> tt = dataList.get(i);
            {
                if (!tt.get(0).equals(groupStr)) {

                    if (count > 1) {

                        for (short t = 0; t < fieldCounts.length; t++ ) {
                            short j = fieldCounts[t];
                            sheet.addMergedRegion(new CellRangeAddress(i + 1 - count, i, j, j));
                        }
                    }
                    count = 1;
                }
                else {
                    count++ ;
                }
                groupStr = tt.get(0);
            }
            // 用于比较列高大小
            int rowCount = 1;
            for (int z = 0; z < tt.size(); z++ ) {
                // 这里来创建每个单元格
                cell1 = row.createCell(z);
                String out = tt.get(z) == null ? "" : JSON.toJson(tt.get(z));
                if (out != null) {
                    boolean flag2 = false;
                    for (short ppp : fieldCounts) {
                        if (ppp == z) {
                            flag2 = true;
                        }
                    }
                    if (!(count > 1 && flag2)) {
                        int countT = out.split("\r\n").length;
                        if (countT > rowCount) {
                            rowCount = countT;
                        }
                    }
                }
                cell1.setCellValue(new HSSFRichTextString(out));
                cell1.setCellStyle(style1);
            }
            // 设置每个行的高
            row.setHeight((short)(rowCount * 300));
        }
        if (count > 1) {
            for (short t = 0; t < fieldCounts.length; t++ ) {
                short j = fieldCounts[t];
                sheet.addMergedRegion(new CellRangeAddress(dataList.size() + 1 - count,
                    dataList.size(), j, j));
            }
        }
        try {
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            // 获取项目绝对路径
            String file_real_path = request.getSession().getServletContext().getRealPath(
                "tempFile");
            // File file = new File(file_real_path);
            // FileTool.deleteChildFolder(file);
            String path = file_real_path + "\\" + fileName;
            System.out.println(file_real_path);
            FileOutputStream fout = new FileOutputStream(path);// "D:\\test.xls"
            wb.write(fout);
            fout.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
        return true;
    }
}