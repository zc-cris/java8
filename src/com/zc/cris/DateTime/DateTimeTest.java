package com.zc.cris.DateTime;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

// 传统的时间都存在线程安全问题，如果要使用java8 之前的方式解决，可以使用 ThreadLocal
public class DateTimeTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        // 使用lambda 表达式代替匿名内部类的书写(然后使用ThreadLocale 锁住每个SimpleDateFormat 对象)
        Callable<Date> task = () -> DateTimeThreadLocale.convert("20161203");

        // 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(10);

        List<Future<Date>> results = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            results.add(pool.submit(task));
        }
        for (Future<Date> result : results) {
            System.out.println(result.get(
            ));
        }
        // 杀掉线程池
        pool.shutdown();

    }

    // java8 时间API 避免了线程安全问题
    @Test
    public void test() throws ExecutionException, InterruptedException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        Callable<LocalDate> task = () -> LocalDate.parse("20171210", dateTimeFormatter);

        ExecutorService pool = Executors.newFixedThreadPool(5);
        List<Future<LocalDate>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(pool.submit(task));
        }
        for (Future<LocalDate> localDateFuture : list) {
            System.out.println(localDateFuture.get());
        }
        pool.shutdown();
    }

    // java8 中的时间对象
    // LocalDate, LocalTime, LocatDateTime
    @Test
    public void test2() {

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);  // 2018-04-24T14:57:55.022 标准时间格式

        // 2016-10-12T12:34:23.000000341 自定义时间
        LocalDateTime localDateTime1 = LocalDateTime.of(2016, 10, 12, 12, 34, 23, 341);
        System.out.println(localDateTime1);

        LocalDateTime localDateTime2 = localDateTime.plusYears(2);
        System.out.println(localDateTime2);

        LocalDateTime localDateTime3 = localDateTime.minusMonths(3);
        System.out.println(localDateTime3);

        System.out.println(localDateTime.getMonth().getValue());
        System.out.println(localDateTime.getYear());
        System.out.println(localDateTime.getDayOfMonth());
        System.out.println(localDateTime.getHour());
        System.out.println(localDateTime.getDayOfWeek());   // TUESDAY

    }

    // java8 中的时间戳：Instant--》以Unix元年 1970年1月1日 00:00:00 到某个时间的毫秒值
    @Test
    public void test3() {

        Instant now = Instant.now();    // 默认得到的是UTC 时区的时间（不是毫秒值）
        System.out.println(now);    // 2018-04-24T07:11:06.595Z

        // 获取我们当前的时间需要加上8个小时的时差
        OffsetDateTime offsetDateTime = now.atOffset(ZoneOffset.ofHours(8));
        System.out.println(offsetDateTime);

        System.out.println(now.toEpochMilli());     // 获取毫秒值
        System.out.println(Instant.ofEpochSecond(60));  // 1970-01-01T00:01:00Z


    }

    // Duration:计算两个时间戳的差值
    // Period:计算两个日期之间的差值
    @Test
    public void test4() throws InterruptedException {
        Instant now = Instant.now();

        Thread.sleep(1000);

        Instant now1 = Instant.now();
        // 打印两个时间戳之间的毫秒值
        System.out.println(Duration.between(now, now1).toMillis());

        LocalTime localTime = LocalTime.now();

        Thread.sleep(1000);

        LocalTime localTime1 = LocalTime.now();
        System.out.println(Duration.between(localTime, localTime1).toMillis());

    }

    @Test
    public void test5() {
        // 自定日期
        LocalDate localDate = LocalDate.of(2015, 12, 23);
        // 获取当前日期
        LocalDate localDate1 = LocalDate.now();
        Period between = Period.between(localDate, localDate1);
        System.out.println(between);
        System.out.println(between.getYears());
        System.out.println(between.getMonths());
        System.out.println(between.getDays());
    }

    // TemporalAdjuster: 时间校正器
    @Test
    public void test6() {
        System.out.println(LocalDate.now());    // 2018-04-24
        System.out.println(LocalTime.now());    // 15:29:00.264
        System.out.println(LocalDateTime.now());    // 2018-04-24T15:29:00.264

        LocalDateTime localDateTime = LocalDateTime.now();
        // 指定任意时间或者日期
        System.out.println(localDateTime.withMonth(3));

        // 下一个星期天
        System.out.println(localDateTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY)));

        // 计算下一个工作日（自定义时间校正器）
        LocalDateTime localDateTime2 = localDateTime.with(l -> {
            LocalDateTime localDateTime1 = (LocalDateTime) l;
            DayOfWeek dayOfWeek = localDateTime1.getDayOfWeek();
            if (dayOfWeek.equals(DayOfWeek.FRIDAY)) {
                return localDateTime1.plusDays(3);
            } else if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
                return localDateTime1.plusDays(2);
            } else {
                return localDateTime1.plusDays(1);
            }
        });
        System.out.println(localDateTime2);

    }

    // DateTimeFormatter:格式化时间/日期
    @Test
    public void test7() {
        // 使用默认的时间格式化格式---》将时间按照指定格式转换为字符串 format
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(dateTimeFormatter.format(localDateTime));    // 2018-04-24

        DateTimeFormatter dateTimeFormatter1 = dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        String format = dateTimeFormatter1.format(localDateTime);
        System.out.println(format);     // 2018年04月24日 16:07:46

        // 解析时间：将字符串按照特定的格式转换为时间对象  parse
        LocalDateTime parse = LocalDateTime.parse("2013年12月11日 13:34:23", dateTimeFormatter1);
        System.out.println(parse);  // 2013-12-11T13:34:23
        System.out.println(DateTimeFormatter.ISO_LOCAL_DATE.format(parse));     // 2013-12-11

    }

    // 时区处理
    @Test
    public void test8() {
        /*Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        availableZoneIds.forEach(System.out::println);*/

        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Tokyo"));
        System.out.println(localDateTime);  // 2018-04-24T20:19:43.095  获取某个时区对应的当前时间

        LocalDateTime localDateTime1 = LocalDateTime.now(ZoneId.of("Asia/Tokyo"));
        ;
        ZonedDateTime zonedDateTime = localDateTime1.atZone(ZoneId.of("Asia/Tokyo"));
        System.out.println(zonedDateTime);  // 2018-04-24T20:21:48.224+09:00[Asia/Tokyo] 获取某个时区的相对于UTC 标准时区的详细时间

    }

}
