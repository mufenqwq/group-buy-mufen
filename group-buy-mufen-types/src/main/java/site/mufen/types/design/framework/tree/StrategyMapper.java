package site.mufen.types.design.framework.tree;

/**
 * @author mufen
 * @Description 策略映射器
 * @create 2025/1/7 18:34
 */
public interface StrategyMapper<T, D, R> {

    StrategyHandler<T, D, R> get(T requestParam, D dynamicCtx);
}
