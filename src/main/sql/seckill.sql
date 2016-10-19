DELIMITER $$

USE `idou`$$

DROP PROCEDURE IF EXISTS `execute_seckill`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `execute_seckill`(
  IN v_seckill_id BIGINT,
  IN v_phone BIGINT,
  IN v_kill_time TIMESTAMP,
  OUT r_result INT
)
BEGIN
  DECLARE insert_count INT DEFAULT 0 ;
  START TRANSACTION ;
  INSERT IGNORE INTO tb_seckill_detail (
    `seckill_id`,
    `user_phone`,
    `create_time`
  )
  VALUES
    (
      v_seckill_id,
      v_phone,
      v_kill_time
    ) ;
  SELECT  ROW_COUNT() INTO insert_count ;
  IF(insert_count = 0) THEN
	ROLLBACK ;
	SET r_result = - 1 ;
  ELSEIF (insert_count < 0) THEN
	ROLLBACK ;
	SET r_result = - 2 ;
  ELSE
	UPDATE
	    `tb_seckill`
	SET
	    `number` = `number` - 1
	WHERE `seckill_id` = v_seckill_id
	    AND `end_time` > v_kill_time
	    AND `number` > 0 ;
	SELECT ROW_COUNT() INTO insert_count ;
	  IF(insert_count = 0)
		THEN ROLLBACK ;
		SET r_result = 0 ;
	  ELSEIF (insert_count < 0)
		THEN ROLLBACK ;
		SET r_result = - 2 ;
	  ELSE
		COMMIT ;
		SET r_result = 1 ;
	  END IF ;
  END IF ;
END$$

DELIMITER ;

-- 调用存储过程
SET @r_result = -3;
CALL execute_seckill(1000,18573163183,NOW(),@r_result);
SELECT @r_result;

-- 存储过程
-- 1：存储过程优化：事务行级锁持有的时间
-- 2：不要过度依赖存储过程
-- 3：简单的逻辑可以应用存储过程
-- 4：QPS：一个秒杀6000/qps