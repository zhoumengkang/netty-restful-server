package net.mengkang.demo.bo;

/**
 * Created by zhoumengkang on 4/1/16.
 */
public class Star {
    private int num;
    private boolean star;

    public Star(int num, boolean star) {
        this.num = num;
        this.star = star;
    }

    public int getNum() {
        return num;
    }

    public boolean isStar() {
        return star;
    }
}
