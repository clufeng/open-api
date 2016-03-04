package com.yonyou.mcloud.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 序列号生成器
 * Created by hubo on 2016/2/29
 */
public class SequenceGenerator {

    /**
     * 同一秒内，最大生成数
     */
    private static final long ONE_STEP = 10000;
    /**
     * 最后同步时间
     */
    private String lastTime;
    /**
     * 计数器
     */
    private int lastCount;
    /**
     * 单例实例
     */
    private volatile static SequenceGenerator instance;
    /**
     * 日志
     */
    private static Logger log = LoggerFactory.getLogger(SequenceGenerator.class);

    private SequenceGenerator() {
        lastTime = getCurrentTime();
    }

    /**
     * 生成ID
     *
     * @return 生成的ID
     */
    public synchronized String next() {

        String curr = getCurrentTime();

        /*
          同一秒内生成序列号
         */
        int count;

        if (curr.equals(lastTime)) {
            count = generateNumber();
        } else {
            lastTime = curr;
            lastCount = 0;
            count = generateNumber();
        }

        return lastTime + "" + String.format("%04d", count);
    }

    /**
     * 同一秒内生成序列号，如果超过最大生成数，则等到下一秒重新生成序列号
     * @return 序列号
     */
    private int generateNumber() {
        if (lastCount == ONE_STEP) {
            while (true) {
                String now = getCurrentTime();
                if (now.equals(lastTime)) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        log.error(e.getMessage(), e);
                    }
                } else {
                    lastTime = now;
                    lastCount = 0;
                    break;
                }
            }
        }
        return lastCount++;
    }

    /**
     * 获取当前时间(格式：yyyyMMddHHmmss)
     *
     * @return 当前时间
     */
    public String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date currentTime = new Date();
        return formatter.format(currentTime);
    }

    /**
     * 获取ID生成器 实例
     *
     * @return SequenceGenerator 实例
     */
    public static SequenceGenerator getInstance() {
        if (instance == null) {
            synchronized (SequenceGenerator.class) {
                if (instance == null) {
                    instance = new SequenceGenerator();
                }
            }
        }
        return instance;
    }

}
