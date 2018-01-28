package XQBHController.Utils.Data;

import XQBHController.ControllerAPI.UI.WarmingDialog;
import XQBHController.Utils.log.Logger;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtils {
    public static String getValue(Node root, Object object) {
        String resoult = "";

        if (object instanceof TextField) {
            resoult = ((TextField) object).getText();
        } else if (object instanceof ComboBox) {
            resoult = ((ComboBox) object).getValue().toString();
        } else if (object instanceof ChoiceBox) {
            resoult = ((ChoiceBox) object).getValue().toString();
        } else if (object instanceof DatePicker) {
            resoult = ((DatePicker) object).getValue().toString().replaceAll("-","");
        } else if (object instanceof String) {
            Node targetNode = getTarget(root, (String) object);
            if (targetNode instanceof TextField) {
                resoult = ((TextField) targetNode).getText();
            } else if (targetNode instanceof ComboBox) {
                resoult = ((ComboBox) targetNode).getValue().toString();
            } else if (targetNode instanceof ChoiceBox) {
                resoult = ((ChoiceBox) targetNode).getValue().toString();
            } else if (targetNode instanceof DatePicker) {
                resoult = ((DatePicker) targetNode).getValue().toString().replaceAll("-","");
            }
        }

        return resoult;
    }

    public static void setValue(Node root, String targetName, String valueString) {
        Node targetNode = getTarget(root, targetName);
        if (targetNode == null) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��" + targetName + "�ֶ�,����ϵ����Ա!");
            return;
        }
        if (targetNode instanceof TextField) {
            ((TextField) targetNode).setText(valueString);
        } else if (targetNode instanceof ChoiceBox) {
            ((ChoiceBox) targetNode).setValue(valueString);
        } else if (targetNode instanceof ComboBox) {
            ((ComboBox) targetNode).setValue(valueString);
        } else if (targetNode instanceof Label) {
            ((Label) targetNode).setText(valueString);
        } else {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "�����" + targetName + "���ʹ���,����ϵ����Ա!");
            return;
        }


        return;
    }

    public static void setVisible(Node root, String targetName, boolean flg) {
        Node targetNode = getTarget(root, targetName);
        if (targetNode == null) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��" + targetName + "�ֶ�,����ϵ����Ա!");
            return;
        }
        targetNode.setVisible(flg);
    }

    public static void setEnable(Node node, String targetName, boolean flg) {
        Node targetNode = getTarget(node, targetName);
        if (targetNode == null) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "��" + targetName + "�ֶ�,����ϵ����Ա!");
            return;
        }
        if (targetNode instanceof TextField) {
            ((TextField) targetNode).setEditable(false);
        } else {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "�����" + targetName + "���ʹ���,����ϵ����Ա!");
            return;
        }

    }

    public static Node getTarget(Node root, String nodeName) {
        if (nodeName.equals(root.getId()))
            return root;
        else {
            if (root instanceof Pane) {
                for (Node node_ : ((Pane) root).getChildren()
                        ) {
                    Node resoult = getTarget(node_, nodeName);
                    if (resoult != null)
                        return resoult;
                }
            }
        }
        return null;

    }



    public static String Date2StringofYear(Date date) {
        String resoult = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//�������ڸ�ʽ
        resoult = df.format(date);
        return resoult;
    }

    public static String Date2StringofTime(Date date) {
        String resoult = "";
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//�������ڸ�ʽ
        resoult = df.format(date);
        return resoult;
    }

    public static String getListMean(String ListName, String Value) {
        File file = new File("src/main/java/resources/list/" + ListName + ".lst");
        if (!file.exists()) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "�Ҳ����б��ļ�" + file.getAbsolutePath());
            return "";
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
            while ((tempString = reader.readLine()) != null) {
                // ��ʾ�к�
                if (Value.equals(tempString.split("-")[0]))
                    return tempString.split("-")[1];
                line++;
            }
            reader.close();
        } catch (IOException e) {
            Logger.logException("LOG_ERR", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Logger.logException("LOG_ERR", e);
                }
            }
        }

        return "";

    }

    public static String getListValue(String ListName, String Mean) {
        File file = new File("src/main/java/resources/list/" + ListName + ".lst");
        if (!file.exists()) {
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "�Ҳ����б��ļ�" + file.getAbsolutePath());
            return "";
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
            while ((tempString = reader.readLine()) != null) {
                // ��ʾ�к�
                if (Mean.equals(tempString.split("-")[1]))
                    return tempString.split("-")[0];
                line++;
            }
            reader.close();
        } catch (IOException e) {
            Logger.logException("LOG_ERR", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Logger.logException("LOG_ERR", e);
                }
            }
        }

        return "";

    }


}
