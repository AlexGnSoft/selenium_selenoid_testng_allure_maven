package com.carespeak.core.helper;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public interface IDataGenerator {

    String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String nonZeroDigits = "123456789";
    String withZeroDigits = "0" + nonZeroDigits;
    String allCharacters = letters + letters.toLowerCase() + withZeroDigits;
    String dd_MM_yy_H_mm = "dd-MM-yy H:mm";
    String MM_dd_yyyy = "MM/dd/yyyy";
    String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /**
     * Returns date as formatted string with default format
     *
     * @param date calendar instance
     * @return formatted date as String
     */
    default String getFormattedDate(Calendar date) {
        return getFormattedDate(date, MM_dd_yyyy);
    }

    /**
     * Returns current date as formatted string
     *
     * @param format - string to be used as date format
     * @return formatted date as String
     */
    default String getFormattedDate(String format) {
        return getFormattedDate(Calendar.getInstance(), format);
    }

    /**
     * Returns current datetime as string
     *
     * @return formatted datetime as String
     */
    default String getFormattedDateTimeNow() {
        return getFormattedDate(Calendar.getInstance(), dd_MM_yy_H_mm);
    }

    /**
     * Returns date as formatted string
     *
     * @param calendar      calendar instance
     * @param formatPattern pattern string
     * @return formatted date as String
     */
    default String getFormattedDate(Calendar calendar, String formatPattern) {
        return new SimpleDateFormat(formatPattern).format(calendar.getTime());
    }

    default String getGeneratedPhoneNumber() {
        StringBuilder number = new StringBuilder("+1555");
        for (int i = 0; i <= 6; i++) {
            int generatedNumber = (int) (Math.random() * 10);
            number.append(generatedNumber);
        }
        return number.toString();
    }

    /**
     * Returns random generated string
     */
    default String getRandomString() {
        return getRandomString(allCharacters, 16);
    }

    /**
     * Returns timestamp based on the current date and time
     */
    default String getTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDDHHMMSS);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = new GregorianCalendar();
        return sdf.format(calendar.getTime());
    }

    /**
     * Returns random number
     *
     * @param length number length
     */
    default int getRandomNumber(int length) {
        StringBuilder sb = new StringBuilder();
        sb.append(nonZeroDigits.charAt(new Random().nextInt(nonZeroDigits.length())));
        for (int i = 1; i < length; i++) {
            sb.append(withZeroDigits.charAt(new Random().nextInt(withZeroDigits.length())));
        }
        return Integer.parseInt(sb.toString());
    }

    /**
     * Returns random generated string
     *
     * @param symbols Initial symbols set
     * @param length  Number of symbols in generated string
     */
    default String getRandomString(String symbols, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(symbols.charAt(new Random().nextInt(symbols.length())));
        }
        return sb.toString();
    }

    /**
     * Tries to receive counter with specified name from local file.
     * Once counter received, it will be incremented and save back to local file
     * It is not recommended to use, use this just as workaround.
     *
     * @param counterName - counter name to receive
     * @return incremented counter
     */
    @SuppressWarnings("deprecated")
    default Integer getPersistentCounter(String counterName) {
        //TODO: implement directory and file creation if need
        String fileName = System.getProperty("user.dir") + "/data/" + counterName + ".txt";
        int counter = 0;
        try {
            File file = new File(fileName);
            List<String> lines = FileUtils.readLines(file);
            if (lines.size() > 0) {
                counter = Integer.parseInt(lines.get(0).trim());
            }
            counter = counter + 1;
            lines.set(0, String.valueOf(counter));
            FileUtils.writeLines(file, lines);
        } catch (IOException ex) {
            throw new RuntimeException("Exception during persistence counter reader", ex);
        }
        return counter;
    }
}
