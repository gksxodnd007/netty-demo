package handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author hubert.squid
 * @since 2020.07.06
 */
@ChannelHandler.Sharable
public class ConvertStringChannelHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        String str = in.toString(CharsetUtil.UTF_8);
        System.out.println(">>> input: " + str);
        ctx.fireChannelRead(str);
    }
}
