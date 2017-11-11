import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {

        //BufferedReader br;
        //br = new BufferedReader(new InputStreamReader(System.in));
        //String input = "";
        //while (input != "END")
        //{
            //input = br.readLine();
            //System.out.println("input : " + input);
            //System.out.print("Adresse : " + ctx.channel().remoteAddress() + "\n");
            //ctx.writeAndFlush("Bonjour");
        //}
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.print(msg);
    }
}