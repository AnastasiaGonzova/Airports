import com.opencsv.CSVReader;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

public class Main {

    private static final String COLUMN_NUMBER_KEY = "column-number";
    private static final String FILE_NAME_KEY = "file-name";
    private static final String YAML_FILE_PATH = "src/main/resources/data.yml";

    public static void main(String[] args) throws IOException{

        Map<String, Object> data;

        try{
            InputStream ymlStream = new FileInputStream(new File(YAML_FILE_PATH));

            Yaml yaml = new Yaml();
            data = yaml.load(ymlStream);
        } catch (IOException e) {
            throw new FileNotFoundException("File on the path \"" + YAML_FILE_PATH + "\" does not exist");
        }

        int needColumn;
        if(args.length == 0 || args[0] == null){
            needColumn = (Integer) data.get(COLUMN_NUMBER_KEY);
        } else {
            needColumn = Integer.parseInt(args[0]);
        }

        List<Map.Entry<Integer, String>> values = FileUtils.Reading((String) data.get(FILE_NAME_KEY), needColumn);

        System.out.print("Enter the key word: ");
        Scanner in = new Scanner(System.in);
        String keyWord = in.nextLine();

        if(values.size() == 0){
            long start = System.currentTimeMillis();
            long end = System.currentTimeMillis();
            System.out.println("Result: No result\n");
            System.out.println("Lines: 0, Time: " + (end-start) + " ms");
            return;
        }

        Collections.sort(values, new Comparator<Map.Entry<Integer, String>>() {
            @Override
            public int compare(Map.Entry<Integer, String> a, Map.Entry<Integer, String> b) {
                return (a.getValue()).compareTo( b.getValue() );
            }
        });

        long start = System.currentTimeMillis();

        int resultIndex = SearchUtils.BinarySearch(values, keyWord);

        if(resultIndex > values.size()) {
            long end = System.currentTimeMillis();
            System.out.println("Result: No result\n");
            System.out.println("Lines: 0, Time: " + (end-start) + " ms");
            return;
        }

        List<Map.Entry<Integer, String>> finalResult = SearchUtils.AroundSearch(values, keyWord, resultIndex);


        List<Integer> keys = new ArrayList<>();
        for(Map.Entry<Integer, String> value : finalResult){
            keys.add(value.getKey());
        }

        Map<Integer, String[]> allInform = FileUtils.SearchInFile(keys, (String) data.get(FILE_NAME_KEY));
        long end = System.currentTimeMillis();


        System.out.println("\nResult: \n");

        for(int i = 0; i < allInform.size(); i++){
            for (String cell : allInform.get(i)) {
                System.out.print(cell + " " );
            }
            System.out.println();
        }

        System.out.println("\nLines: " + allInform.size() +", Time: " + (end-start) + " ms");

    }
}
