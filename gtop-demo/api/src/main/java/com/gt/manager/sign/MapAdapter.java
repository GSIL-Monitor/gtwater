package com.gt.manager.sign;

import org.dom4j.dom.DOMElement;
import org.dom4j.dom.DOMText;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hexu on 2016/6/16.
 */
public class MapAdapter extends XmlAdapter<MapWrapper, Map<String, Object>> {
    @Override
    public MapWrapper marshal(Map<String, Object> m) throws Exception {
        MapWrapper wrapper = new MapWrapper();
        List<Element> elements = new ArrayList<Element>();
        for (Map.Entry<String, Object> property : m.entrySet()) {
            Element e = new DOMElement(property.getKey());
            if (property.getValue() != null)
                e.appendChild(new DOMText(property.getValue().toString()));
//			else
//				e.appendChild(new DOMText(""));
            elements.add(e);
        }
        wrapper.elements = elements;
        return wrapper;
    }

    @Override
    public Map<String, Object> unmarshal(MapWrapper v) throws Exception {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        List<? extends Element> list = v.elements;
        for (Element e : list) {
            map.put(e.getTagName(), e.getTextContent());
        }
        return map;
    }
}

class MapWrapper {
    @XmlAnyElement
    List<? extends Element> elements;
}
