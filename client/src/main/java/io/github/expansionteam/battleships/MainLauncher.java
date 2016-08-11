package io.github.expansionteam.battleships;

import com.google.common.eventbus.EventBus;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.github.expansionteam.battleships.logic.ConnectionConfig;
import io.github.expansionteam.battleships.logic.event.EventHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Objects;

public class MainLauncher extends Application {

    private final static Logger log = Logger.getLogger(MainLauncher.class);
    private ConnectionConfig connectionConfig;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Injector injector = Guice.createInjector(new BattleshipsModule());
        configureServerIp(injector);
        EventBus eventBus = injector.getInstance(EventBus.class);

        eventBus.register(injector.getInstance(EventHandler.class));

        Parent root = FXMLLoader.load(getClass().getResource("/gui/battleships.fxml"), null, new JavaFXBuilderFactory(), c -> {
            final Object controller = injector.getInstance(c);
            eventBus.register(controller);
            return controller;
        });
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setTitle("Battleships");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void configureServerIp(Injector injector) {
        if (Objects.nonNull(getParameters()) && !CollectionUtils.isEmpty(getParameters().getRaw())) {
            List<String> parameters = getParameters().getRaw();
            String serverIp = parameters.get(0);
            log.debug("Server ip address is: " + serverIp);
            connectionConfig = injector.getInstance(ConnectionConfig.class);
            connectionConfig.setServerIp(serverIp);
        }
    }

}
