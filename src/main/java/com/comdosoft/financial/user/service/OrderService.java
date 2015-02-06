package com.comdosoft.financial.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.user.domain.query.OrderReq;
import com.comdosoft.financial.user.domain.zhangfu.Order;
import com.comdosoft.financial.user.domain.zhangfu.OrderGood;
import com.comdosoft.financial.user.mapper.zhangfu.OrderMapper;
import com.comdosoft.financial.user.utils.SysUtils;
import com.comdosoft.financial.user.utils.page.Page;
import com.comdosoft.financial.user.utils.page.PageRequest;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Transactional
    public int createOrderFromCart(OrderReq orderreq) {
        try {
            orderreq.setCartids(SysUtils.Arry2Str(orderreq.getCartid()));
            int totalprice = 0;
            List<Map<String, Object>> goodMapList = orderMapper.getGoodInfos(orderreq);
            for (Map<String, Object> map : goodMapList) {
                map.get("retail_price");
                map.get("quantity");
                map.get("opening_cost");
                int retail_price = SysUtils.String2int("" + map.get("retail_price"));
                int quantity = SysUtils.String2int("" + map.get("quantity"));
                int opening_cost = SysUtils.String2int("" + map.get("opening_cost"));
                totalprice += (retail_price + opening_cost) * quantity;
            }
            orderreq.setTotalprice(totalprice);
            orderreq.setOrdernumber(SysUtils.getOrderNum(0));
            orderMapper.addOrder(orderreq);
            for (Map<String, Object> map : goodMapList) {
                map.get("opening_cost");
                map.get("opening_cost");
                map.get("opening_cost");
                orderMapper.addOrderGood(orderreq);
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Transactional
    public int createOrderFromShop(OrderReq orderreq) {
        try {
            Map<String, Object> goodMap = orderMapper.getGoodInfo(orderreq);
            goodMap.get("retail_price");
            goodMap.get("quantity");
            goodMap.get("opening_cost");
            int retail_price = SysUtils.String2int("" + goodMap.get("retail_price"));
            int quantity = SysUtils.String2int("" + goodMap.get("quantity"));
            int opening_cost = SysUtils.String2int("" + goodMap.get("opening_cost"));
            int totalprice = (retail_price + opening_cost) * quantity;
            orderreq.setTotalprice(totalprice);
            orderreq.setOrdernumber(SysUtils.getOrderNum(0));
            orderMapper.addOrder(orderreq);
            orderMapper.addOrderGood(orderreq);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Page<Object> findAll(Integer page, Integer pageSize, String pid) {
        PageRequest request = new PageRequest(page, pageSize);
        int count = orderMapper.count(pid);
        List<Order> centers = orderMapper.findAll(request, pid);
        List<Object> obj_list = new ArrayList<Object>();
        Map<String, Object> map = null;
        for (Order o : centers) {
            map = new HashMap<String, Object>();
            map.put("order_id", o.getId().toString());
            map.put("order_number", o.getOrderNumber());
            map.put("order_createTime", o.getCreatedAt());
            map.put("order_pay_status", o.getPayStatus());
            map.put("order_totalNum", o.getTotalQuantity() == null ? "" : o.getTotalQuantity().toString());// 订单总件数
            map.put("order_totalPrice", o.getActualPrice());
            map.put("order_psf", "");// 配送费
            List<OrderGood> olist = o.getOrderGoodsList();
            List<Object> newObjList = new ArrayList<Object>();
            Map<String, Object> omap = null;
            if (olist.size() > 0) {
                for (OrderGood od : olist) {
                    omap = new HashMap<String, Object>();
                    omap.put("good_id", od.getGood() == null ? "" : od.getGood().getId().toString());
                    omap.put("good_price", od.getPrice() == null ? "" : od.getPrice().toString());
                    omap.put("good_num", od.getQuantity() == null ? "" : od.getQuantity().toString());
                    omap.put("good_name", od.getGood() == null ? "" : od.getGood().getTitle());
                    omap.put("good_brand", od.getGood() == null ? "" : od.getGood().getGoodsBrand() == null ? "" : od.getGood().getGoodsBrand().getName());
                    omap.put("good_channel", od.getPayChannel() == null ? "" : od.getPayChannel().getName());
                    newObjList.add(omap);
                }
            }
            map.put("order_goodsList", newObjList);
            obj_list.add(map);
        }
        return new Page<Object>(request, obj_list, count);

    }
}