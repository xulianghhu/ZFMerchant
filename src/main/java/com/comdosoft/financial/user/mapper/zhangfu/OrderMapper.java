package com.comdosoft.financial.user.mapper.zhangfu;

import java.util.List;
import java.util.Map;

import com.comdosoft.financial.user.domain.query.OrderReq;
import com.comdosoft.financial.user.domain.zhangfu.GoodsPicture;
import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.domain.zhangfu.Order;

public interface OrderMapper {

    void addOrder(OrderReq orderreq);

    List<Map<String, Object>> getGoodInfos(OrderReq orderreq);

    void addOrderGood(OrderReq orderreq);

    Map<String, Object> getGoodInfo(OrderReq orderreq);
    
    List<Map<String, Object>> checkList(OrderReq orderreq);
    
    Map<String, Object> getPayOrder(OrderReq orderreq);

    List<Map<String, Object>> getPayOrderGood(OrderReq orderreq);
    
    void payFinish(OrderReq orderreq);

// ----gch start --------------
    int countMyOrder(MyOrderReq myOrderReq);

    List<Order> findMyOrderAll(MyOrderReq myOrderReq);

    Order findMyOrderById(Integer id);

    void cancelMyOrder(MyOrderReq myOrderReq);

    void comment(MyOrderReq myOrderReq);

    List<GoodsPicture> findPicByGoodId(Integer gid);

    List<Map<String, Object>> findTraceById(MyOrderReq myOrderReq);

    int countOrderSearch(MyOrderReq myOrderReq);

    List<Order> orderSearch(MyOrderReq myOrderReq);

   

   
    
// ------gch end ---------------------
}
