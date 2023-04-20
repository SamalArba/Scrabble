package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MyServer {

    private final int port;
    private final ClientHandler ch;
    private volatile boolean stop;

    public MyServer(int port, ClientHandler ch) {
        this.port = port;
        this.ch = ch;
    }

    public void start() {
        setStop(false);
        new Thread(()->startServer()).start();
    }

    private void startServer() {
        ServerSocket server;
        try {
            server = new ServerSocket(getPort());
            server.setSoTimeout(1000);
            while (!isStop()){
                try {
                    Socket client = server.accept();
                    ch.handleClient(client.getInputStream(),client.getOutputStream());
                    ch.close();
                    client.close();
                }catch (SocketTimeoutException e) {}
            }
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void close() {
        setStop(true);
    }

    public int getPort() {
        return port;
    }

    public ClientHandler getCh() {
        return ch;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}

