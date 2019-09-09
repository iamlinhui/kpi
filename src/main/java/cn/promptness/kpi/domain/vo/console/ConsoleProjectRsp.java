package cn.promptness.kpi.domain.vo.console;

import lombok.Data;

/**
 * @author linhuid
 * @date 2019/7/1 21:15
 * @since v1.0.0
 */
@Data
public class ConsoleProjectRsp {


    /**
     * 考核方案id
     */
    private Integer projectId;

    /**
     * 考核方案名称
     */
    private String  projectName;


}
