package MultiThreads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExampleMain {

    /**售票網站總共有10萬張票能賣
     * 現在有1萬個緒,每個緒每次買1張 買10次
     * 理論上最後售票網站要剛好被搶光光
     */
    static int totalTickets = 100000;
    static int threadNum = 10000;

    static TrainTickets trainTickets = new TrainTickets(totalTickets);



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
        printResult();


    }



    static private void printResult(){
        System.out.println("正確答案應該是0, 但會出現剩餘的票,而且每次跑完的結果都不一樣 -> 這就是緒使用的參數不同步");
        System.out.println("leftTickets: " + trainTickets.tickets);

    }
}
