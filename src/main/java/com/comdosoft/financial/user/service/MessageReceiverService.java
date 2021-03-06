package com.comdosoft.financial.user.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.zhangfu.MessageReceiver;
import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.domain.zhangfu.SysMessage;
import com.comdosoft.financial.user.mapper.zhangfu.MessageReceiverMapper;
import com.comdosoft.financial.user.utils.page.Page;
import com.comdosoft.financial.user.utils.page.PageRequest;

@Service
public class MessageReceiverService {
    @Resource
    private MessageReceiverMapper messageReceiverMapper;

    public Page<Object> findAll(MyOrderReq myOrderReq) {
        PageRequest request = new PageRequest(myOrderReq.getPage(),myOrderReq.getPageSize());
        int count = messageReceiverMapper.count(myOrderReq.getCustomer_id());
        List<MessageReceiver> centers = messageReceiverMapper.findAll(myOrderReq);
        List<Object> list = new ArrayList<Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String,Object> map = null;
        for(MessageReceiver s: centers){
            map = new HashMap<String,Object>();
            map.put("id", s.getSysMessage() ==null ?"":s.getSysMessage().getId()+"");
            map.put("title", s.getSysMessage() ==null ?"":s.getSysMessage().getTitle());
            if(s.getStatus()==1){
                map.put("status", true);
            }else{
                map.put("status", false);
            }
            map.put("create_at",sdf.format(s.getSysMessage() ==null ?"":s.getSysMessage().getCreatedAt()));
            map.put("content", s.getSysMessage() ==null ?"":s.getSysMessage().getContent());
            list.add(map);
        }
        return new Page<Object>(request, list, count);
    }
    
    public SysMessage findById(MyOrderReq myOrderReq) {
        SysMessage sysMessage = messageReceiverMapper.findById(myOrderReq);
        messageReceiverMapper.isRead(myOrderReq);
        return sysMessage;
    }
    
    public void delete(MyOrderReq myOrderReq){
        messageReceiverMapper.delete(myOrderReq);
    }
    
    public void batchDelete(MyOrderReq myOrderReq){
        messageReceiverMapper.batchDelete(myOrderReq);
    }
    
    public void batchRead(MyOrderReq myOrderReq){
        messageReceiverMapper.batchUpdate(myOrderReq);
    }

//    public void isRead(String id) {
//        messageReceiverMapper.isRead(id);
//    }

}
