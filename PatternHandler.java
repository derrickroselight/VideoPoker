public class PatternHandler {
    public boolean royalFlush;
    public boolean straightFlush;
    public boolean fourKind;
    public boolean fullHouse;
    public boolean flush;
    public boolean straight;
    public boolean threeKind;
    public boolean twoPairs;
    public boolean jackBetter;

    public PatternHandler(HandHandler handHandler) {
        this.royalFlush = checkRoyalFlush(handHandler);
        this.straightFlush = checkStraightFlush(handHandler);
        this.fourKind = checkFourKind(handHandler);
        this.fullHouse = checkFullHouse(handHandler);
        this.flush = checkFlush(handHandler);
        this.straight = checkStraight(handHandler);
        this.threeKind = checkThreeKind(handHandler);
        this.twoPairs = checkTwoPairs(handHandler);
        this.jackBetter = checkJacksOrBetter(handHandler);
    }

    private boolean checkRoyalFlush(HandHandler handHandler) {
        if (checkRoyal(handHandler) && checkFlush(handHandler)) {
            return true;
        }
        return false;
    }

    private boolean checkStraightFlush(HandHandler handHandler) {
        if (checkStraight(handHandler) && checkFlush(handHandler)) {
            return true;
        }
        return false;
    }

    private boolean checkFourKind(HandHandler handHandler) {
        for (int i = 0; i < 13; i++) {
            if (handHandler.pointFreq[i] == 4) {
                return true;
            }
        }
        return false;
    }

    private boolean checkFullHouse(HandHandler handHandler) {
        if (checkThreeKind(handHandler) && checkPair(handHandler)) {
            return true;
        }
        return false;
    }

    private boolean checkFlush(HandHandler handHandler) {
        if (handHandler.suit[0] == handHandler.suit[1]
                && handHandler.suit[1] == handHandler.suit[2]
                && handHandler.suit[2] == handHandler.suit[3]
                && handHandler.suit[3] == handHandler.suit[4]) {
            return true;
        }
        return false;
    }

    private boolean checkStraight(HandHandler handHandler) {
        if ((handHandler.sortedPoint[0] + 1 == handHandler.sortedPoint[1]
                && handHandler.sortedPoint[1] + 1 == handHandler.sortedPoint[2]
                && handHandler.sortedPoint[2] + 1 == handHandler.sortedPoint[3]
                && handHandler.sortedPoint[3] + 1 == handHandler.sortedPoint[4])
                || checkRoyal(handHandler)) {
            return true;
        }
        return false;
    }

    private boolean checkThreeKind(HandHandler handHandler) {
        for (int i = 0; i < 13; i++) {
            if (handHandler.pointFreq[i] == 3) {
                return true;
            }
        }
        return false;
    }

    private boolean checkTwoPairs(HandHandler handHandler) {
        int twoPairs = 0;
        for (int i = 0; i < 13; i++) {
            if (handHandler.pointFreq[i] == 2) {
                twoPairs++;
            }
            if (twoPairs == 2) {
                return true;
            }
        }
        return false;
    }

    private boolean checkJacksOrBetter(HandHandler handHandler) {
        for (int i = 10; i < 13; i++) {
            if (handHandler.pointFreq[i] == 2) {
                return true;
            }
        }
        if (handHandler.pointFreq[0] == 2) {
            return true;
        }
        return false;
    }

    private boolean checkPair(HandHandler handHandler) {
        for (int i = 0; i < 13; i++) {
            if (handHandler.pointFreq[i] == 2) {
                return true;
            }
        }
        return false;
    }

    private boolean checkRoyal(HandHandler handHandler) {
        if (handHandler.pointArrayList.contains(1)
                && handHandler.pointArrayList.contains(10)
                && handHandler.pointArrayList.contains(11)
                && handHandler.pointArrayList.contains(12)
                && handHandler.pointArrayList.contains(13)) {
            return true;
        }
        return false;
    }


}
