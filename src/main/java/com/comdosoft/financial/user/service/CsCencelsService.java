package com.comdosoft.financial.user.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.domain.zhangfu.RepairStatus;
import com.comdosoft.financial.user.domain.zhangfu.UpdateStatus;
import com.comdosoft.financial.user.mapper.zhangfu.CsCencelsMapper;
import com.comdosoft.financial.user.utils.OrderUtils;
import com.comdosoft.financial.user.utils.page.Page;
import com.comdosoft.financial.user.utils.page.PageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class CsCencelsService {

    @Resource
    private CsCencelsMapper csCencelsMapper;
    public Page<List<Object>>  findAll(MyOrderReq myOrderReq) throws ParseException {
        PageRequest request = new PageRequest(myOrderReq.getPage(), myOrderReq.getPageSize());
        int count = csCencelsMapper.count(myOrderReq);
        List<Map<String, Object>> o = csCencelsMapper.findAll(myOrderReq);
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

    /**
     * 取消申请
     * @param myOrderReq
     */
    public void cancelApply(MyOrderReq myOrderReq) {
        myOrderReq.setRepairStatus(RepairStatus.CANCEL);
        csCencelsMapper.cancel(myOrderReq);
    }

    public Map<String,Object> findById(MyOrderReq myOrderReq) throws ParseException {
        Map<String, Object> o = csCencelsMapper.findById(myOrderReq);
        Map<String,Object> map = new HashMap<String,Object>();
        String id = o.get("id").toString();
        map.put("id", id);
        map.put("status", o.get("apply_status"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String apply_time =   o.get("apply_time")+"";
        map.put("apply_time", sdf.format(sdf.parse(apply_time)));
        map.put("apply_num", o.get("apply_num"));//维修编号
        map.put("terminal_num", o.get("serial_num")+"");
        map.put("brand_name", o.get("brand_name")+"");
        map.put("brand_number", o.get("brand_number")+"");
        map.put("zhifu_pingtai", o.get("zhifu_pt")+"");
        map.put("merchant_name", o.get("merchant_name")+"");
        map.put("merchant_phone", o.get("mer_phone")+"");
        map.put("receiver_addr", o.get("address")+"");
        String json = o.get("templete_info_xml")+"";
        map = getTemplePaths(map, json);
        myOrderReq.setId(Integer.parseInt(id));
        List<Map<String,Object>> list = csCencelsMapper.findTraceById(myOrderReq);
        map.put("comments", OrderUtils.getTraceByVoId(myOrderReq, list));
        return map;
    }

    @SuppressWarnings("unchecked")
    public Map<String,Object> getTemplePaths(Map<String, Object> map, String json) {
        ObjectMapper mapper = new ObjectMapper();
        if(!json.equals("")){
            Map<String,Object> child_map = null;
            List<LinkedHashMap<String, Object>> list_json;
            try {
                List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
                list_json = mapper.readValue(json, List.class);
                String[] ids = new String[list_json.size()];
                for(int i=0;i<list_json.size();i++){
                    String c_id = list_json.get(i).get("id")+"";
                    ids[i]= c_id;
                }
                MyOrderReq mo = new MyOrderReq();
                mo.setIds(ids);
                List<Map<String, Object>> childsList = csCencelsMapper.findTemplete(mo);
                for(Map<String,Object> m: childsList){
                    child_map = new HashMap<String,Object>();
                    String temp_id = (m.get("id")+"");
                    String temp_title = m.get("title")+"";
                    String temp_path = m.get("templet_file_path")+"";
                    String temp_up_path = "";
                    for(Map<String,Object> mm: list_json){
                        if(temp_id.equals(mm.get("id")+"")){
                            temp_up_path = mm.get("path")+"" ;
                        }
                    }
                    child_map.put("id", temp_id);
                    child_map.put("title", temp_title);
                    child_map.put("templet_path", temp_path);
                    child_map.put("upload_path", temp_up_path);
                    list.add(child_map);
                }
                map.put("resource_info", list);
            } catch (IOException e) {
                e.printStackTrace();
                map.put("resource_info", new ArrayList<>());
            }
        }else{
            map.put("resource_info", new ArrayList<>());
        }
        return map;
    }

    /**
     * 重新提交注销
     * @param myOrderReq
     */
    public void resubmitCancel(MyOrderReq myOrderReq) {
        myOrderReq.setUpdateStatus(UpdateStatus.PENDING);
        csCencelsMapper.changeStatus(myOrderReq);
    }

    public Page<List<Object>> search(MyOrderReq myOrderReq) throws ParseException {
        PageRequest request = new PageRequest(myOrderReq.getPage(), myOrderReq.getPageSize());
        int count = csCencelsMapper.countSearch(myOrderReq);
        List<Map<String, Object>> o = csCencelsMapper.search(myOrderReq);
        List<Map<String, Object>> list = putDate(o);
        return new Page<List<Object>>(request, list,count);
    }

}
