import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private Card            packCard;
    private Player          p1;
    private Player          p2;
    private Player          p3;
    private Player          p4;
    private int             nbPlayerTurn = 1;
    private List<String>    cardOnGame = new ArrayList<String>();
    private String          lastCard;
    private String          lastCardPlace;
    private boolean         gameReady;

    public Game()
    {
        p1 = null;
        p2 = null;
        p3 = null;
        p4 = null;
        packCard = new Card();
        gameReady = false;
    }

    public void startGame()
    {
        packCard.str1 = packCard.distribution(packCard.str1);
        p1.addCard(packCard.str1);
        packCard.str2 = packCard.distribution(packCard.str2);
        p2.addCard(packCard.str2);
        packCard.str3 = packCard.distribution(packCard.str3);
        p3.addCard(packCard.str3);
        packCard.str4 = packCard.distribution(packCard.str4);
        p4.addCard(packCard.str4);
    }

    public void setPlayerReady(String name)
    {
        System.out.print(name);
        if (p1 != null && p1.getName().equals(name))
            p1.setReady(true);
        else if (p2 != null && p2.getName().equals(name))
            p2.setReady(true);
        else if (p3 != null && p3.getName().equals(name))
            p3.setReady(true);
        else if (p4 != null && p4.getName().equals(name))
            p4.setReady(true);
        if ((p1 != null && p1.isReady()) &&
                (p2 != null && p2.isReady()) &&
                (p3 != null && p3.isReady()) &&
                (p4 != null && p4.isReady()))
        {
            System.out.print("Set game ready");
            gameReady = true;
            startGame();
            p1.setTurn(true);
        }
    }

    public Player getPlayer() {
        if (p1.isTurn())
            return p1;
        else if (p2.isTurn())
            return p2;
        else if (p3.isTurn())
            return p3;
        else
            return p4;
    }

    public void setTurn()
    {
        if (p1.isTurn())
        {
            System.out.print("Turn of player 2");
            nbPlayerTurn = 2;
            p1.setTurn(false);
            p2.setTurn(true);
        }
        else if (p2.isTurn())
        {
            System.out.print("Turn of player 3");
            nbPlayerTurn = 3;
            p2.setTurn(false);
            p3.setTurn(true);
        }
        else if (p3.isTurn())
        {
            System.out.print("Turn of player 4");
            nbPlayerTurn = 4;
            p3.setTurn(false);
            p4.setTurn(true);
        }
        else if (p4.isTurn())
        {
            System.out.print("Turn of player 1");
            nbPlayerTurn = 1;
            p4.setTurn(false);
            p1.setTurn(true);
        }
    }

    public void setPlayer(String name)
    {
        if (p1 == null)
        {
            System.out.print("set p1 ready");
            p1 = new Player(name);
        }
        else if (p2 == null)
        {
            System.out.print("set p2 ready");
            p2 = new Player(name);
        }
        else if (p3 == null)
        {
            System.out.print("set p3 ready");
            p3 = new Player(name);
        }
        else if (p4 == null)
        {
            System.out.print("set p4 ready");
            p4 = new Player(name);
        }
    }

    public void restartGame() {
        this.cardOnGame.clear();
        this.lastCard = "";
        this.lastCardPlace = "";
    }

    public int getNbPlayerTurn() {
        return nbPlayerTurn;
    }

    public boolean isGameReady() {
        return gameReady;
    }

    public boolean allPass() {
        if (p1.isPass() && p2.isPass() && p3.isPass() && p4.isPass())
            return true;
        return false;
    }

    public String getLastCard() {
        return lastCard;
    }

    public void setLastCard(String lastCard) {
        this.lastCard = lastCard;
    }

    public void setCardOnGame(String card)
    {
        cardOnGame.add(card);
    }

    public void setLastCardPlace(String lastCardPlace) {
        this.lastCardPlace = lastCardPlace;
    }

    public String getLastCardPlace() {
        return lastCardPlace;
    }
}
