package edata.org.beetl.sql.core;

import org.beetl.sql.core.SQLIdNameConversion;
import org.beetl.sql.core.SQLLoader;
import org.beetl.sql.core.SQLSource;
import org.beetl.sql.core.db.DBStyle;

/**
 * QueryLoader 代替 ClasspathLoader的功能。
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/1
 */
public class QueryLoader implements SQLLoader {

    @Override
    public SQLSource getSQL(String id) {
        return null;
    }

    @Override
    public boolean isModified(String id) {
        return false;
    }

    @Override
    public boolean exist(String id) {
        return false;
    }

    @Override
    public void addSQL(String id, SQLSource msource) {

    }

    @Override
    public boolean isAutoCheck() {
        return false;
    }

    @Override
    public void setAutoCheck(boolean check) {

    }

    @Override
    public String getCharset() {
        return null;
    }

    @Override
    public void setCharset(String charset) {

    }

    @Override
    public void setSQLIdNameConversion(SQLIdNameConversion sqlIdNc) {

    }

    @Override
    public void setDbStyle(DBStyle dbStyle) {

    }

    @Override
    public void refresh() {

    }
}
