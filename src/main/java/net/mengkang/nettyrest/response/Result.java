package net.mengkang.nettyrest.response;

public class Result<T extends Info> {

    protected T info;

    public T getInfo() {
        return info;
    }

    /**
     * create a Result object with a user defined object extend Info
     *
     * @param info
     */
    public Result(T info) {
        this.info = info;
    }
}
