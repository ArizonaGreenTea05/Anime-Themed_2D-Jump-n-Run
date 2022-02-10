package utils;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringEditor {

    // removes underscores (replaces with space), makes every first letter to capital
    public static String makeNameNice(String s) {
        char[] c = s.toCharArray();
        StringBuilder out = new StringBuilder("" + (char) (c[0] - 32));

        for (int i = 1; i < c.length; i++) {
            if (c[i] != '_') {
                out.append(c[i]);
            } else {
                if (c[i + 1] >= 97 && c[i + 1] <= 122) {
                    out.append(" ").append((char) (c[i + 1] - 32));
                    i++;
                } else {
                    out.append(" ");
                }
            }
        }

        return out.toString();
    }

    // removes spaces
    public static String removeSpaces(String s) {
        char[] c = s.toCharArray();
        StringBuilder out = new StringBuilder("" + (char) (c[0] - 32));

        for (int i = 1; i < c.length; i++) {
            if (c[i] != ' ') {
                out.append(c[i]);
            }
        }

        return out.toString();
    }

    // shortens string to a specific length
    public static String shorten(String s, int length) {
        char[] c = s.toCharArray();

        if (length > c.length) {
            return s;
        }

        StringBuilder out = new StringBuilder();
        for (int i = 0; i < length; i++) {
            out.append(c[i]);
        }

        return out.toString();
    }


    // removes everything behind the '.'
    public static String removeFileEnding(String s) {
        char[] c = s.toCharArray();

        StringBuilder out = new StringBuilder();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '.') {
                break;
            }
            out.append(c[i]);
        }

        return out.toString();
    }


    // converts a string (rgb (x1,x2,x3)) to a color
    public static Color stringToColor(String input) {
        Pattern c = Pattern.compile("rgb *\\( *([0-9]+), *([0-9]+), *([0-9]+) *\\)");
        Matcher m = c.matcher(input);

        if (m.matches()) {
            return new Color(Integer.parseInt(m.group(1)),  // r
                    Integer.parseInt(m.group(2)),  // g
                    Integer.parseInt(m.group(3))); // b
        }
        return null;
    }
}