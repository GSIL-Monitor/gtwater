package com.gt.manager.sign;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("all")
public class XMLUtils {
    private static final ConcurrentHashMap<String, JAXBContext> xmlMarshallerMap = new ConcurrentHashMap<String, JAXBContext>();
//    private static final ConcurrentHashMap<String, JSONJAXBContext> jsonMarshallerMap = new ConcurrentHashMap<String, JSONJAXBContext>();
//	private static final ConcurrentHashMap<String, JAXBContext> xmlUnMarshallerMap = new ConcurrentHashMap<String, JAXBContext>();
//	private static final ConcurrentHashMap<String, JSONMarshaller> jsonMarshallerMap = new ConcurrentHashMap<String, JSONMarshaller>();

    public static <T> T xml2Java(Class<T> t, String xmlPath) throws JAXBException, IOException {
        FileInputStream is = new FileInputStream(xmlPath);
        try {
            return xml2Java(t, is);
        } finally {
            is.close();
        }
    }

    public static <T> T xml2Java(Class<T> t, InputStream is) throws JAXBException, IOException {
        if (t.equals(RequestMeta.class)) {
            SAXReader reader = new SAXReader();
            try {
                Document document = reader.read(is);
                RequestMeta rm = new RequestMeta();
                //request
                Element requestEl = document.getRootElement();
                Element dataEl = requestEl.element("data");
                rm.setSignature(requestEl.elementTextTrim("signature"));
                RequestData data = new RequestData();
                rm.setData(data);
                data.setOperation(dataEl.elementTextTrim("operation"));
                data.setAppKey(dataEl.elementTextTrim("appKey"));
                data.setTimeStamp(dataEl.elementTextTrim("timeStamp"));
                data.setFormat(dataEl.elementTextTrim("format"));
                data.setPartnerId(dataEl.elementTextTrim("partnerId"));
                HashMap<String, String> parameters = new HashMap<String, String>();
                data.setParameters(parameters);
                Element paramsEl = dataEl.element("parameters");
                if (paramsEl != null) {
                    List paramsElList = paramsEl.elements();
                    for (Object o : paramsElList) {
                        Element e = (Element) o;
                        parameters.put(e.getName(), e.getTextTrim());
                    }
                }
                return (T) rm;
            } catch (DocumentException e) {
                throw new JAXBException(e);
            }
//			return null;
        }
        Unmarshaller unmarshaller = getUnMarshaller(t);
        parseJSON.set(false);
        return (T) unmarshaller.unmarshal(is);
    }

    private static Marshaller getMarshaller(Class... clzs) throws JAXBException {
        JAXBContext context;
//        synchronized (xmlMarshallerMap) {
        context = getJAXBContext(clzs);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
        return marshaller;
//        }
    }

    private static Unmarshaller getUnMarshaller(Class... clzs) throws JAXBException {

        return getJAXBContext(clzs).createUnmarshaller();
    }

    private static JAXBContext getJAXBContext(Class... clzs) throws JAXBException {
        ;
        String t = toClassArray(clzs);
        JAXBContext context;
        context = xmlMarshallerMap.get(t);
        if (context == null) {
            synchronized (xmlMarshallerMap) {
                context = JAXBContext.newInstance(clzs);
                xmlMarshallerMap.putIfAbsent(t, context);
                context = xmlMarshallerMap.get(t);
            }
        }
        return context;
    }
//
//    private static JSONMarshaller getJSONMarshaller(Class... clzs) throws JAXBException {
//        String t = toClassArray(clzs);
//        JSONJAXBContext context;
//        context = jsonMarshallerMap.get(t);
//        if (context == null) {
//            synchronized (xmlMarshallerMap) {
//                context = new JSONJAXBContext(clzs);
//                jsonMarshallerMap.putIfAbsent(t, context);
//                context = jsonMarshallerMap.get(t);
//            }
//        }
//        JSONMarshaller marshaller;
//        marshaller = context.createJSONMarshaller();
//        marshaller.setProperty(JSONMarshaller.FORMATTED, false);
//        return marshaller;
//    }

    static final Comparator<String> comparator = new Comparator<String>() {
        @Override
        public int compare(String aClass, String t1) {
            return aClass.compareTo(t1);
        }
    };

    private static String toClassArray(Class... clzs) {
        if (clzs != null && clzs.length > 1) {
            String[] newClzs = new String[clzs.length];
            for (int i = 0, j = clzs.length; i < j; i++)
                newClzs[i] = clzs[i].getName();
            Arrays.sort(newClzs, comparator);
            return Arrays.toString(newClzs);
        } else if (clzs != null && clzs.length == 1 && clzs[0] != null)
            return clzs[0].getName();
        else return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromString(Class<T> t, String str) throws JAXBException, IOException {
        Unmarshaller unmarshaller = getUnMarshaller(t);
        parseJSON.set(false);
        return (T) unmarshaller.unmarshal(new StringReader(str));
    }

    public static <T> void java2XML(T rootObject, OutputStream out) throws JAXBException {
        java2Obj(rootObject, out);
    }

    public static <T> void java2XML(OutputStream out, T rootObject, Class... claz) throws JAXBException {
        java2Obj(out, rootObject, claz);
    }

    public static <T> String java2XML(T rootObject, Class... claz) throws JAXBException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        java2XML(out, rootObject, claz);
        return new String(out.toByteArray());
    }

//    public static <T> String java2JSONString(Collection<T> obj, Class... clzs) throws JAXBException {
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("[");
//        boolean addit = false;
//        for (T o : obj) {
//            if (addit) {
//                sb.append(",");
//
//            } else {
//                addit = true;
//            }
//            sb.append(java2JSONString(o, clzs));
//        }
//        if (addit) sb.append("]");
//        else return "[]";
//        return sb.toString();
//    }

    public static ThreadLocal<Boolean> parseJSON = new ThreadLocal<Boolean>();
//
//    public static <T> String java2JSONString(T rootObject) throws JAXBException {
//        if (rootObject instanceof Collection) return java2JSONString((Collection<?>) rootObject, rootObject.getClass());
//        JSONMarshaller marshaller = getJSONMarshaller(rootObject.getClass());
//        StringWriter writer = new StringWriter();
//        parseJSON.set(true);
//        marshaller.marshallToJSON(rootObject, writer);
//        return writer.toString();
//
//    }
//
//    public static <T> String java2JSONString(T rootObject, Class... clzs) throws JAXBException {
//        if (rootObject instanceof Collection) return java2JSONString((Collection<? extends Object>) rootObject, clzs);
//        JSONMarshaller marshaller = getJSONMarshaller(clzs);
//        StringWriter writer = new StringWriter();
//        parseJSON.set(true);
//        marshaller.marshallToJSON(rootObject, writer);
//        return writer.toString();
//
//    }

    public static <T> void java2Obj(T rootObject, OutputStream out) throws JAXBException {
        java2Obj(out, rootObject);
    }

    public static <T> void java2Obj(OutputStream out, T rootObject, Class... clzs) throws JAXBException {
        if (clzs == null || clzs.length == 0) {
            clzs = new Class[]{rootObject.getClass()};
        }
        Marshaller marshaller = getMarshaller(clzs);
        parseJSON.set(false);
        marshaller.marshal(rootObject, out);
    }

    public static <T> String java2XML(T rootObject) throws JAXBException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        java2XML(rootObject, out);
        return new String(out.toByteArray());
    }
}
