import java.util.*;

public class Main {
    public static final int royalFlushPrize = 800;
    public static final int straightFlushPrize = 50;
    public static final int fourKindPrize = 25;
    public static final int fullHousePrize = 9;
    public static final int flushPrize = 6;
    public static final int straightPrize = 4;
    public static final int threeKindPrize = 3;
    public static final int twoPairPrize = 2;
    public static final int jacksBetterPrize = 1;

    public static void main(String[] args) {
        int[] deck = new int[52];
        for (int i = 0; i < 52; i++) {
            deck[i] = i;
        }

        /* 產生52取5所有組合的分數  */
        ArrayList[] allCombinations = generateAllCombinations(deck);

        /* 算出所有組合的分數 */
        int[] allCombinationsScore = calcAllCombinationsScore(allCombinations);

        double start, finish;
        start = System.currentTimeMillis();
        /* 指定一組手牌 */
        int[] hand = new int[]{5, 18, 24, 25, 50};

        /* 算出最佳解及RTP */
        double[] bestStrategy = calcScoreAfterDraw(hand, allCombinations, allCombinationsScore);
        finish = System.currentTimeMillis();
        printResult(bestStrategy);
        System.out.print("Finish! " + (finish - start) / 1000);
    }

    private static int calcScore(int[] hand) {
        HandHandler handHandler = new HandHandler(hand);
        PatternHandler patternHandler = new PatternHandler(handHandler);
        if (patternHandler.royalFlush) {
            return royalFlushPrize;
        } else if (patternHandler.straightFlush) {
            return straightFlushPrize;
        } else if (patternHandler.fourKind) {
            return fourKindPrize;
        } else if (patternHandler.fullHouse) {
            return fullHousePrize;
        } else if (patternHandler.flush) {
            return flushPrize;
        } else if (patternHandler.straight) {
            return straightPrize;
        } else if (patternHandler.threeKind) {
            return threeKindPrize;
        } else if (patternHandler.twoPairs) {
            return twoPairPrize;
        } else if (patternHandler.jackBetter) {
            return jacksBetterPrize;
        }
        return 0;
    }

    private static ArrayList[] generateSubSet(int[] hand) {
        ArrayList[] result = new ArrayList[32];

        // C(5,5) = 1
        int i = 0;
        result[i] = new ArrayList<>();
        for (int j = 0; j < 5; j++) {
            result[i].add(hand[j]);
        }

        // C(5,4) = 5
        i = 1;
        for (int c1 = 0; c1 < 2; c1++) {
            for (int c2 = c1 + 1; c2 < 3; c2++) {
                for (int c3 = c2 + 1; c3 < 4; c3++) {
                    for (int c4 = c3 + 1; c4 < 5; c4++) {
                        result[i] = new ArrayList<>();
                        result[i].add(hand[c1]);
                        result[i].add(hand[c2]);
                        result[i].add(hand[c3]);
                        result[i].add(hand[c4]);
                        i++;
                    }
                }
            }
        }

        // C(5,3) = 10
        i = 6;
        for (int c1 = 0; c1 < 3; c1++) {
            for (int c2 = c1 + 1; c2 < 4; c2++) {
                for (int c3 = c2 + 1; c3 < 5; c3++) {
                    result[i] = new ArrayList<>();
                    result[i].add(hand[c1]);
                    result[i].add(hand[c2]);
                    result[i].add(hand[c3]);
                    i++;
                }
            }
        }

        // C(5,2) = 10
        i = 16;
        for (int c1 = 0; c1 < 4; c1++) {
            for (int c2 = c1 + 1; c2 < 5; c2++) {
                result[i] = new ArrayList<>();
                result[i].add(hand[c1]);
                result[i].add(hand[c2]);
                i++;
            }
        }

        // C(5,1) = 5
        i = 26;
        for (int c1 = 0; c1 < 5; c1++) {
            result[i] = new ArrayList<>();
            result[i].add(hand[c1]);
            i++;
        }

        // C(5,0) = 1
        i = 31;
        result[i] = new ArrayList<>();

        return result;
    }

    private static int[] convertList(ArrayList arrayList) {
        int[] result = new int[arrayList.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = (int) arrayList.get(i);
        }
        return result;
    }

    private static ArrayList[] generateAllCombinations(int[] deck) {
        ArrayList[] allCombinations = new ArrayList[2_598_960];
        int index = 0;
        for (int c1 = 0; c1 < 48; c1++) {
            for (int c2 = c1 + 1; c2 < 49; c2++) {
                for (int c3 = c2 + 1; c3 < 50; c3++) {
                    for (int c4 = c3 + 1; c4 < 51; c4++) {
                        for (int c5 = c4 + 1; c5 < 52; c5++) {
                            allCombinations[index] = new ArrayList<>();
                            allCombinations[index].add(deck[c1]);
                            allCombinations[index].add(deck[c2]);
                            allCombinations[index].add(deck[c3]);
                            allCombinations[index].add(deck[c4]);
                            allCombinations[index].add(deck[c5]);
                            index++;
                        }
                    }
                }
            }
        }
        return allCombinations;
    }

    private static int[] calcAllCombinationsScore(ArrayList[] allCombinations) {
        int[] result = new int[2_598_960];
        for (int i = 0; i < result.length; i++) {
            result[i] = calcScore(convertList(allCombinations[i]));
        }
        return result;
    }

    private static double[] calcScoreAfterDraw(int[] hand, ArrayList[] allCombinations, int[] allCombinationsScore) {
        /* 產生所有可能的打法(32種) */
        ArrayList[] subHand = generateSubSet(hand);
        int[] subHandScore = new int[32];
        int[] subHandFreq = new int[32];

        /* 0.3秒 */
        /* 把有5張手牌沾到的組合挑出來 把全域從250萬降到106萬*/
        double start3 = System.currentTimeMillis();
        ArrayList[] subCombinations = new ArrayList[1065021];
        int[] subCombinationsScore = new int[1065021];
        int index = 0;
        for (int j = 0; j < allCombinationsScore.length; j++) {
            ArrayList intersection = (ArrayList) allCombinations[j].clone();
            intersection.retainAll(subHand[0]);
            if (intersection.size() > 0) {
                subCombinations[index] = (ArrayList) allCombinations[j].clone();
                subCombinationsScore[index] = allCombinationsScore[j];
                index++;
            }
        }
        double finish3 = System.currentTimeMillis();
        System.out.print("Spot3: " + (finish3 - start3));
        System.out.println();

        /* 3秒 */
        /* 計算32種組合 */
        double start = System.currentTimeMillis();
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < subCombinationsScore.length; j++) {
                Set<Integer> set = new HashSet<>(subCombinations[j]);
                if (set.containsAll(subHand[i])) {
                    subHandScore[i] += subCombinationsScore[j];
                    subHandFreq[i]++;
                }
            }
        }
        double finish = System.currentTimeMillis();
        System.out.print("Spot1: " + (finish - start));
        System.out.println();

        /* Principle of Inclusion and Exclusion */
        /* Discard 0 card */
        int[] newSubHandScore = new int[32];
        int[] newSubHandFreq = new int[32];

        newSubHandScore[0] = subHandScore[0];
        newSubHandFreq[0] = subHandFreq[0];

        /* Discard 1 card */
        for (int i = 1; i < 6; i++) {
            newSubHandScore[i] = subHandScore[i] - subHandScore[0];
            newSubHandFreq[i] = subHandFreq[i] - subHandFreq[0];
        }

        /* Discard 2 cards */
        for (int i = 6; i < 16; i++) {
            newSubHandScore[i] = subHandScore[i];
            newSubHandFreq[i] = subHandFreq[i];
            for (int j = 1; j < 6; j++) {
                Set<Integer> set = new HashSet<>(subHand[j]);
                if (set.containsAll(subHand[i])) {
                    newSubHandScore[i] -= subHandScore[j];
                    newSubHandFreq[i] -= subHandFreq[j];
                }
            }
            newSubHandScore[i] += subHandScore[0];
            newSubHandFreq[i] += subHandFreq[0];
        }

        /* Discard 3 cards */
        for (int i = 16; i < 26; i++) {
            newSubHandScore[i] = subHandScore[i];
            newSubHandFreq[i] = subHandFreq[i];
            for (int j = 6; j < 16; j++) {
                Set<Integer> set = new HashSet<>(subHand[j]);
                if (set.containsAll(subHand[i])) {
                    newSubHandScore[i] -= subHandScore[j];
                    newSubHandFreq[i] -= subHandFreq[j];
                }
            }
            for (int j = 1; j < 6; j++) {
                Set<Integer> set = new HashSet<>(subHand[j]);
                if (set.containsAll(subHand[i])) {
                    newSubHandScore[i] += subHandScore[j];
                    newSubHandFreq[i] += subHandFreq[j];
                }
            }
            newSubHandScore[i] -= subHandScore[0];
            newSubHandFreq[i] -= subHandFreq[0];
        }

        /* Discard 4 cards */
        for (int i = 26; i < 31; i++) {
            newSubHandScore[i] = subHandScore[i];
            newSubHandFreq[i] = subHandFreq[i];
            for (int j = 16; j < 26; j++) {
                Set<Integer> set = new HashSet<>(subHand[j]);
                if (set.containsAll(subHand[i])) {
                    newSubHandScore[i] -= subHandScore[j];
                    newSubHandFreq[i] -= subHandFreq[j];
                }
            }
            for (int j = 6; j < 16; j++) {
                Set<Integer> set = new HashSet<>(subHand[j]);
                if (set.containsAll(subHand[i])) {
                    newSubHandScore[i] += subHandScore[j];
                    newSubHandFreq[i] += subHandFreq[j];
                }
            }
            for (int j = 1; j < 6; j++) {
                Set<Integer> set = new HashSet<>(subHand[j]);
                if (set.containsAll(subHand[i])) {
                    newSubHandScore[i] -= subHandScore[j];
                    newSubHandFreq[i] -= subHandFreq[j];
                }
            }
            newSubHandScore[i] += subHandScore[0];
            newSubHandFreq[i] += subHandFreq[0];
        }

        /* Discard 5 cards */
        for (int i = 31; i < 32; i++) {
            newSubHandScore[i] = subHandScore[i];
            newSubHandFreq[i] = subHandFreq[i];

            for (int j = 26; j < 31; j++) {
                Set<Integer> set = new HashSet<>(subHand[j]);
                if (set.containsAll(subHand[i])) {
                    newSubHandScore[i] -= subHandScore[j];
                    newSubHandFreq[i] -= subHandFreq[j];
                }
            }
            for (int j = 16; j < 26; j++) {
                Set<Integer> set = new HashSet<>(subHand[j]);
                if (set.containsAll(subHand[i])) {
                    newSubHandScore[i] += subHandScore[j];
                    newSubHandFreq[i] += subHandFreq[j];
                }
            }
            for (int j = 6; j < 16; j++) {
                Set<Integer> set = new HashSet<>(subHand[j]);
                if (set.containsAll(subHand[i])) {
                    newSubHandScore[i] -= subHandScore[j];
                    newSubHandFreq[i] -= subHandFreq[j];
                }
            }
            for (int j = 1; j < 6; j++) {
                Set<Integer> set = new HashSet<>(subHand[j]);
                if (set.containsAll(subHand[i])) {
                    newSubHandScore[i] += subHandScore[j];
                    newSubHandFreq[i] += subHandFreq[j];
                }
            }
            newSubHandScore[i] -= subHandScore[0];
            newSubHandFreq[i] -= subHandFreq[0];
        }

        double[] RTP = new double[32];
        for (int i = 0; i < RTP.length; i++) {
            RTP[i] = (double) newSubHandScore[i] / (double) newSubHandFreq[i];
        }

        double[] result = new double[]{-1, -1, -1, -1, -1, -1, -1};
        for (int i = 0; i < RTP.length; i++) {
            if (RTP[i] > result[0]) {
                result[0] = RTP[i];
                result[1] = i;
            }
        }
        for (int i = 2; i < subHand[(int) result[1]].size() + 2; i++) {
            result[i] = convertList(subHand[(int) result[1]])[i - 2];
        }
        return result;
    }

    private static void printResult(double[] result) {
        System.out.println("You should Hold:");
        for (int i = 2; i < 7; i++) {
            if (result[i] != -1) {
                System.out.println((int) result[i]);
            }
        }
        System.out.printf("The RTP is " + "%,.4f", result[0]);
        System.out.println();
    }
}