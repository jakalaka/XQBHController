package XQBHController.FreeTest;

import XQBHController.Utils.PropertiesHandler.PropertiesHandler;

import java.io.File;

public class propertiesTest {
    public static void main(String[] args) {
        PropertiesHandler.writeProperties(new File("resources/Info/sysInfotest.properties"),"ZD0000000002" + "LS","20180204"+"-"+1);

    }
}
