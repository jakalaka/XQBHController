package XQBHController.ControllerUI.ControllerTranUI;

import XQBHController.Controller.Com;
import XQBHController.Controller.ComCall;
import XQBHController.ControllerAPI.UI.WarmingDialog;
import XQBHController.Utils.Data.DataUtils;
import XQBHController.Utils.log.Logger;
import com.sun.javafx.scene.control.skin.TableViewSkin;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javax.security.auth.callback.Callback;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class QueryStatement implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private TableView tableView;

    @FXML
    private ComboBox SHBH_U_I;

    @FXML
    private ComboBox ZDBH_U_I;
    @FXML
    private DatePicker QSRQ_U_I;
    @FXML
    private DatePicker ZZRQ_U_I;
    @FXML
    private CheckBox cb_all;
    @FXML
    private CheckBox cb_1;
    @FXML
    private CheckBox cb_w;
    @FXML
    private CheckBox cb_c;
    @FXML
    private CheckBox cb_t;
    @FXML
    private CheckBox cb_b;
    @FXML
    private CheckBox cb_u;


    static boolean initFlg = false;


    public void initialize(URL location, ResourceBundle resources) {

        addListener(root, eventHandler);

        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            // Do whatever you want
            double xhWidth=35;
            double perfcolWidth = 75;
            List<TableColumn> list = tableView.getColumns();
            BigDecimal tableColNum = new BigDecimal(list.size());
            BigDecimal windowWidth = new BigDecimal(newVal.floatValue()).subtract(new BigDecimal(xhWidth));
            BigDecimal actcolWidth = windowWidth.divide(tableColNum.subtract(new BigDecimal(1)), 2, BigDecimal.ROUND_HALF_UP);
            list.get(0).setPrefWidth(xhWidth);
            if (actcolWidth.doubleValue() > perfcolWidth) {
//                for (TableColumn tableColumn : list) {
//
//                    tableColumn.setPrefWidth(actcolWidth.doubleValue());
//                }
                for (int i =1;i<list.size();i++)
                {
                    list.get(i).setPrefWidth(actcolWidth.doubleValue());
                }
            } else {

                for (int i =1;i<list.size();i++)
                {
                    list.get(i).setPrefWidth(perfcolWidth);
                }
            }
            if (!initFlg) {
                //加载商户信息和终端信息
//                if (Com.listSH_ZDXX.size() > 1) {
                ZDBH_U_I.getItems().add("");
                Logger.log("LOG_DEBUG", "ZDBH_U_I ADD \"\"");
//                }
                for (Map map :
                        Com.listSH_ZDXX) {
                    ZDBH_U_I.getItems().add(DataUtils.getValue(map,"ZDBH_U"));
                    Logger.log("LOG_DEBUG", "ZDBH_U_I ADD " + DataUtils.getValue(map,"ZDBH_U"));
                }
                ZDBH_U_I.setValue(ZDBH_U_I.getItems().get(0));


//                if (Com.listKH_SHXX.size() > 1) {
                Logger.log("LOG_DEBUG", "SHBH_U_I ADD \"\"");
                SHBH_U_I.getItems().add("");
//                }
                for (Map map :
                        Com.listKH_SHXX) {
                    SHBH_U_I.getItems().add(DataUtils.getValue(map,"SHBH_U"));
                    Logger.log("LOG_DEBUG", "SHBH_U_I ADD " + DataUtils.getValue(map,"SHBH_U"));

                }
                SHBH_U_I.setValue(SHBH_U_I.getItems().get(0));


                QSRQ_U_I.setValue(LocalDate.now());
                ZZRQ_U_I.setValue(LocalDate.now());

                initFlg = true;
            }


        });


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
                        WarmingDialog.show(WarmingDialog.Dialog_INPUTERR, "必须选择起始日期");
                        return;
                    }

                    String sZZRQ_U = DataUtils.getValue(root, "ZZRQ_U_I");
                    if (sZZRQ_U.length() <= 0) {
                        WarmingDialog.show(WarmingDialog.Dialog_INPUTERR, "必须选择终止日期");
                        return;
                    }

                    Map In = new HashMap();
                    Map Out = new HashMap();
                    In.put("QSRQ_U", sQSRQ_U);
                    In.put("ZZRQ_U", sZZRQ_U);
//                    In.put("JYZT_U", DataUtils.getListValue("JYZT_U", DataUtils.getValue(root, "JYZT_U_I")));
                    String sJYZT_U = "";
                    if (cb_1.isSelected())
                        sJYZT_U += "1,";
                    if (cb_w.isSelected())
                        sJYZT_U += "w,";
                    if (cb_c.isSelected())
                        sJYZT_U += "c,";
                    if (cb_t.isSelected())
                        sJYZT_U += "t,";
                    if (cb_b.isSelected())
                        sJYZT_U += "b,";
                    if (cb_u.isSelected())
                        sJYZT_U += "u,";
                    if (sJYZT_U.equals("")) {
                        WarmingDialog.show(WarmingDialog.Dialog_INPUTERR, "必须选择交易状态");
                        return;
                    }
                    sJYZT_U = sJYZT_U.substring(0, sJYZT_U.length() - 1);
                    In.put("JYZT_U", sJYZT_U);


                    In.put("SHBH_U", DataUtils.getValue(root, "SHBH_U_I"));
                    In.put("ZDBH_U", DataUtils.getValue(root, "ZDBH_U_I"));
                    In.put("ZFZHLX", DataUtils.getListValue("ZFZHLX", DataUtils.getValue(root, "ZFZHLX_I")));
                    if (false == ComCall.Call("ControllerStatementQuery", "ControllerStatementQuery", In, Out)) {
                        WarmingDialog.show(WarmingDialog.Dialog_ERR, Out.get("CWXX_U").toString());
                        return;
                    }
                    List<Map> listResoult = (List) Out.get("RELIST");

                    ObservableList<tableInfo> data =
                            FXCollections.observableArrayList(
                            );
                    int iNum = 0;
                    for (Map map :
                            listResoult) {
                        iNum++;
                        data.add(new tableInfo(iNum + "",
                                DataUtils.getValue(map,"HTRQ_U"),
                                DataUtils.getValue(map,"HTSJ_U"),
                                DataUtils.getValue(map,"HTLS_U"),
                                DataUtils.getValue(map, "JYJE_U"),
                                DataUtils.getListMean("ZFZHLX", DataUtils.getValue(map, "ZFZHLX")),
                                DataUtils.getValue(map, "SFDH_U"),
                                DataUtils.getValue(map, "KHBH_U"),
                                DataUtils.getValue(map, "SHBH_U"),
                                DataUtils.getValue(map, "ZDBH_U"),
                                DataUtils.getValue(map, "SPXX_U"),
                                DataUtils.getListMean("JYZT_U", DataUtils.getValue(map, "JYZT_U")),
                                DataUtils.getValue(map, "YTHJE_"),
                                DataUtils.getValue(map, "YHTRQ_"),
                                DataUtils.getValue(map, "YHTLS_")));
                    }
                    Logger.log("LOG_DEBUG", "iNum=" + iNum);

                    ((TableColumn) tableView.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<>("XH_UUU_O"));
                    ((TableColumn) tableView.getColumns().get(0)).setCellFactory(TextFieldTableCell.forTableColumn());
                    ((TableColumn) tableView.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<>("HTRQ_U_O"));
                    ((TableColumn) tableView.getColumns().get(1)).setCellFactory(TextFieldTableCell.forTableColumn());
                    ((TableColumn) tableView.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<>("HTSJ_U_O"));
                    ((TableColumn) tableView.getColumns().get(2)).setCellFactory(TextFieldTableCell.forTableColumn());
                    ((TableColumn) tableView.getColumns().get(3)).setCellValueFactory(new PropertyValueFactory<>("HTLS_U_O"));
                    ((TableColumn) tableView.getColumns().get(3)).setCellFactory(TextFieldTableCell.forTableColumn());
                    ((TableColumn) tableView.getColumns().get(4)).setCellValueFactory(new PropertyValueFactory<>("HTJYM__O"));
                    ((TableColumn) tableView.getColumns().get(4)).setCellFactory(TextFieldTableCell.forTableColumn());
                    ((TableColumn) tableView.getColumns().get(5)).setCellValueFactory(new PropertyValueFactory<>("ZFZHLX_O"));
                    ((TableColumn) tableView.getColumns().get(5)).setCellFactory(TextFieldTableCell.forTableColumn());
                    ((TableColumn) tableView.getColumns().get(6)).setCellValueFactory(new PropertyValueFactory<>("JYJE_U_O"));
                    ((TableColumn) tableView.getColumns().get(6)).setCellFactory(TextFieldTableCell.forTableColumn());
                    ((TableColumn) tableView.getColumns().get(7)).setCellValueFactory(new PropertyValueFactory<>("SHBH_U_O"));
                    ((TableColumn) tableView.getColumns().get(7)).setCellFactory(TextFieldTableCell.forTableColumn());
                    ((TableColumn) tableView.getColumns().get(8)).setCellValueFactory(new PropertyValueFactory<>("CZZH_U_O"));
                    ((TableColumn) tableView.getColumns().get(8)).setCellFactory(TextFieldTableCell.forTableColumn());
                    ((TableColumn) tableView.getColumns().get(9)).setCellValueFactory(new PropertyValueFactory<>("SPXX_U_O"));
                    ((TableColumn) tableView.getColumns().get(9)).setCellFactory(TextFieldTableCell.forTableColumn());
                    ((TableColumn) tableView.getColumns().get(10)).setCellValueFactory(new PropertyValueFactory<>("FKM_UU_O"));
                    ((TableColumn) tableView.getColumns().get(10)).setCellFactory(TextFieldTableCell.forTableColumn());
                    ((TableColumn) tableView.getColumns().get(11)).setCellValueFactory(new PropertyValueFactory<>("JYZT_U_O"));
                    ((TableColumn) tableView.getColumns().get(11)).setCellFactory(TextFieldTableCell.forTableColumn());
                    ((TableColumn) tableView.getColumns().get(12)).setCellValueFactory(new PropertyValueFactory<>("YTHJE__O"));
                    ((TableColumn) tableView.getColumns().get(12)).setCellFactory(TextFieldTableCell.forTableColumn());
                    ((TableColumn) tableView.getColumns().get(13)).setCellValueFactory(new PropertyValueFactory<>("YHTRQ__O"));
                    ((TableColumn) tableView.getColumns().get(13)).setCellFactory(TextFieldTableCell.forTableColumn());
                    ((TableColumn) tableView.getColumns().get(14)).setCellValueFactory(new PropertyValueFactory<>("YHTLS__O"));
                    ((TableColumn) tableView.getColumns().get(14)).setCellFactory(TextFieldTableCell.forTableColumn());

                    tableView.setItems(data);

                    tableView.setFixedCellSize(25);
                    tableView.prefHeightProperty().bind(Bindings.size(tableView.getItems()).multiply(tableView.getFixedCellSize() + 1).add(30));


                }

            }
        }


    };

    @FXML
    public void commitHandler(TableColumn.CellEditEvent<tableInfo, String> t) {

        t.getTableColumn().getProperties().put("StringProperty", t.getOldValue());
        t.getTableView().refresh();
    }

    @FXML
    public void selectAll() {
        if (cb_all.isSelected()) {
            cb_1.setSelected(true);
            cb_w.setSelected(true);
            cb_c.setSelected(true);
            cb_t.setSelected(true);
            cb_b.setSelected(true);
            cb_u.setSelected(true);
        } else {
            cb_1.setSelected(false);
            cb_w.setSelected(false);
            cb_c.setSelected(false);
            cb_t.setSelected(false);
            cb_b.setSelected(false);
            cb_u.setSelected(false);
        }


    }


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

        public String getXH_UUU_O() {
            return XH_UUU_O.get();
        }

        public SimpleStringProperty XH_UUU_OProperty() {
            return XH_UUU_O;
        }

        public String getYTHJE__O() {
            return YTHJE__O.get();
        }

        public SimpleStringProperty YTHJE__OProperty() {
            return YTHJE__O;
        }

        public String getYHTRQ__O() {
            return YHTRQ__O.get();
        }

        public SimpleStringProperty YHTRQ__OProperty() {
            return YHTRQ__O;
        }

        public String getYHTLS__O() {
            return YHTLS__O.get();
        }

        public SimpleStringProperty YHTLS__OProperty() {
            return YHTLS__O;
        }

        private final SimpleStringProperty XH_UUU_O;
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
        private final SimpleStringProperty YTHJE__O;
        private final SimpleStringProperty YHTRQ__O;
        private final SimpleStringProperty YHTLS__O;

        public tableInfo(String xh_uuu_o, String htrq_u_o, String htsj_u_o, String htls_u_o, String htjym__o, String zfzhlx_o, String jyje_u_o, String shbh_u_o, String czzh_u_o, String spxx_u_o, String fkm_uu_o, String jyzt_u_o, String ythje__o, String yhtrq__o, String yhtls__o) {
            XH_UUU_O = new SimpleStringProperty(xh_uuu_o);
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
            YTHJE__O = new SimpleStringProperty(ythje__o);
            YHTRQ__O = new SimpleStringProperty(yhtrq__o);
            YHTLS__O = new SimpleStringProperty(yhtls__o);
        }


    }


}