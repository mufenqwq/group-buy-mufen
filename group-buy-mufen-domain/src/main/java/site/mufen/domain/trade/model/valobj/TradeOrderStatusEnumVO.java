package site.mufen.domain.trade.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author mufen
 * @Description
 * @create 2025/1/12 20:13
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TradeOrderStatusEnumVO {
    CREATE(0, "创建"),
    COMPLETE(1, "完成"),
    CLOSE(2, "关闭"),
    ;
    private Integer code;
    private String info;

    public static TradeOrderStatusEnumVO valueOf(Integer code) {
        for (TradeOrderStatusEnumVO value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
