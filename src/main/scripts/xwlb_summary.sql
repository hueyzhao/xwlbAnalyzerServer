create table xwlb_summary (
`id` int(11) not null auto_increment comment 'ID',
`url` varchar(1024) default '' comment '新闻联播摘要的url',
`title` varchar(512) default '' comment '新闻标题',
`content` text comment '摘要内容',
`ctime` int(11) default 0 comment '摘要发布时间',
`utime` int(11) default 0 comment '记录更新时间',
primary key (`id`),
key `idx_ctime` (`ctime`)
)engine=InnoDB auto_increment=1 default charset=utf8mb4 comment='新闻联播摘要表';