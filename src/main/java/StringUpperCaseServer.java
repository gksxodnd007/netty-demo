import config.ServerConfig;
import handler.ConvertStringChannelHandler;
import handler.UpperStringChannelHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author hubert.squid
 * @since 2020.07.06
 */
public class StringUpperCaseServer {

    public static void main(String... args) throws Exception {
        start(new ServerConfig("application.properties"));
    }

    private static void start(ServerConfig config) throws Exception {
        ConvertStringChannelHandler convertStringChannelHandler = new ConvertStringChannelHandler();
        UpperStringChannelHandler upperStringChannelHandler = new UpperStringChannelHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(config.getPort()))
                .childHandler(new ChannelInitializer<SocketChannel>() { // 수락된 channel에 의해 발생하는 이벤트를 처리하는 핸들러 추가 메서드

                    @Override
                    protected void initChannel(SocketChannel channel) {
                        channel.pipeline().addFirst("convert", convertStringChannelHandler);
                        channel.pipeline().addAfter("convert", "upper", upperStringChannelHandler);
                    }
                });
            ChannelFuture future = bootstrap.bind().sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
