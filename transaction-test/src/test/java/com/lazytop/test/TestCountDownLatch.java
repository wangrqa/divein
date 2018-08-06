package com.lazytop.test;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author wangrq1
 * @create 2018-08-02 下午8:41
 **/
public class TestCountDownLatch {


    @Test
    public void testBase(){


        CountDownLatch cdl = new CountDownLatch(5);


        cdl.countDown();


        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




        System.out.println("end");




    }



}
