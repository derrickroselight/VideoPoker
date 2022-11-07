package MultiThreads;

//implements Runnable | extends Threads 可以查一下效能差異
public class ExRunner implements Runnable{

    String runnerName;

    Integer result;

    @Override
    public void run() {


        for(int i = 0; i < 10; i++){
            //每搶一張票,要扣掉一次TrainTickets
            ExampleMain.trainTickets.setTickets(ExampleMain.trainTickets.getTickets() - 1);
        }




    }



    public void setRunnerName(String runnerName) {
        this.runnerName = runnerName;
    }
}
