package pers.john.spring.aop;

import pers.john.spring.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class AdvisorAutoProxyCreator implements BeanPostProcessor, AdvisorRegistry {

    private List<Advisor> advisorList = new ArrayList<>(16);

    @Override
    public void registryAdvisor(Advisor advisor) {
        advisorList.add(advisor);
    }

    @Override
    public List<Advisor> getAdvisor() {
        return advisorList;
    }

    @Override
    public Object postProcessorBeforeInitialization(Object bean, String beanName) {
        return null;
    }

    @Override
    public Object postProcessorAfterInitialization(Object bean, String beanName) {
        return null;
    }
}
