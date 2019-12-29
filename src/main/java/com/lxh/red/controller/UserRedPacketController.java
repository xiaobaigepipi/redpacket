package com.lxh.red.controller;

import com.lxh.red.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/*
 * @PackageName: com.lxh.red.controller
 * @ClassName: UserRedPacketController
 * @Description:
 * @author: 辉
 * @date: 2019/12/29 14:11
 * */
@Controller
@RequestMapping("")
public class UserRedPacketController {
    @Autowired
    UserRedPacketService userRedPacketService;

    @RequestMapping("/grapRedPacket")
    @ResponseBody
    public Map<String, Object> grapRedPacket(Long redPacketId, Integer userId){
        System.out.println("开始抢");
        int result = userRedPacketService.getRedPacketByVersion(redPacketId, userId);
        boolean flag = result > 0;
        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        map.put("message", flag? "抢红包成功" : "抢红包失败");
        return map;
    }
}
