package net.mengkang.nettyrest;

import net.mengkang.nettyrest.response.Info;
import net.mengkang.nettyrest.response.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(StatusCode.class);

    public static Result error(int errorCode) {
        Result result = new Result<>(new Info());
        result.getInfo().setCode(errorCode).setCodeMessage(StatusCode.codeMap.get(errorCode));
        return result;
    }

    public static Result error(int errorCode,String parameter) {
        Result result = new Result<>(new Info());
        result.getInfo()
                .setCode(errorCode)
                .setCodeMessage(String.format(StatusCode.codeMap.get(errorCode), parameter));
        return result;
    }
}
