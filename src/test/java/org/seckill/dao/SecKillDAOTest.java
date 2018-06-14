package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SecKill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置junit spring整合
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉jnuit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SecKillDAOTest {

    //注入DAO实现依赖
    @Resource
    private SecKillDAO secKillDAO;

    @Test
    public void findById() {
        long id = 1000;
        SecKill secKill = secKillDAO.findById(id);
        System.out.println(secKill.getName());
        System.out.println(secKill);
        /**
         * 1000元秒杀iPhone7
         *SecKill{seckillId=1000,
         * name='1000元秒杀iPhone7',
         * number=100,
         * startTime=Sat Apr 07 00:00:00 PDT 2018,
         * endTime=Sun Apr 08 00:00:00 PDT 2018,
         * createdTime=Mon Apr 09 22:23:36 PDT 2018}
         */
    }

    @Test
    public void findAll() {
//        Caused by: org.apache.ibatis.binding.BindingException: Parameter 'offset' not found. Available parameters are [0, 1, param1, param2]
//        java 不保存形参 : findAll(offset, limit) -> findAll(arg0, arg1);
//
        List<SecKill> secKills = secKillDAO.findAll(0, 100);
        for (SecKill secKill : secKills){
            System.out.println(secKill);
        }

        /**
         * SecKill{seckillId=1000, name='1000元秒杀iPhone7', number=100, startTime=Sat Apr 07 00:00:00 PDT 2018, endTime=Sun Apr 08 00:00:00 PDT 2018, createdTime=Mon Apr 09 22:23:36 PDT 2018}
         SecKill{seckillId=1001, name='1000元秒杀iPhone6', number=100, startTime=Sat Apr 07 00:00:00 PDT 2018, endTime=Sun Apr 08 00:00:00 PDT 2018, createdTime=Mon Apr 09 22:23:36 PDT 2018}
         SecKill{seckillId=1002, name='1000元秒杀iPhone6s', number=100, startTime=Sat Apr 07 00:00:00 PDT 2018, endTime=Sun Apr 08 00:00:00 PDT 2018, createdTime=Mon Apr 09 22:23:36 PDT 2018}
         SecKill{seckillId=1003, name='1000元秒杀iPhone5', number=100, startTime=Sat Apr 07 00:00:00 PDT 2018, endTime=Sun Apr 08 00:00:00 PDT 2018, createdTime=Mon Apr 09 22:23:36 PDT 2018}

         */
    }

    @Test
    public void reduceNumber() {
        Date date = new Date();
        int count = secKillDAO.reduceNumber(1000L, date);
        System.out.println(count);
    }


}