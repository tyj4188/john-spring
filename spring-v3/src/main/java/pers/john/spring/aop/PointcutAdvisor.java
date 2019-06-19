/* 
 * 
 * 项目名：	pers.john.spring.aop
 * 文件名：	PointcutAdvicor
 * 模块说明：	
 * 修改历史：
 * 2019/6/19 - Administrator - 创建。
 */

package pers.john.spring.aop;

/**
 * 基于切入点的通知者
 * @author Administrator
 * @date 2019/6/19
 */
public interface PointcutAdvisor extends Advisor {
    /**
     * 获取切入点
     * @return
     */
    public Pointcut getPointcut();
}
