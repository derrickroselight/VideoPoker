package Example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExampleMain {

    //主緒當中是靜態物件 誰都可以拿 且直接先做初始化
    static StaticObject staticObject = new StaticObject();

    static int threadNum = 20;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService exe = Executors.newFixedThreadPool(threadNum);

        for(int i = 0; i < threadNum; i++){

            ExRunner exRunner = new ExRunner();
            exRunner.setRunnerName("No." + i);
            exe.execute(exRunner);
        }
        //緒池關閉, 但尚未完成的緒會繼續執行直到死亡, 所以緒池會等待池內的緒全部結束後才會真正shutdown
        //這個做法就是讓我能知道甚麼時候緒才會全部做完, 用途很大
        exe.shutdown();

        while(true){
            //等緒池真正shutdown後才會跳出這個迴圈, 每個緒做出來的答案,我要等到所有緒做完然後再做合計之類的
            if(exe.isTerminated()){
                System.out.println("All done");
                break;
            }
            Thread.sleep(1000);
        }

        //do some math


    }
}
