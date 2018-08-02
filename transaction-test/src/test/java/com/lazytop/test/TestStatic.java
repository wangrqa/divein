package com.lazytop.test;

import org.junit.Test;

/**
 * @author wangrq1
 * @create 2018-08-01 上午9:27
 **/
public class TestStatic {


    @Test
    public void diffPerf(){

        add0(100,200);
        add1(100,200);

        int n = 5000000;
        long begin = System.currentTimeMillis();
        for(int i=0; i<n; i++){
            add0(100,200);
        }
        System.out.println(System.currentTimeMillis() - begin);

        begin = System.currentTimeMillis();
        for(int i=0; i<n; i++){
            add1(100,200);
        }
        System.out.println(System.currentTimeMillis() - begin);


    }







    static int add0(int x, int y){
        new String(x+y+"");
        return x + y;
    }

    int add1(int x, int y){
        new String(x+y+"");
        return x + y;
    }


}
