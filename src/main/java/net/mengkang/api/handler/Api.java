package net.mengkang.api.handler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * api mapping object
 */
public class Api {

    private String       name;
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

    public void setRegex(String regex) {
        this.regex = regex;
        String[] names = this.name.split("/");
        String[] strings = regex.split("/");
        if (names.length == strings.length) {
            for (int i = 0; i < strings.length; i++) {
                if (strings[i].startsWith("(") && strings[i].endsWith(")")) {
                    parameterNames.add(names[i]);
                }
            }
        }
    }
}
