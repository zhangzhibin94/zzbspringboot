package com.xiniunet.controller;

import com.aliyuncs.exceptions.ClientException;
import com.xiniunet.domain.ErrorType;
import com.xiniunet.domain.SendMessage;
import com.xiniunet.domain.ServerSettings;
import com.xiniunet.domain.User;
import com.xiniunet.response.BaseResponse;
import com.xiniunet.response.RegisterCreateResponse;
import com.xiniunet.service.ProdecerService;
import com.xiniunet.service.UserService;
import com.xiniunet.utils.AliyunMessageUtil;
import com.xiniunet.utils.JedisClient;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//@RestController = @Controller + @ResponseBody
@Controller
@EnableAutoConfiguration
public class DemoController {
    @Autowired
   private UserService userService;

    @Autowired
    private ProdecerService prodecerService;

    @Autowired
    private AliyunMessageUtil aliyunMessageUtil;

    @Autowired
    private JedisClient jedisClient;

    @RequestMapping("/")
    String home(){
        return "blog";
    }
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
    public Object insertUser(){
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
    //测试activeMQ queue发送并消费消息
    /*@RequestMapping("/test_activemq")
    @ResponseBody
    public Object testActiveMQ(String msg){
        Destination destination = new ActiveMQQueue("zzb.queue");
        prodecerService.sendMessage(destination, msg);
        return msg;
    }*/
    //测试activeMQ topic发送并消费消息
    /*@RequestMapping("/test_activemq/topic")
    @ResponseBody
    public Object testActiveMQTopic(String msg){
        Destination destination = new ActiveMQTopic("zzb.topic");
        prodecerService.publish(destination, msg);
        return msg;
    }*/
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    /**
     * 发送短信验证码
     * @param sendMessage
     * @return
     * @throws ClientException
     */
    @RequestMapping("/send_check_code")
    @ResponseBody
    public BaseResponse sendCode(@RequestBody SendMessage sendMessage) throws ClientException {
        BaseResponse response = new BaseResponse();

        if("register".equalsIgnoreCase(sendMessage.getType())){//如果为注册类型
            //1.先查询redis中的该手机号与验证码的记录是否大于4分钟(有效期5分钟-发送冷却1分钟),防止被黑客频繁攻击而产生巨额短信费用
            Long expire = jedisClient.getExpire("register:" + sendMessage.getTelephone(), TimeUnit.MINUTES);
            if(expire!=null&&expire>4){
                response.addError(ErrorType.BUSINESS_ERROR,"您发送验证码的频率过高，请稍后再试");
                return response;
            }
            //2.校验手机号是否已存在
            User user = new User();
            user.setPhone(Long.parseLong(sendMessage.getTelephone()));
            Long existUser = userService.isExistUser(user);
            if(existUser>0){
                response.addError(ErrorType.BUSINESS_ERROR,"手机号已存在");
                return response;
            }
            String msg = aliyunMessageUtil.sendVetifyMessage(sendMessage.getTelephone());
            //3.在redis中生成一条记录，key为前缀+手机号，value为验证码
            jedisClient.set("register:"+sendMessage.getTelephone(),msg);
            //4.设置key的过期时间为5分钟
            jedisClient.expire("register:"+sendMessage.getTelephone(),15, TimeUnit.MINUTES);
            if(msg!=null){//发送成功
                return response;
            }
        }

        return response;
    }

    /**
     * 注册
     * @param
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public RegisterCreateResponse register(@RequestBody User user){
        RegisterCreateResponse response = userService.register(user);
        return response;
    }
    @RequestMapping(value="/login_in", method=RequestMethod.POST)
    @ResponseBody
    public BaseResponse userLogin(User user,
                                    HttpServletRequest request, HttpServletResponse response) {
        BaseResponse baseResponse = new BaseResponse();
        //获取拦截器返回的user信息
        User users = (User)request.getAttribute("user");
        return baseResponse;
    }
}
