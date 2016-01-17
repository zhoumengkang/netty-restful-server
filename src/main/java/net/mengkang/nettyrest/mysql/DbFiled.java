package net.mengkang.nettyrest.mysql;

import java.lang.annotation.*;

/**
 * Created by zhoumengkang on 17/1/16.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface DbFiled {
    public String value();
}
