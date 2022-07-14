package com.yws.cf;

import lombok.Getter;
import org.apache.commons.lang3.time.StopWatch;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CompletableFutureMallDemo {
    static List<NetMall> list = Arrays.asList(
            new NetMall("jd"),
            new NetMall("dangdang"),
            new NetMall("taobao"));


    /**'
     * CompletableFuture异步任务同时搜查
     * @param list
     * @param productName
     * @return
     */
    public static List<String> getPriceByCompletableFuture(List<NetMall> list, String productName) {
        return list
                .stream()
                .map(netMall ->
                        CompletableFuture.supplyAsync(() ->
                            String.format(productName + " in %s price is %.2f", netMall.getNetMallName(), netMall.calcPrice(productName)))
        )
                .collect(Collectors.toList())
                .stream()
                .map(s -> s.join())
                .collect(Collectors.toList());
    }


    /**
     * 一家家搜查
     * @param list
     * @param productName
     * @return
     */
    public static List<String> getPrice(List<NetMall> list, String productName) {
        //《mysql》 in taobao is 90.43
        return list.stream()
                .map(netMall ->
                    String.format(productName + " in %s price is %.2f", netMall.getNetMallName(), netMall.calcPrice(productName)))
                .collect(Collectors.toList());
    }


    public static void main(String[] args) {

        //创建后立即start，常用
        StopWatch watch = StopWatch.createStarted();

        List<String> mysql = getPrice(list, "mysql");
        mysql.stream().forEach(e -> System.out.println(e));

        System.out.println("花费的时间>>" + watch.getTime() + "ms");

        //复位后, 重新计时
        watch.reset();
        watch.start();

        List<String> mysql2 = getPriceByCompletableFuture(list, "mysql");
        mysql2.stream().forEach(e -> System.out.println(e));

        System.out.println("花费的时间>>" + watch.getTime() + "ms");
    }

}


class NetMall {
    @Getter
    private String netMallName;

    public NetMall(String netMallName) {
        this.netMallName = netMallName;
    }

    public double calcPrice(String productName) {
        try { TimeUnit.SECONDS.sleep(1); } catch (Exception e){ e.printStackTrace(); }
        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
    }
}
