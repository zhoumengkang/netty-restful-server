package net.mengkang.nettyrest.mysql;

import java.lang.annotation.*;


// 是否生成文档
@Documented
// 在运行时生效
@Retention(RetentionPolicy.RUNTIME)
// 适用的位置
@Target({ElementType.FIELD})
public @interface DbFiled {
    String value();
}
