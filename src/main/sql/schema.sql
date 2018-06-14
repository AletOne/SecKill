-- sql initial
CREATE DATABASE seckill;
use seckill;
CREATE TABLE seckill (
`seckill_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
`name` VARCHAR (120) NOT NULL,
`number` INT NOT NULL,
`start_time` TIMESTAMP NOT NULL,
`end_time` TIMESTAMP NOT NULL,
`created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (seckill_id),
KEY idx_start_time(start_time),
KEY idx_end_time(end_time),
KEY idx_create_time(created_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT '秒杀库存表';

INSERT INTO
  seckill(name, number, start_time, end_time)
VALUES
  ('1000元秒杀iPhone7', 100, '2018-04-07 00:00:00', '2018-04-08 00:00:00'),
  ('1000元秒杀iPhone6', 100, '2018-04-07 00:00:00', '2018-04-08 00:00:00'),
  ('1000元秒杀iPhone6s', 100, '2018-04-07 00:00:00', '2018-04-08 00:00:00'),
  ('1000元秒杀iPhone5', 100, '2018-04-07 00:00:00', '2018-04-08 00:00:00');

-- 秒杀成功明细表
-- 用户登录认证相关信息
CREATE TABLE success_killed(
  `seckill_id` bigint NOT NULL,
  `user_phone` bigint NOT NULL,
  `state` tinyint NOT NULL DEFAULT -1 comment '-1表示无效, 0: 成功， 1： 付款， 2： 已发货',
  `created_time` TIMESTAMP NOT NULL,
  PRIMARY KEY (seckill_id, user_phone), /*联合主键*/
  KEY idx_create_time(created_time)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT '秒杀成功明细表';

-- mysql -uwang -p123.321wang
-- mysql -u root -p WangQi123.321