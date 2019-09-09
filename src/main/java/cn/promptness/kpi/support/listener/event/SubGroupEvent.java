package cn.promptness.kpi.support.listener.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author linhuid
 * @date 2019/7/2 20:58
 * @since v1.0.0
 */
public class SubGroupEvent extends ApplicationEvent {


    public SubGroupEvent(Integer projectId) {
        super(projectId);
    }
}
