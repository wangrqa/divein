package com.lazytop.test;

import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

/**
 * @author wangrq1
 * @create 2018-07-31 下午7:36
 **/
public class TThread {


    @Test
    public void park(){

        LockSupport.unpark(Thread.currentThread());

        LockSupport.park(Thread.currentThread());
        LockSupport.park(Thread.currentThread());

        LockSupport.park(Thread.currentThread());

        LockSupport.park(Thread.currentThread());


        LockSupport.park(Thread.currentThread());




    }

}
