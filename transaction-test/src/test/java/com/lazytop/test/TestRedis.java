package com.lazytop.test;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangrq1
 * @create 2018-08-02 下午5:32
 **/
public class TestRedis {

    private Object lock = new Object();


    AtomicInteger atomicInteger = new AtomicInteger(0);

    RedisClient client = RedisClient.create(RedisURI.create("redis://10.120.133.39:6383"));
    StatefulRedisConnection<String, String> connect;
    RedisCommands<String, String> commands;

    @Before
    public void setUp(){
        connect = client.connect();
        commands = connect.sync();
    }




    @Test
    public void testWatch(){


        /* 同步执行的命令 */
        String watchKey = "abc";


        ExecutorService service = Executors.newFixedThreadPool(10);




        for(int j=0; j< 10; j++){
            service.execute(()->{
                for(int c=0; c< 50; c++){
                    try {
                        cas(watchKey);
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
                }
            );
        }



        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        service.shutdown();


        System.out.println("sss----" + atomicInteger.get());

        System.out.println(commands.get(watchKey));


        commands.set(watchKey, "");




    }


    public void cas(String watchKey){

        int i = 0;
        for(;;) {

            commands.watch(watchKey);
            String val = commands.get(watchKey);

            commands.multi();

            commands.set(watchKey, val + 1);

            List<Object> list = commands.exec();

            if (!CollectionUtils.isEmpty(list)) {
                System.out.println(Thread.currentThread().getId() + "--" + list.toString());
                break;
            }else {
                i++;
            }
        }
            atomicInteger.incrementAndGet();

        System.out.println(Thread.currentThread().getId() + " retry=" + i);

    }


    public void noCas(String watchKey){
            String val = commands.get(watchKey);
            commands.set(watchKey, val + 1);

    }




}
