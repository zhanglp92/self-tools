package org.example.nio;

import com.sun.org.apache.xpath.internal.operations.String;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

@Log4j2
public class ServerSample {

    /**
     * 生成通道管理器
     */
    private static Selector genSelector() throws IOException {
        // 创建 serverSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(7999));

        // 通道管理器
        Selector selector = Selector.open();

        // 将通道管理器与通道绑定, 并注册ACCEPT事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        return selector;
    }

    private static void listen(Selector selector) throws IOException {
        while (true) {
            // 当有注册时间到达时, 方法返回, 否则阻塞.
            selector.select();

            for (SelectionKey key : selector.selectedKeys()) {
                if (key.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.write(ByteBuffer.wrap("send message to client".getBytes()));
                    socketChannel.close();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Selector selector = genSelector();
        listen(selector);
    }
}
