package net.mengkang.api.handler;

import java.util.HashSet;
import java.util.Set;

/**
 * api mapping object
 */
public class Api {

    private String name;
    private Set<String> httpMethod;
    private String[] classAndMethod;
    private int build;

    public Api() {
        httpMethod = new HashSet<String>();
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

    public String[] getClassAndMethod() {
        return classAndMethod;
    }

    public void setClassAndMethod(String[] classAndMethod) {
        this.classAndMethod = classAndMethod;
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

}
