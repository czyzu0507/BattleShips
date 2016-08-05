package io.github.expansionteam.battleships.logic.message;

public class ProcessMessageTask implements Runnable {

    private final MessageProcessor messageProcessor;
    private final Message message;

    public ProcessMessageTask(MessageProcessor messageProcessor, Message message) {
        this.messageProcessor = messageProcessor;
        this.message = message;
    }

    @Override
    public void run() {
        messageProcessor.processMessage(message);
    }

}
