package com.xiniunet.controller;

import com.aliyuncs.exceptions.ClientException;
import com.xiniunet.domain.ErrorType;
import com.xiniunet.domain.SendMessage;
import com.xiniunet.domain.ServerSettings;
import com.xiniunet.domain.User;
import com.xiniunet.request.LoginByTelephoneRequest;
import com.xiniunet.response.BaseResponse;
import com.xiniunet.response.LoginResponse;
import com.xiniunet.response.RegisterCreateResponse;
import com.xiniunet.service.ProdecerService;
import com.xiniunet.service.UserService;
import com.xiniunet.utils.AliyunMessageUtil;
import com.xiniunet.utils.CookieUtils;
import com.xiniunet.utils.JedisClient;
import com.xiniunet.utils.JsonUtils;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
        String type = "";
        if("register".equalsIgnoreCase(sendMessage.getType())){//如果为注册类型
            type="register";
            //1.校验手机号是否已存在
            User user = new User();
            user.setPhone(Long.parseLong(sendMessage.getTelephone()));
            Long existUser = userService.isExistUser(user);
            if(existUser>0){
                response.addError(ErrorType.BUSINESS_ERROR,"手机号已存在");
                return response;
            }
        }else if("login".equalsIgnoreCase(sendMessage.getType())){//类型为登录类型
            type="login";
        }else {
            response.addError(ErrorType.BUSINESS_ERROR,"发送类型错误");
            return response;
        }
        //2.先查询redis中的该手机号与验证码的记录是否大于14分钟(有效期15分钟-发送冷却1分钟),防止被黑客频繁攻击而产生巨额短信费用
        Long expire = jedisClient.getExpire(type+":" + sendMessage.getTelephone(), TimeUnit.MINUTES);
        if(expire!=null&&expire>14){
            response.addError(ErrorType.BUSINESS_ERROR,"您发送验证码的频率过高，请稍后再试");
            return response;
        }
        //发送短信，返回结果为短信验证码
        String msg = aliyunMessageUtil.sendVetifyMessage(sendMessage.getTelephone());
        //将验证码md5加密
        String md5Message = DigestUtils.md5DigestAsHex(msg.getBytes());
        //3.在redis中生成一条记录，key为前缀+手机号，value为验证码
        jedisClient.set(type+":"+sendMessage.getTelephone(),md5Message);
        //todo 4.设置key的过期时间为15分钟,开发时设置为一小时
        jedisClient.expire(type+":"+sendMessage.getTelephone(),60, TimeUnit.MINUTES);
        if(msg!=null){//发送成功
            return response;
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

    /**
     * 登录
     * @param user
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/login_in", method=RequestMethod.POST)
    @ResponseBody
    public LoginResponse userLogin(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        LoginResponse loginResponse = new LoginResponse();
        User databaseUser = new User();
        //获取拦截器返回的user信息
        if(request.getAttribute("user")!=null){//如果拦截器中存在user信息表明用户已经登录，则直接返回
            User users = (User)request.getAttribute("user");
            loginResponse.setUser(users);
            return loginResponse;
        }else {//拦截器中不存在user信息，则表示用户未登录
            if(StringUtils.isNotEmpty(user.getLoginType())){
                if("telephone".equalsIgnoreCase(user.getLoginType())){//登录类型为手机号
                    //手机号是否在数据库中存在
                    Long result = userService.isExistUser(user);
                    if(result>0){//手机号已存在，则校验手机号和验证码是否匹配
                        LoginByTelephoneRequest loginByTelephoneRequest = new LoginByTelephoneRequest();
                        loginByTelephoneRequest.setTelephone(user.getTelephone());
                        loginByTelephoneRequest.setCode(user.getCode());
                        LoginResponse loginByTelephone = userService.loginByTelephone(loginByTelephoneRequest);
                        if(loginByTelephone.hasError()){
                            loginResponse.addErrors(loginByTelephone.getErrors());
                            return loginResponse;
                        }
                        databaseUser = loginByTelephone.getUser();
                    }else {
                        loginResponse.addError(ErrorType.BUSINESS_ERROR,"您的手机号未注册，请先注册");
                        return loginResponse;
                    }
                }else if("username".equalsIgnoreCase(user.getLoginType())){//登录类型会用户名密码
                    //匹配数据库中的用户名密码与用户输入的用户名面是否相同
                    LoginResponse loginByUserName = userService.loginByUserName(user);
                    if(loginByUserName.hasError()){
                        loginResponse.addErrors(loginByUserName.getErrors());
                        return loginResponse;
                    }
                    //获得匹配成功后的用户对象
                    databaseUser = loginByUserName.getUser();
                }
            }else {
                loginResponse.addError(ErrorType.BUSINESS_ERROR,"登录类型不能为空");
                return loginResponse;
            }
        }
        //生成一个类型为uuid的token存放到cookie中,并设置cookie的过期时间为30分钟
        String token = UUID.randomUUID().toString();
        CookieUtils.setCookie(request,response,"token",token,30*60);
        //将此token同步存放到redis中并设置过期时间为30min,key为token，value为json格式的用户对象
        jedisClient.set("user:sso:"+token, JsonUtils.obj2String(databaseUser));
        jedisClient.expire("user:sso:"+token,30,TimeUnit.MINUTES);
        loginResponse.setUser(databaseUser);
        return loginResponse;
    }
}
