import org.jdesktop.swingx.JXTable;

import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static Boolean isFinish(String str)
    {
        if (str.contains("A") || str.contains("B")
                || str.contains("C") || str.contains("D"))
            return (false);
        return (true);
    }

    public static String trueValue(String str) {
        if (str.matches("[0-9]?[0-9][A-Z] [0-9]?[0-9][A-Z]")) {
            return (str.split(" ", 2)[0]);
        }
        return (str);
    }

    public static Boolean isValid(String str)
    {
        if (str.matches("[0-9]?[0-9][A-Z]") ||
                str.matches("[0-9]?[0-9][A-Z] [0-9]?[0-9][A-Z]"))
            return (true);
        return (false);


    }

    public static void main(String[] args) {

        Card c = new Card();

        c.str1 = c.distribution(c.str1);
        System.out.println(c.str1);
        //c.str1 = im.delight.java.emoji.Emoji.replaceInText(c.str1);
        //System.out.println(c.str1 + '\n');

        c.str2 = c.distribution(c.str2);
        System.out.println(c.str2);
        //c.str2 = im.delight.java.emoji.Emoji.replaceInText(c.str2);
        //System.out.println(c.str2 + '\n');

        c.str3 = c.distribution(c.str3);
        System.out.println(c.str3);
        //c.str3 = im.delight.java.emoji.Emoji.replaceInText(c.str3);
        //System.out.println(c.str3 + '\n');

        c.str4 = c.distribution(c.str4);
        System.out.println(c.str4);
        //c.str4 = im.delight.java.emoji.Emoji.replaceInText(c.str4);
        //System.out.println(c.str4 + '\n');


        //une fois que les cartes sont distribuées, les joueurs envoient les input
        String str = c.str1;
        String truestr = null;
        String falsestr = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            while (!isFinish(str)) {

                System.out.print("Enter something : ");
                String input = br.readLine();

                if ("q".equals(input)) {
                    System.out.println("Exit!");
                    System.exit(0);
                }
                else if (isValid(input)) {
                    truestr = trueValue(input);
                        if (str.contains(truestr)) {
                            str = str.replace(truestr, "");
                            System.out.println(str);
                            System.out.println("You play the " + truestr);
                            //send socket string "input", parse
                        }
                        else
                            System.out.println("Try Again");
                }
                else
                    System.out.println("Try Again");
                System.out.println("-----------\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
