package XQBHController.Controller;

import XQBHController.Utils.PropertiesHandler.PropertiesHandler;
import XQBHController.Utils.log.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import static XQBHController.Utils.PropertiesHandler.PropertiesHandler.readKeyFromXML;

/**
 * Created by Administrator on 2017/7/1 0001.
 */
public class Com {

    /**
     * 日志等级
     */
    public static String LogLV = "";
    /**
     * 终端编号
     */
    static String ZDBH_U = "";

    /**
     * 终端校验码
     */
    static String ZDJYM_ = "";

    /**
     * 终端状态,用于控制异常情况
     */
    public static String ZDZT_U = "OK";


    public static String PowerControlRelayIP;
    public static int PowerControlPort;
    public static int PowerControlAdress;
    public static String QRReaderComName;
    public static String FinishScannerComName;
    public static String FinishScannerState = "E";

    public static final String upPrivateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCEar5WY48F5InUFQdudLWKI9V2teyapeDXuoiJX/t1x9/87Buxdtu/PvvI3nSJk016IIbhGP9MrmhNCBONsuKQB8S08tE4YeHmYfd7J58di24gRNRcay1ebr7EwUcW+4f3XKM6hUfQLnl/omkuFzLfv+ndbNYZu9bIRWhhazpUG3xtZh3+uodp0emT70gj7+p7VLojRreoZ204ylK5yNJKjkRl3oHJVvlXlW/dgRwXY0hJzeC57Q7YieGJ0QDkt8w1oyb5lWajp35FoCmcvwHxty9mmfnWXt3qURn8ZtJo95kYxCrnFm92jdIVr3YaZ1fQ6adVLsJUagd0s6emQh6vAgMBAAECggEAb0hZ/7YZy4T+RAsMPMq+ioKE8gf/+ROwuvwbpP/SD3DTj/ZJa8IM+VOQPIaff8MYmKtfTys32xSzuRExhaMxfoPYz41FQVIZjAkG+CwbL7Qu79WIdsbn0PXXQvl/qhPnd34V+6do688y8o3mQQLkEWByxVCjOes/nP2ftEduNKHIWCRASCtQJ5pGcgEOp3BipEXRzBPklVAbUUYTNTDXKzJxrubH90HTXjOuKXORDBk4UTiTI8cWdAg0KDxnpagAKBC9dmAloXajDzysfbgX1bhq9LlFqR10+A4YkzpTAbL0V+Xh7ZCTl25hhy+GaKpd/RSBF+ERb6o0XgVR9Elc0QKBgQDwReY1zT9ClDkYhRmGj/O95pWo/7Opkl5+p0y/NUL/pChHJ4J1GaiatXQTmFkUGa7J6CO9NTZ3zdJ7ZcAm95GyI7LXaaIfm35ZdN6fYiPSu7sys1amW7f3anF6GHKzRQRYUZgbhwPaGvS1HFUfnYDjl21CqtSbgW9BWmBwikosGQKBgQCNFZHDRtAxEkB4nSCwU68U1yfHEqz0mZSZDn0HugfeX0aiVe13/G0FHdsxI/Anou141/i8Qrb19Xn5aqTHwJNmvTlbXictbEmacHWOGoNvXSznkdG9fWE7BY4yguTLxLndD0fdjQasp1tKMTG04k64bFLonxgG9oxMkv1VZc96BwKBgHFoPKmGT+aH+Y8GO68UwPIQJPGYh19xU6KqKoJRjGcHP2+eSWgmDTvAi6I4FUt0d9ia9kt3E1dm0YMm2pRJ4/3V9bLRDBGpHfDxRaaq9sefjlL27N4mimWAW0FKytCssclR8d6EUqAeewQE9HSwrcY+kfaWlTU02aNaGgzkaO/5AoGAV7CWXsd+02FCzTTsgmwhIFTylltXQNjMca19rPXFukOBxZie9rrgkBOUj6CEvj4YV8n1Ah59Vbbzz0CnlrhtZagrJE0LEMKDpQhNKLv2AZvqMyyBLsPlUSgMz/xndPebhnje9CeZhGqo5R5ahNE8mIhLp+ZqqrlHTrj12MRlBrUCgYBbBWiHuNbQ3aR1e5nBi1zQdQPduv8y+uJiBdMeyQO1UahracwTZCIXvYL7ZIxvqhi81fMcwu3/65Aw+bOTHo1BanDSzICdOH1SSNzlzskfHmmXthIjSB2gsjryA2Smt8wBmZNDrkv0V0QsbqKXC5eGL5X2KRp5MK/c1RVdScvlOg==";
    public static final String rePublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl6R10WXCdCwDdhozSVVsYVCkI6vnrIgGofR2y7J+d2/x7sRoPuHf7z7nMEfr/tlXA3ZH6PwWKOM2+7Kr0B6zMV4HYYywDmM7DxxBGnh2pxQKbTXcHxZHRFNiYeDKt8guT/QE3+g5UuTE5ICoYrvYhGV7PbADrBKaTMw8d+oxe0Y7m1sURwnw7v0AZrk0PcBxY4qaokEiG48Z0gtzuvOBLfsOEFfqrcBT7hp8c+GXUlwfV3x7HZDkvXUWPcEpazYje6KLjeGkD4EambQGDHh7IKJiIKZXCqITYGv778/hLtktDGfcyQPke+yQLfMQFNtu7O3zU+iFrVYbfXVU9pgZeQIDAQAB";


    public static final String getIn = ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>";

    public static final String getOut = "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<";

    public static boolean UIFinish = false;


    public static final String SQLERR_SELECT = "查询数据库失败!!!";
    public static final String SQLERR_UPDATE = "更新数据库失败!!!";
    public static final String SQLERR_DELETE = "删除数据库失败!!!";
    public static final String SQLERR_INSERT = "插入数据库失败!!!";
    public static final String SQLERR_SESSION = "获取sqlsession失败!!!";


    public static String ControllerIP = "";

    public static boolean pauseFLG = false;

    public static String sQTDX_U = "";
    public static String sSHBH_U = "";

    public static String sKHDLZH = "";
    public static String sKHBH_U = "";

    public static String sZDBH_U = "";


    public static boolean isLogin = false;


    public static List<Map> listKH_SHXX = new ArrayList();
    public static List<Map> listSH_ZDXX = new ArrayList();

    public static String modelFile = "resources/ZDBH_U/主页";

    public static String modelZipFile = "resources/ZDBH_U/主页.zip";

    public static final String clientEncryptPrivateKey ="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCF2l8g+5tSpIvwI5aCD10pd46fhRBNJiLkdGLh2zulafBSAeSVGnDAhjG3F43zFMv+BBfA4kwgtXkLY7dnp2ckiEpy2HLNBUp6qmx/BVVih5POGx/ujZm8WDE6VO3lWc2Ui4gkmNxiv4vmPTNuZ1Pz5lGEBTo7jPIqiPNPz4x/WeiH/CmI95Yw06zvzHDYvu6pxdtFfg8KoGbuTruGHZbE0c1gt63hn+FaYtkbk9fvcKZxr3lMLt8BMdEfhRhgFfsDo6/CkQyS3lFL0DpCtXF6YyI4vwfRP5zgjHx1SmKq25sK7MShaOtzVHkQtTd6szFr0WdZYsswcLR422ObNjBfAgMBAAECggEAGC+nNMyB+mLlLlUf4wxnpxCFYummUmprr6AgJfN5SaBk3kydQxvt97vHy++jpKLDYXjX2fCKFPb1kktIXqBvELjXyvy1cbpdBOE6jZEnJpCc8ocQNAi+GLxO2N1zxxd9ADReO06rs+QsoUO5wV9GWjHp1NMk/JGxSGJKpMc5+eC/1+geZlvR9L+NbFN1YQMfjqRAk3SeAPhEzbQkd5IhcseWlt9rh9X+XrlYLbXlwbfBXj2/wztnSaLzJxKcspisppskLonoNKiFxgN46F3ZltQNvF7hv/4mnNEQCq45q+5JaWJrgJ1PX0Xqph+sr/WNRRFljXBilRjhZt70WdnzYQKBgQDM2jLScbCx8sFry++wOPq5+TcIbCX9QnxHTSOLowfeCh4ozXkVf37gV2FvIsMfKV76vs2INxtQdoRLNodAcRNOHUA7Q15aXvo1E6pzrzOCkx+0RuNidKRGfVU5SBCx72/TNVxfj/moDf6NrZGotJ2NKaakCAxeqxf0DjoR9VAMyQKBgQCnRgVjZyEClPFkNQIHN9wPS7FMsbbPo8YXHNilXYQfyLsi5o9Z7GhhzS7V3bRp6294Hky8ArgZ/5UqCIQCc6DvaZIiAnXeYKrxZ3bHqRvwyoTrcxOtV2utqPZj00nwgHK2atFyGl3tCoMlXaQUo+E2jjRkIym3rReVkziTODPv5wKBgDNtkBa/DhIOlLqAT2NZWrC3vTYzGHJ1b4fi+MqEmmQG/D1YIE7iXDLsHPzuqDe5hivDHQxWcVgI+Pt87AWknakdtNNr/VMIxx3uGvvB/1eHogz7Qvijud4sdunTisVxDAzlN5SSK6YiJUbiTVAiT+9xhnFlx904bOILdE6v3HHpAoGAJn1OMBlC1z0+bjhkRxTrZfmcynD6B70/j4Hrt+FUzZt6tAUpZx+mxRpZdIyXPugVtiYCsiBODG1q/UkIVygUGALKxViblpfXvcR46GhZLYbsHuFT3ccH1+XRDBdKJDTqMF9T4lV+11Rb6PUrFDTBVbRTCdeteb4ydxBxLC76hHECgYBmoK8Pc2gsbuGmG/txOy6WyEPbp8W2YgGww6c5wQRuhlZUH84tK7M6IOXjzrRXMuwxihTzdVUwKBJaTBnb+4O+n/Qr510hL8zRg6B8/9GW9VEdKnahybtggNb0upGjgD4DX+xDgxBvg4dq8gBQusPny98OvsFy30jdkeIobWmeNg==";
    public static final String clientSignPrivateKey ="MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDYRwlUWTrXg9jPiU9JDq8fh2pfNwtT6KpE18hPfo5yicAGUUsefxL9oC5ioWcPkV+Xu++2oxdmaGLr/ujj2m1WjCcO70Ou4SrbbNbmtcJBy7OEGlgNqf7tl1Zow0mYhT6ujcgtY57hlu8TGFQV4kGQf8XKQYJ7keVkY127YXuKU6CmLGqXCYeQJJHguP9J0TqNLny6BOlOjSS44dSpnX0UPU4NhroNS5HLDv9iamyxgctehSN9hiPY891pB82C0qXB49GxOhXmbKydhPtu75sWEeEqQDAp1YnoaaquF7DZRFAgjS0kkrcUwkV2PxPmeYRhi52ihn48evJCGiN6Hs9VAgMBAAECggEBANHm15oiY6ZIkwKQ2/8mnjX2Yfl43aiZFa9s0T69sBhfspsvCL3XTKIUdRBKX1DPoTwNLRBPZuWACAnMw1BobFdj/IBVHJY8eDCviD4vRxI+VcKvIqhYRU9n4ngYmHPLVdNpTU8n8Uo2B1+769e1WDaam2a5f57YMQ7mFVFHwfOzf2YKv9gWecvtHCPXuDmoWKvV/KYVvo8xZ4nGwGJdTyY0vZTfbjonKyEnQiljzULR9thqR/eejTbG34gaACvucvsupCdF/mKqaHPioEcNrEfwmAa+sFEMXpUemZQnrS22C4f7dAsu9PIiTZpipIm6YbjJ0lywnpf9yYJ7VYfsbIECgYEA7P078bVP3QR+XIHPqJjWaDGGAxTWJrVT16Iv5xhMBNHCB4tIwEQFVChDNVPEw8UdeQQvc3gvnIsVn2hca29zXkVYqH/degugVr1rxGLuePRGFmGitgcn9QqmWKFOm/PNiDZW576gsLZjd6b+mPaevKsdd54YdPDofJTc8LM/I7UCgYEA6aB4caAnwQ0BBUxZd2ZSGVwJrmHn5nXKDjxrL87Z6vg4OwDX6dVcAH9OtknihmAtduai2ayg4EWe8kFVnd51iXaSfzEiwdKHKPgUA6eta1huBHp3SP5DNumVTDPtD/bf4nd2vrJBor3LVbe2r6L/H2ShmPHPnQhW8kTDSz4pgSECgYBAQw69JSgpy20kUoLnucHx8PPg5AaJ6oN4pl8M8Ba0+9f8SbWJhShYwK4wyK1DVLEAPrVLP1zRuxk654agD1GeT3mR/1IkJQDuZGDTmOwHWl2i9gi0CU65cJDY2azCNyMVe36nSpayNFLWgC7rdXxntpK/+9uv4h94oLkkf8ZwPQKBgQCqtT2s0PibYDQhufMZgqN0skLEr/dx9xmII2+yxDOJNIxp2KjrzKHoHx3VptElnPs7iTTvVutKVLTakRDNRPKfWgubcrzR4VIvhm2hahEWgcwJ665joJ5ebnlP8BVFd/+Ji/8xQjEhiAsefBm55qECQFav2ej49lIJvmLxBN/w4QKBgQCuLrqQiacvOuucK/0Tv/R2czOCRV1Sc4GgpZBGZhv0R+7CXGk2VlQ/KQ+NiRdOTLO0wzisUYGBsHwj1k6IJcfKn/9qm5FvfKoXPPkamWivCWJNbwE6AzIvmwDRiZ9m6AXgWGVZTdd/DK8hc3qegbE9rjQciA5Bk2Xk+LtfI5BdWA==";
    public static final String serverSignPublicbKey ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPj8/pRvs6x2lDu4cxob0K0Ui/gm7kAIEEQ6fT4aH0XEzOLb9JEyFBhcag6PqN7PY/KYtZBweMRa4fGR1xi2DSamo1GLGc9oZKpN8IYZVya6Ps0RgvSB2nHw6F69cFhm5HuO01FM331lf1avZkxaofUSXa4Cy0y8acjsCvvoOknwIDAQAB";
    public static final String charset="GBK";


    /**
     * 获取前台流水=C+12位终端编号+7位序号
     *
     * @return
     */
    public static String getQTLS() {
        String sQTLS_U = "";


        if (Com.isLogin) {


            String sKey = "";
            if ("kh".equals(Com.sQTDX_U)) {
                sKey = Com.sKHBH_U;
            } else if ("sh".equals(Com.sQTDX_U)) {
                sKey = Com.sSHBH_U;
            } else if ("zd".equals(Com.sQTDX_U)) {
                sKey = Com.sZDBH_U;
            }

            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
            String sDate = df.format(new Date());


            String sSCJYLS = readKeyFromXML(new File("resources/Info/sysInfo.properties"), sKey + "LS");
            //文件中格式如"ZD0000000001LS=20170101-1"
            int iXH = 0;
            if (!"".equals(sSCJYLS) && sSCJYLS != null) {
                Logger.log("LOG_DEBUG", "开始插入配置文件");
                String[] strings = sSCJYLS.split("-");
                if (sDate.equals(strings[0])) {//序号增加
                    iXH=Integer.parseInt(strings[1])+1;
                    //重新写入
                    PropertiesHandler.writeProperties(new File("resources/Info/sysInfo.properties"),sKey + "LS",sDate+"-"+iXH);
                } else {
                    iXH = 1;
                    //重新写入
                    PropertiesHandler.writeProperties(new File("resources/Info/sysInfo.properties"),sKey + "LS",sDate+"-"+iXH);

                }
            } else {
                Logger.log("LOG_DEBUG", "开始插入配置文件");
                iXH = 1;
                //重新写入
                PropertiesHandler.writeProperties(new File("resources/Info/sysInfo.properties"),sKey + "LS",sDate+"-"+iXH);


            }
            sQTLS_U = "C" + sKey + String.format("%07d", iXH);

            Logger.log("LOG_DEBUG", "前台流水:" + sQTLS_U);

        } else {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmssSSS");//设置日期格式
            String sDate = df.format(new Date());
            sQTLS_U = "TMP" + sDate;

            Logger.log("LOG_DEBUG", "未登录,生成临时流水号:" + sQTLS_U);
        }


        return sQTLS_U;
    }

    /**
     * 获取前台机器日期
     *
     * @return
     */
    public static String getDate() {
        String date = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");//设置日期格式
        date = df.format(new Date());
        return date;
    }


    public static void main(String[] args) {
    }
}
