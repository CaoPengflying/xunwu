package com.cpf.xunwu.controller;

import com.cpf.xunwu.base.ApiResponse;
import com.cpf.xunwu.entity.House;
import com.cpf.xunwu.entity.User;
import com.cpf.xunwu.service.HouseService;
import com.cpf.xunwu.service.UserService;
import com.google.common.collect.Maps;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * date 2020/7/19
 *
 * @author caopengflying
 */
@RestController
public class ResultUnionController {
    AtomicInteger atomicInteger = new AtomicInteger(0);
    @Resource
    private UserService userService;
    @Resource
    private HouseService houseService;
    @Resource(name = "executorService")
    private ThreadPoolTaskExecutor executorService;

    @PostMapping("/resultUnion")
    public ApiResponse resultUnion() {
        int i = atomicInteger.incrementAndGet();
        CompletableFuture<User> f1 = CompletableFuture.supplyAsync(() -> userService.selectUserByName("cpf"), executorService);
        CompletableFuture<House> f2 = CompletableFuture.supplyAsync(() -> houseService.getById(1), executorService);
        //等待三个数据源都返回后，再组装数据。这里会有一个线程阻塞
/*        try {
            f1.get(100L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        CompletableFuture.allOf(f1, f2).join();*/
        User user = null;
        House house = null;
        HashMap<String, Object> map = Maps.newHashMap();
        try {
            user = f1.get(100L, TimeUnit.MILLISECONDS);
            map.put("user", user);
            System.out.println(Thread.currentThread().getName() + "第" + i + "次" + user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            house = f2.get(100L, TimeUnit.MILLISECONDS);
            map.put("house", house);
            System.out.println(Thread.currentThread().getName() + "第" + i + "次" + house);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ApiResponse(200, "获取成功", map);
    }
}
