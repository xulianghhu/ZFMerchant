package com.comdosoft.financial.user.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.user.domain.query.OrderReq;
import com.comdosoft.financial.user.domain.query.PosReq;
import com.comdosoft.financial.user.domain.zhangfu.Good;
import com.comdosoft.financial.user.domain.zhangfu.GoodsPicture;
import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.domain.zhangfu.Order;
import com.comdosoft.financial.user.domain.zhangfu.OrderGood;
import com.comdosoft.financial.user.domain.zhangfu.OrderStatus;
import com.comdosoft.financial.user.mapper.zhangfu.GoodMapper;
import com.comdosoft.financial.user.mapper.zhangfu.OrderMapper;
import com.comdosoft.financial.user.mapper.zhangfu.ShopCartMapper;
import com.comdosoft.financial.user.utils.OrderUtils;
import com.comdosoft.financial.user.utils.SysUtils;
import com.comdosoft.financial.user.utils.Exception.LowstocksException;
import com.comdosoft.financial.user.utils.page.Page;
import com.comdosoft.financial.user.utils.page.PageRequest;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private GoodMapper goodMapper;
    @Autowired
    private ShopCartMapper shopCartMapper;

    @Transactional(value = "transactionManager-zhangfu")
    public int createOrderFromCart(OrderReq orderreq) throws LowstocksException {
        orderreq.setCartids(SysUtils.Arry2Str(orderreq.getCartid()));
        int totalprice = 0;
        int count = 0;
        List<Map<String, Object>> checkList = orderMapper.checkList(orderreq);
        for (Map<String, Object> map : checkList) {
            int count2 = SysUtils.String2int("" + map.get("count"));
            int quantity = SysUtils.String2int("" + map.get("quantity"));
            if (count2 < quantity) {
                throw new LowstocksException("库存不足");
            } else {
                int goodId = SysUtils.String2int("" + map.get("goodid"));
                PosReq posreq = new PosReq();
                posreq.setGoodId(goodId);
                posreq.setCity_id(count2-quantity);
                goodMapper.upQuantity(posreq);
            }
        }
        List<Map<String, Object>> goodMapList = orderMapper.getGoodInfos(orderreq);
        for (Map<String, Object> map : goodMapList) {
            int retail_price = SysUtils.String2int("" + map.get("retail_price"));
            int quantity = SysUtils.String2int("" + map.get("quantity"));

            int opening_cost = SysUtils.String2int("" + map.get("opening_cost"));
            totalprice += (retail_price + opening_cost) * quantity;
            count += quantity;
        }
        orderreq.setTotalcount(count);
        orderreq.setTotalprice(totalprice);
        orderreq.setOrdernumber(SysUtils.getOrderNum(0));
        orderMapper.addOrder(orderreq);
        for (Map<String, Object> map : goodMapList) {
            orderreq.setGoodId(SysUtils.String2int("" + map.get("goodid")));
            orderreq.setPaychannelId(SysUtils.String2int("" + map.get("paychanelid")));
            orderreq.setQuantity(SysUtils.String2int("" + map.get("quantity")));
            int price = SysUtils.String2int("" + map.get("price"));
            int retail_price = SysUtils.String2int("" + map.get("retail_price"));
            int quantity = SysUtils.String2int("" + map.get("quantity"));
            int opening_cost = SysUtils.String2int("" + map.get("opening_cost"));
            orderreq.setPrice(price + opening_cost);
            orderreq.setRetail_price(retail_price + opening_cost);
            orderreq.setQuantity(quantity);
            orderMapper.addOrderGood(orderreq);
            shopCartMapper.delete(SysUtils.String2int("" + map.get("id")));
        }
        return orderreq.getId();
    }

    @Transactional(value = "transactionManager-zhangfu")
    public int createOrderFromShop(OrderReq orderreq) throws LowstocksException{
            Map<String, Object> goodMap = orderMapper.getGoodInfo(orderreq);
            int retail_price = SysUtils.String2int("" + goodMap.get("retail_price"));
            int quantity = orderreq.getQuantity();
            int count = SysUtils.String2int("" + goodMap.get("count"));
            if (count < quantity) {
                throw new LowstocksException("库存不足");
            } else {
                int goodId = SysUtils.String2int("" + goodMap.get("goodid"));
                PosReq posreq = new PosReq();
                posreq.setGoodId(goodId);
                posreq.setCity_id(count-quantity);
                goodMapper.upQuantity(posreq);
            }
            int opening_cost = SysUtils.String2int("" + goodMap.get("opening_cost"));
            int totalprice = (retail_price + opening_cost) * quantity;
            orderreq.setTotalcount(quantity);
            orderreq.setTotalprice(totalprice);
            orderreq.setOrdernumber(SysUtils.getOrderNum(1));
            orderreq.setType(1);
            orderMapper.addOrder(orderreq);
            int price = SysUtils.String2int("" + goodMap.get("price"));
            orderreq.setPrice(price + opening_cost);
            orderreq.setRetail_price(retail_price + opening_cost);
            orderMapper.addOrderGood(orderreq);
            return orderreq.getId();
    }

    @Transactional(value = "transactionManager-zhangfu")
    public int createOrderFromLease(OrderReq orderreq) throws LowstocksException{
            Map<String, Object> goodMap = orderMapper.getGoodInfo(orderreq);
            int lease_deposit = SysUtils.String2int("" + goodMap.get("lease_deposit"));
            int quantity = orderreq.getQuantity();
            int count = SysUtils.String2int("" + goodMap.get("count"));
            if (count < quantity) {
                throw new LowstocksException("库存不足");
            } else {
                int goodId = SysUtils.String2int("" + goodMap.get("goodid"));
                PosReq posreq = new PosReq();
                posreq.setGoodId(goodId);
                posreq.setCity_id(count-quantity);
                goodMapper.upQuantity(posreq);
            }
            int opening_cost = SysUtils.String2int("" + goodMap.get("opening_cost"));
            int totalprice = (lease_deposit + opening_cost) * quantity;
            orderreq.setTotalcount(quantity);
            orderreq.setTotalprice(totalprice);
            orderreq.setOrdernumber(SysUtils.getOrderNum(2));
            orderreq.setType(2);
            orderMapper.addOrder(orderreq);
            orderreq.setPrice(lease_deposit + opening_cost);
            orderreq.setRetail_price(lease_deposit + opening_cost);
            orderMapper.addOrderGood(orderreq);
            return orderreq.getId();
    }

    
    public Map<String, Object> payOrder(OrderReq orderreq) {
        Map<String, Object> map=orderMapper.getPayOrder(orderreq);
        map.put("good", orderMapper.getPayOrderGood(orderreq));
        return map;
    }
    
    public void payFinish(OrderReq orderreq) {
        orderMapper.payFinish(orderreq);
        
    }
    /**
     * 上jwb
     * -------------------------------------------------------------
     * 下gch
     */

    public Page<Object> findMyOrderAll(MyOrderReq myOrderReq) {
        PageRequest request = new PageRequest(myOrderReq.getPage(), myOrderReq.getPageSize());
        int count = orderMapper.countMyOrder(myOrderReq);
        List<Order> centers = orderMapper.findMyOrderAll(myOrderReq);
        List<Object> obj_list = putDate(centers);
        return new Page<Object>(request, obj_list, count);
    }

    public List<Object> putDate(List<Order> centers) {
        List<Object> obj_list = new ArrayList<Object>();
        Map<String,Object> map = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        for(Order o : centers){
            map = new HashMap<String, Object>();
            map.put("order_id", o.getId()+"");
            map.put("order_type", o.getTypes()==null?"":o.getTypes()+"");
            map.put("order_number", o.getOrderNumber()==null?"":o.getOrderNumber());
            if(o.getCreatedAt()!=null){
                String d = sdf.format(o.getCreatedAt());
                map.put("order_createTime", d);
            }else{
                map.put("order_createTime", "");
            }
            map.put("order_status", o.getStatus()==null?"":o.getStatus());
            map.put("order_totalNum", o.getTotalQuantity() == null ? "" : o.getTotalQuantity().toString());// 订单总件数
            map.put("order_totalPrice", o.getActualPrice()==null?"":o.getActualPrice());
            map.put("order_psf", "0");//配送费
            List<OrderGood> olist = o.getOrderGoodsList();
            List<Object> newObjList = new ArrayList<Object>();
            Map<String, Object> omap = null;
//            map.put("goods_list_size", olist.size()); 
            if (olist.size() > 0) {
                for (OrderGood od : olist) {
                    omap = new HashMap<String, Object>();
                    omap.put("good_id",  od.getGood() == null ? "" : od.getGood().getId()==null?"":od.getGood().getId());
                    omap.put("good_volume_number",  od.getGood() == null ? "" : od.getGood().getVolumeNumber()==null?"":od.getGood().getVolumeNumber());//热销量
                    omap.put("good_price", od.getPrice() == null ? "" : od.getPrice()+"");
                    omap.put("good_num", od.getQuantity() == null ? "" : od.getQuantity()+"");
                    omap.put("good_name", od.getGood() == null ? "" : od.getGood().getTitle()==null?"":od.getGood().getTitle());
                    omap.put("good_brand", od.getGood() == null ? "" : od.getGood().getGoodsBrand() == null ? "" : od.getGood().getGoodsBrand().getName()+"");
                    omap.put("good_channel", od.getPayChannel() == null ? "" : od.getPayChannel().getName()==null?"":od.getPayChannel().getName());
                    String good_logo = "";
                    if(null !=od.getGood()){
                        Good g = od.getGood();
                        Integer gid = g.getId();
                        List<GoodsPicture> list = orderMapper.findPicByGoodId(gid);
                        if(list.size()>0){
                            GoodsPicture gp  = list.get(0);
                            good_logo = gp.getUrlPath();
                        }
                     } 
                    omap.put("good_logo", good_logo);
                    newObjList.add(omap);
                }
                map.put("order_goodsList", newObjList);
            }
            obj_list.add(map);
        }
        return obj_list;
    }

    /**
     * 订单详情
     * @param id
     * @return
     * @throws ParseException 
     */
    public Object findMyOrderById(Integer id) throws ParseException {
        Order o = orderMapper.findMyOrderById(id);
        List<Object> obj_list = new ArrayList<Object>();
        Map<String,Object> map = new HashMap<String, Object>();
//        String oid = o.getId()==null ?"":o.getId().toString();
        map.put("order_id", id);
        map.put("order_number", o.getOrderNumber()==null?"":o.getOrderNumber());//订单编号
        map.put("order_payment_type", o.getOrderPayment()==null ?"":o.getOrderPayment().getPayType().getName());//支付方式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        String d = sdf.format(o.getCreatedAt());
        map.put("order_createTime", d);//订单日期
//        map.put("order_pay_status", o.getPayStatus().getName());
        map.put("order_status", o.getStatus());
        map.put("order_totalNum", o.getTotalQuantity() == null ? "" : o.getTotalQuantity().toString());// 订单总件数
        map.put("order_totalPrice", o.getActualPrice()+"");//订单总额
        map.put("order_price", o.getTotalPrice()+"");//订单原价
        map.put("order_psf", "0");//配送费
        map.put("order_receiver", o.getCustomerAddress()==null ?"":o.getCustomerAddress().getReceiver());
        map.put("order_address", o.getCustomerAddress()==null ?"":o.getCustomerAddress().getAddress());
        map.put("order_receiver_phone", o.getCustomerAddress()==null ?"":o.getCustomerAddress().getMoblephone());
        map.put("order_comment", o.getComment()==null?"":o.getComment());//留言
        Integer invoce_type = o.getInvoiceType();
        String invoce_name ="";
        if(null != invoce_type && invoce_type==1){//个人
            invoce_name = "个人";
        }else if(null != invoce_type && invoce_type==0){//公司
            invoce_name = "公司";
        }
        map.put("order_invoce_type", invoce_name);//发票类型
        map.put("order_invoce_info", o.getInvoiceInfo());//发票抬头
        List<OrderGood> olist = o.getOrderGoodsList();
        List<Object> newObjList = new ArrayList<Object>();
        Map<String, Object> omap = null;
        if (olist.size() > 0) {
            OrderGood og = olist.get(0);
            map.put("good_merchant", og.getGood() == null ? "" : og.getGood().getFactory()==null?"":og.getGood().getFactory().getName()==null?"":og.getGood().getFactory().getName());//供货商
            for (OrderGood od : olist) {
                omap = new HashMap<String, Object>();
//                omap.put("order_good_id", od.getId().toString());
                omap.put("good_id", od.getGood() == null ? "" : od.getGood().getId()==null?"":od.getGood().getId());
                omap.put("good_price", od.getPrice() == null ? "" : od.getPrice()+"");
                omap.put("good_num", od.getQuantity() == null ? "" : od.getQuantity()+"");
                omap.put("good_name", od.getGood() == null ? "" : od.getGood().getTitle()==null?"":od.getGood().getTitle());
                omap.put("good_brand", od.getGood() == null ? "" : od.getGood().getGoodsBrand() == null ? "" : od.getGood().getGoodsBrand().getName());
                omap.put("good_channel", od.getPayChannel() == null ? "" : od.getPayChannel().getName()==null?"":od.getPayChannel().getName());
                String good_logo = "";
                if(null !=od.getGood()){
                    Good g = od.getGood();
                    if(g.getPicsList().size()>0){
                        GoodsPicture gp  = g.getPicsList().get(0);
                        good_logo = gp.getUrlPath();
                    }
                }
                omap.put("good_logo", good_logo);
                newObjList.add(omap);
            }
        }
        map.put("order_goodsList", newObjList);
        MyOrderReq myOrderReq = new MyOrderReq();
        myOrderReq.setId(id);
        List<Map<String,Object>> list = orderMapper.findTraceById(myOrderReq);
        map.put("comments", OrderUtils.getTraceByVoId(myOrderReq, list));
        obj_list.add(map);
        return obj_list;
    }

    public void cancelMyOrder(MyOrderReq myOrderReq) {
        myOrderReq.setOrderStatus(OrderStatus.CANCEL);
        orderMapper.cancelMyOrder(myOrderReq);
    }

    public void comment(MyOrderReq myOrderReq) {
        myOrderReq.setOrderStatus(OrderStatus.EVALUATED);
        orderMapper.comment(myOrderReq);
    }

    public Page<Object> orderSearch(MyOrderReq myOrderReq) {
        PageRequest request = new PageRequest(myOrderReq.getPage(), myOrderReq.getPageSize());
        int count = orderMapper.countOrderSearch(myOrderReq);
        List<Order> centers = orderMapper.orderSearch(myOrderReq);
        List<Object> obj_list = putDate(centers);
        return new Page<Object>(request, obj_list, count);
    }

   

}
