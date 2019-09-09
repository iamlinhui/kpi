package cn.promptness.kpi.support.listener.event;

import cn.promptness.kpi.domain.pojo.KpiConsole;
import org.springframework.context.ApplicationEvent;

/**
 * 考核出结果更新成绩单
 *
 * @author linhuid
 * @date 2019-07-06 00:25
 * @since v1.0.0
 */
public class UpdateProfileEvent extends ApplicationEvent {


    public UpdateProfileEvent(KpiConsole source) {
        super(source);
    }
}
