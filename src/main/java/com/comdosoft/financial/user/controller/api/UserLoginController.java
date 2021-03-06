package com.comdosoft.financial.user.controller.api;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.service.UserLoginService;
import com.comdosoft.financial.user.utils.SysUtils;

/**
 * 
 * 用户登陆 <功能描述>
 *
 * @author xfh 2015年2月7日
 *
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserLoginController {

    @Resource
    private UserLoginService userLoginService;

    @Value("${passPath}")
    private String passPath;

    /**
     * 用户登陆
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "studentLogin", method = RequestMethod.POST)
    public Response studentLogin(@RequestBody Customer customer) {
        try {
            customer.setTypes(Customer.TYPE_CUSTOMER);
            customer.setStatus(Customer.STATUS_NORMAL);
            Customer tomer = userLoginService.doLogin(customer);
            if (tomer != null) {
                userLoginService.updateLastLoginedAt(customer);
                return Response.getSuccess(tomer);
            } else {
                return Response.getError("用户名或密码错误！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getError("系统异常！");
        }
    }
    
    /**
     * (pc端，含有图片验证码校验)
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "sizeUpImgCode", method = RequestMethod.POST)
    public Response sizeUpImgCode(@RequestBody Map<String, String> map ,HttpSession session) {
        try {
        	if((String) session.getAttribute("imageCode") == null){
        		return Response.getError("验证码错误！");
        	}else{
        		if(((String) session.getAttribute("imageCode")).equalsIgnoreCase(map.get("imgnum"))){
               	 return Response.getSuccess("true");
               }else{
               	return Response.getError("验证码错误！");
               }
        	}
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getError("系统异常！");
        }
    }

    /**
     * 发送手机验证码(找回密码)
     * 
     * @param number
     */
    @RequestMapping(value = "sendPhoneVerificationCodeFind", method = RequestMethod.POST)
    public Response sendPhoneVerificationCodeFind(@RequestBody Map<String, String> map) {
        try {
            Customer customer = new Customer();
            customer.setUsername(map.get("codeNumber"));
            char[] randchar = SysUtils.getRandNum(6);
            String str = "";
            for (int i = 0; i < randchar.length; i++) {
                str += randchar[i];
            }
            customer.setDentcode(str);
            if (userLoginService.findUname(customer) == 0) {
                return Response.getError("用户不存在！");
            } else {
                userLoginService.updateCode(customer);
                return Response.getSuccess(str);
            }
        } catch (Exception e) {
            return Response.getError("获取验证码失败！");
        }
    }
    
    /**
     * (找回密码web端第一步)
     * 
     * @param number
     */
    @RequestMapping(value = "getFindUser", method = RequestMethod.POST)
    public Response getFindUser(@RequestBody Map<String, Object> map,HttpSession session) {
        try {
            Customer customer = new Customer();
            System.out.println(map.get("username")+"查看姓名！");
            customer.setUsername((String)map.get("username"));
            if (userLoginService.findUname(customer) == 0) {
                return Response.getError("用户不存在！");
            } else {
                return Response.getSuccess("用户存在！");
            }
        } catch (Exception e) {
            return Response.getError("系统异常！");
        }
    }
    

    /**
     * 发送手机验证码(注册)
     * 
     * @param number
     */
    @RequestMapping(value = "sendPhoneVerificationCodeReg", method = RequestMethod.POST)
    public Response sendPhoneVerificationCodeReg(@RequestBody Map<String, Object> map) {
        try {
            Customer customer = new Customer();
            customer.setUsername((String)map.get("codeNumber") );
            char[] randchar = SysUtils.getRandNum(6);
            String str = "";
            for (int i = 0; i < randchar.length; i++) {
                str += randchar[i];
            }
            customer.setPassword("0");
            customer.setCityId(0);
            customer.setDentcode(str);
            customer.setStatus(Customer.STATUS_NON_END);
            if (userLoginService.findUname(customer) == 0) {
                // 添加假状态
                userLoginService.addUser(customer);
                return Response.getSuccess(str);
            } else {
                if (userLoginService.findUnameAndStatus(customer) == 0) {
                    return Response.getError("该用户已注册！");
                } else {
                    userLoginService.updateCode(customer);
                    return Response.getSuccess(str);
                    
                }
            }
        } catch (Exception e) {
            return Response.getError("获取验证码失败！");
        }
    }

    /**
     * 发送邮箱验证
     * 
     * @param number
     */
    @RequestMapping(value = "sendEmailVerificationCode", method = RequestMethod.POST)
    public Response sendEmailVerificationCode(@RequestBody String codeNumber) {
    	 return Response.getSuccess("发送邮件成功！");
    }

    /**
     * 找回密码(web)校验验证码
     * 
     * @param customer
     * @return
     */
    @RequestMapping(value = "webFicationCode", method = RequestMethod.POST)
    public Response webFicationCode(@RequestBody Customer customer) {
        try {
        	System.out.println(customer.getCode()+"验证码1");
        	System.out.println(userLoginService.findCode(customer)+"验证码2");
                if (customer.getCode().equals(userLoginService.findCode(customer))) {
                	
                    return Response.getSuccess("验证码正确！");
                } else {
                    return Response.getError("验证码错误！");
                }
        } catch (Exception e) {
            return Response.getError("请求失败！");
        }
    }
    
    /**
     * 找回密码(web)修改密码
     * 
     * @param customer
     * @return
     */
    @RequestMapping(value = "webUpdatePass", method = RequestMethod.POST)
    public Response webUpdatePass(@RequestBody Customer customer) {
        try {
            if (userLoginService.findUname(customer) > 0) {
                    userLoginService.updatePassword(customer);
                    return Response.getSuccess("找回密码成功！");
            } else {
                return Response.getError("用户名错误！");
            }
        } catch (Exception e) {
        	e.printStackTrace();
            return Response.getError("请求失败！");
        }
    }
    
    
    /**
     * 找回密码
     * 
     * @param customer
     * @return
     */
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public Response updatePassword(@RequestBody Customer customer) {
        try {
            if (userLoginService.findUname(customer) > 0) {
                if (customer.getCode().equals(userLoginService.findCode(customer))) {
                    userLoginService.updatePassword(customer);
                    return Response.getSuccess("找回密码成功！");
                } else {
                    return Response.getError("验证码错误！");
                }
            } else {
                return Response.getError("用户名错误！");
            }
        } catch (Exception e) {
            return Response.getError("请求失败！");
        }
    }

    /**
     * 注册用户
     * 
     * @param customer
     * @return
     */
    @RequestMapping(value = "userRegistration", method = RequestMethod.POST)
    public Response userRegistration(@RequestBody Customer customer, HttpSession session) {
        try {
            customer.setTypes(Customer.TYPE_CUSTOMER);
            customer.setStatus(Customer.STATUS_NON_END);
            if (userLoginService.findUserAndStatus(customer) == 0) {
                if (!customer.getAccountType()) {
                    if (customer.getCode().equals(userLoginService.findCode(customer))) {
                        customer.setPhone(customer.getUsername());
                        customer.setStatus(Customer.STATUS_NORMAL);
                        userLoginService.updateUser(customer);
                        return Response.getSuccess("注册成功！");
                    } else {
                        return Response.getError("验证码错误！");
                    }
                } else {
                    customer.setEmail(customer.getUsername());
                    customer.setStatus(Customer.STATUS_NON_ACTIVE);
                    userLoginService.updateUser(customer);
                    return Response.getSuccess("激活链接已发送至你的邮箱，请点击激活。");
                }
            } else {
                return Response.getError("用户已注册！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getError("注册失败！系统异常");
        }
    }
    
    /**
     * 获取验证码（登录用）
     * 
     * @param response
     */
    @RequestMapping("getRandCodeImg")
    public void getRandCodeImg(HttpServletResponse response,HttpSession session) {

        // 设置页面不缓存数据
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 获取4位随机验证码
        char[] randNum = SysUtils.getRandNum(4);
        String randNumStr = new String(randNum);

        // 将验证码存入session
        session.setAttribute("imageCode", randNumStr);

        // 生成验证码图片
        BufferedImage image = SysUtils.generateRandImg(randNum);

        try {// 输出图象到页面
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (IOException ioEx) {
           // logger.error("验证码图片显示异常", ioEx);
        }
    }
}
