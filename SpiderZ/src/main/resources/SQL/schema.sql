SET FOREIGN_KEY_CHECKS=0;

DROP TABLE if EXISTS userInfos;

-- http://www.cnblogs.com/lsx1993/p/4663147.html 看看对创建索引的建议，不要使用uuid当innodb数据库的主键
CREATE TABLE users (
    userId                 CHAR(32)     NOT NULL             COMMENT '用户的唯一uuid，hash索引值',
    urlToken               VARCHAR(50)  NOT NULL             COMMENT '用户查询使用的id参数，通常就是第一个用户名',
    name                   VARCHAR(100) NOT NULL DEFAULT '' COMMENT '用户名称',
    gender                 TINYINT(2)   NOT NULL DEFAULT -1 COMMENT '性别',
    headline               VARCHAR(100) NOT NULL DEFAULT '' COMMENT '标题栏个性签名',
    voteUpCount            INTEGER      NOT NULL DEFAULT 0  COMMENT '该用户的回答被赞同的次数',
    favoritedCount         INTEGER      NOT NULL DEFAULT 0  COMMENT '答案被收藏的次数',
    followerCount          INTEGER      NOT NULL DEFAULT 0  COMMENT 'ta被多少人关注',
    followingCount         INTEGER      NOT NULL DEFAULT 0  COMMENT 'ta关注的人数',
    answerCount            INTEGER      NOT NULL DEFAULT 0  COMMENT '提供答案的数目',
    articlesCount          INTEGER      NOT NULL DEFAULT 0  COMMENT '文章数目',
    questionCount          INTEGER      NOT NULL DEFAULT 0  COMMENT '提问数目',
    logsCount              INTEGER      NOT NULL DEFAULT 0  COMMENT '参与公共编辑的次数',
    thankedCount           INTEGER      NOT NULL DEFAULT 0  COMMENT '送出感谢的数目',
    description            TEXT         NOT NULL DEFAULT '' COMMENT '个人描述',
    followingQuestionCount INTEGER      NOT NULL DEFAULT 0  COMMENT '关注问题的数目',
    followingColumnsCount  INTEGER      NOT NULL DEFAULT 0  COMMENT '关注的专栏数目',

    PRIMARY KEY (userId)
) ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- 一对多的用户关系表
CREATE TABLE relationships (
    fromUserId             CHAR(32)     NOT NULL             COMMENT '用户的唯一uuid，hash索引值',
    toUserId               CHAR(32)     NOT NULL             COMMENT '用户的唯一uuid，hash索引值',
    fromUrlToken           VARCHAR(50)  NOT NULL             COMMENT '用户查询使用的id参数，通常就是第一个用户名',
    toUrlToken             VARCHAR(50)  NOT NULL             COMMENT '用户查询使用的id参数，通常就是第一个用户名',
    name                   VARCHAR(100) NOT NULL DEFAULT '' COMMENT '用户名称',
    gender                 TINYINT(2)   NOT NULL DEFAULT -1 COMMENT '性别',
    isFollowed             TINYINT(1)   NOT NULL DEFAULT 0  COMMENT '是否关注',
    isFollowing            TINYINT(1)   NOT NULL DEFAULT 0  COMMENT '是否关注',
    isAdvertiser           TINYINT(1)   NOT NULL DEFAULT 0  COMMENT '是否关注',
    isOrg                  TINYINT(1)   NOT NULL DEFAULT 0  COMMENT '是否关注',
    answerCount            INTEGER      NOT NULL DEFAULT 0  COMMENT '提供答案的数目',
    followerCount          INTEGER      NOT NULL DEFAULT 0  COMMENT '粉丝数量',
    articlesCount          INTEGER      NOT NULL DEFAULT 0  COMMENT '文章数量',
    headline               VARCHAR(100) NOT NULL DEFAULT '' COMMENT '标题栏个性签名',
    url                    VARCHAR(200) NOT NULL DEFAULT '' COMMENT '用户名称',
    avatarUrlTemplate      VARCHAR(200) NOT NULL DEFAULT '' COMMENT '用户名称',
    avatarUrl              VARCHAR(200) NOT NULL DEFAULT '' COMMENT '用户名称',
    PRIMARY KEY (fromUserId, toUserId),
    INDEX Index_urlToken (fromUrlToken, toUrlToken)
) ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT='用户关系表';