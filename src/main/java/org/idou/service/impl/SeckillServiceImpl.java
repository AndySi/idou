package org.idou.service.impl;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.idou.dao.SeckillDao;
import org.idou.dao.SeckillDetailDao;
import org.idou.dao.cache.RedisDao;
import org.idou.dto.Exposer;
import org.idou.dto.SeckillExecution;
import org.idou.entity.Seckill;
import org.idou.entity.SeckillDetail;
import org.idou.enums.SeckillStatuEnum;
import org.idou.exception.RepeatKillException;
import org.idou.exception.SeckillCloseException;
import org.idou.exception.SeckillException;
import org.idou.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhangSi on 2016/10/18.
 */
@Service
public class SeckillServiceImpl implements SeckillService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //注入Service依赖
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SeckillDetailDao seckillDetailDao;
    @Autowired
    private RedisDao redisDao;
    //md5盐值字符串，用于混淆MD5
    private final String slat = "asdfadfa231512sd#@!355448&#222";

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        // 优化点：缓存优化:超时的基础上维护一致性
        //1:访问redis
        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null) {
            //2:访问数据库
            seckill = seckillDao.queryById(seckillId);
            if (seckill == null) {
                return new Exposer(false, seckillId);
            } else {
                //3:放入redis
                redisDao.putSeckill(seckill);
            }
        }

        if (seckill == null) {
            return new Exposer(false, seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        //系统当前时间
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        //转化特定字符串的过程，不可逆
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    @Transactional
    /**
     * 使用注释控制事务方法的优点：
     * 1：开发团队达成一致约定，明确标注事务方法的编程风格
     * 2：保证事务方法的执行时间尽可以短，不要穿插其他的网络操作，RPC/HTTP请求或者剥离到事务方法外部
     * 3：不是所有的方法都需要事务，如只有和条修改操作，只读操作不需要事务控制
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill date rewrite");
        }
        //执行秒杀逻辑：减库存+记录购买行为
        Date nowTime = new Date();
        try {
            //记录购买行为
            int insertCount = seckillDetailDao.insertSuccessKilled(seckillId, userPhone);
            //唯一：seckillId,userPhone
            if (insertCount <= 0) {
                //重复秒杀
                throw new RepeatKillException("seckill repeated");
            } else {
                //减库存
                int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
                if (updateCount <= 0) {
                    //没有更新到记录,秒杀结束 rollback
                    throw new SeckillCloseException("seckill is closed");
                } else {
                    //秒杀成功 commit
                    SeckillDetail seckillDetail = seckillDetailDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatuEnum.SUCCESS, seckillDetail);
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SeckillException("seckill inner error" + e.getMessage());
        }
    }

    public SeckillExecution executeSeckillProdure(long seckillId, long userPhone, String md5) {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            return new SeckillExecution(seckillId, SeckillStatuEnum.DATA_REWRITE);
        }
        Date nowTime = new Date();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("seckillId", seckillId);
        map.put("phone", userPhone);
        map.put("killTime", nowTime);
        map.put("result", null);
        //执行存储过程
        try {
            seckillDao.killByProcedure(map);
            //获取result
            Integer result = MapUtils.getInteger(map, "result", -2);
            if (result == 1) {
                SeckillDetail seckillDetail = seckillDetailDao.queryByIdWithSeckill(seckillId, userPhone);
                return new SeckillExecution(seckillId, SeckillStatuEnum.SUCCESS, seckillDetail);
            } else {
                return new SeckillExecution(seckillId, SeckillStatuEnum.stateOf(result));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SeckillExecution(seckillId, SeckillStatuEnum.INNER_ERROR);
        }
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
