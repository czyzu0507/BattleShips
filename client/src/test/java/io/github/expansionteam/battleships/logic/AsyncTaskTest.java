package io.github.expansionteam.battleships.logic;

import org.testng.annotations.Test;

import java.util.concurrent.ExecutorService;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AsyncTaskTest {

    @Test
    public void runTaskAsynchronously() {
        // given
        ExecutorService executorServiceMock = mock(ExecutorService.class);
        AsyncTask asyncTask = new AsyncTask(executorServiceMock);

        // when
        asyncTask.runLater(() -> {
        });

        // then
        verify(executorServiceMock).execute(isA(Runnable.class));
    }

}