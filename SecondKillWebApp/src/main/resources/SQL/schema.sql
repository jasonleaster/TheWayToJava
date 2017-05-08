SET FOREIGN_KEY_CHECKS=0;

DROP TABLE if EXISTS commodityOrder;
DROP TABLE if EXISTS success_killed;

-- 创建库存明细表
CREATE TABLE commodityOrder(
    `commodityId`       BIGINT          NOT NULL  AUTO_INCREMENT          COMMENT '商品库存id',
    `commodityName`     VARCHAR (120)   NOT NULL  DEFAULT ''               COMMENT '商品名称',
    `reservedNumber`    INT             NOT NULL  DEFAULT 0                 COMMENT '库存数量',
    `startTime`         TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀开始时间',
    `endTime`           TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀结束时间',
    `createTime`        TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (commodityId),
    KEY indexStartTime(startTime),
    KEY indexEndTime(endTime),
    KEY indexCreateTime(createTime)
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

-- 初始化数据
INSERT INTO commodityOrder(commodityName,reservedNumber,startTime,endTime)
VALUES
    ('1000元秒杀iphone7',100,'2016-10-02 00:00:00','2016-10-03 00:00:00'),
    ('50元秒杀ipad air',100,'2016-10-02 00:00:00','2016-10-03 00:00:00'),
    ('200元秒杀mac book',100,'2016-10-02 00:00:00','2016-10-03 00:00:00'),
    ('100元秒杀红米note',100,'2016-10-02 00:00:00','2016-10-03 00:00:00');

--  秒杀成功明细表
-- 用户登录认证相关信息
CREATE TABLE successDeals(
    `commodityId`   BIGINT     NOT NULL             COMMENT '秒杀商品id',
    `userPhone`     BIGINT     NOT NULL DEFAULT 0  COMMENT '用户手机号',
    `state`         INT        NOT NULL DEFAULT -1 COMMENT '状态表示-1:无效 0:成功 1:已付款 2:已发货',
    `createTime`    TIMESTAMP NOT NULL             COMMENT '创建时间',
    PRIMARY KEY (commodityId, userPhone),/*联合主键，防止用户重复秒杀*/
    KEY indexCreateTime(createTime)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';
# 连接数据库控制台



