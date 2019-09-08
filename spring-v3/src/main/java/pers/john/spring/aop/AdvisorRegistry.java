package pers.john.spring.aop;

import java.util.List;

public interface AdvisorRegistry {

    public void registryAdvisor(Advisor advisor);

    public List<Advisor> getAdvisor();

}
