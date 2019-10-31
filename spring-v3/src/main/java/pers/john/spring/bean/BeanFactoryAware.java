/* 版权所有(C)，欧拉信息服务有限公司，2019，所有权利保留。
 *
 * 项目名：	pers.john.spring.bean
 * 文件名：	BeanFacotryAware
 * 模块说明：
 * 修改历史：
 * 2019/10/28 - tongyongjian - 创建。
 */

package pers.john.spring.bean;

/**
 * Bean 工厂自动注入
 * @author tongyongjian
 * @date 2019/10/28
 */
public interface BeanFactoryAware extends Aware {

    public void setBeanFactory(BeanFactory beanFactory);
}
