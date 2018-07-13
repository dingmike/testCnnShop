package com.platform;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


public class DemoTest {
    private Integer num = 0;

    @Test
    public void test() {
        if (num==null) {
            num = 0;
        }
        if (num <=3) {
            num++;
            this.test();
            return;
        }
        System.out.println("来嘛");
     }

}
