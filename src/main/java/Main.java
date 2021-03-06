import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

public class Main {

    private static final String COLUMN_NUMBER_KEY = "column-number";
    private static final String FILE_NAME_KEY = "file-name";
    private static final String YAML_FILE_PATH = "src/main/resources/data.yml";

    public static void main(String[] args) throws IOException{

        Map<String, Object> dataFromYaml;

        try{
            InputStream ymlStream = new FileInputStream(new File(YAML_FILE_PATH));

            Yaml yaml = new Yaml();
            dataFromYaml = yaml.load(ymlStream);
        } catch (IOException e) {
            throw new FileNotFoundException("File on the path \"" + YAML_FILE_PATH + "\" does not exist");
        }

        int needColumnNumber;
        if(args.length == 0 || args[0] == null){
            needColumnNumber = (Integer) dataFromYaml.get(COLUMN_NUMBER_KEY);
        } else {
            needColumnNumber = Integer.parseInt(args[0]);
        }

        List<Map.Entry<Integer, String>> values = FileUtils.Reading((String) dataFromYaml.get(FILE_NAME_KEY), needColumnNumber);

        System.out.print("Enter the key word: ");
        Scanner in = new Scanner(System.in);
        String keyWord = in.nextLine();

        if(values.size() == 0){
            long startTime = System.currentTimeMillis();
            long endTime = System.currentTimeMillis();
            System.out.println("Result: No result\n");
            System.out.println("Lines: 0, Time: " + (endTime-startTime) + " ms");
            return;
        }

        Collections.sort(values, new Comparator<Map.Entry<Integer, String>>() {
            @Override
            public int compare(Map.Entry<Integer, String> a, Map.Entry<Integer, String> b) {
                return (a.getValue()).compareTo( b.getValue() );
            }
        });

        long startTime = System.currentTimeMillis();

        int startIndex = SearchUtils.BinarySearch(values, keyWord);

        if(startIndex > values.size()) {
            long endTime = System.currentTimeMillis();
            System.out.println("Result: No result\n");
            System.out.println("Lines: 0, Time: " + (endTime-startTime) + " ms");
            return;
        }

        List<Map.Entry<Integer, String>> filteredValues = SearchUtils.SearchAroundIndex(values, keyWord, startIndex);


        List<Integer> keysOfFilteredValues = new ArrayList<>();
        for(Map.Entry<Integer, String> value : filteredValues){
            keysOfFilteredValues.add(value.getKey());
        }

        Map<Integer, String[]> allInformOfFilteredValues = FileUtils.ReadingByKeys(keysOfFilteredValues, (String) dataFromYaml.get(FILE_NAME_KEY));
        long endTime = System.currentTimeMillis();


        System.out.println("\nResult: \n");

        for(int i = 0; i < allInformOfFilteredValues.size(); i++){
            for (String cell : allInformOfFilteredValues.get(i)) {
                System.out.print(cell + " " );
            }
            System.out.println();
        }

        System.out.println("\nLines: " + allInformOfFilteredValues.size() +", Time: " + (endTime-startTime) + " ms");

    }
}
