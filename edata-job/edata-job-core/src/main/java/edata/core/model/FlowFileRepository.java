package edata.core.model;

import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FlowFileRepository<T> {
    private Map<Long,FlowFile<T>> repository = new ConcurrentHashMap<>();

}
