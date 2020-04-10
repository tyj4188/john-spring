/* 版权所有(C)，欧拉信息服务有限公司，2019，所有权利保留。
 *
 * 项目名：	pers.john.spring.test.v3
 * 文件名：	AopTest
 * 模块说明：
 * 修改历史：
 * 2019/10/29 - tongyongjian - 创建。
 */

package pers.john.spring.test.v3;

import org.junit.Test;
import pers.john.spring.aop.AdvisorAutoProxyCreator;
import pers.john.spring.aop.advisor.AspectJPointcutAdvisor;
import pers.john.spring.bean.GenericBeanDefinition;
import pers.john.spring.bean.PreBuildBeanFactory;
import pers.john.spring.samples.ABean;
import pers.john.spring.samples.aop.MyAfterAdvice;
import pers.john.spring.samples.aop.MyAroundAdvice;
import pers.john.spring.samples.aop.MyBeforeAdvice;

/**
 * @author tongyongjian
 * @date 2019/10/29
 */
public class AopTest {

    static PreBuildBeanFactory factory = new PreBuildBeanFactory();

    @Test
    public void testAop() throws Throwable {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(ABean.class);
        factory.registerBeanDefinition("aBean", beanDefinition);

        // 注册前置增强
        beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(MyBeforeAdvice.class);
        factory.registerBeanDefinition("myBeforeAdvice", beanDefinition);

        // 注册后置增强
        beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(MyAfterAdvice.class);
        factory.registerBeanDefinition("myAfterAdvice", beanDefinition);

        // 注册环绕增强
        beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(MyAroundAdvice.class);
        factory.registerBeanDefinition("myAroundAdvice", beanDefinition);

        // 往BeanFactory中注册AOP的BeanPostProcessor
        AdvisorAutoProxyCreator proxyCreator = new AdvisorAutoProxyCreator();
        factory.registerBeanPostProcessor(proxyCreator);

        // 注册前置切面
        proxyCreator.registryAdvisor(
            new AspectJPointcutAdvisor("myBeforeAdvice"
                , "execution(* pers.john.spring.samples.ABean.*(..))"));
        // 注册后置切面
        proxyCreator.registryAdvisor(
            new AspectJPointcutAdvisor("myAfterAdvice"
                , "execution(* pers.john.spring.samples.ABean.do*(..))"));
        // 注册环绕切面
        proxyCreator.registryAdvisor(
            new AspectJPointcutAdvisor("myAroundAdvice"
                , "execution(* pers.john.spring.samples.ABean.*(..))"));

        factory.preInstantiateSingleton();

        ABean aBean = (ABean) factory.getBean("aBean");
        aBean.doSomething();
        System.out.println("--------------------------------");
        aBean.eating();
    }

    @Test
    public void sqlPrint() {
        String sql = "UPDATE olayc_uc_vip_info_history SET `year` = 2020 WHERE `year` = 0 AND season = 1 AND id BETWEEN %s AND %s;";

        int min = 0, max = 0, step = 100000;

        for(int i = 1968710; i < 3382154;) {
            min = i + 1;
            i += step;
            System.out.println(String.format(sql, min, i));
        }
    }

}
