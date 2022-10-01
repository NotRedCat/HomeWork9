package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class FileParseTest {
    ClassLoader classLoader = FileParseTest.class.getClassLoader();
    String zipFileName = "simple_zip.zip";
    String csvFileName = "csv.csv";
    String pdfFileName = "pdf.pdf";
    String xlsxFileName = "excel.xlsx";

    String jsonName = "Car.json";

    private InputStream getZipFile(String zipFileName, String fileName) throws Exception {
        URL zipUrl = classLoader.getResource("simple_zip.zip");
        File zipFile = new File(zipUrl.toURI());
        ZipFile zip = new ZipFile(zipFile);
        return zip.getInputStream(zip.getEntry(fileName));

    }

    @Test
     void xlsTest() throws Exception {
         try (InputStream xlsFileStream = getZipFile(zipFileName, xlsxFileName)) {
             XLS xls = new XLS(xlsFileStream);
             assertThat(xls.excel
                     .getSheetAt(0).getRow(1).getCell(0).getStringCellValue())
                     .isEqualTo("Вагон");
         }
     }

     @Test
     void pdfTest() throws Exception {
         try (InputStream pdfFileStream = getZipFile(zipFileName,pdfFileName)) {
             PDF pdf = new PDF(pdfFileStream);
             assertThat(pdf.text).contains("Тестовые данные для занятия по работе с файлами");
         }
     }

     @Test
     void csvTest() throws Exception {
         try (InputStream csvFileStream = getZipFile(zipFileName, csvFileName)) {
             CSVReader csvReader = new CSVReader(new InputStreamReader(csvFileStream));
             List<String[]> csv = csvReader.readAll();
             assertThat(csv).contains(
                     new String[]{"Вагон;6543665;ЭХ646336;10.09.2022"},
                     new String[]{"Контейнер;RZDU6578643;ОТ909654;22.09.2022"},
                     new String[]{"Авто;АА456А33;567;13.09.2022"});
         }
     }
/*
   @Test
   void csvTest() throws Exception {
       InputStream is = classLoader.getResourceAsStream(csvFileName);
       CSVReader csvReader = new CSVReader(new InputStreamReader(is, UTF_8));
       List<String[]> csv = csvReader.readAll();
       assertThat(csv).contains(
               new String[]{"Вагон", " 6543665", " ЭХ646336", " 10.09.2022"},
               new String[]{"Контейнер", " RZDU6578643", " ОТ909654", " 22.09.2022"},
               new String[]{"Авто", " АА456А33", " 567", " 13.09.2022"});}
   @Test
   void pdfTest() throws Exception {
       try (InputStream pdfFileStream = classLoader.getResourceAsStream(pdfFileName)) {
           PDF pdf = new PDF(pdfFileStream);
           assertThat(pdf.text).contains("Тестовые данные для занятия по работе с файлами");
       }}

   @Test
   void xlsTest() throws Exception {
       try (InputStream xlsFileStream = classLoader.getResourceAsStream(xlsxFileName)) {
           XLS xls = new XLS(xlsFileStream);
           assertThat(xls.excel
                   .getSheetAt(0).getRow(1).getCell(0).getStringCellValue())
                   .isEqualTo("Вагон");
       }
   }
*/
    @Test
    void jsonTest() throws Exception {
        InputStream is = classLoader.getResourceAsStream(jsonName);
        ObjectMapper objectMapper = new ObjectMapper();
        Car car = objectMapper.readValue(is, Car.class);
        assertThat(car.model).isEqualTo("Toyota");
        assertThat(car.year).isEqualTo(2012);
        assertThat(car.automatic_transmission).isEqualTo(true);
        assertThat(car.engine_capacity).isEqualTo(2.0);
        assertThat(car.number).isEqualTo("AA345A70");

    }
    private void closeInputStream (InputStream file) throws IOException {
        if(file !=null)
            file.close();
    }

}
