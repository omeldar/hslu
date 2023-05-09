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
            if (inputChar != '0' && inputChar != '1')
                return false;

            if (inputChar == '0') {
                switch(state) {
                    case Z0:
                        state = State.Z1;
                        break;
                    case Z2:
                        state = State.Z4;
                        break;
                    default:
                        return false;
                }
                continue;
            }
            switch(state) {
                case Z1:
                case Z3:
                case Z4:
                    state = State.Z2;
                    break;
                case Z2:
                    state = State.Z3;
                    break;
                default:
                    return false;
            }
        }
        return state == State.Z1 || state == State.Z4;
    }
}