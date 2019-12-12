///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package ma.bservices.mb;
//import ma.bservices.services.CronJob;
//import org.quartz.CronTrigger;
//import org.quartz.Scheduler;
//import org.quartz.SchedulerFactory;
//import org.quartz.impl.StdSchedulerFactory;
//import org.quartz.JobDetail;
///**
// *
// * @author j.allali
// */
//public class CronSchedule {
//    
//    public CronSchedule() throws Exception{
//        
//        SchedulerFactory sf=new StdSchedulerFactory();
//        Scheduler sched=sf.getScheduler();
//        JobDetail jd=new JobDetail("Job1", "group1", CronJob.class);
//        CronTrigger ct=new CronTrigger("conTrigger", "group2", "0 04 17 ? * MON-FRI");
//        sched.scheduleJob(jd, ct);
//        sched.start();
//    }
//    
//    public static void main(String[] Mel){
//        try{
//            new CronSchedule();
//        }catch(Exception e){
//            e.getStackTrace();
//        }
//    }
//    
//}
