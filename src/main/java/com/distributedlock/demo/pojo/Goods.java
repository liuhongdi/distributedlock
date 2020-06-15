package com.distributedlock.demo.pojo;

public class Goods {

    //商品id
    private int goods_id;
    public int getGoods_id() {
        return this.goods_id;
    }
    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    //商品名字
    private String goods_name;
    public String getGoods_name() {
        return this.goods_name;
    }
    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    //商品库存数
    private int stock;
    public int getStock() {
        return this.stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
}
