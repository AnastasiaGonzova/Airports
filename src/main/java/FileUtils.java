import com.opencsv.CSVReader;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FileUtils {

    public static List<Map.Entry<Integer, String>> Reading(final String fileName, final int needColumnNumber) throws IOException {
        try {
            FileReader filereader = new FileReader(fileName);
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord = csvReader.readNext();
            if(nextRecord.length < needColumnNumber){
                throw new IndexOutOfBoundsException("There is no column with number " + needColumnNumber);
            }
            Map<Integer, String> result = new HashMap<>();
            do {
                if(nextRecord[0] == null || nextRecord[0] == "" || !StringUtils.isNumeric(nextRecord[0])){
                    throw new IllegalArgumentException("Value does not exist or is not a number");
                }
                if(nextRecord[needColumnNumber - 1] == null || nextRecord[needColumnNumber - 1] == ""){
                    throw new IllegalArgumentException("Value does not exist");
                }
                result.put(Integer.parseInt(nextRecord[0]), nextRecord[needColumnNumber - 1]);
            } while ((nextRecord = csvReader.readNext()) != null);
                csvReader.close();

            List<Map.Entry<Integer, String>> values = new ArrayList<>(result.entrySet());
            return values;
        } catch (IOException e) {
            throw new FileNotFoundException("File with name \"" +fileName+ "\" does not exist");
        }
    }

    public static Map<Integer, String[]> ReadingByKeys(List<Integer> keys, final String fileName) throws IOException{
        Map<Integer, String[]> allInform = new LinkedHashMap<>();
        try {
            FileReader fileReader = new FileReader("airports.csv");
            CSVReader csvReader = new CSVReader(fileReader);
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                int index = Integer.parseInt(nextRecord[0]);
                if(keys.contains(index)){
                    int position = keys.indexOf(index);
                    allInform.put(position, nextRecord);
                }
            }
            return allInform;
        } catch(IOException e) {
            throw new FileNotFoundException("File with name \"" + fileName + "\" does not exist");
        }
    }
}
