package net.mengkang.api.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * api route map, read from routeMap.xml
 */
public class ApiRoute {

    private static final Logger logger = LoggerFactory.getLogger(ApiRoute.class);

    private static final String routeName = "/routeMap.xml";

    private static final String apiName           = "name";
    private static final String apiHttpMethod     = "httpMethod";
    private static final String apiClassAndMethod = "classAndMethod";
    private static final String apiBuild          = "build";

    public static final Map<String, Api> apiMap = new HashMap<String, Api>();

    static {
        init();
    }

    public static void init() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(ApiRoute.class.getResourceAsStream(routeName));

            NodeList apiList = doc.getElementsByTagName("api");
            for (int i = 0, apiLength = apiList.getLength(); i < apiLength; i++) {
                Element element = (Element) apiList.item(i);

                Api api = new Api();
                api.setName(element.getAttribute(apiName));

                for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
                    if (node.getNodeType() == Node.ELEMENT_NODE) {

                        String name = node.getNodeName();
                        String value = node.getFirstChild().getNodeValue();

                        if (name.equals(apiHttpMethod)) {
                            api.addHttpMethod(value);
                        } else if (name.equals(apiClassAndMethod)) {
                            api.setClassAndMethod(value.split("\\."));
                        } else if (name.equals(apiBuild)) {
                            try {
                                api.setBuild(Integer.parseInt(value));
                            } catch (NumberFormatException e) {
                                logger.error(e.getMessage());
                            }
                        }
                    }
                }

                apiMap.put(api.getName(), api);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
