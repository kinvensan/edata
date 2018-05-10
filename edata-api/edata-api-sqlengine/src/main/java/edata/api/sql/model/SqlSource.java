package edata.api.sql.model;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * SqlSource
 *
 * @author kinven
 * @version 0.1
 * @date 18-5-7
 **/
public class SqlSource {
    private String id;
    private String template;
    private Map<String,Object> params = new LinkedHashMap<>();

    public SqlSource(){

    }

    public void addParamsMap(Map<String,Object> params){
        this.params.putAll(params);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template.trim();
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SqlSource)) return false;
        SqlSource sqlSource = (SqlSource) o;
        return Objects.equals(id, sqlSource.id) &&
                Objects.equals(template, sqlSource.template) &&
                Objects.equals(params, sqlSource.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, template, params);
    }

    @Override
    public String toString() {
        return "SqlSource{" +
                "id='" + id + '\'' +
                ", template='" + template + '\'' +
                ", params=" + params +
                '}';
    }
}
