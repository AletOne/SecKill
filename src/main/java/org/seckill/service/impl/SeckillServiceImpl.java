package org.seckill.service.impl;

import org.seckill.dao.SecKillDAO;
import org.seckill.dao.SuccessKillDAO;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.SecKill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private SecKillDAO secKillDAO;

    private SuccessKillDAO successKillDAO;

    //md5盐值字符串
    private final static String salt = "dfjwq9p4tgpf43986u24gnfoeijd2qp0984y6245p98tfjqo3ty42gtq3";

    @Override
    public List<SecKill> getSeckillList() {
        return secKillDAO.findAll(0, 4);
    }

    @Override
    public SecKill getById(Long seckillId) {
        return secKillDAO.findById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        SecKill secKill = secKillDAO.findById(seckillId);
        if (secKill == null){
            return new Exposer(false, seckillId);
        }

        Date startTime = secKill.getStartTime();
        Date endTime = secKill.getEndTime();
        Date currTime = new Date();
        if (currTime.getTime() < startTime.getTime()
                || currTime.getTime() > startTime.getTime()){
            return new Exposer(false, seckillId, currTime.getTime(), startTime.getTime(), endTime.getTime());
        }

        //md5 不可逆
        String md5 = getMD5(seckillId);//ToDo
        return new Exposer(true, md5, seckillId);
    }

    @Override
    public SeckillExecution excuteSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if (md5 == null || !md5.equals(getMD5(seckillId))){
            throw new SeckillException("seckill data rewrite");
        }

        //秒杀逻辑: 减库存+秒杀行为
        Date currTime = new Date();
        try {
            int updateCount = secKillDAO.reduceNumber(seckillId, currTime);
            if (updateCount <= 0){
                //没有更新到记录
                throw new SeckillCloseException("Closed!");
            }

            //记录购买行为
            int insertCount = successKillDAO.insertSuccessKilled(seckillId, userPhone);
            if (insertCount <= 0){
                //重复秒杀
                throw new RepeatKillException("User has bought this product");
            }

            //秒杀成功
            SuccessKilled successKilled = successKillDAO.findByIdWithSeckillId(seckillId, userPhone);
            return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
        }catch (SeckillCloseException | RepeatKillException e1){
            throw e1;
        }catch (Exception e){
            //转换编译异常为runtime 异常，方便spring做rollback
            logger.error(e.getMessage(), e);
            throw new SeckillException("seckill inner error " + e.getMessage());
        }

    }

    private String getMD5(long seckillId){
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
