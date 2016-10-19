package org.idou.dao;

import org.apache.ibatis.annotations.Param;
import org.idou.entity.SeckillDetail;

/**
 * Created by Administrator on 2016/10/17.
 */
public interface SeckillDetailDao {
    /**
     * 插入购买明细，可过滤重复
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

    /**
     * 根据ID查询SeckillDetail并携带秒杀产品对象实体
     * @param seckillId
     * @return
     */
    SeckillDetail queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
}
