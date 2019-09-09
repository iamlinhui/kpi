package cn.promptness.kpi.domain.vo.hr;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Lynn
 * @date 2018/9/29 18:17
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleRsp {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 数据范围
     */
    private String dataScope;

    /**
     * 是否是系统数据
     */
    private String sysData;

    /**
     * 是否是可用
     */
    private String useable;

    private String id;


}
