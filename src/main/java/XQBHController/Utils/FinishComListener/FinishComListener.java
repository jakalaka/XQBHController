package XQBHController.Utils.FinishComListener;

import XQBHController.Controller.Com;
import XQBHController.ControllerAPI.UI.WarmingDialog;
import XQBHController.ControllerUI.Order;
import XQBHController.Utils.log.Logger;

import javax.comm.*;
import java.io.InputStream;

/**
 * Com21EventListener��ʹ�á��¼�����ģʽ����������COM21,
 * ��ͨ��COM21����������������ȡ�ö˿ڽ��յ������ݣ��ڱ������������Դ���COM11����
 * ʹ�á��¼�����ģʽ����������,�����ֶ���һ���¼�������,����ʵ��SerialPortEventListener
 * �ӿڲ���дserialEvent����,��serialEvent�����б�д�����߼���
 */
public class FinishComListener implements SerialPortEventListener {

    //1.�������
    CommPortIdentifier com21 = null;//δ�򿨵Ķ˿�
    static SerialPort serialComPort = null;//�򿪵Ķ˿�
    InputStream inputStream = null;//������
    public  static boolean isClose;
    public  static boolean subIsClose;

    //2.���캯����
    //ʵ�ֳ�ʼ����������ȡ����COM21���򿪴��ڡ���ȡ��������������Ϊ��������¼���������
    public FinishComListener() throws Exception {
            //��ȡ���ڡ��򿪴����ڡ���ȡ���ڵ���������
            com21 = CommPortIdentifier.getPortIdentifier(Com.FinishScannerComName);
            serialComPort = (SerialPort) com21.open("FinishComListener", 1000);
            inputStream = serialComPort.getInputStream();

            //�򴮿�����¼���������
            serialComPort.addEventListener(this);
            //���õ��˿��п�������ʱ�����¼�,�����ñز����١�
            serialComPort.notifyOnDataAvailable(true);

    }

    //��д�̳еļ���������
    @Override
    public void serialEvent(SerialPortEvent event) {
        //�������ڻ���������ݵ�����
        byte[] cache = new byte[1024];
        //��¼�Ѿ����ﴮ��COM21��δ����ȡ�����ݵ��ֽڣ�Byte������
        int availableBytes = 0;
        //��������ݿ��õ�ʱ�䷢��,��������ݵĶ�д
        if(event.getEventType() == SerialPortEvent.DATA_AVAILABLE){
            if (!isClose) {
                isClose=true;
            }
            subIsClose=true;

        }
    }

    //��main�����д������ʵ��
    public static void start() {
        try {
            new FinishComListener();
        } catch (Exception e) {
            Logger.logException("LOG_ERR",e);
            WarmingDialog.show(WarmingDialog.Dialog_ERR, "δ��⵽���ɨ����!");
            return;
        }
//        wirte();
        Closer closer=new Closer();
        Thread t=new Thread(closer);
        t.start();
        Com.FinishScannerState="N";

    }


}

class Closer implements  Runnable{
    @Override
    public void run() {
        while (true)
        {
            if(FinishComListener.subIsClose==false) {
                if(FinishComListener.isClose == true) {
                    if (!Order.finalOut) {
                        Logger.log("LOG_DEBUG", "�ѳ���");
                        Order.finalOut = true;
                    }else {
                        Logger.log("LOG_ERR", "δ��⵽���򼴳���!!!");
                        Com.ZDZT_U="ERR_OutAgain";
                        /*������Ҫ�����Ͽ����ʹ��ĵ�Դ*/

                    }
                }
                FinishComListener.isClose = false;
            }
            FinishComListener.subIsClose=false;
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                Logger.logException("LOG_ERR",e);
                WarmingDialog.show(WarmingDialog.Dialog_ERR, "��FinishComListener�����쳣!");
            }
        }
    }
}