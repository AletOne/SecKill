package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

public interface SuccessKillDAO {

    /**
     * insert purchase info, can filter duplicate
     * @param seckillId
     * @param userPhone
     * @return row(s) number for insert; 0 for failed.
     */
    public int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * find success kill and seckill entity
     * @param seckillId
     * @return
     */
    public SuccessKilled findByIdWithSeckillId(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}
