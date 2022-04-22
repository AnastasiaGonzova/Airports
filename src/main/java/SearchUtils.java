import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchUtils {

    public static int BinarySearch(List<Map.Entry<Integer, String>> values, String keyWord){
        int firstIndex = 0;
        int lastIndex = values.size() - 1;

        if(firstIndex == lastIndex){
            return firstIndex;
        }

        if(
                (values.get(firstIndex).getValue().contains(keyWord) &&  values.get(firstIndex).getValue().indexOf(keyWord) == 0)
                &&  (values.get(lastIndex).getValue().contains(keyWord) &&  values.get(lastIndex).getValue().indexOf(keyWord) == 0)
        ) {
            return firstIndex;
        }

        int resultIndex = values.size()+1;
        while(firstIndex <= lastIndex) {
            int middleIndex = (firstIndex + lastIndex) / 2;
            String checkWord = values.get(middleIndex).getValue();
            if (checkWord.contains(keyWord) && checkWord.indexOf(keyWord) == 0) {
                resultIndex = middleIndex;
                break;
            }
            else if (checkWord.compareTo(keyWord) < 0){
                firstIndex = middleIndex + 1;
            }
            else if (checkWord.compareTo(keyWord) > 0){
                lastIndex = middleIndex - 1;
            }
        }

        return resultIndex;
    }

    public static List<Map.Entry<Integer, String>> SearchAroundIndex(List<Map.Entry<Integer, String>> values, String keyWord, int startIndex){

        List<Map.Entry<Integer, String>> leftPart = new ArrayList<>();
        List<Map.Entry<Integer, String>> rightPart = new ArrayList<>();

        int index = startIndex;
        while(
                index >= 0 && index < values.size()
                        && values.get(index).getValue().contains(keyWord) && values.get(index).getValue().indexOf(keyWord) == 0
        ){
            leftPart.add(0, values.get(index));
            index--;
        }

        index = startIndex + 1;
        while(
                index >= 0 && index < values.size()
                        && values.get(index).getValue().contains(keyWord) && values.get(index).getValue().indexOf(keyWord) == 0
        ) {
            rightPart.add(values.get(index));
            index++;
        }

        leftPart.addAll(rightPart);

        return leftPart;
    }

}
