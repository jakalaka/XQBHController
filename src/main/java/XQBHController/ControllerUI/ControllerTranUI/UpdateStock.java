package XQBHController.ControllerUI.ControllerTranUI;

import XQBHController.Controller.Com;
import XQBHController.Controller.ComCall;
import XQBHController.ControllerAPI.UI.WarmingDialog;
import XQBHController.ControllerUI.ControllerUnitUI.Obj_Goods_Update;
import XQBHController.Utils.Data.DataUtils;
import XQBHController.Utils.log.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class UpdateStock implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private FlowPane flowPane;

    public void initialize(URL location, ResourceBundle resources) {

        addListener(root, eventHandler);


        for (int i=0;i<100;i++)
        {

            Obj_Goods_Update obj_goods_update=new Obj_Goods_Update("商品"+i,100,"");
            flowPane.getChildren().add(obj_goods_update.SP);
        }


        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            // Do whatever you want
            flowPane.setPrefWrapLength(newVal.doubleValue());


        });


//        DataUtils.setEnable(root, "HTRQ_U_O", false);
//        DataUtils.setEnable(root, "HTSJ_U_O", false);
//        DataUtils.setEnable(root, "HTLS_U_O", false);
//        DataUtils.setEnable(root, "HTJYM__O", false);
//        DataUtils.setEnable(root, "ZFZHLX_O", false);
//        DataUtils.setEnable(root, "JYJE_U_O", false);
//        DataUtils.setEnable(root, "SFDH_U_O", false);
//        DataUtils.setEnable(root, "SHBH_U_O", false);
//        DataUtils.setEnable(root, "CZZH_U_O", false);
//        DataUtils.setEnable(root, "SPXX_U_O", false);
//        DataUtils.setEnable(root, "FKM_UU_O", false);
//        DataUtils.setEnable(root, "JYZT_U_O", false);


    }

    public EventHandler eventHandler = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            Object eventObj = event.getSource();

//            if (eventObj instanceof TextField) {
//
//                if ("ZFBDDH_I".equals(DataUtils.getID(eventObj))) {
//                    cleanValue();
//                    Map In = new HashMap();
//                    Map Out = new HashMap();
//                    In.put("CXFS_U", "z");
//                    In.put("ZFBDDH", DataUtils.getValue(root, "ZFBDDH_I"));
//                    if (false == ComCall.Call("ControllerSinglequery", "ControllerSinglequery", In, Out)) {
//                        WarmingDialog.show("查询出错", Out.get("CWXX_U").toString());
//                        return;
//                    }
//                    DataUtils.setValue(root, "HTRQ_U_O", Out.get("YHTRQ_").toString());
//                    DataUtils.setValue(root, "HTSJ_U_O", Out.get("YHTSJ_").toString());
//                    DataUtils.setValue(root, "HTLS_U_O", Out.get("YHTLS_").toString());
//                    DataUtils.setValue(root, "HTJYM__O", Out.get("YHTJYM").toString());
//                    String sZFZHLX = Out.get("ZFZHLX").toString();
//                    DataUtils.setValue(root, "ZFZHLX_O", DataUtils.getListMean("ZFZHLX",sZFZHLX));
//
//                    DataUtils.setValue(root, "JYJE_U_O", Out.get("JYJE_U").toString());
//                    DataUtils.setValue(root, "SFDH_U_O", Out.get("SFDH_U").toString());
//                    DataUtils.setValue(root, "SHBH_U_O", Out.get("SHBH_U").toString());
//                    DataUtils.setValue(root, "CZZH_U_O", Out.get("ZDBH_U").toString());
//                    DataUtils.setValue(root, "SPXX_U_O", Out.get("SPXX_U").toString());
//                    DataUtils.setValue(root, "FKM_UU_O", Out.get("FKM_UU").toString());
//                    DataUtils.setValue(root, "JYZT_U_O", DataUtils.getListMean("JYZT_U",Out.get("JYZT_U").toString()));
//
//
//                } else if ("WXDH_U_I".equals(DataUtils.getID(eventObj))) {
//
//                    cleanValue();
//                    Map In = new HashMap();
//                    Map Out = new HashMap();
//                    In.put("CXFS_U", "w");
//                    In.put("WXDH_U", DataUtils.getValue(root, "WXDH_U_I"));
//                    if (false == ComCall.Call("ControllerSinglequery", "ControllerSinglequery", In, Out)) {
//                        WarmingDialog.show("查询出错", Out.get("CWXX_U").toString());
//                        return;
//                    }
//                    DataUtils.setValue(root, "HTRQ_U_O", Out.get("YHTRQ_").toString());
//                    DataUtils.setValue(root, "HTSJ_U_O", Out.get("YHTSJ_").toString());
//                    DataUtils.setValue(root, "HTLS_U_O", Out.get("YHTLS_").toString());
//                    DataUtils.setValue(root, "HTJYM__O", Out.get("YHTJYM").toString());
//                    String sZFZHLX = Out.get("ZFZHLX").toString();
//                    DataUtils.setValue(root, "ZFZHLX_O", DataUtils.getListMean("ZFZHLX",sZFZHLX));
//
//                    DataUtils.setValue(root, "JYJE_U_O", Out.get("JYJE_U").toString());
//                    DataUtils.setValue(root, "SFDH_U_O", Out.get("SFDH_U").toString());
//                    DataUtils.setValue(root, "SHBH_U_O", Out.get("SHBH_U").toString());
//                    DataUtils.setValue(root, "CZZH_U_O", Out.get("ZDBH_U").toString());
//                    DataUtils.setValue(root, "SPXX_U_O", Out.get("SPXX_U").toString());
//                    DataUtils.setValue(root, "FKM_UU_O", Out.get("FKM_UU").toString());
//                    DataUtils.setValue(root, "JYZT_U_O", DataUtils.getListMean("JYZT_U",Out.get("JYZT_U").toString()));
//                } else if ("HTLS_U_I".equals(DataUtils.getID(eventObj))) {
//                    cleanValue();
//                    Map In = new HashMap();
//                    Map Out = new HashMap();
//                    In.put("CXFS_U", "h");
//                    String sHTRQ_U=DataUtils.getValue(root, "HTRQ_U_I");
//                    if(sHTRQ_U.length()<=0)
//                    {
//                        WarmingDialog.show("查询出错", "必须选择后台日期");
//                        return;
//                    }
//                    In.put("HTRQ_U", sHTRQ_U);
//                    In.put("HTLS_U", DataUtils.getValue(root, "HTLS_U_I"));
//                    if (false == ComCall.Call("ControllerSinglequery", "ControllerSinglequery", In, Out)) {
//                        WarmingDialog.show("查询出错", Out.get("CWXX_U").toString());
//                        return;
//                    }
//                    DataUtils.setValue(root, "HTRQ_U_O", Out.get("YHTRQ_").toString());
//                    DataUtils.setValue(root, "HTSJ_U_O", Out.get("YHTSJ_").toString());
//                    DataUtils.setValue(root, "HTLS_U_O", Out.get("YHTLS_").toString());
//                    DataUtils.setValue(root, "HTJYM__O", Out.get("YHTJYM").toString());
//                    String sZFZHLX = Out.get("ZFZHLX").toString();
//                    DataUtils.setValue(root, "ZFZHLX_O", DataUtils.getListMean("ZFZHLX",sZFZHLX));
//
//                    DataUtils.setValue(root, "JYJE_U_O", Out.get("JYJE_U").toString());
//                    DataUtils.setValue(root, "SFDH_U_O", Out.get("SFDH_U").toString());
//                    DataUtils.setValue(root, "SHBH_U_O", Out.get("SHBH_U").toString());
//                    DataUtils.setValue(root, "CZZH_U_O", Out.get("ZDBH_U").toString());
//                    DataUtils.setValue(root, "SPXX_U_O", Out.get("SPXX_U").toString());
//                    DataUtils.setValue(root, "FKM_UU_O", Out.get("FKM_UU").toString());
//                    DataUtils.setValue(root, "JYZT_U_O", DataUtils.getListMean("JYZT_U",Out.get("JYZT_U").toString()));
//                }
//
//
//            } else if (eventObj instanceof ComboBox) {
//                if ("CXFS_U_I".equals(DataUtils.getID(eventObj))) {
//                    if ("支付宝单号".equals(DataUtils.getValue(root, eventObj))) {
//                        DataUtils.setVisible(root, "HTLS_U_I", false);
//                        DataUtils.setVisible(root, "HTRQ_U_I", false);
//                        DataUtils.setVisible(root, "WXDH_U_I", false);
//                        DataUtils.setVisible(root, "ZFBDDH_I", true);
//                    } else if ("微信单号".equals(DataUtils.getValue(root, eventObj))) {
//                        DataUtils.setVisible(root, "HTLS_U_I", false);
//                        DataUtils.setVisible(root, "HTRQ_U_I", false);
//                        DataUtils.setVisible(root, "WXDH_U_I", true);
//                        DataUtils.setVisible(root, "ZFBDDH_I", false);
//                    } else if ("后台流水".equals(DataUtils.getValue(root, eventObj))) {
//                        DataUtils.setVisible(root, "HTLS_U_I", true);
//                        DataUtils.setVisible(root, "HTRQ_U_I", true);
//                        DataUtils.setVisible(root, "WXDH_U_I", false);
//                        DataUtils.setVisible(root, "ZFBDDH_I", false);
//                    }
//                }
//            }
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

    public void cleanValue() {
//        DataUtils.setValue(root, "HTRQ_U_O", "");
//        DataUtils.setValue(root, "HTSJ_U_O", "");
//        DataUtils.setValue(root, "HTLS_U_O", "");
//        DataUtils.setValue(root, "HTJYM__O", "");
//        DataUtils.setValue(root, "ZFZHLX_O", "");
//        DataUtils.setValue(root, "JYJE_U_O", "");
//        DataUtils.setValue(root, "SFDH_U_O", "");
//        DataUtils.setValue(root, "SHBH_U_O", "");
//        DataUtils.setValue(root, "CZZH_U_O", "");
//        DataUtils.setValue(root, "SPXX_U_O", "");
//        DataUtils.setValue(root, "FKM_UU_O", "");
//        DataUtils.setValue(root, "JYZT_U_O", "");
    }


}
