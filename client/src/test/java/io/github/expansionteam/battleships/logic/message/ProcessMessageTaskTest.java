package io.github.expansionteam.battleships.logic.message;

import org.testng.annotations.Test;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ProcessMessageTaskTest {

    @Test
    public void run() {
        // Given
        MessageProcessor messageProcessorMock = mock(MessageProcessor.class);
        Message message = new Message("");
        ProcessMessageTask processMessageTask = new ProcessMessageTask(messageProcessorMock, message);

        // When
        processMessageTask.run();

        // Then
        verify(messageProcessorMock).processMessage(isA(Message.class));
    }

}