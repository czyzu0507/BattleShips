package io.github.expansionteam.battleships;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.github.expansionteam.battleships.logic.message.MessageSender;
import io.github.expansionteam.battleships.logic.message.MessageSenderProvider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BattleshipsModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(MessageSender.class).toProvider(MessageSenderProvider.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    public EventBus provideEventBus() {
        return new EventBus();
    }

    @Provides
    public ExecutorService provideExecutorService() {
        return Executors.newSingleThreadExecutor();
    }

}
