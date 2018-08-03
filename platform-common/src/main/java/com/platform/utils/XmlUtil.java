package com.platform.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.Writer;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * xml相关的工具类
 *
 * @author yang.y
 */
@SuppressWarnings("unchecked")
public class XmlUtil {

    /**
     * xml字符串转换成bean对象
     *
     * @param xmlStr xml字符串
     * @param clazz  待转换的class
     * @return 转换后的对象
     */
    public static Object xmlStrToBean(String xmlStr, Class clazz) {
        Object obj = null;
        try {
            // 将xml格式的数据转换成Map对象
            Map<String, Object> map = xmlStrToMap(xmlStr);
            // 将map对象的数据转换成Bean对象
            obj = mapToBean(map, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 将xml格式的字符串转换成Map对象
     *
     * @param xmlStr xml格式的字符串
     * @return Map对象
     * @throws Exception 异常
     */
    public static Map<String, Object> xmlStrToMap(String xmlStr) throws Exception {
        if (StringUtils.isNullOrEmpty(xmlStr)) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        // 将xml格式的字符串转换成Document对象
        Document doc = DocumentHelper.parseText(xmlStr);
        // 获取根节点
        Element root = doc.getRootElement();
        // 获取根节点下的所有元素
        List children = root.elements();
        // 循环所有子元素
        if (children != null && children.size() > 0) {
            for (int i = 0; i < children.size(); i++) {
                Element child = (Element) children.get(i);
                map.put(child.getName(), child.getTextTrim());
            }
        }
        return map;
    }

    /**
     * 将xml格式字符串转换成Bean对象
     * 多级子节点递归遍历
     *
     * @param xmlStr
     * @param clazz
     * @return
     * @throws Exception
     */
    public static Object xmlStrToJavaBean(String xmlStr, Class clazz) {
        if (StringUtils.isNullOrEmpty(xmlStr)) {
            return null;
        }
        Object obj = null;
        Map<String, Object> map = new HashMap<String, Object>();
        // 将xml格式的字符串转换成Document对象
        Document doc;
        try {
            doc = DocumentHelper.parseText(xmlStr);

            // 获取根节点
            Element root = doc.getRootElement();
            map = elementToMap(root, map);
            // 将map对象的数据转换成Bean对象
            obj = mapToBean(map, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 递归遍历xml子节点，转换Map
     *
     * @param element
     * @param map
     * @return
     */
    public static Map<String, Object> elementToMap(Element element, Map<String, Object> map) {
        if (element == null || map == null)
            return null;
        List children = element.elements();
        if (children != null && children.size() > 0) {
            for (int i = 0; i < children.size(); i++) {
                Element child = (Element) children.get(i);
                if (child.elements() != null && child.elements().size() > 0)
                    elementToMap(child, map);
                else
                    map.put(child.getName(), child.getTextTrim());
            }
        }
        return map;
    }

    /**
     * 将Map对象通过反射机制转换成Bean对象
     *
     * @param map   存放数据的map对象
     * @param clazz 待转换的class
     * @return 转换后的Bean对象
     * @throws Exception 异常
     */
    public static Object mapToBean(Map<String, Object> map, Class clazz) throws Exception {
        Object obj = clazz.newInstance();
        if (map != null && map.size() > 0) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String propertyName = entry.getKey();
                Object value = entry.getValue();
                String setMethodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
                Field field = getClassField(clazz, propertyName);
                if (field != null) {
                    Class fieldTypeClass = field.getType();
                    value = convertValType(value, fieldTypeClass);
                    clazz.getMethod(setMethodName, field.getType()).invoke(obj, value);
                }
            }
        }
        return obj;
    }

    /**
     * 将Object类型的值，转换成bean对象属性里对应的类型值
     *
     * @param value          Object对象值
     * @param fieldTypeClass 属性的类型
     * @return 转换后的值
     */
    private static Object convertValType(Object value, Class fieldTypeClass) {
        Object retVal = null;
        if (Long.class.getName().equals(fieldTypeClass.getName())
                || long.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Long.parseLong(value.toString());
        } else if (Integer.class.getName().equals(fieldTypeClass.getName())
                || int.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Integer.parseInt(value.toString());
        } else if (Float.class.getName().equals(fieldTypeClass.getName())
                || float.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Float.parseFloat(value.toString());
        } else if (Double.class.getName().equals(fieldTypeClass.getName())
                || double.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Double.parseDouble(value.toString());
        } else {
            retVal = value;
        }
        return retVal;
    }

    /**
     * 获取指定字段名称查找在class中的对应的Field对象(包括查找父类)
     *
     * @param clazz     指定的class
     * @param fieldName 字段名称
     * @return Field对象
     */
    private static Field getClassField(Class clazz, String fieldName) {
        if (Object.class.getName().equals(clazz.getName())) {
            return null;
        }
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }

        Class superClass = clazz.getSuperclass();
        if (superClass != null) {// 简单的递归一下
            return getClassField(superClass, fieldName);
        }
        return null;
    }

    /**
     * 示例 <xml> <return_code><![CDATA[SUCCESS]]></return_code>
     * <return_msg><![CDATA[OK]]></return_msg>
     * <appid><![CDATA[wx2421b1c4370ec43b]]></appid>
     * <mch_id><![CDATA[10000100]]></mch_id>
     * <nonce_str><![CDATA[IITRi8Iabbblz1Jc]]></nonce_str>
     * <openid><![CDATA[oUpF8uMuAJO_M2pxb1Q9zNjWeS6o]]></openid>
     * <sign><![CDATA[7921E432F65EB8ED0CE9755F0E86D72F]]></sign>
     * <result_code><![CDATA[SUCCESS]]></result_code>
     * <prepay_id><![CDATA[wx201411101639507cbf6ffd8b0779950874]]></prepay_id>
     * <trade_type><![CDATA[JSAPI]]></trade_type> </xml>
     *
     * 将xml数据(<![CDATA[SUCCESS]]>格式)映射到java对象中
     *
     * @param xml
     *            待转换的xml格式的数据
     * @param t
     *            待转换为的java对象
     * @return
     */
    public static <T> T getObjectFromXML(String xml, Class<T> t) {
        // 将从API返回的XML数据映射到Java对象
        XStream xstream = XStreamFactroy.init(true);
        xstream.alias("xml", t);
        xstream.ignoreUnknownElements();// 暂时忽略掉一些新增的字段
//		if(Objects.equals(xstream.fromXML(xml).getClass(), t.getClass())) {
//			return t.cast(xstream.fromXML(xml));
//		}
//		return null;
        return t.cast(xstream.fromXML(xml));
    }
    /**
     * XStream工具类
     * @author phil
     *
     */
    static class XStreamFactroy {

        private static final String START_CADA = "<![CDATA[";
        private static final String END_CADA = "]]>";

        /**
         * 是否启用<![CDATA[]]>
         *
         * @param flag  true 表示启用 false表示不启用
         * @return
         */
        static XStream init(boolean flag) {
            XStream xstream = null;
            if (flag) {
                xstream = new XStream(new XppDriver() {
                    public HierarchicalStreamWriter createWriter(Writer out) {
                        return new PrettyPrintWriter(out) {
                            @Override
                            protected void writeText(QuickWriter writer, String text) {
                                if (!text.startsWith(START_CADA)) {
                                    text = START_CADA + text + END_CADA;
                                }
                                writer.write(text);
                            }
                        };
                    }
                });
            } else {
                xstream = new XStream();
            }
            return xstream;
        }

        /**
         * 用于处理在实体对象中带有_的属性,如果用上述方法，会出现有两个__,导致结果不正确! 属性中有_的属性一定要有改方法
         *
         * @return 返回xstream 对象 new DomDriver("UTF-8", new
         *         XmlFriendlyNameCoder("-_", "_")
         */
        public static XStream initSplitLine() {
            XStream xstream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
            return xstream;
        }
    }


    /**
     * 将java对象转换为xml文件,并去除 _ 应用场景是 去除实体中有_划线的实体, 默认会有两个_,调用该方法则会去除一个
     *
     * @param obj
     * @return
     */
    public static String toSplitXml(Object obj) {
        String result = "";
        XStream xstream = XStreamFactroy.initSplitLine();
        xstream.alias("xml", obj.getClass());
        result = xstream.toXML(obj);
        return result;
    }

}
