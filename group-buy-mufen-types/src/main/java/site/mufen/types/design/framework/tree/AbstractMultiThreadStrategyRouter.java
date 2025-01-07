package site.mufen.types.design.framework.tree;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author mufen
 * @Description
 * @create 2025/1/7 21:28
 */
public abstract class AbstractMultiThreadStrategyRouter<T, D, R> implements StrategyHandler<T, D, R>, StrategyMapper<T, D, R> {

    @Getter
    @Setter
    protected StrategyHandler<T, D, R> defaultStrategyHandler = StrategyHandler.DEFAULT;


    /**
     * 路由方法，接受requestParam 和 dynamicCtx,调用映射下一个节点的apply方法
     * @param requestParam 请求参数
     * @param dynamicCtx 动态上下文
     * @return 路由结果
     * @throws Exception
     */
    public R router(T requestParam, D dynamicCtx) throws Exception {
        StrategyHandler<T, D, R> strategyHandler = get(requestParam, dynamicCtx);
        if (strategyHandler != null) return strategyHandler.apply(requestParam, dynamicCtx);
        return defaultStrategyHandler.apply(requestParam, dynamicCtx);
    }

    @Override
    public R apply(T requestParam, D dynamicCtx) throws Exception {
        // 异步加载数据
        multiThread(requestParam, dynamicCtx);
        // 业务流程受理
        return doApply(requestParam, dynamicCtx);
    }

    protected abstract R doApply(T requestParam, D dynamicCtx) throws Exception;

    protected abstract void multiThread(T requestParam, D dynamicCtx) throws ExecutionException, InterruptedException, TimeoutException;



}
