/* 
 * 
 * 项目名：	pers.john.spring.aop
 * 文件名：	AspectJPointcutAdvicor
 * 模块说明：	
 * 修改历史：
 * 2019/6/19 - Administrator - 创建。
 */

package pers.john.spring.aop.advisor;

import pers.john.spring.aop.pointcut.AspectJExpressionPointcut;
import pers.john.spring.aop.pointcut.Pointcut;

/**
 * 基于 AspectJ 实现的切点通知者
 * @author Administrator
 * @date 2019/6/19
 */
public class AspectJPointcutAdvisor extends AbstractPointcutAdvisor {

    public AspectJPointcutAdvisor(String adviceBeanName, String expression) {
        super(adviceBeanName, expression);
        this.pointcut = new AspectJExpressionPointcut(expression);
    }

    @Override
    public String getAdviceBeanName() {
        return adviceBeanName;
    }

    @Override
    public String getPointcutExpression() {
        return expression;
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }
}
