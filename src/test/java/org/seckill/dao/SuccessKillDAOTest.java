package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKillDAOTest {

    @Resource
    private SuccessKillDAO successKillDAO;
    @Test
    public void insertSuccessKilled() {
        int count = successKillDAO.insertSuccessKilled(1001L, 15084919594L);
        System.out.println(count);
    }

    @Test
    public void findByIdWithSeckillId() {
        SuccessKilled successKilled = successKillDAO.findByIdWithSeckillId(1001L, 15084919594L);
        System.out.println(successKilled);
        System.out.println(successKilled.getSecKill());

//        SuccessKilled{seckillId=1000,
//              userPhone=15084919594,
//              state=-1,
//              createdTime=Sun Apr 15 16:15:35 PDT 2018}
//        SecKill{seckillId=1000,
//              name='1000元秒杀iPhone7',
//              number=100,
//              startTime=Sat Apr 07 00:00:00 PDT 2018,
//              endTime=Sun Apr 08 00:00:00 PDT 2018,
//              createdTime=Mon Apr 09 22:23:36 PDT 2018}

    }
}