import handler.ConvertStringChannelHandler;
import handler.UpperStringChannelHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;

/**
 * @author hubert.squid
 * @since 2020.07.07
 */
public class ChannelHandlerTest {

    @Test
    void convertStringChannelHandlerTest() {
        // given
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new ConvertStringChannelHandler());
        String expect = "hello netty world!!";
        ByteBuf dut = Unpooled.copiedBuffer(expect.getBytes(Charset.defaultCharset()));

        // when
        embeddedChannel.writeInbound(dut);

        // then
        String result = embeddedChannel.readInbound();
        Assertions.assertEquals(expect, result);
    }

    @Test
    void upperStringChannelHandlerTest() {
        // given
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new UpperStringChannelHandler());
        String dut = "hello netty world!!";
        String expect = dut.toUpperCase();

        // when
        embeddedChannel.writeInbound(dut);

        // then
        ByteBuf result = embeddedChannel.readOutbound();
        Assertions.assertEquals(expect, result.toString(Charset.defaultCharset()));
    }
}
