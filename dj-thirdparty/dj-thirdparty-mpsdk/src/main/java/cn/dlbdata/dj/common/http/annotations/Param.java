package cn.dlbdata.dj.common.http.annotations;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Param {
    String key() default "";
    int min() default Integer.MIN_VALUE;
    int max() default Integer.MAX_VALUE;
    int arraySizeMin() default -1;
    int arraySizeMax() default -1;
    boolean required() default false;
}
