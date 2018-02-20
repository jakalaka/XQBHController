package XQBHController.ControllerUI.ControllerTranUI;

import XQBHController.Controller.ComCall;
import XQBHController.ControllerAPI.Com.ComRefund;
import XQBHController.ControllerAPI.UI.WarmingDialog;
import XQBHController.Utils.Data.DataUtils;
import XQBHController.Utils.log.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static XQBHController.Utils.Data.DataUtils.Date2StringofTime;
import static XQBHController.Utils.Data.DataUtils.Date2StringofYear;

public class QueryTran implements Initializable {
    @FXML
    private AnchorPane root;

    public void initialize(URL location, ResourceBundle resources) {

        addListener(root, eventHandler);

        DataUtils.setEnable(root, "HTRQ_U_O", false);
        DataUtils.setEnable(root, "HTSJ_U_O", false);
        DataUtils.setEnable(root, "HTLS_U_O", false);
        DataUtils.setEnable(root, "HTJYM__O", false);
        DataUtils.setEnable(root, "ZFZHLX_O", false);
        DataUtils.setEnable(root, "JYJE_U_O", false);
        DataUtils.setEnable(root, "SFDH_U_O", false);
        DataUtils.setEnable(root, "SHBH_U_O", false);
        DataUtils.setEnable(root, "CZZH_U_O", false);
        DataUtils.setEnable(root, "SPXX_U_O", false);
        DataUtils.setEnable(root, "FKM_UU_O", false);
        DataUtils.setEnable(root, "JYZT_U_O", false);


    }

    public EventHandler eventHandler = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            Object eventObj = event.getSource();

            if (eventObj instanceof TextField) {
                if ("ZFBDDH_I".equals(((TextField) eventObj).getId())) {
                    cleanValue();
                    Map In = new HashMap();
                    Map Out = new HashMap();
                    In.put("CXFS_U", "z");
                    In.put("ZFBDDH", DataUtils.getValue(root, "ZFBDDH_I"));
                    if (false == ComCall.Call("ControllerSingleQuery", "ControllerSingleQuery", In, Out)) {
                        WarmingDialog.show(WarmingDialog.Dialog_ERR, DataUtils.getValue(Out, "CWXX_U"));
                        return;
                    }
                    DataUtils.setValue(root, "HTRQ_U_O", DataUtils.getValue(Out, "YHTRQ_"));
                    DataUtils.setValue(root, "HTSJ_U_O", DataUtils.getValue(Out, "YHTSJ_"));
                    DataUtils.setValue(root, "HTLS_U_O", DataUtils.getValue(Out, "YHTLS_"));
                    DataUtils.setValue(root, "HTJYM__O", DataUtils.getValue(Out, "YHTJYM"));
                    String sZFZHLX = DataUtils.getValue(Out, "ZFZHLX");
                    DataUtils.setValue(root, "ZFZHLX_O", DataUtils.getListMean("ZFZHLX", sZFZHLX));

                    DataUtils.setValue(root, "JYJE_U_O", DataUtils.getValue(Out, "JYJE_U"));
                    DataUtils.setValue(root, "SFDH_U_O", DataUtils.getValue(Out, "SFDH_U"));
                    DataUtils.setValue(root, "SHBH_U_O", DataUtils.getValue(Out, "SHBH_U"));
                    DataUtils.setValue(root, "CZZH_U_O", DataUtils.getValue(Out, "ZDBH_U"));
                    DataUtils.setValue(root, "SPXX_U_O", DataUtils.getValue(Out, "SPXX_U"));
                    DataUtils.setValue(root, "FKM_UU_O", DataUtils.getValue(Out, "FKM_UU"));
                    DataUtils.setValue(root, "JYZT_U_O", DataUtils.getListMean("JYZT_U", DataUtils.getValue(Out, "JYZT_U")));


                } else if ("WXDH_U_I".equals(((TextField) eventObj).getId())) {

                    cleanValue();
                    Map In = new HashMap();
                    Map Out = new HashMap();
                    In.put("CXFS_U", "w");
                    In.put("WXDH_U", DataUtils.getValue(root, "WXDH_U_I"));
                    if (false == ComCall.Call("ControllerSingleQuery", "ControllerSingleQuery", In, Out)) {
                        WarmingDialog.show(WarmingDialog.Dialog_ERR, DataUtils.getValue(Out, "CWXX_U"));
                        return;
                    }
                    DataUtils.setValue(root, "HTRQ_U_O", DataUtils.getValue(Out, "YHTRQ_"));
                    DataUtils.setValue(root, "HTSJ_U_O", DataUtils.getValue(Out, "YHTSJ_"));
                    DataUtils.setValue(root, "HTLS_U_O", DataUtils.getValue(Out, "YHTLS_"));
                    DataUtils.setValue(root, "HTJYM__O", DataUtils.getValue(Out, "YHTJYM"));
                    String sZFZHLX = DataUtils.getValue(Out, "ZFZHLX");
                    DataUtils.setValue(root, "ZFZHLX_O", DataUtils.getListMean("ZFZHLX", sZFZHLX));

                    DataUtils.setValue(root, "JYJE_U_O", DataUtils.getValue(Out, "JYJE_U"));
                    DataUtils.setValue(root, "SFDH_U_O", DataUtils.getValue(Out, "SFDH_U"));
                    DataUtils.setValue(root, "SHBH_U_O", DataUtils.getValue(Out, "SHBH_U"));
                    DataUtils.setValue(root, "CZZH_U_O", DataUtils.getValue(Out, "ZDBH_U"));
                    DataUtils.setValue(root, "SPXX_U_O", DataUtils.getValue(Out, "SPXX_U"));
                    DataUtils.setValue(root, "FKM_UU_O", DataUtils.getValue(Out, "FKM_UU"));
                    DataUtils.setValue(root, "JYZT_U_O", DataUtils.getListMean("JYZT_U", DataUtils.getValue(Out, "JYZT_U")));
                } else if ("HTLS_U_I".equals(((TextField) eventObj).getId())) {
                    cleanValue();
                    Map In = new HashMap();
                    Map Out = new HashMap();
                    In.put("CXFS_U", "h");
                    String sHTRQ_U = DataUtils.getValue(root, "HTRQ_U_I");
                    if (sHTRQ_U.length() <= 0) {
                        WarmingDialog.show(WarmingDialog.Dialog_INPUTERR, "必须选择后台日期");
                        return;
                    }
                    In.put("HTRQ_U", sHTRQ_U);
                    In.put("HTLS_U", DataUtils.getValue(root, "HTLS_U_I"));
                    if (false == ComCall.Call("ControllerSingleQuery", "ControllerSingleQuery", In, Out)) {
                        WarmingDialog.show(WarmingDialog.Dialog_ERR, DataUtils.getValue(Out, "CWXX_U"));
                        return;
                    }
                    DataUtils.setValue(root, "HTRQ_U_O", DataUtils.getValue(Out, "YHTRQ_"));
                    DataUtils.setValue(root, "HTSJ_U_O", DataUtils.getValue(Out, "YHTSJ_"));
                    DataUtils.setValue(root, "HTLS_U_O", DataUtils.getValue(Out, "YHTLS_"));
                    DataUtils.setValue(root, "HTJYM__O", DataUtils.getValue(Out, "YHTJYM"));
                    String sZFZHLX = DataUtils.getValue(Out, "ZFZHLX");
                    DataUtils.setValue(root, "ZFZHLX_O", DataUtils.getListMean("ZFZHLX", sZFZHLX));

                    DataUtils.setValue(root, "JYJE_U_O", DataUtils.getValue(Out, "JYJE_U"));
                    DataUtils.setValue(root, "SFDH_U_O", DataUtils.getValue(Out, "SFDH_U"));
                    DataUtils.setValue(root, "SHBH_U_O", DataUtils.getValue(Out, "SHBH_U"));
                    DataUtils.setValue(root, "CZZH_U_O", DataUtils.getValue(Out, "ZDBH_U"));
                    DataUtils.setValue(root, "SPXX_U_O", DataUtils.getValue(Out, "SPXX_U"));
                    DataUtils.setValue(root, "FKM_UU_O", DataUtils.getValue(Out, "FKM_UU"));
                    DataUtils.setValue(root, "JYZT_U_O", DataUtils.getListMean("JYZT_U", DataUtils.getValue(Out, "JYZT_U")));
                }


            } else if (eventObj instanceof ComboBox) {
                if ("CXFS_U_I".equals(((ComboBox) eventObj).getId())) {
                    if ("支付宝单号".equals(DataUtils.getValue(root, eventObj))) {
                        DataUtils.setVisible(root, "HB_HTLS_U_I", false);
                        DataUtils.setVisible(root, "HB_HTRQ_U_I", false);
                        DataUtils.setVisible(root, "HB_WXDH_U_I", false);
                        DataUtils.setVisible(root, "HB_ZFBDDH_I", true);
                    } else if ("微信单号".equals(DataUtils.getValue(root, eventObj))) {
                        DataUtils.setVisible(root, "HB_HTLS_U_I", false);
                        DataUtils.setVisible(root, "HB_HTRQ_U_I", false);
                        DataUtils.setVisible(root, "HB_WXDH_U_I", true);
                        DataUtils.setVisible(root, "HB_ZFBDDH_I", false);
                    } else if ("后台流水".equals(DataUtils.getValue(root, eventObj))) {
                        DataUtils.setVisible(root, "HB_HTLS_U_I", true);
                        DataUtils.setVisible(root, "HB_HTRQ_U_I", true);
                        DataUtils.setVisible(root, "HB_WXDH_U_I", false);
                        DataUtils.setVisible(root, "HB_ZFBDDH_I", false);
                    }
                }
            } else if (eventObj instanceof Button) {
                if ("refund".equals(((Button) eventObj).getId()))//退货按钮
                {
                    Logger.log("LOG_DEBUG", "click refund");
                    String resoult = ComRefund.exec(DataUtils.getValue(root, "HTRQ_U_O"), DataUtils.getValue(root, "HTLS_U_O"), DataUtils.getValue(root, "JYJE_U_O"));
                    if (!"SUCEESS".equals(resoult)) {
                        WarmingDialog.show(WarmingDialog.Dialog_ERR, resoult);
                    }else {
                        Logger.log("LOG_DEBUG","refund success");
                    }


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

    public void cleanValue() {
        DataUtils.setValue(root, "HTRQ_U_O", "");
        DataUtils.setValue(root, "HTSJ_U_O", "");
        DataUtils.setValue(root, "HTLS_U_O", "");
        DataUtils.setValue(root, "HTJYM__O", "");
        DataUtils.setValue(root, "ZFZHLX_O", "");
        DataUtils.setValue(root, "JYJE_U_O", "");
        DataUtils.setValue(root, "SFDH_U_O", "");
        DataUtils.setValue(root, "SHBH_U_O", "");
        DataUtils.setValue(root, "CZZH_U_O", "");
        DataUtils.setValue(root, "SPXX_U_O", "");
        DataUtils.setValue(root, "FKM_UU_O", "");
        DataUtils.setValue(root, "JYZT_U_O", "");
    }


}
