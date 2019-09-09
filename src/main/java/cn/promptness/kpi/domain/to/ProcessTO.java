package cn.promptness.kpi.domain.to;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 开启流程数据的传输对象
 *
 * @author linhuid
 * @date 2019/7/2 19:23
 * @since v1.0.0
 */
@Data
@Slf4j
public class ProcessTO {


    /**
     * 审批人 flow_id
     */
    private String assignee;


    /**
     * 截止时间
     */
    private Date deadLine;

    /**
     * 数据group_id
     */
    private String group;

    /**
     * 数据group_id
     */
    private String subGroup;


    /**
     * 考核大组人员
     */
    private List<String> userList;


    /**
     * 考核分组人员
     */
    private List<String> subGroupUserList;


    /**
     * 是否独立考核组
     */
    private Boolean independent;


    /**
     * 当前环节是否结束
     */
    private Boolean finish;


    /**
     * 是否申述
     */
    private Boolean appeal;

    /**
     * 分组id
     */
    private List<Integer> subGroupList;


    public Map<String, Object> getVariables() {

        Map<String, Object> result = Maps.newHashMap();
        List<Field> fields = new ArrayList<>(Arrays.asList(this.getClass().getDeclaredFields()));
        fields.addAll(Arrays.asList(this.getClass().getSuperclass().getDeclaredFields()));

        for (Field field : fields) {
            try {
                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                if (field.get(this) == null) {
                    field.setAccessible(isAccessible);
                    continue;
                }
                if (!Modifier.isStatic(field.getModifiers())) {
                    result.put(field.getName(), field.get(this));
                }
                field.setAccessible(isAccessible);
            } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }
        }

        return result;
    }


}
