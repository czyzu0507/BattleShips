package io.github.expansionteam.battleships.logic.message;

import com.google.inject.Inject;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.channels.SocketChannel;

public class MessageSender {

    private final static Logger log = Logger.getLogger(MessageSender.class);

    private final SocketChannel socketChannel;
    private final MessageFactory messageFactory;

    @Inject
    public MessageSender(SocketChannel socketChannel, MessageFactory messageFactory) {
        this.socketChannel = socketChannel;
        this.messageFactory = messageFactory;
    }

    Message sendMessageAndWaitForResponse(Message message) {
        try {
            DataInputStream inputStream = new DataInputStream(socketChannel.socket().getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socketChannel.socket().getOutputStream());

            outputStream.writeUTF(message.toString());
            outputStream.flush();

            String jsonText = inputStream.readUTF();
            return messageFactory.createFromJson(jsonText);
        } catch (EOFException ex) {
            log.info("Enemy has lost the connection.");
        } catch (IOException ex) {
            log.error("", ex);
        }

        throw new IllegalStateException("We should handle that but for now I don't know how.");
    }

}
