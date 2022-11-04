package Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StaticObject {

    //this is a staticObj

    int i;

    int[] intArray;

    HashMap<Integer, Integer> intMap;

    List<Integer> intList;

    //const
    public StaticObject() {
        this.i = 0;
        init();
    }

    private void init(){
        intArray = new int[10];
        intMap = new HashMap<>();
        intList = new ArrayList<>();
        for(int i = 0; i < intArray.length; i++){
            intArray[i] = i;

            intMap.put(i, i * 100);

            intList.add(i);
        }



    }

    public int getI() {
        return i;
    }

    public int[] getIntArray() {
        return intArray;
    }

    public HashMap<Integer, Integer> getIntMap() {
        return intMap;
    }

    public List<Integer> getIntList() {
        return intList;
    }
}
