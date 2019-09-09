package cn.promptness.kpi.support.config;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author linhuid
 * @date 2019/7/1 12:29
 * @since v1.0.0
 */
@Slf4j
@Configuration
public class WorkflowConfig {

    private static final String FONT_NAME = "宋体";

    @Autowired
    @Bean
    public SpringProcessEngineConfiguration processEngineConfiguration(DataSourceTransactionManager transactionManager, @Qualifier("workflowDataSource") DataSource dataSource) {
        SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
        processEngineConfiguration.setDataSource(dataSource);
        processEngineConfiguration.setTransactionManager(transactionManager);
        //数据库模式：更新； 第一次运行：创建各个表 以后运行：修改表；
        processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE);
        processEngineConfiguration.setAsyncExecutorActivate(Boolean.FALSE);
        //默认用 英文字体生成图之类的数据
        processEngineConfiguration.setActivityFontName(FONT_NAME);
        processEngineConfiguration.setAnnotationFontName(FONT_NAME);
        processEngineConfiguration.setLabelFontName(FONT_NAME);

        return processEngineConfiguration;
    }

    /**
     * 流程引擎的配置信息: ProcessEngine： 流程引擎可以得到7个service组件
     */
    @Bean
    @Autowired
    public ProcessEngineFactoryBean processEngine(SpringProcessEngineConfiguration processEngineConfiguration) {
        ProcessEngineFactoryBean processEngine = new ProcessEngineFactoryBean();
        processEngine.setProcessEngineConfiguration(processEngineConfiguration);
        return processEngine;
    }

    /**
     * 持久化服务组件:负责保存流程定义信息等
     */
    @Bean
    @Autowired
    public RepositoryService repositoryService(ProcessEngine processEngine) {
        return processEngine.getRepositoryService();
    }

    /**
     * 运行时服务组件：查询流程运行期间一些信息，控制流程等
     */
    @Bean
    @Autowired
    public RuntimeService runtimeService(ProcessEngine processEngine) {
        return processEngine.getRuntimeService();
    }

    /**
     * 任务服务组件：完成任务，签收任务等...
     */
    @Bean
    @Autowired
    public TaskService taskService(ProcessEngine processEngine) {
        return processEngine.getTaskService();
    }

    /**
     * 询流程历史记录等信息；历史组件
     */
    @Bean
    @Autowired
    public HistoryService historyService(ProcessEngine processEngine) {
        return processEngine.getHistoryService();
    }

    /**
     * 管理组件，监听流程、定时任务等...
     */
    @Bean
    @Autowired
    public ManagementService managementService(ProcessEngine processEngine) {
        return processEngine.getManagementService();
    }

    /**
     * 用户模块组件，CRUD一些用户以及用户分组等信息
     */
    @Bean
    @Autowired
    public IdentityService identityService(ProcessEngine processEngine) {
        return processEngine.getIdentityService();
    }

    /**
     * 表单组件：没用
     */
    @Bean
    @Autowired
    public FormService formService(ProcessEngine processEngine) {
        return processEngine.getFormService();
    }
}
