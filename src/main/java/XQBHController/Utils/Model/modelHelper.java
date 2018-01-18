package XQBHController.Utils.Model;



import XQBHController.ControllerAPI.GetSPNum;
import XQBHController.ControllerAPI.WarmingDialog;
import XQBHController.ControllerUI.ControllerUIMain;
import XQBHController.ControllerUI.Controller;
import XQBHController.ControllerUI.MyModel;
import XQBHController.Utils.log.Logger;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.util.Map;
import java.util.Vector;

public class modelHelper {
    public static void setAllUHidden(MyModel model){
        if (null==model)
            return;
        model.getAnchorPane().setVisible(false);
        for (Map.Entry<String, MyModel> entry :
                model.getElements().entrySet()) {
            setAllUHidden(model.getElements().get(entry.getKey()));
        }
    }
    public static void go(MyModel model){

        if (model.getModelType().equals("things"))
        {
            int irestNum;
            irestNum= GetSPNum.exec(model.getName());
            model.restNumLable.setText("ʣ���棺"+irestNum);
            if (irestNum<=0)
            {
                model.buy.setText("������");
                model.buy.getStyleClass().removeAll("buyButton_over","buyButton");
                model.buy.getStyleClass().add("buyButton_over");

            }else {
                model.buy.setText("����");
                model.buy.getStyleClass().removeAll("buyButton_over","buyButton");
                model.buy.getStyleClass().add("buyButton");
            }
        }

        setAllUHidden(Controller.model);
        model.getAnchorPane().setVisible(true);
        Controller.model.position.add(model);
        updateGuide();
    }
    public static void go2(int index){
        setAllUHidden(Controller.model);
        Controller.model.position.get(index).getAnchorPane().setVisible(true);
        Vector<MyModel> tmp=new Vector<MyModel>();
        for (int i=0;i<=index;i++)
        {
            tmp.add(Controller.model.position.get(i));
        }
        Controller.model.position.clear();

        for (int i=0;i<=index;i++)
        {
            Controller.model.position.add(tmp.get(i));
        }

        updateGuide();
    }
    public static void goBack(){

        int index=Controller.model.position.size();
        if (index<2)
            return;
        setAllUHidden(Controller.model);

        Controller.model.position.remove(index-1);
        Controller.model.position.get(index-2).getAnchorPane().setVisible(true);
        updateGuide();
    }
    public static void goHome(){
        MyModel tmp=Controller.model.position.get(0);
        setAllUHidden(Controller.model);
       tmp.getAnchorPane().setVisible(true);
        Controller.model.position.removeAllElements();
        Controller.model.position.add(tmp);
        updateGuide();
    }
    public static void updateGuide(){
        ControllerUIMain.controller.cleanFlow();

        for (int i=0;i<Controller.model.position.size();i++)
        {
            String text="";
            if (i==0)
                text="��ҳ";
            else
                text=Controller.model.position.get(i).getName();
            guideButton button=new guideButton(text,i);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
                go2(button.index);
            });
            button.getStyleClass().add("guideText");
            ControllerUIMain.controller.addFlow(button);
        }
    }
}

class guideButton extends Button{
    public int index;
    public guideButton(String text,int index){
        super(text);
        this.index=index;
    }
}
