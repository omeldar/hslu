package ch.hslu.ad.sw13;

public class PatternSearch {
    private static final int PATTERN_LENGTH = 6;
    private enum State {
        Z0(0),
        Z1(1),
        Z2(2),
        Z3(3),
        Z4(4),
        Z5(5);

        private final int index;
        private State(int value) {
            this.index = value;
        }

        public int getValue() {
            return index;
        }
    }

    public static int stateSearch(final String a){
        State state = State.Z0;
        char[] charArr = a.toUpperCase().toCharArray();
        for (int i = 0; i < a.length(); i++) {

            /*
            * Here we need to calculate how many characters we need in the current state
            * to be able to still find it. If there aren't enough chars to find the pattern,
            * we can abort and return -1
            * if(the characters needed > characters available)
            */
            if(PATTERN_LENGTH - state.getValue() > a.length() - i){
                return -1;
            }

            char inputChar = charArr[i];
            switch (state) {
                case Z0:
                    if(inputChar == 'A'){
                        state = State.Z1;
                    }
                    break;
                case Z1:
                    if(inputChar == 'N'){
                        state = State.Z2;
                    }
                    else if(inputChar == 'S'){
                        state = State.Z0;
                    }
                    break;
                case Z2:
                    if(inputChar == 'A'){
                        state = State.Z3;
                        continue;
                    }
                    state = State.Z0;
                    break;
                case Z3:
                    if(inputChar == 'N'){
                        state = State.Z4;
                    }
                    else if(inputChar == 'A'){
                        state = State.Z1;
                    }
                    else{
                        state = State.Z0;
                    }
                    break;
                case Z4:
                    if(inputChar == 'A'){
                        state = State.Z5;
                    }
                    else if(inputChar == 'N'){
                        state = State.Z2;
                    }
                    else {
                        state = State.Z0;
                    }
                    break;
                case Z5:
                    if(inputChar == 'S'){
                        return i - (PATTERN_LENGTH -1);
                    }
                    else if(inputChar == 'N'){
                        state = State.Z4;
                    }
                    else if(inputChar == 'A'){
                        state = State.Z3;
                    }
                    break;
            }
        }
        return -1;
    }
}
