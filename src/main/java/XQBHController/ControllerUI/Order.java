package XQBHController.ControllerUI;

public class Order {
    public static String HTLS_U;
    public static String HTRQ_U;
    public static String SPMC_U;
    public static double JYJE_U;
    public static String QRCODE;
    public static boolean finalOut=true;
    public static String callStatus;
    public static boolean outFail;

    public static String controllerIP;
    public static int controllerPort;
    public static int controllerAdress;



    public static void Init(){
        SPMC_U="";
        JYJE_U=0;
        QRCODE="";
        finalOut=true;
        callStatus ="FAIL";
        outFail=false;
        controllerIP="";
        controllerPort=9999;
        controllerAdress=9999;

    }
}
