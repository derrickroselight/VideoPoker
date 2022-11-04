package Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StaticResult {

    //this is a staticObj

    int result ;

    int wrongResult;



    //const
    public StaticResult() {
        this.result = 0;
        this.wrongResult = 0;
    }


    public int getResult() {
        return result;
    }

    public int getWrongResult() {
        return wrongResult;
    }


    /**
     * 靜態物件避免資訊出錯, 需要做同步, 避免多續同一時間做寫入
     * @param result
     */
    synchronized public void addResult(int result) {
        this.result += result;
    }

    //同樣做法但沒同步 寫入時資料就不安全
    public void addWrongResult(int result){
        this.wrongResult += result;
    }
}
