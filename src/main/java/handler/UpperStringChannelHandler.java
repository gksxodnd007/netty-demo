package handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author hubert.squid
 * @since 2020.07.06
 */
@ChannelHandler.Sharable
public class UpperStringChannelHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String str = (String) msg;
        System.out.println(">>> to upper case: " + str.toUpperCase());
        ctx.writeAndFlush(Unpooled.copiedBuffer(str.toUpperCase().getBytes()))
            .addListener(ChannelFutureListener.CLOSE);
    }
}
