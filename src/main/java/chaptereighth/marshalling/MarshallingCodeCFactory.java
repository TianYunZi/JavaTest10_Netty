package chaptereighth.marshalling;

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * Created by Admin on 2017/5/11.
 */
public class MarshallingCodeCFactory {

    /**
     * 创建Jboss Marshalling解码器MarshallingDecoder.
     *
     * @return
     */
    public static MarshallingDecoder buildMarshallingDecoder() {
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration marshallingConfiguration = new MarshallingConfiguration();
        marshallingConfiguration.setVersion(5);
        UnmarshallerProvider unmarshallerProvider = new DefaultUnmarshallerProvider(marshallerFactory, marshallingConfiguration);
        MarshallingDecoder decoder = new MarshallingDecoder(unmarshallerProvider, 1024);
        return decoder;
    }

    /**
     * 创建Jboss Marshalling编码器MarshallingEncoder.
     *
     * @return
     */
    public static MarshallingEncoder buildMarshallingEncoder() {
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration marshallingConfiguration = new MarshallingConfiguration();
        marshallingConfiguration.setVersion(5);
        MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, marshallingConfiguration);
        return new MarshallingEncoder(provider);
    }
}
