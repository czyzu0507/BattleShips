package io.github.expansionteam.battleships.logic.client;

import io.github.expansionteam.battleships.logic.EventProcessor;
import org.mockito.ArgumentCaptor;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

public class EventMessengerTest {

    @Test
    public void sendMessageToServerAndProcessAnswer() {
        // Given
        String answer = "answer";
        SocketClient socketClientMock = mock(SocketClient.class);
        when(socketClientMock.talkWithServer(isA(String.class))).thenReturn(answer);

        EventProcessor eventProcessorMock = mock(EventProcessor.class);

        EventMessenger eventMessenger = new EventMessenger(socketClientMock, eventProcessorMock);

        // When
        String message = "message";
        eventMessenger.send(message);

        // Then
        ArgumentCaptor<String> messageArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(socketClientMock).talkWithServer(messageArgumentCaptor.capture());
        assertThat(messageArgumentCaptor.getValue()).isEqualTo(message);

        ArgumentCaptor<String> jsonArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(eventProcessorMock).processJson(jsonArgumentCaptor.capture());
        assertThat(jsonArgumentCaptor.getValue()).isEqualTo(answer);
    }

}
