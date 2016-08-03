package io.github.expansionteam.battleships.logic.client;

import org.testng.annotations.Test;

import java.util.concurrent.ExecutorService;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

public class ClientTest {

    @Test
    public void sendMessage() {
        // Given
        ExecutorService executorServiceMock = mock(ExecutorService.class);
        EventMessenger eventMessengerMock = mock(EventMessenger.class);

        Client client = new Client(executorServiceMock, eventMessengerMock);

        // When
        String message = "message";
        client.sendMessage(message);

        // Then
        verify(executorServiceMock, times(1)).execute(isA(Runnable.class));
    }

}