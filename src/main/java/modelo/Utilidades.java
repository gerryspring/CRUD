package modelo;

public class Utilidades {

    public static void main(String[] args) {
        String x = "1234567891";
        String p = padString(x,40);

        System.out.println(p.length());
    }
    public static String padString(String str, int leng) {
        for (int i = str.length(); i < leng; i++)
            str += " ";
        return str;
    }

}
