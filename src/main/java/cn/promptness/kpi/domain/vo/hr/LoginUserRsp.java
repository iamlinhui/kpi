package cn.promptness.kpi.domain.vo.hr;

import lombok.Data;

import java.io.Serializable;

/**
 * 已登录用户
 *
 * @author pengruib
 * @date 2018/9/25
 * @since 1.0.1
 */
@Data
public class LoginUserRsp implements Serializable {

    /**
     * user_id 也是邮箱前缀
     */
    private String username;

    /**
     * 中文名字
     */
    private String name;

    private String phone;

    private String email;

    private String token;

    private Long expire;

}
