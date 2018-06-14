package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SecKill;

import java.util.Date;
import java.util.List;

public interface SecKillDAO {

    /**
     * storage - 1;
     * @param seckillId
     * @param killTime
     * @return >1 for updated row(s) number; 0 for failed
     */
    public int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * search by id
     * @param seckillId
     * @return
     */
    public SecKill findById(long seckillId);

    /**
     * page search
     * @param offset
     * @param limit
     * @return
     */
    public List<SecKill> findAll(@Param("offset") int offset, @Param("limit") int limit);
}
