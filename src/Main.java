
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        root.getStylesheets().add("stylesheet.css");
        primaryStage.setTitle("The Yellow Notebook");
        primaryStage.getIcons().add(new Image("ynb.png"));
        primaryStage.setScene(new Scene(root, 850, 850));
        //primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception{
        try{
        	ExerciseData.getInstance().close();
            DailyData.getInstance().saveDailyData();
        }catch (IOException e){
           System.out.println(e.getMessage());
        }
    }

    @Override
    public void init() throws Exception{
        try{
        	if(!ExerciseData.getInstance().open()) {
        		System.out.println("FATAL ERROR");
        		Platform.exit();
        	}
            DailyData.getInstance().loadDailyData();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
