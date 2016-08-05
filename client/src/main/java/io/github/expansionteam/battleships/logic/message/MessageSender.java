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

    public Message sendMessageAndWaitForResponse(Message message) {
        try {
            DataInputStream inputStream = new DataInputStream(socketChannel.socket().getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socketChannel.socket().getOutputStream());

            outputStream.writeUTF(message.getBody());
            outputStream.flush();

            String json = inputStream.readUTF();
            return messageFactory.createFromJson(json);
        } catch (EOFException e) {
            log.debug("Enemy has lost the connection.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.debug("Error.");
        throw new IllegalStateException("Error.");
    }
}
