package cn.promptness.kpi.support.common;

import javax.servlet.http.Cookie;

/**
 * @author linhuid
 * @date 2019/7/1 20:07
 * @since v1.0.0
 */
public interface Constants {

    Cookie DEFAULT_COOKIE = new Cookie("DEFAULT_COOKIE", "");

    String TOKEN_COOKIE_KEY = "yjs_sso_token";

    String USER_SESSION_KEY = "yjs_sso_user";

    String REQUEST_LOGIN_USER_ROLE = "___login_user_role___";

    String PROCESS_DEFINITION_KEY = "kpi";

    String AUTO_DISTRIBUTE_SUBGROUP = "auto_distribute_subgroup";

    String SUB_PROCESS_ACTIVITY_ID = "subgroup_sub_process";

    String SUB_TASK_DEFINITION_KEY = "subgroup_score";
    
    String TOP_TASK_DEFINITION_KEY = "group_score";

}
