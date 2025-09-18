package com.neusoft.utils;

import com.neusoft.model.Users;

public class UserUtils {
    //线程变量，存放user实体类信息，即使是静态的与其他线程也是隔离的
    private static ThreadLocal<Users> userThreadLocal = new ThreadLocal<Users>();

    //从当前线程变量中获取用户信息
    public static Users getLoginUser() {
        System.out.println(Thread.currentThread().getId());
        Users user = userThreadLocal.get();
        return user;
    }


    //为当前的线程变量赋值上用户信息
    public static void setLoginUser(Users user) {
        System.out.println(Thread.currentThread().getId());
        userThreadLocal.set(user);
    }

    //清除线程变量
    public static void removeUser() {
        System.out.println(Thread.currentThread().getId());
        userThreadLocal.remove();
    }
}
