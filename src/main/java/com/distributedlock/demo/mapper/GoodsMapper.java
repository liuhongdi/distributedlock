package com.distributedlock.demo.mapper;

import com.distributedlock.demo.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Mapper
public interface GoodsMapper {

    Goods selectOneGoods(int goods_id);
    int updateOneGoodsStock(Goods goodsOne);
}