package com.imooc.mall.service.impl;

import com.imooc.mall.dao.ProductMapper;
import com.imooc.mall.dao.ShippingMapper;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.pojo.Cart;
import com.imooc.mall.pojo.Product;
import com.imooc.mall.pojo.Shipping;
import com.imooc.mall.service.ICartService;
import com.imooc.mall.service.IOrderService;
import com.imooc.mall.vo.OrderVo;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private ShippingMapper shippingMapper;
    @Autowired
    private ICartService cartService;

    @Autowired
    private ProductMapper productMapper;
    @Override
    public ResponseVo<OrderVo> create(Integer uid, Integer shippingId) {
        //收货地址校验
        Shipping shipping = shippingMapper.selectByUidAndShippingId(uid,shippingId);
        if (shipping == null){
            return ResponseVo.error(ResponseEnum.SHIPPING_NOT_EXIST);
        }
        //获取购物车（uid）， 校验 是否有商品、库存
        List<Cart> cartList = cartService.listForCart(uid).stream()
                .filter(Cart::getProductSelected)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(cartList)){
            return ResponseVo.error(ResponseEnum.CART_SELECTED_IS_EMPTY);
        }
        //获取cartList里的 productIds
        Set<Integer> productIdSet = cartList.stream()
                .map(Cart::getProductId)
                .collect(Collectors.toSet());
        List<Product> productList = productMapper.selectByProductIdSet(productIdSet);
        Map<Integer,Product> map = productList.stream()
                .collect(Collectors.toMap(Product::getId,product -> product));

        for (Cart cart : cartList){
            //根据productId查数据库
            Product product = map.get(cart.getProductId());
            //是否有商品
            if (product == null){
                return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST,
                        "商品不存在, productId = "+cart.getProductId());
            }
            //库存是否充足
            if (product.getStock() < cart.getQuantity()){
                return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR,
                        "库存不正确，" + product.getName());
            }
        }
        //计算总价格，只计算选中的商品价格

        //生成订单，入库：order和order_item，事务

        //库存减少

        //更新购物车（选中的商品删除）

        //构造orderVo

        return null;
    }
}
