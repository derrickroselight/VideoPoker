import java.util.ArrayList;
import java.util.Arrays;

public class HandHandler {
    public int[] suit;
    public int[] hand;
    public int[] point;
    public int[] sortedPoint;
    public int[] pointFreq;
    public ArrayList<Integer> pointArrayList;

    public HandHandler(int[] hand) {
        this.hand = hand;
        this.suit = checkSuit(this.hand);
        this.point = checkPoint(this.hand);
        this.sortedPoint = sortPoint();
        this.pointArrayList = generatePointArrayList();
        this.pointFreq = generatePointFreq();
    }

    private int[] checkSuit(int[] hand) {
        int[] result = new int[5];
        result[0] = (int) Math.floor(hand[0] / 13);
        result[1] = (int) Math.floor(hand[1] / 13);
        result[2] = (int) Math.floor(hand[2] / 13);
        result[3] = (int) Math.floor(hand[3] / 13);
        result[4] = (int) Math.floor(hand[4] / 13);
        return result;
    }

    private int[] checkPoint(int[] hand) {
        int[] result = new int[5];
        result[0] = hand[0] % 13 + 1;
        result[1] = hand[1] % 13 + 1;
        result[2] = hand[2] % 13 + 1;
        result[3] = hand[3] % 13 + 1;
        result[4] = hand[4] % 13 + 1;
        return result;
    }

    private int[] sortPoint() {
        int[] result = new int[5];
        System.arraycopy(this.point, 0, result, 0, 5);
        Arrays.sort(result);
        return result;
    }

    private ArrayList<Integer> generatePointArrayList() {
        ArrayList<Integer> result = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            result.add(this.point[i]);
        }
        return result;
    }

    private int[] generatePointFreq() {
        int[] result = new int[13];
        for (int i = 0; i < 5; i++) {
            result[this.point[i] - 1]++;
        }
        return result;
    }
}
