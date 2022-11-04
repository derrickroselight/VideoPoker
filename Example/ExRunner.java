package Example;

//implements Runnable | extends Threads 可以查一下效能差異
public class ExRunner implements Runnable{

    String runnerName;

    Integer result;

    @Override
    public void run() {

        System.out.println(runnerName + " get static i => " + ExampleMain.staticObject.i);
        System.out.println(runnerName + " get static List => " + ExampleMain.staticObject.intList.toString());

        this.result = doSomeDifficultMath();

        //寫入結果到靜態物件
        ExampleMain.staticResult.addResult(result);
        ExampleMain.staticResult.addWrongResult(result);

    }

    private int doSomeDifficultMath(){
        return 1;
    }


    public void setRunnerName(String runnerName) {
        this.runnerName = runnerName;
    }
}
