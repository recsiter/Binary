package binarynumber;

import numbers.Binary;

/**
 * @author --G--
 */
public class BinaryNumber {

    public static void main(String[] args) {
        int number = -1;
        int shiftN = 1;
        Binary binary = new Binary(number);
        Binary bBinary = new Binary(-100);
        System.out.println(binary.rightShift(shiftN).
                getDecimalValue());
        System.out.println(number >> shiftN);
        //System.out.println(Integer.MIN_VALUE - Integer.MAX_VALUE);
        //System.out.println(Integer.MAX_VALUE + 3);
        // System.out.println(bBinary.getDecimalValue());
        //System.out.println(binary.getDecimalValue());
        //int bit = 1;
//        for (int i = 0; i < 32; i++) {
//            System.out.println(bit << i);
//        }
//        String binaryText = "1";
//        for (int i = 0; i < 32; i++) {
//            //System.out.println(binaryText.length());
//            binary = new Binary(binaryText);
//            System.out.println(binary.getDecimalValue());
//            binaryText = fillZeros(binaryText);
//        }

    }

    private static String fillZeros(String binaryText) {

        binaryText += "0";

        return binaryText;
    }

}
