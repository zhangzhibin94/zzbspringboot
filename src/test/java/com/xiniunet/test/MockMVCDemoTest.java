package com.xiniunet.test;

import com.xiniunet.StartApplication;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)//底层其实用junit SpringJunit4ClassRunner
@SpringBootTest(classes = {StartApplication.class})//启动整个springboot工程
@AutoConfigureMockMvc//用于模拟http请求，可以直接访问controller层
public class MockMVCDemoTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void testSpringboot(){
        try {
            //1.MockMvcRequestBuilders构建请求，andExpect表示期望值，若返回的结果的状态码为200则返回请求的结果
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/gopage")).
                    andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
            MockHttpServletResponse response = mvcResult.getResponse();
            int status = response.getStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
