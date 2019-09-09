package cn.promptness.kpi.domain.vo.table;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author linhuid
 * @date 2019/7/2 14:21
 * @since v1.0.0
 */
@Data
public class TableReq {
    
    /**
     * 卡片id
     */
    @Min(1)
    @NotNull
    private Integer consoleId;


}
