package edata.api.sqlengine.query;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * QueryManager
 *
 * @author kinven
 * @version 0.1
 * @date 2018/4/30
 */
@Data
@RequiredArgsConstructor
@Accessors(chain = true)
public class Query {
    @NonNull
    private Select select;
    @NonNull
    private From from;
    private Where where;
    private GroupBy groupBy;
    private OrderBy orderBy;
}
