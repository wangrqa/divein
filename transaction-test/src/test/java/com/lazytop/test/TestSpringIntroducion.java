package com.lazytop.test;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.IntroductionInterceptor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultIntroductionAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;

/**
 * @author wangrq1
 * @create 2018-08-03 上午11:09
 **/
public class TestSpringIntroducion {

    interface ITask{
        String hello(String name);
    }


    interface IOther{
        String otherFunc(String name);
    }

    class BarTask implements ITask{

        @Override
        public String hello(String name) {
            return "hi, " + name;
        }
    }


    class PerfInteceptor implements IntroductionInterceptor, IOther{

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {

            if(implementsInterface(
                    invocation.getMethod().getDeclaringClass())) {
                // 呼叫執行額外加入（mixin）的行為
                return invocation.getMethod().invoke(this, invocation.getArguments());
            }
            else {
                return invocation.proceed();
            }
        }

        @Override
        public boolean implementsInterface(Class<?> intf) {
            return intf.isAssignableFrom(IOther.class);
        }

        @Override
        public String otherFunc(String name) {
            System.out.println("other function");
            return name;
        }
    }




    @Test
    public void testNameMatcher(){

        ITask iTask = new BarTask();

        ProxyFactory weaver = new ProxyFactory(iTask);
        weaver.setOptimize(true);
        weaver.setTarget(iTask);

        weaver.setInterfaces(IOther.class);

        PerfInteceptor inteceptor = new PerfInteceptor();

        DefaultIntroductionAdvisor advisor = new DefaultIntroductionAdvisor(inteceptor);

        weaver.addAdvisor(advisor);

        ITask proxy = (ITask)weaver.getProxy();


        String hi = proxy.hello("Alex");
        System.out.println(hi);


        ((IOther)proxy).otherFunc("Alex");


    }


}
