package edata.api.sqlengine.builder;

import edata.api.sqlengine.core.engine.Beetl;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * BeetlExpression
 *
 * @author kinven
 * @version 0.1
 * @date 18-5-7
 **/
public class BeetlExpression {

    private static BeetlExpression instance;

    private GroupTemplate gt = null;
    private Properties ps = null;
    public BeetlExpression() {
        try {
            ps = loadDefaultConfig();
            Properties ext = loadExtConfig();
            ps.putAll(ext);
            StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
            Configuration cfg = Configuration.defaultConfiguration();
            gt = new GroupTemplate(resourceLoader, cfg);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static BeetlExpression getInstance(){
        if(null == instance) {
            instance = new BeetlExpression();
        }
        return instance;
    }

    /***
     * 加载cfg自定义配置
     *
     * @return
     */
    public Properties loadDefaultConfig () {
        Properties ps  = new Properties();
        InputStream ins = this.getClass().getResourceAsStream(
                "/edatasql.properties");
        if(ins==null) return ps;
        try {
            ps.load(ins);
        } catch (IOException e) {
            throw new RuntimeException("默认配置文件加载错:/edatasql.properties");
        }
        return ps;
    }


    public Properties loadExtConfig () {
        Properties ps  = new Properties();
        InputStream ins = Thread.currentThread().getContextClassLoader().getResourceAsStream(
                "edatasql-ext.properties");
        if(ins==null){
            return ps;
        }

        try {
            ps.load(ins);
            ins.close();
        } catch (IOException e) {
            throw new RuntimeException("默认配置文件加载错:/edatasql-ext.properties");
        }

        return ps;
    }




    public GroupTemplate getGroupTemplate() {
        return gt;
    }

    public Properties getPs() {
        return ps;
    }

    public String render(String template,Map<String,Object> params){
        Template t = gt.getTemplate(template);
        params.forEach((key,value)-> t.binding(key,value));
        return t.render();
    }
}
