package XQBHController.FreeTest;

import XQBHController.Controller.ControllerInit;
import XQBHController.ControllerTran.ZDLogin;

public class loopLogin {
    public static void main(String[] args) {
        if (false == ControllerInit.Init())
            return;
        int i = 0;
        while (true) {
            ZDLogin.exec();
            i++;
            System.out.println("i= " +i);
        }
    }
}
