package com.lazytop.test;

import com.lazytop.transtest.domain.PhoneNumberBelong;
import com.lazytop.transtest.domain.SysRegion;
import com.lazytop.transtest.mappers.PhoneNumberBelongMapper;
import com.lazytop.transtest.mappers.SysRegionMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author wangrq1
 * @create 2018-07-30 下午7:16
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:/applicationContext-dao.xml"
})
public class TestTransac {


//    @Autowired
//    private PhoneNumberBelongMapper phoneNumberBelongMapper;
//

    @Autowired
    private SysRegionMapper sysRegionMapper;




    @Test
    public void perfDiff(){


        SysRegion belong = sysRegionMapper.selectByPrimaryKey(1);

        int n=500;

        long begin = System.currentTimeMillis();
        for(int i=0; i< n; i++){
            belong = sysRegionMapper.selectByPrimaryKey(1);
            System.out.println(belong.getName());
        }

        System.out.println(System.currentTimeMillis() - begin);













    }



}
