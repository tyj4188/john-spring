/* 版权所有(C)，欧拉信息服务有限公司，2019，所有权利保留。
 *
 * 项目名：	pers.john.spring.aop
 * 文件名：	AopProxy
 * 模块说明：
 * 修改历史：
 * 2019/9/10 - tongyongjian - 创建。
 */

package pers.john.spring.aop;

/**
 * 代理顶层接口
 * @author tongyongjian
 * @date 2019/9/10
 */
public interface AopProxy {

    public Object getProxy();

    public Object getProxy(ClassLoader classLoader);
}
