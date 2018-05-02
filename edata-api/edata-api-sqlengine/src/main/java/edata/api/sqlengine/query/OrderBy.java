package edata.api.sqlengine.query;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderBy
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/27
 */
@Data
@Builder
public class OrderBy {
    private List<SelectColumn> columns = new ArrayList<>();

}
