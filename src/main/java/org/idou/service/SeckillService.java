package org.idou.service;

import org.idou.dto.Exposer;
import org.idou.dto.SeckillExecution;
import org.idou.entity.Seckill;
import org.idou.exception.RepeatKillException;
import org.idou.exception.SeckillCloseException;
import org.idou.exception.SeckillException;

import java.util.List;

/**
 * 三个方面：方法定义粒度、参数、返回类型（return类型/异常）
 * Created by ZhangSi on 2016/10/18.
 */
public interface SeckillService {
    /**
     * 查询所有秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启是输出秒杀接口地址，
     * 否则输出系统时间和秒杀时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
        throws SeckillException,RepeatKillException,SeckillCloseException;

    /**
     * 执行秒杀操作
     */
    SeckillExecution executeSeckillProdure(long seckillId, long userPhone, String md5);
}
