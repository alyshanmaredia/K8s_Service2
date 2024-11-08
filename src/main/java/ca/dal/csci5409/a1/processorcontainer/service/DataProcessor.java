package ca.dal.csci5409.a1.processorcontainer.service;

import ca.dal.csci5409.a1.processorcontainer.constant.Constants;
import ca.dal.csci5409.a1.processorcontainer.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

@Slf4j
public class DataProcessor {
    public static int ParseDataSourceFile(String fileName, String product) throws Exception {
        Properties properties = new Properties();

        try (InputStream in = new FileInputStream(Constants.DATAPROCESSORFILEPATH + fileName)) {
            log.info("Parsing file data : {}", fileName);

            properties.load(in);

            return CalculateProductSum(properties, product, fileName);

        } catch (FileNotFoundException e) {
            log.error("File not found: {}", e.getMessage());
            throw new CustomException(HttpStatus.NOT_FOUND, "File not found.", fileName);
        } catch (IOException e) {
            log.error("File content error: {}", e.getMessage());
            throw new CustomException(HttpStatus.BAD_REQUEST, Constants.FILENOTINCSVERRORMSG , fileName);
        } catch (ArrayIndexOutOfBoundsException e){
            log.error("File content error: {}", e.getMessage());
            throw new CustomException(HttpStatus.BAD_REQUEST, Constants.FILENOTINCSVERRORMSG, fileName);
        }
    }

    private static int CalculateProductSum(Properties properties, String product, String fileName) throws CustomException {
        int total = 0;
        Set<String> propertyNames = properties.stringPropertyNames();

        for (String key : propertyNames) {
            String[] array = ParseKey(key, fileName);

            if (array[0].equals(product)) {
                total += Integer.parseInt(array[1]);
            }
        }
        return total;
    }

    private static String[] ParseKey(Object key, String fileName) throws CustomException {
        String[] array = key.toString().split(",");

        if (array.length < 2 || array[1] == null) {
            log.error("File content is not in the expected CSV format.");
            throw new CustomException(HttpStatus.BAD_REQUEST,Constants.FILENOTINCSVERRORMSG, fileName);
        }
        return array;
    }
}
