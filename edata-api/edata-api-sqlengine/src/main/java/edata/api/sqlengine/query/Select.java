package edata.api.sqlengine.query;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Select is SQL part "select * from xxx"
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/26
 */
@Data
@Builder
public class Select {
    private List<SelectColumn> columns = new ArrayList<>();


}
