package org.idou.service;

import org.idou.dto.Exposer;
import org.idou.dto.SeckillExecution;
import org.idou.entity.Seckill;
import org.idou.exception.RepeatKillException;
import org.idou.exception.SeckillCloseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ZhangSi on 2016/10/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;
    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        //｛｝占位符
        logger.info("list={}",list);
    }

    @Test
    public void getById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}",seckill);
    }

    /**
     * 测试代码完整逻辑，注意可重复执行
     * @throws Exception
     */
    @Test
    public void exportSeckillUrl() throws Exception {
        long id=1001;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if(exposer.isExposed()){
            logger.info("exposer={}",exposer);
            long phone = 18573163183L;
            String md5 = "d29ff7311bd1a13370a617ab104d6fbd";
            try{
                SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
                logger.info("result={}",seckillExecution);
            }catch (RepeatKillException e){
                logger.info(e.getMessage());
            }catch (SeckillCloseException e){
                logger.info(e.getMessage());
            }
        }else{
            //秒杀未开启
            logger.warn("exposer={}",exposer);
        }
    }

    @Test
    public void executeSeckill() throws Exception {
        long id = 1001;
        long phone = 18573163183L;
        String md5 = "d29ff7311bd1a13370a617ab104d6fbd";
        try{
            SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
            logger.info("result={}",seckillExecution);
        }catch (RepeatKillException e){
            logger.info(e.getMessage());
        }catch (SeckillCloseException e){
            logger.info(e.getMessage());
        }
    }

}