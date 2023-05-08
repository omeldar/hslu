package ch.hslu.ad.sw12;

/**
 * ^0(1{1,2}|0{1})*0$
 */
public class Dea {
    private enum State {
        Z0,
        Z1,
        Z2,
        Z3,
        Z4
    }

    /**
     * Checks if input is a word of the defined language
     */
    public static boolean isWordLanguage(final String input) {
        State state = State.Z0;
        for (char inputChar : input.toCharArray()) {
            if (inputChar == '0') {
                switch(state) {
                    case Z0:
                        break;
                    default:
                        return false;
                }
            } else if (inputChar == '1') {

            } else {
                // is not allowed char, so return false
                return false;
            }
            return false;
        }
        return false;
    }
}