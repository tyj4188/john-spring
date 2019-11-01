/* 
 * 
 * 项目名：	pers.john.spring.aop
 * 文件名：	AbstractPointcutAdvicor
 * 模块说明：	
 * 修改历史：
 * 2019/6/19 - Administrator - 创建。
 */

package pers.john.spring.aop.advisor;

import pers.john.spring.aop.pointcut.Pointcut;

/**
 * 基于切点的抽象通知者，定义一些公共属性
 * @author Administrator
 * @date 2019/6/19
 */
public abstract class AbstractPointcutAdvisor implements PointcutAdvisor {

    /**
     * 通知类名称
     */
    protected String adviceBeanName;

    /**
     * 切点表达式
     */
    protected String expression;

    /**
     * 切点接口类
     */
    protected Pointcut pointcut;

    public AbstractPointcutAdvisor(String adviceBeanName, String expression) {
        this.adviceBeanName = adviceBeanName;
        this.expression = expression;
    }
}
