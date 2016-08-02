package io.github.expansionteam.battleships;

import com.google.common.eventbus.EventBus;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class MainLauncher extends Application {

    private final static Logger log = Logger.getLogger(MainLauncher.class.getSimpleName());

    @Override
    public void start(Stage primaryStage) throws Exception {
        Injector injector = Guice.createInjector(new BattleshipsModule());
        EventBus eventBus = injector.getInstance(EventBus.class);

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
        log.debug("Logging test in client");
        launch(args);
    }

}
