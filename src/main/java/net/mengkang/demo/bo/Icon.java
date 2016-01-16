package net.mengkang.demo.bo;

/**
 * Created by zhoumengkang on 17/1/16.
 */
public class Icon {

    private int    height;
    private int    width;
    private String url;

    public Icon(int height, int width, String url) {
        this.height = height;
        this.width = width;
        this.url = url;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
