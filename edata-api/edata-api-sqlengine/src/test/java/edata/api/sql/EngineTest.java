package edata.api.sql;

import edata.api.sql.builder.ColumnBuilder;
import edata.api.sql.builder.ExpressionBuilder;
import edata.api.sql.builder.FilterBuilder;
import edata.api.sql.builder.QueryBuilder;
import edata.api.sql.model.Query;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * EngineTest
 *
 * @author kinven
 * @version 0.1
 * @date 18-5-10
 **/
public class EngineTest {

    @Before
    public void setup(){
        Engine.getInstance();
    }

    @Test
    public void testSimpleSqlSouce(){
        Query query1 = QueryBuilder.builder().newOne().addColumn(
                ColumnBuilder.builder().newOne().name("a").table("t").build()
        ).build();
        assertEquals("select t.a as a from t",Engine.getInstance().parse(query1).getTemplate());
    }

    @Test
    public void testFilterSqlSource(){
        Query query2 = QueryBuilder.builder().newOne().addColumn(
                ColumnBuilder.builder().newOne().name("a").table("t").build()
        ).addFilter(
                FilterBuilder.builder().newOne().name("a").table("t").expr(ExpressionBuilder.builder().newOne().func("a=1").build()).build()
        ).build();
        assertEquals("select t.a as a from t where a=1",Engine.getInstance().parse(query2).getTemplate());
    }

    @Test
    public void testAggergateSqlSource(){
        Query query3 = QueryBuilder.builder().newOne().addColumn(
                ColumnBuilder.builder().newOne().name("a").table("t").build()
        ).addColumn(
                ColumnBuilder.builder().newOne().name("b").table("t").aggregate(1).func("sum(${column.fullName})").build()
        ).addFilter(
                FilterBuilder.builder().newOne().name("a").table("t").func("${filter.fullName}=1").build()
        ).build();
        assertEquals("select t.a as a,sum(t.b) as b from t where t.a=1 group by t.a",Engine.getInstance().parse(query3).getTemplate());
    }

}