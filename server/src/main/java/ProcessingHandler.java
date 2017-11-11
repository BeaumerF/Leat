import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ProcessingHandler extends ChannelInboundHandlerAdapter {

    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static final Game game = new Game();

    public ProcessingHandler()
    {
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.print("HandlerAdded : " + ctx.channel().remoteAddress() + "\n");
        Channel incoming = ctx.channel();
        for (Channel channel : channels)
            channel.writeAndFlush("[SERVER] = " + incoming.remoteAddress() + "has join\n");
        channels.add(ctx.channel());
        game.setPlayer(incoming.remoteAddress().toString());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channels)
            channel.writeAndFlush("[SERVER] = " + incoming.remoteAddress() + "has left\n");
        channels.remove(ctx.channel());
    }

    public void advertisePlayer()
    {
        for (Channel channel : channels) {
            if (channel.remoteAddress().toString().equals(game.getPlayer().getName()))
            {
                channel.writeAndFlush("Your card : " + game.getPlayer().getListCard() + "\nChoose one card with [/Put : nameOfCard - nameAnnonceCard]\n");
                channel.writeAndFlush("Last card play : '" + game.getLastCard() + "'\n");
            }
            else
                channel.writeAndFlush("Is turn of player " + game.getNbPlayerTurn() + "...\n");
        }
    }

    public boolean upperCard(String lieCard)
    {
        if (game.getLastCard() == null)
            return true;
        String[]    tmpcard = game.getLastCard().split(" ");
        String[]    tmpliecard = lieCard.split(" ");

        if (tmpcard[0].equals("Valet")) tmpcard[0] = "11";
        if (tmpcard[0].equals("Dame")) tmpcard[0] = "12";
        if (tmpcard[0].equals("Roi")) tmpcard[0] = "13";
        if (tmpliecard[0].equals("Valet")) tmpliecard[0] = "11";
        if (tmpliecard[0].equals("Dame")) tmpliecard[0] = "12";
        if (tmpliecard[0].equals("Roi")) tmpliecard[0] = "13";

        System.out.print("Card 1 : " + tmpcard[0] + "\t Card 2 : " + tmpliecard[0]);

        if (Integer.parseInt(tmpliecard[0]) < Integer.parseInt(tmpcard[0]))
            return false;
        else
            return true;
    }

    public void PutCardOnGame(ChannelHandlerContext ctx, Object msg)
    {
        String      card = "", tmp = "", lieCard = "";
        int         i = 0;
        int         nbCard = 0;
        tmp = msg.toString().substring(5, msg.toString().length());
        System.out.print("Tmp command : " + tmp + "\n");
        String[]    tokencommand = tmp.split("-");
        for (String t : tokencommand)
        {
            if (nbCard == 0)
                card = t;
            else
                lieCard = t;
            nbCard++;
        }
        card = card.substring(0, card.length() - 1);
        lieCard = lieCard.substring(1, lieCard.length() - 2);
        System.out.print("card : '" + card + "'\tLieCard : '" + lieCard + "'\n");
        if (!game.getPlayer().getListCard().contains(card) || !upperCard(lieCard))
        {
            ctx.writeAndFlush("Invalid card, retry...\n");
            return;
        }
        game.getPlayer().getListCard().remove(card);
        game.setLastCard(lieCard);
        game.setCardOnGame(card);
        game.setLastCardPlace(card);
        for (Channel channel : channels) {
            if (!channel.remoteAddress().toString().equals(game.getPlayer().getName()))
                channel.writeAndFlush("He put card ;  " + card + ", If you think this player lie /Lie\n\n");
        }
        game.setTurn();
        advertisePlayer();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.print("'" + msg.toString() + "'");
        if (msg.toString().contains("/Ready")) {
            game.setPlayerReady(ctx.channel().remoteAddress().toString());
            if (game.isGameReady()) {
                System.out.print("Game is Ready");
                for (Channel channel : channels)
                    channel.writeAndFlush("Game is ready.\n\n");
                advertisePlayer();
            }
        }
        else if (msg.toString().contains("/Put")) {
            game.getPlayer().setPass(false);
            PutCardOnGame(ctx, msg);
        }
        else if (msg.toString().contains("/Pass")) {
            game.getPlayer().setPass(true);
            if (game.allPass())
            {
                game.restartGame();
                for (Channel channel : channels) {
                        channel.writeAndFlush("All players have passed, new turn ! \n\n");
                }
            }
            game.setTurn();
            advertisePlayer();
        }
        else if (msg.toString().contains("/Lie")) {
            if (game.getLastCardPlace().equals(game.getLastCard()))
            {

            }
        }
    }
}