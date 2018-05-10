package edata.api.sql.builder;

import edata.api.sql.model.Query;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * QueryBuilderTest
 *
 * @author kinven
 * @version 0.1
 * @date 18-5-7
 **/
public class QueryBuilderTest {

    @Test
    public void testOneColumnGenTable(){
        Query query = QueryBuilder.builder().newOne().addColumn(
                ColumnBuilder.builder().newOne().name("a").table("t").build()
        ).build();
        assertEquals(1,query.getTables().size() );
    }

}