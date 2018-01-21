package XQBHController.ControllerUI.ControllerTranUI;

import XQBHController.Controller.Com;
import XQBHController.Controller.ComCall;
import XQBHController.ControllerAPI.UI.WarmingDialog;
import XQBHController.Utils.Data.DataUtils;
import XQBHController.Utils.log.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class QueryStatement implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private TableView tableView;

    @FXML
    private ComboBox SHBH_U_CB_I;

    @FXML
    private ComboBox ZDBH_U_CB_I;

    @FXML
    private TableColumn HTRQ_U_GRD_O;

    static boolean initFlg = false;


    public void initialize(URL location, ResourceBundle resources) {

        addListener(root, eventHandler);

        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            // Do whatever you want
            double colminWidth = 75;
            List<TableColumn> list = tableView.getColumns();
            BigDecimal aa = new BigDecimal(list.size());
            BigDecimal bb = new BigDecimal(newVal.floatValue());
            BigDecimal cc = bb.divide(aa, 2, BigDecimal.ROUND_HALF_UP);
            if (cc.doubleValue() > colminWidth) {
                for (TableColumn tableColumn : list) {
                    tableColumn.setPrefWidth(cc.doubleValue());
                }
            } else {
                for (TableColumn tableColumn : list) {
                    tableColumn.setPrefWidth(colminWidth);
                }
            }
            if (!initFlg) {
                //加载商户信息和终端信息
                if (Com.listZDXX_U.size() > 1) {
                    ZDBH_U_CB_I.getItems().add("");
                    Logger.log("LOG_DEBUG", "ZDBH_U_CB_I ADD \"\"");
                }
                for (String string :
                        Com.listZDXX_U) {
                    ZDBH_U_CB_I.getItems().add(string);
                    Logger.log("LOG_DEBUG", "ZDBH_U_CB_I ADD " + string);
                }
                ZDBH_U_CB_I.setValue(ZDBH_U_CB_I.getItems().get(0));


                if (Com.listSHXX_U.size() > 1) {
                    Logger.log("LOG_DEBUG", "SHBH_U_CB_I ADD \"\"");
                    SHBH_U_CB_I.getItems().add("");
                }
                for (String string :
                        Com.listSHXX_U) {
                    SHBH_U_CB_I.getItems().add(string);
                    Logger.log("LOG_DEBUG", "SHBH_U_CB_I ADD " + string);

                }
                SHBH_U_CB_I.setValue(SHBH_U_CB_I.getItems().get(0));
                initFlg = true;
            }


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
            if (eventObj instanceof Button) {
                if (((Button) eventObj).getId().equals("OK")) {

                    /*输入下合法性检查*/
                    String sQSRQ_U = DataUtils.getValue(root, "QSRQ_U_I");
                    if (sQSRQ_U.length() <= 0) {
                        WarmingDialog.show("查询出错", "必须选择起始日期");
                        return;
                    }

                    String sZZRQ_U = DataUtils.getValue(root, "ZZRQ_U_I");
                    if (sZZRQ_U.length() <= 0) {
                        WarmingDialog.show("查询出错", "必须选择终止日期");
                        return;
                    }

                    Map In = new HashMap();
                    Map Out = new HashMap();
                    In.put("QSRQ_U", sQSRQ_U);
                    In.put("ZZRQ_U", sZZRQ_U);
                    In.put("JYZT_U", DataUtils.getListValue("JYZT_U", DataUtils.getValue(root, "JYZT_U_I")));
                    In.put("SHBH_U", DataUtils.getValue(root, "SHBH_U_I"));
                    In.put("ZDBH_U", DataUtils.getValue(root, "ZDBH_U_I"));
                    In.put("ZFZHLX", DataUtils.getListValue("ZFZHLX", DataUtils.getValue(root, "ZFZHLX_I")));
                    if (false == ComCall.Call("ControllerStatementQuery", "ControllerStatementQuery", In, Out)) {
                        WarmingDialog.show("查询出错", Out.get("CWXX_U").toString());
                        return;
                    }
                    List<Map> listResoult = (List) Out.get("RELIST");


                    ObservableList<tableInfo> data =
                            FXCollections.observableArrayList(
                            );

                    for (Map map :
                            listResoult) {
                        data.add(new tableInfo(map.get("HTRQ_U").toString(),map.get("HTSJ_U").toString(),map.get("HTLS_U").toString(),map.get("JYJE_U").toString(),DataUtils.getListMean("ZFZHLX",map.get("ZFZHLX").toString()),map.get("SFDH_U").toString(),map.get("KHBH_U").toString(),map.get("SHBH_U").toString(),map.get("ZDBH_U").toString(),map.get("SPXX_U").toString(),DataUtils.getListMean("JYZT_U",map.get("JYZT_U").toString())));
                    }
                    ((TableColumn)tableView.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<>("HTRQ_U_O"));
                    ((TableColumn)tableView.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<>("HTSJ_U_O"));
                    ((TableColumn)tableView.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<>("HTLS_U_O"));
                    ((TableColumn)tableView.getColumns().get(3)).setCellValueFactory(new PropertyValueFactory<>("HTJYM__O"));
                    ((TableColumn)tableView.getColumns().get(4)).setCellValueFactory(new PropertyValueFactory<>("ZFZHLX_O"));
                    ((TableColumn)tableView.getColumns().get(5)).setCellValueFactory(new PropertyValueFactory<>("JYJE_U_O"));
                    ((TableColumn)tableView.getColumns().get(6)).setCellValueFactory(new PropertyValueFactory<>("SHBH_U_O"));
                    ((TableColumn)tableView.getColumns().get(7)).setCellValueFactory(new PropertyValueFactory<>("CZZH_U_O"));
                    ((TableColumn)tableView.getColumns().get(8)).setCellValueFactory(new PropertyValueFactory<>("SPXX_U_O"));
                    ((TableColumn)tableView.getColumns().get(9)).setCellValueFactory(new PropertyValueFactory<>("FKM_UU_O"));
                    ((TableColumn)tableView.getColumns().get(10)).setCellValueFactory(new PropertyValueFactory<>("JYZT_U_O"));

                    tableView.setItems(data);




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

    }
    public static class tableInfo {

        public String getHTRQ_U_O() {
            return HTRQ_U_O.get();
        }

        public SimpleStringProperty HTRQ_U_OProperty() {
            return HTRQ_U_O;
        }

        public void setHTRQ_U_O(String HTRQ_U_O) {
            this.HTRQ_U_O.set(HTRQ_U_O);
        }

        public String getHTSJ_U_O() {
            return HTSJ_U_O.get();
        }

        public SimpleStringProperty HTSJ_U_OProperty() {
            return HTSJ_U_O;
        }

        public void setHTSJ_U_O(String HTSJ_U_O) {
            this.HTSJ_U_O.set(HTSJ_U_O);
        }

        public String getHTLS_U_O() {
            return HTLS_U_O.get();
        }

        public SimpleStringProperty HTLS_U_OProperty() {
            return HTLS_U_O;
        }

        public void setHTLS_U_O(String HTLS_U_O) {
            this.HTLS_U_O.set(HTLS_U_O);
        }

        public String getHTJYM__O() {
            return HTJYM__O.get();
        }

        public SimpleStringProperty HTJYM__OProperty() {
            return HTJYM__O;
        }

        public void setHTJYM__O(String HTJYM__O) {
            this.HTJYM__O.set(HTJYM__O);
        }

        public String getZFZHLX_O() {
            return ZFZHLX_O.get();
        }

        public SimpleStringProperty ZFZHLX_OProperty() {
            return ZFZHLX_O;
        }

        public void setZFZHLX_O(String ZFZHLX_O) {
            this.ZFZHLX_O.set(ZFZHLX_O);
        }

        public String getJYJE_U_O() {
            return JYJE_U_O.get();
        }

        public SimpleStringProperty JYJE_U_OProperty() {
            return JYJE_U_O;
        }

        public void setJYJE_U_O(String JYJE_U_O) {
            this.JYJE_U_O.set(JYJE_U_O);
        }

        public String getSHBH_U_O() {
            return SHBH_U_O.get();
        }

        public SimpleStringProperty SHBH_U_OProperty() {
            return SHBH_U_O;
        }

        public void setSHBH_U_O(String SHBH_U_O) {
            this.SHBH_U_O.set(SHBH_U_O);
        }

        public String getCZZH_U_O() {
            return CZZH_U_O.get();
        }

        public SimpleStringProperty CZZH_U_OProperty() {
            return CZZH_U_O;
        }

        public void setCZZH_U_O(String CZZH_U_O) {
            this.CZZH_U_O.set(CZZH_U_O);
        }

        public String getSPXX_U_O() {
            return SPXX_U_O.get();
        }

        public SimpleStringProperty SPXX_U_OProperty() {
            return SPXX_U_O;
        }

        public void setSPXX_U_O(String SPXX_U_O) {
            this.SPXX_U_O.set(SPXX_U_O);
        }

        public String getFKM_UU_O() {
            return FKM_UU_O.get();
        }

        public SimpleStringProperty FKM_UU_OProperty() {
            return FKM_UU_O;
        }

        public void setFKM_UU_O(String FKM_UU_O) {
            this.FKM_UU_O.set(FKM_UU_O);
        }

        public String getJYZT_U_O() {
            return JYZT_U_O.get();
        }

        public SimpleStringProperty JYZT_U_OProperty() {
            return JYZT_U_O;
        }

        public void setJYZT_U_O(String JYZT_U_O) {
            this.JYZT_U_O.set(JYZT_U_O);
        }

        private final SimpleStringProperty HTRQ_U_O;
        private final SimpleStringProperty HTSJ_U_O;
        private final SimpleStringProperty HTLS_U_O;
        private final SimpleStringProperty HTJYM__O;
        private final SimpleStringProperty ZFZHLX_O;
        private final SimpleStringProperty JYJE_U_O;
        private final SimpleStringProperty SHBH_U_O;
        private final SimpleStringProperty CZZH_U_O;
        private final SimpleStringProperty SPXX_U_O;
        private final SimpleStringProperty FKM_UU_O;
        private final SimpleStringProperty JYZT_U_O;

        public tableInfo(String htrq_u_o, String htsj_u_o, String htls_u_o, String htjym__o, String zfzhlx_o, String jyje_u_o, String shbh_u_o, String czzh_u_o, String spxx_u_o, String fkm_uu_o, String jyzt_u_o) {
            HTRQ_U_O = new SimpleStringProperty(htrq_u_o);
            HTSJ_U_O = new SimpleStringProperty(htsj_u_o);
            HTLS_U_O = new SimpleStringProperty(htls_u_o);
            HTJYM__O = new SimpleStringProperty(htjym__o);
            ZFZHLX_O = new SimpleStringProperty(zfzhlx_o);
            JYJE_U_O = new SimpleStringProperty(jyje_u_o);
            SHBH_U_O = new SimpleStringProperty(shbh_u_o);
            CZZH_U_O = new SimpleStringProperty(czzh_u_o);
            SPXX_U_O = new SimpleStringProperty(spxx_u_o);
            FKM_UU_O = new SimpleStringProperty(fkm_uu_o);
            JYZT_U_O = new SimpleStringProperty(jyzt_u_o);
        }
    }


}