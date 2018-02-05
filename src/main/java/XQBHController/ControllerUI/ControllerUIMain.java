package XQBHController.ControllerUI;

import XQBHController.Utils.log.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.File;

public class ControllerUIMain extends Application {

    public static Controller controller;
    public static Stage primaryStage;


    @Override
    public void start(Stage primaryStage) throws Exception {
        ControllerUIMain.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));

        primaryStage.setTitle("����ٻ�");
        Scene scene = new Scene(root);

//        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNIFIED);
        Image image=new Image("resources/img/cio.jpg");
        primaryStage.getIcons().add(image);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        primaryStage.show();

    }


    public static void main(String[] args) {

        if (false == Init())
            return;
        launch(args);
    }

    public static boolean Init() {



        File prop=new File(System.getProperty("java.home")+"/lib/");
        Logger.log("LOG_DEBUG","target propfile="+prop.getAbsolutePath());

        //��ȡ�����ļ�


        return true;
    }

}
