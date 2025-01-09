package site.mufen.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.mufen.types.annotations.DCCValue;
import site.mufen.types.common.Constants;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mufen
 * @Description
 * @create 2025/1/9 23:51
 */
@Slf4j
@Configuration
public class DCCValueBeanFactory implements BeanPostProcessor {

    private static final String BASE_CONFIG_PATH = "group_buy_market_dcc_";

    private final RedissonClient redissonClient;

    private final Map<String, Object> dccObject = new HashMap<>();

    public DCCValueBeanFactory(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Bean("dccTopic")
    public RTopic dccRedisTopicListener(RedissonClient redissonClient) {
        RTopic topic = redissonClient.getTopic("group_buy_market_dcc");

        topic.addListener(String.class, (channel, msg) -> {
            String[] split = msg.split(Constants.SPLIT);

            String attribute = split[0];
            String key = BASE_CONFIG_PATH.concat(attribute);
            String value = split[1];
            RBucket<Object> bucket = redissonClient.getBucket(key);
            if (!bucket.isExists()) return;
            bucket.set(value);

            Object objBean = dccObject.get(key);
            if (null == objBean) return;

            Class<?> objBeanClass = objBean.getClass();
            if (AopUtils.isAopProxy(objBean)) {
                objBeanClass = AopUtils.getTargetClass(objBean);
            }

            try {
                Field field = objBeanClass.getDeclaredField(attribute);
                field.setAccessible(true);
                field.set(objBean, value);
                field.setAccessible(false);
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return topic;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetBeanClass = bean.getClass();
        Object targetBeanObject = bean;
        if (AopUtils.isAopProxy(bean)) {
            targetBeanClass = AopUtils.getTargetClass(bean);
            targetBeanObject = AopProxyUtils.getSingletonTarget(bean);
        }

        Field[] fields = targetBeanClass.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(DCCValue.class)) continue;

            // 如果有 DCCValue 的注解
            DCCValue dccValue = field.getAnnotation(DCCValue.class);

            String value = dccValue.value();
            if (StringUtils.isBlank(value)) {
                throw new RuntimeException("DCCValue 注解未设置 key");
            }

            String[] split = value.split(Constants.COLON);
            String key = BASE_CONFIG_PATH.concat(split[0]);
            String defaultValue = split.length == 2 ? split[1] : null;

            String setValue = defaultValue;

            try {
                if (StringUtils.isBlank(defaultValue)) {
                    throw new RuntimeException("DCCValue 注解未设置默认值");
                }
                RBucket<String> bucket = redissonClient.getBucket(key);
                boolean exists = bucket.isExists();
                if (!exists) {
                    bucket.set(defaultValue);
                } else {
                    setValue = bucket.get();
                }

                field.setAccessible(true);
                field.set(targetBeanObject, setValue);
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            dccObject.put(key, targetBeanObject);
        }

        return bean;
    }
}
