package com.lazytop.test;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.cglib.proxy.Proxy;

/**
 * @author wangrq1
 * @create 2018-08-03 上午11:09
 **/
public class TestSpringWeaver {

    interface ITask{
        String hello(String name);
    }

    class BarTask implements ITask{

        @Override
        public String hello(String name) {
            return "hi, " + name;
        }
    }


    class PerfInteceptor implements MethodInterceptor{
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {

            System.out.println("begin");

            Object obj = invocation.proceed();

            System.out.println("end");

            return obj;
        }
    }




    @Test
    public void testNameMatcher(){

        ITask iTask = new BarTask();

        ProxyFactory weaver = new ProxyFactory(iTask);

        weaver.addInterface(ITask.class);

        NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor();
        advisor.setMappedName("hello");
        advisor.setAdvice(new PerfInteceptor());

        weaver.addAdvisor(advisor);


        ITask proxy = (ITask)weaver.getProxy();


        String hi = proxy.hello("Alex");
        System.out.println(hi);



    }


}
