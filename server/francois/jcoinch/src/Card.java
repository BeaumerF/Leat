import java.util.Random;

public class Card {
    public String str1;
    public String str2;
    public String str3;
    public String str4;

    public Card()
    {

        str1 = "";
        str2 = "";
        str3 = "";
        str4 = "";
    }

    public Boolean IsAvailable(String tmp) {
        if (str1.contains(tmp) ||
                str2.contains(tmp) ||
                str3.contains(tmp) ||
                str4.contains(tmp))
            return (false);
        return (true);
    }

    public String distribution(String str) {
        Random rand = new Random();
        int nb = 7;
        int letter = 65;
        int count = 0;
        String tmp = "";

        while (count < 8) {
            nb = rand.nextInt(8) + 7;
            letter = rand.nextInt(4) + 65;
            tmp += Integer.toString(nb);
            tmp += (char)letter;
            if (IsAvailable(tmp) && !str.contains(tmp)) {
                str += tmp;
                if (count + 1 < 8)
                    str += ' ';
                ++count;
            }
            tmp = "";
        }
        str += '\0';
        return (str);
    }
}
