package edata.api.sqlengine;

import edata.api.sqlengine.query.*;
import org.junit.Test;

import java.util.Arrays;

/**
 * QueryTest
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/30
 */

public class QueryTest {

    @Test
    public void createQuery(){
        SelectColumn selectColumnA = new SelectColumn(1,"a","table");
        SelectColumn selectColumnB = new SelectColumn(1,"b","table");

        Select select = new Select();
        select.setColumns(Arrays.asList(selectColumnA,selectColumnB));
        WhereColumn whereColumnC = new WhereColumn("c","table");
        whereColumnC.setExpression(new BaseExpression("c=1"));

    }

}
