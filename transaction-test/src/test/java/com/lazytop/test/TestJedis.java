package com.lazytop.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangrq1
 * @create 2018-08-02 下午5:32
 **/
public class TestJedis {


    JedisPool pool;

    AtomicInteger atomicInteger = new AtomicInteger(0);

    @Before
    public void setUp(){

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(100);
        config.setMaxTotal(100);

        pool = new JedisPool(config, "10.120.133.39", 6383);

    }




    @Test
    public void testWatch(){


        /* 同步执行的命令 */
        String watchKey = "abc";


        ExecutorService service = Executors.newFixedThreadPool(10);


        for(int j=0; j < 5; j++){
            service.execute(()->{
                for(int c=0; c < 100; c++){
                    try {
                        casAppend(watchKey, c+"");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                }
            );
        }



        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        service.shutdown();

        System.out.println("count：" + atomicInteger.get());

        Jedis jedis = pool.getResource();

        System.out.println(jedis.get(watchKey));


        jedis.set(watchKey, "");

        jedis.close();

        pool.close();




    }


    private Object lock = new Object();

    public void casAppend(String watchKey, String str){

        Jedis client = pool.getResource();

        int retry = 0;
        for(;;) {
            client.watch(watchKey);
            Transaction transaction = client.multi();
            transaction.append(watchKey, str);
            List<Object> list = transaction.exec();

            if (!CollectionUtils.isEmpty(list)) {
                break;
            }else {
                retry++;
            }
        }
        client.close();
        System.out.println(Thread.currentThread().getId() + " retry=" + retry);
    }


    public void noCas(String watchKey){
            atomicInteger.incrementAndGet();

            Jedis client = pool.getResource();
            String val = client.get(watchKey);
            client.set(watchKey, val + 1);
            client.close();


    }

    public void noCas2(String watchKey){

        synchronized (lock){
        atomicInteger.incrementAndGet();

        Jedis client = pool.getResource();
        String val = client.get(watchKey);
        client.set(watchKey, val + 1);
        client.close();
        }


    }




}
