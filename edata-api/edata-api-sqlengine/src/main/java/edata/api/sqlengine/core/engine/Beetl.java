package edata.api.sqlengine.core.engine;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Beetl
 *
 * @author kinven
 * @version 0.1
 * @date 18-5-7
 **/
public class Beetl {

    GroupTemplate gt = null;
    Properties ps = null;
    public Beetl(Properties other) {
        try {
            ps = loadDefaultConfig();
            Properties ext = loadExtConfig();
            ps.putAll(ext);
            ps.putAll(other);
            StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
            Configuration cfg = Configuration.defaultConfiguration();
            gt = new GroupTemplate(resourceLoader, cfg);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

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
}
