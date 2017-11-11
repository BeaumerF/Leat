import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Player {
    private List<String> ListCard = new ArrayList<String>();
    private boolean isTurn;
    private String name;
    private boolean isReady;
    private boolean pass;

    public List<String> getListCard() {
        return ListCard;
    }

    public void addListCard(String name, String number) {
        if (number.equals("11"))
            number = "Valet";
        if (number.equals("12"))
            number = "Dame";
        if (number.equals("13"))
            number = "Roi";
        ListCard.add(number + " " + name);
    }

    public Player(String name)
    {
        this.name = name;
        this.isTurn = false;
        this.pass = false;
    }

    public void addCard(String listCard)
    {
        String[] tokens = listCard.split(" ");
        System.out.println("list card : " + name);
        boolean contains;
        for (String t : tokens)
        {
            contains = t.contains("A");
            if (contains)
                addListCard("Trèfle", t.replace("A", ""));
            contains = t.contains("B");
            if (contains)
                addListCard("Pique", t.replace("B", ""));
            contains = t.contains("C");
            if (contains)
                addListCard("Carreau", t.replace("C", ""));
            contains = t.contains("D");
            if (contains)
                addListCard("Coeur", t.replace("D", ""));
        }
        for (int i=0;i < ListCard.size();i++)
        {
            System.out.print("[" + ListCard.get(i) + "] ");
        }
        System.out.println("\n");
    }

    public void setTurn(boolean turn) {
        isTurn = turn;
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public boolean isReady() {
        return isReady;
    }

    public String getName() {
        return name;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public boolean isPass() {
        return pass;
    }
}
