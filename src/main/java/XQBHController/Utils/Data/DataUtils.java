package XQBHController.Utils.Data;

import XQBHController.ControllerAPI.UI.WarmingDialog;
import XQBHController.Utils.log.Logger;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DataUtils {
    public static String getValue(Node root, Object object) {
        String resoult = "";

        if (object instanceof TextField) {
            resoult = ((TextField) object).getText();
        } else if (object instanceof ComboBox) {
            resoult = ((ComboBox) object).getValue().toString();
        } else if (object instanceof String) {
            HBox hBox = getHBox(root, (String) object);
            if (hBox.getChildren().size() < 2)
                return resoult;
            if (hBox.getChildren().get(1) instanceof TextField) {
                resoult = ((TextField) hBox.getChildren().get(1)).getText();
            } else if (hBox.getChildren().get(1) instanceof DatePicker) {
                if (((DatePicker) hBox.getChildren().get(1)).getValue() == null)
                    resoult = "";
                else
                    resoult = ((DatePicker) hBox.getChildren().get(1)).getValue().toString().replaceAll("-", "");

            } else if (hBox.getChildren().get(1) instanceof ComboBox) {

                resoult = ((ComboBox) hBox.getChildren().get(1)).getValue().toString();
            }
        }

        return resoult;
    }

    public static void setValue(Node node, String hboxName, String string) {
        HBox hBox = getHBox(node, hboxName);
        if (hBox == null) {
            WarmingDialog.show("无字段错误", "无" + hboxName + "字段,请联系管理员!");
            return;
        }
       for (Node node_:hBox.getChildren())
       {
           if (node_ instanceof TextField)
           {
               ((TextField) node_).setText(string);
           }
       }


        return;
    }

    public static void setVisible(Node node, String hboxName, boolean flg) {
        HBox hBox = getHBox(node, hboxName);
        if (hBox == null) {
            WarmingDialog.show("无字段错误", "无" + hboxName + "字段,请联系管理员!");
            return;
        }
        hBox.setVisible(flg);
        hBox.getChildren().get(0).setVisible(flg);
        hBox.getChildren().get(1).setVisible(flg);

    }

    public static void setEnable(Node node, String hboxName, boolean flg) {
        HBox hBox = getHBox(node, hboxName);
        if (hBox == null) {
            WarmingDialog.show("无字段错误", "无" + hboxName + "字段,请联系管理员!");
            return;
        }
        Node node_ = hBox.getChildren().get(1);
        if (node_ instanceof TextField) {
            ((TextField) node_).setEditable(false);
        }
    }

    private static HBox getHBox(Node node, String hboxName) {
        if (node instanceof HBox) {

            if (hboxName.equals(node.getId()))
                return (HBox) node;
            else {
                for (Node node_ : ((Pane) node).getChildren()
                        ) {
                    HBox hbox = getHBox(node_, hboxName);
                    if (hbox != null)
                        return hbox;
                }
            }

        } else if (node instanceof Pane) {
            for (Node node_ : ((Pane) node).getChildren()
                    ) {
                HBox hbox = getHBox(node_, hboxName);
                if (hbox != null)
                    return hbox;
            }
        } else {//其他控件停止查询

        }

//
        return null;

    }

    public static String getID(Object object) {
        String resoult = "";

        if (object instanceof TextField) {
            resoult = ((HBox) ((TextField) object).getParent()).getId();
        } else if (object instanceof ComboBox) {
            resoult = ((HBox) ((ComboBox) object).getParent()).getId();
        } else if (object instanceof Button) {

        }

        return resoult;
    }

    public static String Date2StringofYear(Date date) {
        String resoult = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        resoult = df.format(date);
        return resoult;
    }

    public static String Date2StringofTime(Date date) {
        String resoult = "";
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
        resoult = df.format(date);
        return resoult;
    }

    public static String getListMean(String ListName, String Value) {
        File file = new File("src/main/java/resources/list/" + ListName+".lst");
        if (!file.exists()) {
            WarmingDialog.show("错误", "找不到列表文件" + file.getAbsolutePath());
            return "";
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                if (Value.equals(tempString.split("-")[0]))
                    return tempString.split("-")[1];
                line++;
            }
            reader.close();
        } catch (IOException e) {
            Logger.logException("LOG_ERR",e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Logger.logException("LOG_ERR",e);
                }
            }
        }

        return "";

    }
    public static String getListValue(String ListName, String Mean) {
        File file = new File("src/main/java/resources/list/" + ListName+".lst");
        if (!file.exists()) {
            WarmingDialog.show("错误", "找不到列表文件" + file.getAbsolutePath());
            return "";
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                if (Mean.equals(tempString.split("-")[1]))
                    return tempString.split("-")[0];
                line++;
            }
            reader.close();
        } catch (IOException e) {
            Logger.logException("LOG_ERR",e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Logger.logException("LOG_ERR",e);
                }
            }
        }

        return "";

    }


}
