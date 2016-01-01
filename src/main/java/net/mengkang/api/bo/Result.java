package net.mengkang.api.bo;

/**
 * Created by zhoumengkang on 30/12/15.
 */
public class Result {

    protected Info info;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public Result() {
        this.info = new Info();
    }

    /**
     * create a Result object with a user defined object extend Info
     *
     * @param info
     */
    public Result(Info info) {
        this.info = info;
    }
}
