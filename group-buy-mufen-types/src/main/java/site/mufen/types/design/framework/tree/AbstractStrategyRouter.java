package site.mufen.types.design.framework.tree;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mufen
 * @Description
 * @create 2025/1/7 18:38
 */
public abstract class AbstractStrategyRouter<T, D, R> implements StrategyHandler<T, D, R>, StrategyMapper<T, D, R> {

    @Getter
    @Setter
    protected StrategyHandler<T, D, R> defaultStrategyHandler = StrategyHandler.DEFAULT;

    public R router(T requestParam, D dynamicCtx) throws Exception {
        StrategyHandler<T, D, R> strategyHandler = get(requestParam, dynamicCtx);
        if (strategyHandler != null) return strategyHandler.apply(requestParam, dynamicCtx);
        return defaultStrategyHandler.apply(requestParam, dynamicCtx);
    }
}
