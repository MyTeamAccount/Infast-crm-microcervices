package com.example.project_service.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Main {
    private static Semaphore semaphore=new Semaphore(1);
    public static void main(String[] args) {

        Runnable task=()->{
            try {
                semaphore.acquire();
                System.out.println("Thread ishlamoqda : "+Thread.currentThread().getName());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
            }
        };

        Thread thread1=new Thread(task);
        Thread thread2=new Thread(task);
        thread1.start();
        thread2.start();
    }
}



