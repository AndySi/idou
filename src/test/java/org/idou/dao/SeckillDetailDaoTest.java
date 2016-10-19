package org.idou.dao;

import org.idou.entity.SeckillDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by ZhangSi on 2016/10/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDetailDaoTest {
    @Resource
    private SeckillDetailDao seckillDetailDao;
    @Test
    public void insertSuccessKilled() throws Exception {
        long id = 1000L;
        long phone = 18573163183L;
        int insertCount = seckillDetailDao.insertSuccessKilled(id, phone);
        System.out.println("insertCount================"+insertCount);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        long id = 1000L;
        long phone = 18573163183L;
        SeckillDetail seckillDetail = seckillDetailDao.queryByIdWithSeckill(id, phone);
        System.out.println(seckillDetail);
        System.out.println(seckillDetail.getSeckill());
    }

}