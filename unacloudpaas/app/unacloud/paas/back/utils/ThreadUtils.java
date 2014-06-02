package unacloud.paas.back.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class ThreadUtils{
    public static final ExecutorService pool=Executors.newFixedThreadPool(2);

}
