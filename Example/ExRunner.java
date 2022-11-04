package Example;

//implements Runnable | extends Threads 可以查一下效能差異
public class ExRunner implements Runnable{

    String runnerName;

    @Override
    public void run() {

        System.out.println(runnerName + " get static i => " + ExampleMain.staticObject.i);
        System.out.println(runnerName + " get static List => " + ExampleMain.staticObject.intList.toString());

    }


    public void setRunnerName(String runnerName) {
        this.runnerName = runnerName;
    }
}
