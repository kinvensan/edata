package edata.api.sql.kit;

import org.beetl.core.*;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.beetl.ext.fn.GetValueFunction;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

/**
 * BeetlKit
 *
 * @author kinven
 * @version 0.1
 * @date 18-5-8
 **/
public class BeetlKit {
    /**
     * BeetlKit 默认使用的GroupTemplate，用户可以设置新的
     */
    public static GroupTemplate gt = null;
    static
    {
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        Configuration cfg;
        try
        {
            cfg = Configuration.defaultConfiguration();

        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        gt = new GroupTemplate(resourceLoader, cfg);
        gt.registerFunction("beetlKit", new GetValueFunction());
        ScannerKit.getFunctionInfoMap().forEach((name,functionInfo) -> {
            gt.registerFunction(name,functionInfo.getInstance());
        });
        gt.setErrorHandler(new ConsoleErrorHandler() {
            protected void println(Writer w, String msg)
            {
                try
                {
                    w.write(msg);
                    w.write('\n');
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            protected void print(Writer w, String msg)
            {
                try
                {
                    w.write(msg);
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            protected void printThrowable(Writer w, Throwable t)
            {

                t.printStackTrace(new PrintWriter(w));
            }

            protected String getResourceName(String resourceId)
            {
                if (resourceId.length() > 10)
                {
                    return resourceId.substring(0, 10).concat("...");
                }
                else
                {
                    return resourceId;
                }
            }
        });
    }

    /* 模板部分 */

    /** 渲染模板
     * @param template
     * @param paras
     * @return 模板返回值
     */
    public static String render(String template, Map<String, Object> paras)
    {
        Template t = gt.getTemplate(template);
        t.binding(paras);
        return t.render();
    }

    /**
     * 注册函数
     * @param name
     * @param function
     */
    public static void registerFunction(String name, Function function){
        gt.registerFunction(name,function);
    }
}
