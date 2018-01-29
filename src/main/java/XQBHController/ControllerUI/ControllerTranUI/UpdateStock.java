package XQBHController.ControllerUI.ControllerTranUI;

import XQBHController.Controller.Com;
import XQBHController.ControllerAPI.Com.DownloadModelFile;
import XQBHController.ControllerAPI.Com.GetZDModel;
import XQBHController.ControllerAPI.UI.WarmingDialog;
import XQBHController.ControllerUI.ControllerAPIUI.GetLastModelFromZD;
import XQBHController.ControllerUI.ControllerUnitUI.Obj_Goods_Update;
import XQBHController.Utils.Data.DataUtils;
import XQBHController.Utils.Model.DataModel;
import XQBHController.Utils.log.Logger;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import javax.swing.text.html.ImageView;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static XQBHController.Utils.PropertiesHandler.PropertiesReader.readKeyFromXML;

public class UpdateStock implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private FlowPane flowPane;

    @FXML
    private ComboBox ZDBH_U_I;
    @FXML
    private TextField SEARCH_I;

    @FXML
    private ImageView image;

    public static Map<String, DataModel> mapDataModel = new HashMap();


    static boolean initFlg = false;


    public void initialize(URL location, ResourceBundle resources) {

        addListener(root, eventHandler);

        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            // Do whatever you want
            flowPane.setPrefWrapLength(newVal.doubleValue());
            if (!initFlg) {
                for (Map map :
                        Com.listSH_ZDXX) {
                    String sZDBH_U = map.get("ZDBH_U").toString();
                     DataModel dataModel = GetLastModelFromZD.exec(sZDBH_U);
                    if (dataModel == null) {
                        Logger.log("LOG_DEBUG", sZDBH_U + "'s model is null");
                        continue;
                    }
                    if (dataModel.isBuildSuccess()) {
                        mapDataModel.put(sZDBH_U, dataModel);
                    }else {
                        WarmingDialog.show(WarmingDialog.Dialog_ERR,"错误的终端信息["+sZDBH_U+"]");
                    }
                    Logger.log("LOG_DEBUG", "put " + sZDBH_U + "'s model");

                }

                for (Map map :
                        Com.listSH_ZDXX) {
                    ZDBH_U_I.getItems().add(map.get("ZDBH_U"));
                    Logger.log("LOG_DEBUG", "ZDBH_U_I ADD " + map.get("ZDBH_U"));
                }
                ZDBH_U_I.setValue(ZDBH_U_I.getItems().get(0));


                initFlg = true;
            }

        });


    }

    public EventHandler eventHandler = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            Object eventObj = event.getSource();
            if (eventObj instanceof ComboBox) {
                if ("ZDBH_U_I".equals(((ComboBox) eventObj).getId())) {//选择终端信息时，加载本地model信息，获取终端model内容

                    String sZDBH_U = DataUtils.getValue(root, "ZDBH_U_I");

                    Logger.log("LOG_DEBUG", "createUnitFromDatamodel ZDBH_U=" + sZDBH_U);
                    //加载本地的model文件
                    flowPane.getChildren().clear();
                    if (false == createUnitFromDatamodel(sZDBH_U, mapDataModel.get(sZDBH_U),""))
                        return;


                    //根据终端对应的IP查询终端对应的model信息
                    String sIP = "";
                    for (Map map :
                            Com.listSH_ZDXX) {
                        if (sZDBH_U.equals(map.get("ZDBH_U"))) {
                            sIP = map.get("IP_UUU").toString();
                            break;
                        }
                    }
                    Logger.log("LOG_DEBUG", "ZDBH_U=[" + sZDBH_U + "]  IP=[" + sIP + "]");

                    DataModel dataModel = GetLastModelFromZD.exec(sZDBH_U);
                    if (dataModel==null)
                        return;

                    setGoodsAccout(dataModel);


                }
            } else if (eventObj instanceof Button) {
                if ("reload".equals(((Button) eventObj).getId()))//点击重新加载时，下载终端model模型，获取终端model内容
                {
                    //跟终端通讯，获取datamodel 重画flowPane
                    String sZDBH_U = DataUtils.getValue(root, "ZDBH_U_I");
                    if (sZDBH_U.equals("")) {
                        WarmingDialog.show(WarmingDialog.Dialog_INPUTERR, "必须选择终端");
                        return;
                    }


                    try {
                        if (true != DownloadModelFile.exec(sZDBH_U))
                            return;
                    } catch (Exception e) {
                        Logger.logException("LOG_ERR", e);
                        return;
                    }

                    flowPane.getChildren().clear();
                    SEARCH_I.setText("");
                    if (false == createUnitFromDatamodel(sZDBH_U, mapDataModel.get(sZDBH_U),""))
                        return;


                    //根据终端对应的IP查询终端对应的model信息
                    String sIP = "";
                    for (Map map :
                            Com.listSH_ZDXX) {
                        if (sZDBH_U.equals(map.get("ZDBH_U"))) {
                            sIP = map.get("IP_UUU").toString();
                            break;
                        }
                    }
                    Logger.log("LOG_DEBUG", "ZDBH_U=[" + sZDBH_U + "]  IP=[" + sIP + "]");

                    DataModel dataModel = GetLastModelFromZD.exec(sZDBH_U);
                    if (dataModel==null)
                        return;

                    setGoodsAccout(dataModel);
                    WarmingDialog.show(WarmingDialog.Dialog_OVER, "加载商品数据成功!!!");


                }
            } else if (eventObj instanceof TextField) {
                if ("SEARCH_I".equals(((TextField) eventObj).getId())) { //搜索
                    flowPane.getChildren().clear();
                    String sSEARCH=DataUtils.getValue(root,"SEARCH_I");
                    String sZDBH_U=DataUtils.getValue(root,"ZDBH_U_I");

                    if (false == createUnitFromDatamodel(sZDBH_U, mapDataModel.get(sZDBH_U),sSEARCH))
                        return;


                    DataModel dataModel = GetLastModelFromZD.exec(sZDBH_U);
                    if (dataModel==null)
                        return;

                    setGoodsAccout(dataModel);

                }
            }
        }
    };

    public static void addListener(Node node, EventHandler eventHandler) {
        if (node instanceof Pane) {
            for (Node node_ : ((Pane) node).getChildren()
                    ) {
                addListener(node_, eventHandler);
            }


        } else if (node instanceof TextField) {
            ((TextField) node).setOnAction(eventHandler);
        } else if (node instanceof Button) {
            ((Button) node).setOnAction(eventHandler);
        } else if (node instanceof ComboBox) {
            ((ComboBox) node).setOnAction(eventHandler);
        }

    }

    public void setGoodsAccout(DataModel dataModel) {

        if (dataModel.getModelType().equals("goods")) {
            Node node = DataUtils.getTarget(flowPane, dataModel.getPosition());
            if (node != null) {
                DataUtils.setValue(node, "goodsNum", dataModel.getGoodsAmount() + "");
            }
        } else {
            for (Map.Entry<String, DataModel> entry :
                    dataModel.getElements().entrySet()) {
                setGoodsAccout(entry.getValue());
            }
        }
    }

    public boolean createUnitFromDatamodel(String sZDBH_U, DataModel dataModel,String condition) {
        if (dataModel == null) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "获取商品信息失败，请重新加载商品模型");
            return false;
        }

        if (dataModel.getModelType().equals("goods")) {
            if (condition.length()==0||dataModel.getPosition().contains(condition))
            flowPane.getChildren().add(new Obj_Goods_Update(sZDBH_U, dataModel).SP);
        } else {
            for (Map.Entry<String, DataModel> entry :
                    dataModel.getElements().entrySet()) {
                createUnitFromDatamodel(sZDBH_U, entry.getValue(),condition);
            }
        }
        return true;
    }


}
