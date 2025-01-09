package site.mufen.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author mufen
 * @Description 标签范围枚举值
 * @create 2025/1/9 21:51
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TagScopeEnumVO {
    VISIBLE(true, false, "是否可以看见拼团"),
    ENABLE(true, false, "是否可参与拼团")
    ;
    private Boolean allow;
    private Boolean refuse;
    private String desc;
}
