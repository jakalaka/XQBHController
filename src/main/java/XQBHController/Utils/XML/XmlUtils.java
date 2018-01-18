package XQBHController.Utils.XML; /**
 * Created by Administrator on 2017/7/2 0002.
 */

import XQBHController.Utils.log.Logger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.*;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

public class XmlUtils {

    /**
     * ����Map��װxml��Ϣ��,ֵ�����֧�ֻ����������͡�String��BigInteger��BigDecimal,�Լ�����Ԫ��Ϊ����֧���������͵�Map
     *

     * @return
     */
    public static String map2XML(Map<String, Object> In) {
        String jsonString = JSON.toJSONString(In);
        return jsonString;
    }

    /**
     * ����xml��Ϣ��ת��ΪMap
     *
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static Map XML2map(String xml)  {
        JSONObject jsonObject = JSON.parseObject(xml);
        return  jsonObject.getInnerMap();
    }

    public static void main(String[] args) {

    }
}