package pers.john.spring.aop.pointcut;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.ShadowMatch;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * AspectJ 表达式的切点实现
 */
public class AspectJExpressionPointcut implements Pointcut {

    private String expression;

    private PointcutParser pointcutParser;

    private PointcutExpression pointcutExpression;

    public AspectJExpressionPointcut(String expression) {
        Objects.requireNonNull(expression, "切面表达式不能为 NULL");
        this.expression = expression;
        this.pointcutParser = PointcutParser
            .getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution();
        this.pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }

    public boolean matchClass(Class<?> beanClass) {
        return pointcutExpression.couldMatchJoinPointsInType(beanClass);
    }

    public boolean matchMethod(Method method, Class<?> beanClass) {
        ShadowMatch shadowMatch = pointcutExpression.matchesMethodExecution(method);
        return shadowMatch.alwaysMatches();
    }

    public String getExpression() {
        return expression;
    }
}
