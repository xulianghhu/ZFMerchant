package com.comdosoft.financial.user.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.domain.zhangfu.RepairStatus;
import com.comdosoft.financial.user.domain.zhangfu.UpdateStatus;
import com.comdosoft.financial.user.mapper.zhangfu.CsRepairMapper;
import com.comdosoft.financial.user.utils.OrderUtils;
import com.comdosoft.financial.user.utils.page.Page;
import com.comdosoft.financial.user.utils.page.PageRequest;

@Service
public class CsRepairService {
    
    @Resource
    private CsRepairMapper repairMapper;

    public Page<List<Object>> findAll(MyOrderReq myOrderReq) throws ParseException {
        PageRequest request = new PageRequest(myOrderReq.getPage(), myOrderReq.getPageSize());
        List<Map<String, Object>> o = repairMapper.findAll(myOrderReq);
        int count = repairMapper.count(myOrderReq);
        List<Map<String, Object>> list = putDate(o);
        return new Page<List<Object>>(request, list,count);
    }

    public List<Map<String, Object>> putDate(List<Map<String, Object>> o) throws ParseException {
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        for(Map<String,Object> m: o){
            map = new HashMap<String,Object>();
            String d = (m.get("created_at")+"");
            Date date = sdf.parse(d);
            String c_date = sdf.format(date);
            String status = (m.get("status")+"");
            map.put("id",m.get("id"));
            map.put("status", status);
            map.put("create_time", c_date);
            map.put("terminal_num", m.get("serial_num"));//终端号
            map.put("apply_num", m.get("apply_num"));//维修编号
            map.put("brand_name", m.get("brand_name")+"");
            map.put("brand_number", m.get("brand_number")+"");
            map.put("zhifu_pingtai", m.get("zhifu_pt")+"");
            map.put("merchant_name", m.get("merchant_name")+"");
            map.put("merchant_phone", m.get("mer_phone")+"");
            list.add(map);
        }
        return list;
    }

    public void cancelApply(MyOrderReq myOrderReq) {
        myOrderReq.setRepairStatus(RepairStatus.CANCEL);
        repairMapper.cancelApply(myOrderReq);
    }

    public Map<String,Object> findById(MyOrderReq myOrderReq) throws ParseException {
        Map<String, Object> o = repairMapper.findById(myOrderReq);
        Map<String,Object> map = new HashMap<String,Object>();
        String id = o.get("id").toString();
        map.put("id", id);
        map.put("status", o.get("apply_status"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String apply_time =   o.get("apply_time")+"";
        map.put("apply_num", o.get("apply_num"));//维修编号
        map.put("apply_time", sdf.format(sdf.parse(apply_time)));
        map.put("terminal_num", o.get("serial_num")==null?"":o.get("serial_num"));
        map.put("brand_name", o.get("brand_name")==null?"":o.get("brand_name"));
        map.put("brand_number", o.get("brand_number")==null?"":o.get("brand_number"));
        map.put("zhifu_pingtai", o.get("zhifu_pt")==null?"":o.get("zhifu_pt"));
        map.put("merchant_name", o.get("merchant_name")==null?"":o.get("merchant_name"));
        map.put("merchant_phone", o.get("mer_phone")==null?"":o.get("mer_phone"));
        map.put("repair_price", o.get("repair_price")==null?"":o.get("repair_price"));
        map.put("receiver_addr", o.get("address")==null?"":o.get("address"));
        map.put("receiver_person", o.get("receiver")==null?"":o.get("receiver"));
        map.put("receiver_phone", o.get("receiver_phone")==null?"":o.get("receiver_phone"));
        map.put("change_reason", o.get("description")==null?"":o.get("description"));
        myOrderReq.setId(Integer.parseInt(id));
        List<Map<String,Object>> list = repairMapper.findTraceById(myOrderReq);
        map.put("comments", OrderUtils.getTraceByVoId(myOrderReq, list));
        return map;
    }
    
    public void addMark(MyOrderReq myOrderReq) {
        repairMapper.addMark(myOrderReq);
    }

    public Page<List<Object>> getTraceById(MyOrderReq myOrderReq) throws ParseException {
        List<Map<String,Object>> list = repairMapper.findTraceById(myOrderReq);
        return OrderUtils.getTraceByVoId(myOrderReq, list);
    }
    
    public void resubmitCancel(MyOrderReq myOrderReq) {
        myOrderReq.setUpdateStatus(UpdateStatus.PENDING);
        repairMapper.changeStatus(myOrderReq);
    }

    public Page<List<Object>> search(MyOrderReq myOrderReq) throws ParseException {
        PageRequest request = new PageRequest(myOrderReq.getPage(), myOrderReq.getPageSize());
        List<Map<String, Object>> o = repairMapper.search(myOrderReq);
        int count = repairMapper.countSearch(myOrderReq);
        List<Map<String, Object>> list = putDate(o);
        return new Page<List<Object>>(request, list,count);
    }
}
