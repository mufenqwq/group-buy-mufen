package site.mufen.types.design.framework.tree;

/**
 * @author mufen
 * @Description 策略处理器
 * @create 2025/1/7 18:35
 */
public interface StrategyHandler<T, D, R> {

    StrategyHandler DEFAULT = (T, D) -> null;

    R apply(T requestParam, D dynamicCtx) throws Exception;
}
