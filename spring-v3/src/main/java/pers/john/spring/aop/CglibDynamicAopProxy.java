/* 版权所有(C)，欧拉信息服务有限公司，2019，所有权利保留。
 *
 * 项目名：	pers.john.spring.aop
 * 文件名：	CglibDynamaicAopProxy
 * 模块说明：
 * 修改历史：
 * 2019/9/10 - tongyongjian - 创建。
 */

package pers.john.spring.aop;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Cglib 实现动态代理
 * @author tongyongjian
 * @date 2019/9/10
 */
public class CglibDynamicAopProxy implements AopProxy, MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy)
        throws Throwable {
        return null;
    }

    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return null;
    }
}
