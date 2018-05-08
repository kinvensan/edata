package edata.api.sqlengine.gen;

import edata.api.sqlengine.builder.ColumnBuilder;
import edata.api.sqlengine.builder.ExpressionBuilder;
import edata.api.sqlengine.builder.FilterBuilder;
import edata.api.sqlengine.builder.QueryBuilder;
import edata.api.sqlengine.model.Query;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * QuerySqlGenTest
 *
 * @author kinven
 * @version 0.1
 * @date 18-5-7
 **/
public class QuerySqlGenTest {

    private Query query1;
    private Query query2;
    private Query query3;

    @Before
    public void setup(){
        query1 = QueryBuilder.builder().newOne().addColumn(
                ColumnBuilder.builder().newOne().name("a").table("t").build()
        ).build();
        query2 = QueryBuilder.builder().newOne().addColumn(
                ColumnBuilder.builder().newOne().name("a").table("t").build()
        ).addFilter(
                FilterBuilder.builder().newOne().name("a").table("t").expr(ExpressionBuilder.builder().newOne().func("a=1").build()).build()
        ).build();

        query3 = QueryBuilder.builder().newOne().addColumn(
                ColumnBuilder.builder().newOne().name("a").table("t").build()
        ).addColumn(
                ColumnBuilder.builder().newOne().name("b").table("t").aggregate(1).func("sum(${column.fullName})").build()
        ).addFilter(
                FilterBuilder.builder().newOne().name("a").table("t").func("${filter.fullName}=1").build()
        ).build();
    }

    @Test
    public void testGenSimpleSql(){
        QuerySqlGen querySqlGen = new QuerySqlGen();
        assertEquals("select t.a as a from t ",querySqlGen.from(query1).genSql());
    }

    @Test
    public void testGenFilterSql(){
        QuerySqlGen querySqlGen = new QuerySqlGen();
        assertEquals("select t.a as a from t where a=1 ",querySqlGen.from(query2).genSql());
    }

    @Test
    public void testAggergateSql(){
        QuerySqlGen querySqlGen = new QuerySqlGen();
        assertEquals("select t.a as a,sum(t.b) as b from t where t.a=1 group by t.a ",querySqlGen.from(query3).genSql());
    }

}