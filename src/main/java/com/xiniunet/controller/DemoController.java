package com.xiniunet.controller;

import com.xiniunet.domain.ServerSettings;
import com.xiniunet.domain.User;
import com.xiniunet.mapper.UserMapper;
import com.xiniunet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//@RestController = @Controller + @ResponseBody
@Controller
@EnableAutoConfiguration
public class DemoController {
    @Autowired
   private UserService userService;

    /*@RequestMapping("/")
    @ResponseBody
    String home(){
        return "Hello world";
    }*/
    @RequestMapping("/test")//由于使用了@RestController就可以省去@ResponseBody将返回结果转为json
    public Map<String,String> testMap(){
        Map<String,String> people = new HashMap<>();
        people.put("name:","小明");
        return people;
    }
    //@RequestMapping此注解中参数path为具体的访问路径，此方法路径即为localhost:8080/123/333,
    //参数method限制请求方法（非必须），method包含get、post等
    /*@RequestMapping(path="/{city_id}/{people_id}",method = RequestMethod.GET)
    public Map<String,String> testGet(@PathVariable("city_id")String cityId,
                                      @PathVariable("people_id")String peopleId){
        Map<String,String> people = new HashMap<>();
        people.put("cityId:",cityId);
        people.put("PeopleId:",peopleId);
        return people;
    }*/
    @GetMapping(value = "/v1/pages_user")
    public Map<Object,Object> getUser(@RequestParam(defaultValue = "0",name = "pages") int from,int size){
        Map<Object,Object> people = new HashMap<>();
        people.put("from:",from);
        people.put("size:",size);
        return people;
    }

    /**
     * 功能描述：使用bean对象传输数据
     * 注意：1.需要添加@RequestBody注解
     *       2.需要指定http请求头中的content-type为application/json
     *       3.使用工具指定参数时需要选择body
     * @param user
     * @return
     */
    @PostMapping(value = "v1/user")
    public User user(@RequestBody User user){
        return user;
    }

    /**
     * 功能描述：从http头中获取参数
     * @param token
     * @param id
     * @return
     */
    @PostMapping(value = "get_header")
    public Object getHeader(@RequestHeader("access_token")String token,String id){
        Map<Object,Object> people = new HashMap<>();
        people.put("access_token:",token);
        people.put("id:",id);
        return people;
    }

    /**
     * 与servlet中的request相同，可以从键值对中获取具体的value值
     * @param request
     * @returnindex
     */
    @PostMapping(value = "get_request")
    public Object testRequest(HttpServletRequest request){
        Map<Object,Object> people = new HashMap<>();
        people.put("id:",request.getParameter("id"));
        return people;
    }

    @Autowired
    private ServerSettings serverSettings;
    @GetMapping("/test_properties")
    @ResponseBody
    public Object testProperties(){
        return serverSettings;
    }
    @RequestMapping(value = "/api/index")
    public String indexMap(){
        return "index";
    }
    /*public static void main(String[] args) {
        SpringApplication.run(DemoController.class,args);
    }*/
    @RequestMapping("/insert_user")
    @ResponseBody
    public Object inserUser(){
        User user = new User();
        user.setId(9993l);
        user.setPassword("zzb123456");
        user.setName("lisi");
        user.setPhone(1323412142l);
        user.setUserName("zhangsan");
        /*long l = userService.insertUser(user);*/
        List<User> userList = userService.findUser(user);
        /*System.out.println(l);*/
        return userList;
    }
}
