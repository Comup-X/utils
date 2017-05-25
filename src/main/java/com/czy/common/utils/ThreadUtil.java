/*
 * 文 件 名 : ThreadUtil
 * 版    权 : CZYSOFT TECHNOLOGY CO.,LTD.Copyright 2017-2030.All rights reserved
 * 描    述 : <描述>
 * 修 改 人 : <工号>xu.yang22@zte.com.cn
 * 修改时间 : 2017-05-24 10:10
 * 需求单号 : <需求Redmine单号>
 * 变更单号 : <变更Redmine单号>
 * 修改内容 : <修改内容>
 * Version : V1.0
 */
package com.czy.common.utils;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <一句话功能简介><br>
 *
 * @author [004293]004293@ch.com
 * @version [版本号, 2017-05-24]
 * @Description: //TODO <功能详细描述>
 * @ClassName:ThreadUtil
 * @see [相关类/方法]
 * @since [产品/模块]
 */
public final class ThreadUtil {

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() + 1, Runtime.getRuntime().availableProcessors() + 1, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(512));

    public static Future<?> runOnBackground(Runnable runnable) {
        return executor.submit(runnable);
    }
}
