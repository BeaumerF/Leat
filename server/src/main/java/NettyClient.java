import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.Buffer;

public class NettyClient  {

    public static void main(String[] args) throws Exception {
        new NettyClient("localhost", 4444).run();
    }

    private final String host;
    private final int port;

    public NettyClient(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception
    {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap()
                    .group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                                ch.pipeline().addLast(new StringEncoder(), new StringDecoder(), new ClientHandler());
                        }
                    });

            Channel channel = b.connect(host, port).sync().channel();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                channel.writeAndFlush(in.readLine() + "\r\n");
            }

        } finally {
            workerGroup.shutdownGracefully();
        }
    }


}