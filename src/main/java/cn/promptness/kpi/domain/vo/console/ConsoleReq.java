package cn.promptness.kpi.domain.vo.console;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 考核卡片请求参数
 *
 * @author linhuid
 * @date 2019/7/1 19:32
 * @since v1.0.0
 */
@Data
public class ConsoleReq {

    @NotNull(message = "projectId不能为空")
    @Min(value = 1, message = "projectId数值不合法")
    private Integer projectId;
}
