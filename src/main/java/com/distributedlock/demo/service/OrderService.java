package com.distributedlock.demo.service;

public interface OrderService {
    public boolean decrementProductStoreLock(int goodsId, int buyNum);
    public boolean decrementProductStoreNoLock(int goodsId, int buyNum);
}
