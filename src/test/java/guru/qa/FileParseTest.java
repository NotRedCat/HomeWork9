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
import java.util.List;
import java.util.zip.ZipFile;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class FileParseTest {
    ClassLoader classLoader = FileParseTest.class.getClassLoader();
    String zipFileName = "simple_zip.zip";
    String csvFileName = "simple_zip/csv.csv";
    String pdfFileName = "simple_zip/pdf.pdf";
    String xlsxFileName = "simple_zip/excel.xlsx";

    String jsonName = "Car.json";

    private InputStream getZipFile(String zipFileName, String fileName) throws Exception {
        URL zipUrl = classLoader.getResource(zipFileName);
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
            closeStream(xlsFileStream);
        }
    }

    @Test
    void pdfTest() throws Exception {
        try (InputStream pdfFileStream = getZipFile(zipFileName, pdfFileName)) {
            PDF pdf = new PDF(pdfFileStream);
            assertThat(pdf.text).contains("Тестовые данные для занятия по работе с файлами");
            closeStream(pdfFileStream);
        }

    }

    @Test
    void csvTest() throws Exception {
        try (InputStream csvFileStream = getZipFile(zipFileName, csvFileName)) {
            CSVReader csvReader = new CSVReader(new InputStreamReader(csvFileStream, UTF_8));
            List<String[]> csv = csvReader.readAll();
            assertThat(csv).contains(
                    new String[]{"Вагон", " 6543665", " ЭХ646336", " 10.09.2022"},
                    new String[]{"Контейнер", " RZDU6578643", " ОТ909654", " 22.09.2022"},
                    new String[]{"Авто", " АА456А33", " 567", " 13.09.2022"});
            closeStream(csvFileStream);
        }

    }

    @Test
    void jsonTest() throws Exception {
        InputStream is = classLoader.getResourceAsStream(jsonName);
        ObjectMapper objectMapper = new ObjectMapper();
        Car car = objectMapper.readValue(is, Car.class);
        assertThat(car.model).isEqualTo("Toyota");
        assertThat(car.year).isEqualTo(2012);
        assertThat(car.automaticTransmission).isEqualTo(true);
        assertThat(car.engineCapacity).isEqualTo(2.0);
        assertThat(car.number).isEqualTo("AA345A70");

    }

    private void closeStream(InputStream file) throws IOException {
        if (file != null)
            file.close();
    }

}
