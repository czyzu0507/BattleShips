package io.github.expansionteam.battleships;

import com.google.common.eventbus.EventBus;
import com.google.common.net.InetAddresses;
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
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

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
        primaryStage.setMinWidth(WINDOW_WIDTH);
        primaryStage.setMinHeight(WINDOW_HEIGHT);
        primaryStage.setTitle("Battleships");
        primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void configureServerIp(Injector injector) {
        if (Objects.nonNull(getParameters()) && !CollectionUtils.isEmpty(getParameters().getRaw())) {
            List<String> parameters;
            String serverIp = "";
            try {
                parameters = getParameters().getRaw();
                serverIp = parameters.get(0);
                InetAddresses.forString(serverIp);
                log.debug("Server ip address is: " + serverIp);
                ConnectionConfig connectionConfig = injector.getInstance(ConnectionConfig.class);
                connectionConfig.setServerIp(serverIp);
            } catch (IllegalArgumentException e) {
                log.error(serverIp + " is invalid ip address.");
                System.exit(1);
            }
        }
    }

}
