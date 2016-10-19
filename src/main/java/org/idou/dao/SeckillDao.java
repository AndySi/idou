package org.idou.dao;

import org.apache.ibatis.annotations.Param;
import org.idou.entity.Seckill;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/17.
 */
public interface SeckillDao {
    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return 如果影响行数>1,表示更新行数
     */
    int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime")  Date killTime);

    /**
     * 根据ID查询秒杀对象
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 根据偏移量查询秒杀商品列表
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);

    void killByProcedure(Map<String,Object> paramMap);
}
