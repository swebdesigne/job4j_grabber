package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;


public class AlertRabbit {
    private Connection cn;

    private void init() throws ClassNotFoundException, SQLException {
        Class.forName(getProperties().getProperty("driver-class-name"));
        cn = DriverManager.getConnection(
                getProperties().getProperty("url"),
                getProperties().getProperty("username"),
                getProperties().getProperty("password")
        );
    }

    private void create() {
        try (Statement statement = cn.createStatement()) {
            statement.execute("create table if not exists rabbit(id serial primary key, created_date timestamp);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Properties getProperties() {
        Properties conf = new Properties();
        try (InputStream in = AlertRabbit.class
                .getClassLoader().getResourceAsStream("rabbit.properties")
        ) {
            conf.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conf;
    }

    public static void main(String[] args) throws SchedulerException, SQLException, ClassNotFoundException {
        AlertRabbit rabbit = new AlertRabbit();
        try {
            rabbit.init();
            rabbit.create();
            List<Long> store = new ArrayList<>();
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("store", rabbit.cn);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(
                            Integer.parseInt(rabbit.getProperties().getProperty("rabbit.interval"))
                    )
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
            System.out.println(store);
        } catch (SchedulerException | InterruptedException se) {
            se.printStackTrace();
        }
    }

    public static class Rabbit implements Job {

        public Rabbit() {
            System.out.println(String.format("hashCode %s ", hashCode()));
        }

        public LocalDateTime getLocalDateTime() {
            long millis = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(millis);
            LocalDateTime localDateTime = timestamp.toLocalDateTime();
            return localDateTime.truncatedTo(ChronoUnit.MICROS);
        }

        @Override
        public void execute(JobExecutionContext context) {
            System.out.println("Rabbit runs here ...");
            Connection connection = (Connection) context.getJobDetail().getJobDataMap().get("store");
            try (PreparedStatement statement = connection.prepareStatement("insert into rabbit (created_date) values (?)")) {
                statement.setTimestamp(1, Timestamp.valueOf(getLocalDateTime()));
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
