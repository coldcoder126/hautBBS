package cn.coldcoder.controller.backend;

import cn.coldcoder.common.Const;
import cn.coldcoder.common.ServerResponse;
import cn.coldcoder.pojo.Admin;
import cn.coldcoder.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("admin/")
public class adminController {

    @Autowired
    private IAdminService iAdminService;

    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    public String Login(String account,String password,HttpSession session){
        ServerResponse<Admin> response = iAdminService.login(account,password);
        if(response.isSuccess()){
            Admin admin = response.getData();
            session.setAttribute(Const.CURRENT_ADMIN,admin);
            return "topic";
        }
        return "redirect:login";
    }

    @RequestMapping("loginFromWeChat.do")
    @ResponseBody
    public ServerResponse loginFromWeChat(String account,String password){
        return  iAdminService.login(account,password);
    }

    @RequestMapping("logout.do")
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_ADMIN);
        return ServerResponse.createBySuccessMessage("登出成功");
    }

}
