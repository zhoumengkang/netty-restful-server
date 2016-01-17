package net.mengkang.nettyrest.mysql;

import java.lang.annotation.*;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface DbFiled {
    String value();
}
