import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author hubert.squid
 * @since 2020.07.06
 */
public class TestClient {

    @Test
    void call() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress("localhost", 19000))
                .handler(new ChannelInitializer<SocketChannel>() { // 수락하는 channel에 의해 발생하는 이벤트를 처리하는 핸들러 추가 메서드

                    @Override
                    protected void initChannel(SocketChannel channel) {
                        channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) {
                                ctx.writeAndFlush(Unpooled.copiedBuffer("hello netty world!!".getBytes()));
                            }

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                                ByteBuf in = (ByteBuf) msg;
                                System.out.println(">>> Client Received : " + in.toString(Charset.defaultCharset()));
                            }
                        });
                    }
                });
            ChannelFuture future = bootstrap.connect().sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
