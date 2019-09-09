package cn.promptness.kpi.domain.vo.project;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author linhuid
 * @date 2019/7/2 22:04
 * @since v1.0.0
 */
@Data
public class AddMemberReq {


    @NotNull
    @Min(1)
    private Integer projectId;


    @NotNull
    @NotEmpty
    private List<String> userIdList;


}
