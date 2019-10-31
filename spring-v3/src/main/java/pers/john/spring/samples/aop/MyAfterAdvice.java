/* 版权所有(C)，欧拉信息服务有限公司，2019，所有权利保留。
 *
 * 项目名：	pers.john.spring.samples.aop
 * 文件名：	MyAfterAdvice
 * 模块说明：
 * 修改历史：
 * 2019/10/29 - tongyongjian - 创建。
 */

package pers.john.spring.samples.aop;

import com.alibaba.fastjson.JSONObject;
import pers.john.spring.aop.advice.MethodAfterAdvice;

import java.lang.reflect.Method;

/**
 * @author tongyongjian
 * @date 2019/10/29
 */
public class MyAfterAdvice implements MethodAfterAdvice {
    @Override
    public void after(Object returnValue, Method method, Object[] args, Object target) {
        System.out.println(String.format("This Is MyAfterAdvice.after, returnValue = %s, Method = %s, args = %s, target = %s"
            , JSONObject.toJSONString(returnValue), method.getName(), JSONObject.toJSONString(args), target));
    }
}
