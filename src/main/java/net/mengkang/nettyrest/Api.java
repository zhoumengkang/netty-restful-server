package net.mengkang.nettyrest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * api mapping object
 */
public class Api {

    private String       name;  // endpoint
    private String       regex;
    private List<String> parameterNames;
    private Set<String>  httpMethod;
    private String       resource;
    private int          build;

    public Api() {
        httpMethod = new HashSet<>();
        parameterNames = new ArrayList<>();
    }

    public List<String> getParameterNames() {
        return parameterNames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

        String[]      strings       = name.split("/");
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0, len = strings.length; i < len; i++) {
            if (strings[i].length() == 0) {
                continue;
            }
            stringBuilder.append("/");
            if (strings[i].startsWith(":")) {
                parameterNames.add(strings[i].substring(1));
                stringBuilder.append("([^/]+)");
            } else {
                stringBuilder.append(strings[i]);
            }
        }

        this.regex = stringBuilder.toString();
    }

    public Set<String> getHttpMethod() {
        return httpMethod;
    }

    public int getBuild() {
        return build;
    }

    public void setBuild(int build) {
        this.build = build;
    }

    public void addHttpMethod(String httpMethod) {
        this.httpMethod.add(httpMethod);
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getRegex() {
        return regex;
    }
}
