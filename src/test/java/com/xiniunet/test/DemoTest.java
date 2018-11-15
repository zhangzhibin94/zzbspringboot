package com.xiniunet.test;

import com.xiniunet.StartApplication;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)//底层其实用junit SpringJunit4ClassRunner
@SpringBootTest(classes = {StartApplication.class})//启动整个springboot工程
public class DemoTest {
    @Before
    public void testBefore(){
        System.out.println("before");
    }
    @Test
    public void testSpringboot(){
        System.out.println("test demo!");
        TestCase.assertEquals(1,0);//断言,不通过
    }
}
