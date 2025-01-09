package site.mufen.types.annotations;

import java.lang.annotation.*;

/**
 * @author mufen
 * @Description 注解，动态配置中心
 * @create 2025/1/9 23:05
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface DCCValue {
    String value() default "";
}
