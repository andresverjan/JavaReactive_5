import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class prueba {
    public static void main(String[] args){
        Map<String, String> campos = new HashMap<>();
        campos.put("112", "J");
        campos.put("122", "H");
        campos.put("222", "M");


        String column = campos.get("222");
        int porcentaje = 50;

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Hoja 1");

        int columnIndex = column.charAt(0) - 'A';

        Row row = sheet.createRow(0);

        Cell cell = row.createCell(columnIndex);
        cell.setCellValue(porcentaje);


        try {
            FileOutputStream out = new FileOutputStream(
                    new File("excel.xlsx"));
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
