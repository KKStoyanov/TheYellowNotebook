package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import singletons.DailyData;
import singletons.ExerciseData;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("../view/sample.fxml"));
        Parent root = loader.load();
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        root.getStylesheets().add("css/stylesheet.css");
        primaryStage.setTitle("The Yellow Notebook");
        primaryStage.getIcons().add(new Image("imgs/ynb.png"));
        primaryStage.setScene(new Scene(root, 850, 850));
        //primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception{
        ExerciseData.getInstance().close();
		DailyData.getInstance().close();
		//DailyData.getInstance().saveDailyData();
    }

    @Override
    public void init() throws Exception{
        if(!ExerciseData.getInstance().open()) {
			System.out.println("FATAL ERROR");
			Platform.exit();
		}
		if(!DailyData.getInstance().open()) {
			System.out.println("FATAL ERROR");
			Platform.exit();
		}
         // DailyData.getInstance().loadDailyData();
    }
}
