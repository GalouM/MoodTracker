package com.galou.moodtracker.models;

import java.text.DecimalFormat;

/**
 * <b>
 *     Convert a number from number to word in French
 * </b>
 * <p>
 *     Convert the given number into word
 * </p>
 *
 * @author galou
 * @version 1.0
 *
 *
 */
public class NumberToWords {

    private static final  String[] tensNames = {
            "",
            "",
            "vingt",
            "trente",
            "quarante",
            "cinquante",
            "soixante",
            "soixante",
            "quatre-vingt",
            "quatre-vingt"
    };

    private static final String[] numNames1 = {
            "",
            "un",
            "deux",
            "trois",
            "quatre",
            "cinq",
            "six",
            "sept",
            "huit",
            "neuf",
            "dix",
            "onze",
            "douze",
            "treize",
            "quatorze",
            "quinze",
            "seize",
            "dix-sept",
            "dix-huit",
            "dix-neuf"
    };

    private  static final String[] numName2 = {
            "",
            "",
            "deux",
            "trois",
            "quatre",
            "cinq",
            "six",
            "sept",
            "huit",
            "neuf",
            "dix"
    };

    /**
     * Constructor
     */
    public NumberToWords() {
    }

    /**
     * Used to convert number from 0 to 100, return a String
     * @param number
     *      number to convert, has to be between 0 and 99
     * @return
     *      a string of the number converted in word
     *
     * @see NumberToWords#numNames1
     * @see NumberToWords#tensNames
     */
    private static String convertZeroToHundred(int number) {

        int theTen = number / 10;
        int lNum = number % 10;
        String result = "";

        switch (theTen) {
            case 1 :
            case 7 :
            case 9 :
                lNum = lNum + 10;
                break;
            default:
        }

        // separator "-" "et"  ""
        String link = "";
        if (theTen > 1) {
            link = "-";
        }
        // other cases
        switch (lNum) {
            case 0:
                link = "";
                break;
            case 1 :
                if (theTen == 8) {
                    link = "-";
                }
                else {
                    link = " et ";
                }
                break;
            case 11 :
                if (theTen==7) {
                    link = " et ";
                }
                break;
            default:
        }

        // tnes in letters
        switch (theTen) {
            case 0:
                result = numNames1[lNum];
                break;
            case 8 :
                if (lNum == 0) {
                    result = tensNames[theTen];
                }
                else {
                    result = tensNames[theTen]
                            + link + numNames1[lNum];
                }
                break;
            default :
                result = tensNames[theTen]
                        + link + numNames1[lNum];
        }
        return result;
    }

    /**
     * Used to convert number from 100 to 999, return a String
     *
     * @param number
     *      number to convert has to be from 100 to 999
     * @return
     *      a string of the number converted in word
     *
     * @see NumberToWords#numName2
     */
    private static String convertLessThanOneThousand(int number) {

        int hundreads = number / 100;
        int rest = number % 100;
        String sReste = convertZeroToHundred(rest);

        String result;
        switch (hundreads) {
            case 0:
                result = sReste;
                break;
            case 1 :
                if (rest > 0) {
                    result = "cent " + sReste;
                }
                else {
                    result = "cent";
                }
                break;
            default :
                if (rest > 0) {
                    result = numName2[hundreads] + " cent " + sReste;
                }
                else {
                    result = numName2[hundreads] + " cents";
                }
        }
        return result;
    }

    /**
     * Convert any given number in word and return a String
     *
     * @param number
     *      number to convert
     * @return
     *      a string of the number converted in word
     *
     */
    public static String convert(long number) {
        // 0 à 999 999 999 999
        if (number == 0) { return "zéro"; }

        String snumber = Long.toString(number);

        // pad of "0"
        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);

        // XXXnnnnnnnnn
        int billions = Integer.parseInt(snumber.substring(0,3));
        // nnnXXXnnnnnn
        int millions  = Integer.parseInt(snumber.substring(3,6));
        // nnnnnnXXXnnn
        int hundreadThousand = Integer.parseInt(snumber.substring(6,9));
        // nnnnnnnnnXXX
        int thousands = Integer.parseInt(snumber.substring(9,12));

        String tradBillions;
        switch (billions) {
            case 0:
                tradBillions = "";
                break;
            case 1 :
                tradBillions = convertLessThanOneThousand(billions)
                        + " milliard ";
                break;
            default :
                tradBillions = convertLessThanOneThousand(billions)
                        + " milliards ";
        }
        String result =  tradBillions;

        String tradMillions;
        switch (millions) {
            case 0:
                tradMillions = "";
                break;
            case 1 :
                tradMillions = convertLessThanOneThousand(millions)
                        + " million ";
                break;
            default :
                tradMillions = convertLessThanOneThousand(millions)
                        + " millions ";
        }
        result =  result + tradMillions;

        String tradHundreadThousand;
        switch (hundreadThousand) {
            case 0:
                tradHundreadThousand = "";
                break;
            case 1 :
                tradHundreadThousand = "mille ";
                break;
            default :
                tradHundreadThousand = convertLessThanOneThousand(hundreadThousand)
                        + " mille ";
        }
        result =  result + tradHundreadThousand;

        String tradMille;
        tradMille = convertLessThanOneThousand(thousands);
        result =  result + tradMille;

        return result;
    }

}
