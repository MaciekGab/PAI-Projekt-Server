package pl.dmcs.server;

import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;

public class EchoMultiServer {
    private ServerSocket serverSocket;

    public void start(int port) throws IOException {
//         ssl
        SSLServerSocketFactory sslServerSocketFactory =
                (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
        serverSocket = sslServerSocketFactory.createServerSocket(port);
//        serverSocket = new ServerSocket(port);
        while (true) {
            new EchoClientHandler(serverSocket.accept()).start();
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

}
