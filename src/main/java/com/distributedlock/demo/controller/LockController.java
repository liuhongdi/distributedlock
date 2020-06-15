package com.distributedlock.demo.controller;

import com.distributedlock.demo.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/lock")
public class LockController {

    @Resource
    OrderService orderService;

    //加锁购买商品
    @GetMapping("/buylock")
    @ResponseBody
    public String buyLock() {
        int goodsId = 3;
        orderService.decrementProductStoreLock(goodsId,1);
        return "success";
    }

    //不加锁购买商品
    @GetMapping("/buynolock")
    @ResponseBody
    public String buyNoLock() {
        int goodsId = 3;
        orderService.decrementProductStoreNoLock(goodsId,1);
        return "success";
    }
}



