package com.efun.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kinven on 16-11-9.
 */
@Repository
public class HBaseHelloDao {

    @Autowired
    @Qualifier("hbaseDataSource")
    DataSource hbaseDataSource;

    public Map<String,String> getHello(){
        Map<String,String> result = new HashMap<>();
        try(
        Connection con = hbaseDataSource.getConnection();
        PreparedStatement statement = con.prepareStatement("select * from test");
        ) {
            ResultSet rset = statement.executeQuery();
            while (rset.next()) {
                result.put(rset.getString("mykey"),rset.getString("mycolumn"));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return result;
    }
}
