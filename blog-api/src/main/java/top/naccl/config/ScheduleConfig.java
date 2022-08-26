package top.naccl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import top.naccl.service.VxMsg;
import top.naccl.util.common.SpringContextUtils;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(taskExecutors());
    }

    @Bean(destroyMethod = "shutdown")
    public ScheduledThreadPoolExecutor taskExecutors() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(50);
        ex.setRemoveOnCancelPolicy(true);// 取消后就从线程池移除，没有这句的话只是取消任务
        ex.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return ex;
    }

    @Scheduled(cron = "0 18 17 * * ?")
    public void sendVxMsg() throws Exception{
        SpringContextUtils.getBean(VxMsg.class).sendVxMsg();
    }
}
