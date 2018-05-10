package edata.api.sql;

import edata.api.sql.kit.StringKit;
import lombok.Data;

/**
 * Config
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/5
 */
@Data
public class Config {

    public static final String DEFAULT_PACKAGE = Config.class.getPackage().getName();

    private String entityPackage = StringKit.substringBeforeLast(DEFAULT_PACKAGE,".");
    private String functionPackage = DEFAULT_PACKAGE;

}
