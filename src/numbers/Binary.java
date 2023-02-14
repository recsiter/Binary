package numbers;

/**
 * @author G
 */
public class Binary {

    //<editor-fold defaultstate="collapsed" desc="constants">
    private static final int LESS_SIGNIFICANT_BIT = 31;
    private static final int SIGN_BIT = 0;
    private static final int LENGTH = 32;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="variables">
    private int[] bitArray;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="inits">
    private Binary(int[] bitArray) {
        this.bitArray = bitArray;
    }

    public Binary() {
        bitArray = new int[LENGTH];
    }

    public Binary(String numberText) {
        this();
        fillBitArrayFromText(numberText);
    }

    public Binary(int decimalValue) {
        this();
        toBinary(decimalValue);
    }

    private void fillBitArrayFromText(String numberText) {

        int localValue = LENGTH - numberText.length();
        for (int i = 0; i < numberText.length(); i++, localValue++) {
            //String text = String.valueOf(numberText.charAt(i));
            String text = numberText.charAt(i) + "";
            int nextBit = Integer.parseInt(text);
            bitArray[localValue] = nextBit;

        }
    }

    private void toBinary(int decimalNumber) {
        for (int i = 0; i < LENGTH; i++) {
            bitArray[i] = ((decimalNumber >> (LESS_SIGNIFICANT_BIT - i)) & 1);
        }
    }
//</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="getters-setters">
    public int getDecimalValue() {
        int result = 0;
        for (int i = SIGN_BIT; i < LENGTH; i++) {
            result ^= (bitArray[i] << LESS_SIGNIFICANT_BIT - i);
        }
        return result;
    }

    public boolean isNegative() {
        return bitArray[SIGN_BIT] == 1;

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="operators">
    public Binary negate() {
        int[] twosComplementArray = createTwosComplementArray();
        return new Binary(twosComplementArray);
    }

//    public Binary add(Binary second) {
//        Binary result = new Binary();
//        int carry = 0;
//        for (int i = LESS_SIGNIFICANT_BIT; i >= SIGN_BIT; i--) {
//            int aBit = bitArray[i];
//            int bBit = second.bitArray[i];
//            result.bitArray[i] = (aBit + bBit + carry) % 2;
//            carry = (aBit + bBit + carry) / 2;
//
//        }
//        return result;
//    }
//    public Binary add(Binary second) {
//        Binary result = new Binary();
//        int carry = 0;
//        for (int i = LESS_SIGNIFICANT_BIT; i >= SIGN_BIT; i--) {
//            int aBit = bitArray[i];
//            int bBit = second.bitArray[i];
//            result.bitArray[i] = (aBit ^ bBit) ^ carry;
//            carry = (aBit & bBit) | (aBit | bBit) & carry;
//
//        }
//        return result;
//    }
    public static Binary add(Binary firstBinary, Binary second) {
        Binary result = new Binary();
        int carry = 0;
        for (int i = LESS_SIGNIFICANT_BIT; i >= SIGN_BIT; i--) {
            int aBit = firstBinary.bitArray[i];
            int bBit = second.bitArray[i];
            result.bitArray[i] = (aBit ^ bBit) ^ carry;
            carry = (aBit & bBit) | (aBit | bBit) & carry;

        }
        return result;
    }

    public Binary substract(Binary second) {
        Binary negated = new Binary(second.createTwosComplementArray());
        //return this.add(negated);
        return add(this, negated);
    }

    private int[] createTwosComplementArray() {
        int[] twoComplement = new int[LENGTH];
        int changeBit = 0;
        for (int i = LESS_SIGNIFICANT_BIT; i >= SIGN_BIT; i--) {
            twoComplement[i] = bitArray[i] ^ changeBit;
//            if (bitArray[i] == 1) {
//                changeBit = 1;
//            }
            changeBit |= bitArray[i];
            //changeBit = changeBit | bitArray[i];

        }
        return twoComplement;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="relational operators">
    public boolean eq(Binary other) {
        return compareTo(other) == 0;
    }

    public boolean notEquals(Binary other) {
        return !eq(other);
    }

    public boolean lessThan(Binary other) {
        return compareTo(other) < 0;
    }

    public boolean lessThanOrEquals(Binary other) {
        return compareTo(other) <= 0;
    }

    public boolean greaterThan(Binary other) {
        return compareTo(other) > 0;
    }

    public boolean greaterThanOrEquals(Binary other) {
        return compareTo(other) >= 0;
    }

    private int compareTo(Binary other) {
        int result = 0;
        int index = SIGN_BIT;
        while (index < LENGTH && result == 0) {
            result = bitArray[index] - other.bitArray[index];
            index++;
        }
        return correctCompareToResultForNegativeNumbers(other, result);
    }

    private int correctCompareToResultForNegativeNumbers(Binary other,
            int result) {
//        if (bitArray[SIGN_BIT] != other.bitArray[SIGN_BIT]) {
//            result *= -1;
//        }
//        return result;
        int multiplier = -(bitArray[SIGN_BIT] ^ other.bitArray[SIGN_BIT]);
        return result * (multiplier | 1);
    }
    //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="shift operators">
    public Binary leftShift(int n) {
        n = getBottomFiveBits(n);
        Binary result = new Binary();
        for (int i = SIGN_BIT; i < LESS_SIGNIFICANT_BIT - n; i++) {
            result.bitArray[i] = bitArray[i + n];

        }
        return result;
    }

    public Binary rightShift(int n) {
        n = getBottomFiveBits(n);
        Binary result = new Binary();
        for (int i = LESS_SIGNIFICANT_BIT - n; i >= SIGN_BIT; i--) {
            result.bitArray[i + n] = bitArray[i];
            if (i < n) {
                result.bitArray[i] = bitArray[SIGN_BIT];
            }

        }

        return result;
    }

    public Binary signedRightShift(int n) {
        n = getBottomFiveBits(n);
        Binary result = new Binary();
        for (int i = LESS_SIGNIFICANT_BIT - n; i >= SIGN_BIT; i--) {
            result.bitArray[i + n] = bitArray[i];
        }

        return result;
    }

    private int getBottomFiveBits(int n) {
        return n & 31;
    }
//</editor-fold>

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bitArray.length; i++) {
            builder.append(bitArray[i]);

        }
        return builder.toString();
    }

}
