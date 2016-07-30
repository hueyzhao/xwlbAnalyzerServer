create table xwlb_wordcount (
`id` int(11) not null auto_increment comment 'ID',
`keyword` varchar(1024) default '' comment '新闻联播摘要的分词',
`count` int(11) default 0 comment '新闻联播摘要的分词出现次数',
`publish_time` int(11) comment '摘要发版时间',
`ctime` int(11) default 0 comment '记录创建时间',
`utime` int(11) default 0 comment '记录更新时间',
primary key (`id`),
  key `idx_publish_time` (`publish_time`)
)engine=InnoDB auto_increment=1 default charset=utf8mb4 comment='新闻联播词频表';