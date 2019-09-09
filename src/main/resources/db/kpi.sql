/*
SQLyog Ultimate v11.25 (64 bit)
MySQL - 8.0.13 : Database - kpi
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`kpi` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;

USE `kpi`;

/*Table structure for table `kpi_console` */

DROP TABLE IF EXISTS `kpi_console`;

CREATE TABLE `kpi_console` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `profile_id` int(11) unsigned DEFAULT '0',
  `project_id` int(11) unsigned DEFAULT '0',
  `group_id` int(11) unsigned DEFAULT '0',
  `flow_id` int(11) unsigned DEFAULT '0',
  `node` int(4) NOT NULL DEFAULT '0' COMMENT '1绩效目标填写;2员工自评;3分组评分;4大组评分;5绩效面谈;6确认结果;7申述中;8处理申述',
  `user_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '',
  `finish_count` int(11) unsigned DEFAULT '0',
  `total_count` int(11) unsigned DEFAULT '0',
  `create_time` timestamp NULL DEFAULT NULL,
  `dead_line` timestamp NULL DEFAULT NULL,
  `finish_time` timestamp NULL DEFAULT NULL,
  `remark` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '',
  `is_hide` tinyint(1) DEFAULT '0',
  `is_finished` tinyint(1) DEFAULT '0' COMMENT '卡片是否完成',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `kpi_group` */

DROP TABLE IF EXISTS `kpi_group`;

CREATE TABLE `kpi_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `parent_id` int(11) DEFAULT '0' COMMENT '上一级ID',
  `process_instance_id` bigint(20) DEFAULT '0' COMMENT '流程实例ID',
  `name` varchar(50) NOT NULL COMMENT '考核组名称',
  `type` tinyint(2) DEFAULT NULL COMMENT '考核方式(1限制KPI占比/自评+打分 2不限KPI占比/上级直接打分 3自评+打分)',
  `category` tinyint(1) DEFAULT '0' COMMENT '类型(0独立 1非独立 2 非独立分组)',
  `kpi_percent` tinyint(4) unsigned DEFAULT '70' COMMENT 'KPI占比',
  `point_ceil` tinyint(4) DEFAULT NULL COMMENT 'KPI+GS条数上限',
  `point_floor` tinyint(4) DEFAULT NULL COMMENT 'KPI+GS条数下限',
  `interviewer_id` varchar(64) DEFAULT NULL COMMENT '绩效面谈人ID',
  `interviewer_name` varchar(100) DEFAULT NULL COMMENT '绩效面谈人名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `office_id` varchar(64) DEFAULT NULL COMMENT '部门ID',
  `office_name` varchar(100) DEFAULT NULL COMMENT '部门名称',
  `project_id` int(11) DEFAULT NULL COMMENT '考核方案ID',
  `creator` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(64) DEFAULT NULL COMMENT '修改者',
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `index_project` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1110 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='绩效考核组';

/*Table structure for table `kpi_group_flow` */

DROP TABLE IF EXISTS `kpi_group_flow`;

CREATE TABLE `kpi_group_flow` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` varchar(64) NOT NULL COMMENT '审批者ID',
  `user_name` varchar(100) DEFAULT NULL COMMENT '审批者名称',
  `type` tinyint(4) DEFAULT NULL COMMENT '审批类型(1评明细/0评总分)',
  `job` varchar(50) DEFAULT NULL COMMENT '职级标签（如组长leader）',
  `level` tinyint(3) unsigned DEFAULT NULL COMMENT '审批级别(第一/二/三级)',
  `is_limit` tinyint(1) DEFAULT NULL COMMENT '是否评分约束(1强制分布评分 0非强制分布评分)',
  `group_id` int(11) DEFAULT NULL COMMENT '考核组ID',
  `creator` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(64) DEFAULT NULL COMMENT '修改者',
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_hide` tinyint(1) DEFAULT '0' COMMENT '是否对上游(前面的审批人)隐藏',
  `project_id` int(11) unsigned DEFAULT NULL COMMENT '考核方案ID',
  `is_finished` tinyint(1) unsigned DEFAULT '0' COMMENT '当前审批是否结束',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1606 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='绩效考核审批流';

/*Table structure for table `kpi_group_member` */

DROP TABLE IF EXISTS `kpi_group_member`;

CREATE TABLE `kpi_group_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` varchar(64) NOT NULL COMMENT '员工ID',
  `user_name` varchar(100) DEFAULT NULL COMMENT '员工名称',
  `office_id` varchar(64) DEFAULT NULL COMMENT '归属部门ID',
  `office_name` varchar(100) DEFAULT NULL COMMENT '归属部门名称',
  `job_name` varchar(100) DEFAULT NULL COMMENT '岗位名称',
  `leader_id` varchar(64) DEFAULT NULL COMMENT '审批经理ID',
  `leader_name` varchar(100) DEFAULT NULL COMMENT '审批经理名称',
  `company_date` datetime DEFAULT NULL COMMENT '入职日期',
  `is_service` tinyint(1) DEFAULT '1' COMMENT '是否在职',
  `is_service_predict` tinyint(1) DEFAULT '1' COMMENT '入职满3个月',
  `is_salary_predict` tinyint(1) DEFAULT '1' COMMENT '是否薪资转正',
  `group_id` int(11) NOT NULL COMMENT '考核组ID',
  `profile_id` int(11) DEFAULT NULL COMMENT '考核表ID',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1278 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='绩效考核关联';

/*Table structure for table `kpi_profile` */

DROP TABLE IF EXISTS `kpi_profile`;

CREATE TABLE `kpi_profile` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户ID',
  `self_score` double(5,2) DEFAULT NULL COMMENT '自评分数',
  `self_result` char(1) DEFAULT NULL COMMENT '自评结果(A/B/C/D/E)',
  `examine_score` double(5,2) DEFAULT NULL COMMENT '考核分数',
  `examine_result` char(1) DEFAULT NULL COMMENT '考核结果(A/B/C/D/E)',
  `final_score` double(5,2) DEFAULT NULL COMMENT '最终分数',
  `final_result` char(1) DEFAULT NULL COMMENT '最终结果(A/B/C/D/E)',
  `states` tinyint(4) DEFAULT '0' COMMENT '状态(0初始化;1目标制定草稿;2目标制定完成;3自评草稿;4自评完成;5考核中;6结果待确认;7申诉中;8申述待确认;9已确认结果)',
  `interview` tinyint(4) DEFAULT '0' COMMENT '是否已经面谈(0 没有/1已经面谈)',
  `project_id` int(11) DEFAULT NULL COMMENT '考核方案ID',
  `target_time` timestamp NULL DEFAULT NULL COMMENT '目标制定时间',
  `self_time` timestamp NULL DEFAULT NULL COMMENT '自评时间',
  `confirm_time` timestamp NULL DEFAULT NULL COMMENT '确认时间',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `self_summary` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '自评综述(800字)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1148 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='绩效考核表概况';

/*Table structure for table `kpi_profile_appeal` */

DROP TABLE IF EXISTS `kpi_profile_appeal`;

CREATE TABLE `kpi_profile_appeal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(45) DEFAULT '' COMMENT '申述人',
  `project_id` bigint(20) DEFAULT NULL COMMENT '季度考核id',
  `profile_id` bigint(20) DEFAULT NULL COMMENT '成绩表id',
  `current_count` int(11) DEFAULT '1' COMMENT '当前申述次数',
  `before_state` double(5,2) DEFAULT '0.00' COMMENT '申述前的成绩',
  `after_state` double(5,2) DEFAULT '0.00' COMMENT '申述后的成绩',
  `status` int(2) DEFAULT '1' COMMENT '申述的状态(1：未处理，2：已处理，3：已撤销)',
  `is_confirm` tinyint(1) DEFAULT '0' COMMENT '员工是否确认(0未确认/1已确认)',
  `handle_user_id` varchar(45) DEFAULT '' COMMENT '处理人',
  `result_type` tinyint(1) DEFAULT '-1' COMMENT '处理结果类型(0驳回/1修改)',
  `state_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '申述时间',
  `handle_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '处理时间',
  `handle_reason` varchar(255) DEFAULT '' COMMENT '处理留言',
  `state_reason` varchar(255) DEFAULT '' COMMENT '申述理由',
  `handle_user_name` varchar(45) DEFAULT '' COMMENT '处理人姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=204 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `kpi_profile_point` */

DROP TABLE IF EXISTS `kpi_profile_point`;

CREATE TABLE `kpi_profile_point` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` tinyint(1) NOT NULL COMMENT '指标类型(0 KPI  1 GS)',
  `target` text COMMENT '关键绩效指标',
  `power` int(5) unsigned DEFAULT NULL COMMENT '权重',
  `explain` text COMMENT '目标值及说明',
  `source` varchar(255) DEFAULT NULL COMMENT '数据来源',
  `statement` varchar(255) DEFAULT NULL COMMENT '完成情况自述',
  `score` double(5,2) DEFAULT NULL COMMENT '自评评分',
  `can_edit` tinyint(1) DEFAULT '1' COMMENT '目标制定能否被编辑（1能 0不能）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `profile_id` int(11) DEFAULT NULL COMMENT '考核表ID',
  `sort` int(2) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2527 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='绩效考核表关键详情';

/*Table structure for table `kpi_profile_score` */

DROP TABLE IF EXISTS `kpi_profile_score`;

CREATE TABLE `kpi_profile_score` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` varchar(64) DEFAULT NULL COMMENT '审批人员工ID',
  `job` varchar(100) DEFAULT NULL COMMENT '审批人职级标签',
  `comment` varchar(255) DEFAULT '' COMMENT '点评',
  `score` double(5,2) DEFAULT '0.00' COMMENT '评分',
  `category` tinyint(4) DEFAULT NULL COMMENT '分类(0总评 1明细分评)',
  `relation_id` int(11) DEFAULT NULL COMMENT '考核表(profile_id)/明细ID(profile_point_id)',
  `flow_id` int(11) DEFAULT NULL COMMENT '流程ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2963 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='绩效考核评分';

/*Table structure for table `kpi_project` */

DROP TABLE IF EXISTS `kpi_project`;

CREATE TABLE `kpi_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) DEFAULT NULL COMMENT '考核方案名称',
  `start_time` datetime DEFAULT NULL COMMENT '考核周期开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '考核周期结束时间',
  `remind_time` varchar(50) DEFAULT NULL COMMENT '催交时间方案(提前天数 逗号分隔)',
  `user_count` smallint(6) DEFAULT NULL COMMENT '考核员工数',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) DEFAULT '0' COMMENT '-9已删除 0草稿 1未确认 2目标制定中 3目标制定结束 4分配考核组中 5分配人员结束 6系统分配流程中 7考核中   9已归档',
  `is_open` tinyint(1) DEFAULT '1' COMMENT '是否启用',
  `creator` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(64) DEFAULT NULL COMMENT '修改者',
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `index_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=168 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='绩效考核方案';

/*Table structure for table `kpi_project_notice` */

DROP TABLE IF EXISTS `kpi_project_notice`;

CREATE TABLE `kpi_project_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `remind_day` tinyint(4) DEFAULT NULL COMMENT '提前天数',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `project_time_id` int(11) DEFAULT NULL COMMENT '考核方案时间点编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_time_reming` (`project_time_id`,`remind_day`)
) ENGINE=InnoDB AUTO_INCREMENT=1748 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `kpi_project_time` */

DROP TABLE IF EXISTS `kpi_project_time`;

CREATE TABLE `kpi_project_time` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `sign` varchar(100) DEFAULT NULL COMMENT '标记（英文）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注（中文）',
  `setting_time` datetime DEFAULT NULL COMMENT '设置时间',
  `type` tinyint(2) DEFAULT '1' COMMENT '类型（0普通 1预设 2实际）',
  `process` varchar(50) DEFAULT NULL COMMENT '对应节点（自评self 组长leader 总监director 总经理manager 总裁ceo 面谈interview 结果result）',
  `project_id` int(11) DEFAULT NULL COMMENT '考核方案ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_project_process_type` (`type`,`process`,`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3015 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
