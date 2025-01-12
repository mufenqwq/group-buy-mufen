package site.mufen.domain.trade.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author mufen
 * @Description
 * @create 2025/1/12 20:29
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupBuyProgressVO {

    private Integer targetCount;
    private Integer completeCount;
    private Integer lockCount;
}
