package com.jankinwu.bkm.queue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author wwg
 * @description 延迟任务类
 * @date 2024/3/3 22:27
 */
public class DelayedTask implements Delayed {

    // 延迟时间
    private final long delayTime;

    // 到期时间
    private final long expireTime;

    // 要执行的任务
    private final Runnable task;

    public DelayedTask(long delayTime, Runnable task) {
        this.delayTime = delayTime;
        this.expireTime = System.currentTimeMillis() + delayTime;
        this.task = task;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(expireTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed other) {
        if (this == other) {
            return 0;
        }
        long diff = this.getDelay(TimeUnit.MILLISECONDS) - other.getDelay(TimeUnit.MILLISECONDS);
        return Long.compare(diff, 0);
    }

    public void execute() {
        task.run();
    }
}
