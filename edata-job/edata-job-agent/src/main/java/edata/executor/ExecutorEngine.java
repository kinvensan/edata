package edata.executor;

import edata.ReturnT;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;

public class ExecutorEngine {

    private static final ExecutorCompletionService<ReturnT<String>> executorService = new ExecutorCompletionService<ReturnT<String>>(Executors.newFixedThreadPool(4));
    private static final Map<Long,BashProcess> runningBashProcess = new ConcurrentHashMap<>();
    private static final Map<Long,BashProcess> errorBashProcess = new ConcurrentHashMap<>();


    public void scan() {
        //获取nsq中的两个值，taskname，taskid以及task
        List<String> engines = new ArrayList<>();
        for(String base: engines) {
            String url = base ;
            long taskId = 1L;
            BashProcess bashProcess = BashProcess.create();
            runningBashProcess.put(taskId,bashProcess);
            executorService.submit(() -> {
                runningBashProcess.put(taskId,bashProcess);
                bashProcess.run();
                runningBashProcess.remove(taskId);
                return ReturnT.SUCCESS;
            });
        }
    }

}
