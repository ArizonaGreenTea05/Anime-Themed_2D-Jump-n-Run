package utils;

public class ElseUtils {

    public static String makeNameNice(String s){
        char[] c = s.toCharArray();
        StringBuilder out = new StringBuilder("" + (char) (c[0] - 32));

        for (int i = 1; i < c.length; i++) {
            if(c[i] != '_'){
                out.append(c[i]);
            } else {
                out.append(" ").append((char) (c[i + 1] - 32));
                i++;
            }
        }

        return out.toString();
    }

    public static String removeSpaces(String s){
        char[] c = s.toCharArray();
        StringBuilder out = new StringBuilder("" + (char) (c[0] - 32));

        for (int i = 1; i < c.length; i++) {
            if(c[i] != ' '){
                out.append(c[i]);
            }
        }

        return out.toString();
    }

    public static String shorten(String s, int length) {
        char[] c = s.toCharArray();

        if(length > c.length) {return s;}

        String out = "";
        for (int i = 0; i < length; i++) {
            out += c[i];
        }

        return out;
    }
}
