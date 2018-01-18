package XQBHController.ControllerAPI;

import XQBHController.ControllerUI.ControllerUIMain;
import XQBHController.ControllerUI.LoginController;
import XQBHController.ControllerUI.WarmingDialogController;
import XQBHController.Utils.log.Logger;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class ShowLogin  {
    public Stage loginCartoonStage;

    public   boolean exec(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(WarmingDialogController.class.getResource("Login.fxml"));

        try {
            loader.load();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        Stage stage_dialog = new Stage(StageStyle.UNDECORATED);
        stage_dialog.initModality(Modality.WINDOW_MODAL);
        stage_dialog.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        stage_dialog.initStyle(StageStyle.UNIFIED);
        Image image=new Image("resources/img/cio.jpg");
        stage_dialog.getIcons().add(image);
        stage_dialog.initOwner(ControllerUIMain.primaryStage);
        LoginController controller = loader.getController();

        controller.setStage(stage_dialog);
        controller.setScene(scene);

        stage_dialog.showAndWait();



        return true;
    }



    public void close() {
        Event.fireEvent(loginCartoonStage, new WindowEvent(loginCartoonStage, WindowEvent.WINDOW_CLOSE_REQUEST));
        Logger.log("LOG_DEBUG","ZFWAITCartoon close");
    }
}
