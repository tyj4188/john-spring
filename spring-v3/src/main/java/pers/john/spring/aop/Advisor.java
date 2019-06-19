/* 
 * 
 * 项目名：	pers.john.spring.aop
 * 文件名：	Advicor
 * 模块说明：	
 * 修改历史：
 * 2019/6/19 - Administrator - 创建。
 */

package pers.john.spring.aop;

/**
 * 通知者，给用户方便使用，整合了 Advice, Pointcut
 * @author Administrator
 * @date 2019/6/19
 */
public interface Advisor {
    /**
     * 获取通知类的 BeanName
     * @return
     */
    public String getAdviceBeanName();

    /**
     * 获得切点表达式
     * @return
     */
    public String getPointcutExpression();
}
