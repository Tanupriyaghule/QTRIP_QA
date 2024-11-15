package qtriptest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class DP {
    public static Object[][] dpMethod(String sheet) throws IOException {
        int rowIndex = 0;
        int cellIndex = 0;
        List<List<String>> outputList = new ArrayList<>();
        FileInputStream excelFile = new FileInputStream(new File(
                "/home/crio-user/workspace/ghuletanupriya-ME_QTRIP_QA_V2/app/src/test/resources/DatasetsforQTrip.xlsx"));
        try (Workbook workbook = new XSSFWorkbook(excelFile)) {
            Sheet selectedSheet = workbook.getSheet(sheet);
            Iterator<Row> iterator = selectedSheet.iterator();
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                List<String> innerList = new ArrayList<>();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (rowIndex > 0 && cellIndex > 0) {
                        if (cell.getCellType() == CellType.STRING) {
                            innerList.add(cell.getStringCellValue());
                        } else if (cell.getCellType() == CellType.NUMERIC) {
                            innerList.add(String.valueOf(cell.getNumericCellValue()));
                        }
                    }
                    cellIndex = cellIndex + 1;
                }
                rowIndex = rowIndex + 1;
                cellIndex = 0;
                if (!innerList.isEmpty())
                    outputList.add(innerList);

            }
        }
        excelFile.close();
        // Convert List<List<String>> to String[][]
        String[][] stringArray = new String[outputList.size()][];
        for (int i = 0; i < outputList.size(); i++) {
            List<String> row = outputList.get(i);
            stringArray[i] = row.toArray(new String[0]);
        }
        return stringArray;
    }

    @DataProvider(name = "qtripData")
    public Object[][] TestCase01() throws IOException {
        return dpMethod("TestCase01");
    }
     
    @DataProvider(name="qtripUIData")
    public Object[][] TestCase02() throws IOException{
             return dpMethod("TestCase02");
        }
        @DataProvider(name="qtripBookData")
       public Object[][] TestCase03() throws IOException{
         return dpMethod("TestCase03");
        }
       @DataProvider(name="qtripData1")
       public Object[][] TestCase04() throws IOException{
            return dpMethod("TestCase04");   
}
}
