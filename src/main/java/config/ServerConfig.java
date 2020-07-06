package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author hubert.squid
 * @since 2020.07.06
 */
public class ServerConfig {

    private final Properties properties;

    public ServerConfig(String fileName) {
        InputStream in = getClass().getClassLoader().getResourceAsStream(fileName);
        this.properties = new Properties();

        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return Integer.parseInt(this.properties.getProperty("netty.server.port"));
    }
}
