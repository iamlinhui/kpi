package cn.promptness.kpi.service.rpc;

import cn.promptness.kpi.domain.vo.hr.LoginUserRsp;
import cn.promptness.kpi.domain.vo.hr.RoleRsp;
import cn.promptness.kpi.support.common.Constants;
import cn.promptness.kpi.support.common.enums.RoleTypeEnum;
import cn.promptness.kpi.support.common.utils.HttpClientUtils;
import cn.promptness.kpi.support.common.utils.HttpUtils;
import cn.promptness.kpi.support.config.properties.HrProperties;
import cn.promptness.kpi.support.config.properties.SsoServerProperties;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cn.promptness.kpi.domain.vo.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author linhuid
 * @date 2019/7/1 20:32
 * @since v1.0.0
 */
@Service
@Slf4j
public class UserService {

    @Resource
    private SsoServerProperties ssoServerProperty;
    @Resource
    private HttpClientUtils httpClientUtils;
    @Resource
    private HrProperties hrProperties;

    private final static String URL_FORMAT = "%s://%s:%s%s";

    /**
     * 更据token获取用户基本信息
     *
     * @param token sso口令
     * @return 对应用户信息
     * @author linhuid
     * @date 2019/7/1 21:39
     * @since v1.0.0
     */
    public LoginUserRsp getUserByToken(String token) {
        String uri = String.format("/api/users/info/tokens/%s", token);
        String url = String.format(URL_FORMAT, this.ssoServerProperty.getSchema(), this.ssoServerProperty.getHost(), this.ssoServerProperty.getPort(), uri);

        try {
            HttpResult httpResult = httpClientUtils.doGet(url);

            if (httpResult.isFailed()) {
                return null;
            }
            HttpResult<LoginUserRsp> rspHttpResult = new Gson().fromJson(httpResult.getContent().toString(), new TypeToken<HttpResult<LoginUserRsp>>() {
            }.getType());

            if (HttpResult.getDefaultHttpResult().getCode() != rspHttpResult.getCode()) {
                return null;
            }
            return (LoginUserRsp) httpResult.getContent();
        } catch (Exception e) {
            log.error("获取用户信息失败:{}", e.getMessage());
        }
        return null;
    }

    /**
     * 角色校验
     *
     * @param userId 用户id
     * @return 用户是否有指定角色的权限
     * @author linhuid
     * @date 2019/7/1 21:38
     * @since v1.0.0
     */
    public boolean checkApprovalRole(String userId, RoleTypeEnum... roleTypeEnums)  {
        if (roleTypeEnums.length == 0) {
            return false;
        }

        try {
            for (RoleTypeEnum roleTypeEnum : roleTypeEnums) {
                boolean approvalRole = checkApprovalRole(userId, roleTypeEnum);
                if (approvalRole) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 角色校验
     *
     * @param userId 用户id
     * @return 用户是否有指定角色的权限
     * @author linhuid
     * @date 2019/7/1 21:38
     * @since v1.0.0
     */
    @SuppressWarnings("unchecked")
    private boolean checkApprovalRole(String userId, RoleTypeEnum roleTypeEnum) throws Exception {

        Object attribute = HttpUtils.getRequest().getSession().getAttribute(Constants.REQUEST_LOGIN_USER_ROLE);
        if (attribute == null) {
            HttpResult httpResult = httpClientUtils.doPost(hrProperties.getUserRoleApi(), ImmutableMap.of("userId", userId));
            List<RoleRsp> roleRspList = new Gson().fromJson(httpResult.getContent().toString(), new TypeToken<List<RoleRsp>>() {
            }.getType());
            List<String> roleNameList = roleRspList.stream().map(RoleRsp::getName).collect(Collectors.toList());
            if (roleNameList.isEmpty()) {
                return false;
            }
            HttpUtils.getRequest().getSession().setAttribute(Constants.REQUEST_LOGIN_USER_ROLE, roleNameList);
            if (roleNameList.contains(RoleTypeEnum.PERFORMANCE_MANAGER.getName())) {
                return true;
            }
            return roleNameList.contains(roleTypeEnum.getName());

        }

        List<String> roleNameList = (List<String>) attribute;
        if (roleNameList.contains(RoleTypeEnum.PERFORMANCE_MANAGER.getName())) {
            return true;
        }

        return roleNameList.contains(roleTypeEnum.getName());

    }
}
