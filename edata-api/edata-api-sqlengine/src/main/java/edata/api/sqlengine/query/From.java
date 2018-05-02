package edata.api.sqlengine.query;

import lombok.Builder;
import lombok.Data;

import java.util.*;

/**
 * From is SQL part "select * from xxx"
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/26
 */
@Data
@Builder
public class From {
    private String table;
    private List<FromJoin> joins= new ArrayList<>();

}
