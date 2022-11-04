import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Runner {


    static int threadNum = 10;

    public static void main(String[] args) {
        ExecutorService exe = Executors.newFixedThreadPool(threadNum);

        for(int i = 0; i < threadNum; i++){
            Main runnable = new Main();
            exe.execute(runnable);
        }
    }

}
