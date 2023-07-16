package com.appleyk.verify.listener;

import com.appleyk.core.ex.CommonException;
import com.appleyk.core.listener.ACustomVerifyListener;
import com.appleyk.core.model.LicenseExtraParam;
import com.appleyk.core.result.ResultCode;
import org.springframework.stereotype.Component;

/**
 * <p>Lic自定义验证监听器A</p>
 *
 * @author appleyk
 * @version v1.0.0
 * @blob https://blog.csdn.net/appleyk
 * @date created on 10:24 下午 2020/8/21
 */
@Component
public class CustomVerifyListenerA extends ACustomVerifyListener {

    /**单例Bean,这个静态变量会缓存证书中最大用户注册数的*/
    private static Long registerAmount = 0L;

    @Override
    public boolean verify(LicenseExtraParam licenseExtra) throws CommonException {
        registerAmount = licenseExtra.getRegisterAmount();
        System.out.println("======= 自定义证书验证监听器A 实现verify方法  =======");
        System.out.println("证书最大验证人数是："+registerAmount);
        /**
         * 这里，可以做一些和业务系统有关的参数验证
         * 比如在XX接口（Controller）上加@VLicense注解时，会触发这个方法的调用
         * 在这个业务系统这个监听方法中，我们可以先去user表查询下，当前系统的注册用户数量
         * 然后拿着这个数量和lic参数中约束的最大注册用户量进行比较，如果>，则抛出异常
         * 这样做的目的是防止业务系统部署到客户端本地服务器上时，数据库是公开的，防止对方通过手动改表来添加用户
         */
        long count = 1001;
        if(count>=registerAmount){
            throw new CommonException(ResultCode.INTERNAL,"系统当前用户数超过最大用户注册限制数【"+registerAmount+"】");
        }

        System.out.println("系统注册最大用户数量未超过"+count+"，验证用过！");
        return true;
    }

    /**
     * 这个单独的重载的验证方法，主要是用于业务系统中进行调用，带参数的verify方法用于注解
     * 而这个不带参数的verify，更加灵活些,比如在系统中注册接口（Controller）上，除了自身@VLicense注解外，
     * 可以在调用这个方法，来额外的验证注册人数是否已满
     * @return
     * @throws CommonException
     */
    public boolean verify() throws CommonException{
        Long count = 100L;
        if(count.equals(registerAmount)){
            throw new CommonException(ResultCode.INTERNAL,
                    "系统当前用户数已达到最大用户注册限制数【"+registerAmount+"】，无法再注册新用户。如需扩充用户注册的数量，请联系我们重新购买License！");
        }
        return true;
    }


}
