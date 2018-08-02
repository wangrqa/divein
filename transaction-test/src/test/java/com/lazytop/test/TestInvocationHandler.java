package com.lazytop.test;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk动态代理测试
 * @author wangrq1
 * @create 2018-08-01 下午11:16
 **/
public class TestInvocationHandler {

    public interface IHello{
        void sayHello();
    }

    public interface IHello2{
        void sayHello2();
    }

    static class Hello implements IHello, IHello2{
        public void sayHello() {
            System.out.println("Hello world!!");
        }


        @Override
        public void sayHello2() {

        }
    }

    static class HWInvocationHandler implements InvocationHandler {
        //目标对象
        private Object target;

        public HWInvocationHandler(Object target){
            this.target = target;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("------插入前置通知代码-------------");
            //执行相应的目标方法
            Object rs = method.invoke(target, args);
            System.out.println("------插入后置处理代码-------------");
            return rs;
        }
    }



    @Test
    public void proxyInterface(){


        Object hello = Proxy.newProxyInstance(IHello.class.getClassLoader(), new Class[]{IHello.class, IHello2.class}, new HWInvocationHandler(new Hello()));


        ((IHello)hello).sayHello();

        ((IHello2)hello).sayHello2();


    }



}
