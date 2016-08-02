package io.github.expansionteam.battleships;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.ProvidedBy;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.github.expansionteam.battleships.gui.Client;

public class BattleshipsModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    public EventBus provideEventBus() {
        return new EventBus();
    }

    @Provides
    @Singleton
    public Client getClient() {
        return new Client();
    }

}
