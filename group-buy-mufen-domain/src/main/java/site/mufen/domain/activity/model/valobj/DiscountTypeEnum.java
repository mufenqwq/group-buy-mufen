package site.mufen.domain.activity.model.valobj;

import lombok.*;

/**
 * @author mufen
 * @Description 折扣类型Enum
 * @create 2025/1/8 11:39
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum DiscountTypeEnum {
    BASE(0, "基础折扣"),
    TAG(1, "人群折扣"),
    ;
    private Integer code;
    private String info;

    public static DiscountTypeEnum get(Integer code) {
        switch (code) {
            case 0: return BASE;
            case 1: return TAG;
            default: throw new RuntimeException("err discountType");
        }
    }
}
