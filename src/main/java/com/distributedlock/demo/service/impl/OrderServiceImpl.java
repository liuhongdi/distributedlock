package com.distributedlock.demo.service.impl;

import com.distributedlock.demo.mapper.GoodsMapper;
import com.distributedlock.demo.pojo.Goods;
import com.distributedlock.demo.service.OrderService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private GoodsMapper goodsMapper;

    /*
    *   加锁减库存
    * */
    @Override
    public boolean decrementProductStoreLock(int goodsId, int buyNum) {
        String key = "dec_store_lock_" + goodsId;
        //生成锁对象
        RLock lock = redissonClient.getLock(key);
        try {
            //2, TimeUnit.MINUTES
            lock.lock(2, TimeUnit.MINUTES);

            boolean upRes = updateGoodsStock(goodsId, buyNum);
            if (upRes == false) {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return false;
        } finally {
            //解锁
            if (lock.isHeldByCurrentThread()){
                System.out.println("----------------release lock");
                lock.unlock();
            }

        }
        return true;
    }

    /*
     *   减库存
     * */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    //@Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean updateGoodsStock(int goodsId, int buyNum) {
        Goods goodsOne = goodsMapper.selectOneGoods(goodsId);
        System.out.println("-------------------------当前库存:"+goodsOne.getStock()+"-------购买数量:"+buyNum);
        if (goodsOne.getStock() < buyNum || goodsOne.getStock() <= 0) {
            System.out.println("------------------------fail:buy fail,return");
            return false;
        }
        int upStock = goodsOne.getStock()-buyNum;
        goodsOne.setStock(upStock);
        int upNum = goodsMapper.updateOneGoodsStock(goodsOne);
        System.out.println("-------------------------success:成交订单数量:"+upNum);
        return true;
    }

    /*
     *   不加锁减库存
     * */
    @Override
    public boolean decrementProductStoreNoLock(int goodsId, int buyNum) {
        //System.out.println("----------decrementProductStoreNoLock");
        return updateGoodsStock(goodsId, buyNum);
    }


}
