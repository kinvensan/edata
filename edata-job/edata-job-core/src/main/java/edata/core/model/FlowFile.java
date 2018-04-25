package edata.core.model;

import java.util.Properties;

public class FlowFile<T> {
    private Properties attributes;
    private T contents;

    public Properties getAttributes(){
        return this.attributes;
    }

    public T getContents() {
        return contents;
    }
}
