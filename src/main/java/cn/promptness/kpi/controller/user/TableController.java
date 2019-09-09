package cn.promptness.kpi.controller.user;

import cn.promptness.kpi.domain.vo.hr.LoginUserRsp;
import cn.promptness.kpi.domain.vo.table.TableReq;
import cn.promptness.kpi.service.impl.TableService;
import cn.promptness.kpi.domain.vo.HttpResult;
import cn.promptness.kpi.support.common.utils.BindUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 卡片步入列表控制器
 *
 * @author linhuid
 * @date 2019/7/2 12:58
 * @since v1.0.0
 */
@RestController
@RequestMapping(value = "/activity")
public class TableController {


    @Resource
    private TableService tableService;

    /**
     * 根据ConsoleType返回不同的数据
     *
     * @param taskReq 请求参数
     * @return 列表数据集合
     * @author linhuid
     * @date 2019/7/2 14:25
     * @since v1.0.0
     */
    @PostMapping(value = "/task", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public HttpResult dealWithTask(@RequestBody @Validated TableReq taskReq) {
        LoginUserRsp user = BindUtils.getUser();
        Object tableData = tableService.getTableData(taskReq.getConsoleId(), user);
        return HttpResult.getSuccessHttpResult(tableData);
    }


}
