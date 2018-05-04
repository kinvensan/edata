package edata.api.sqlengine.gen;

import edata.api.sqlengine.model.*;
import edata.api.sqlengine.type.RelationType;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;


/**
 * QuerySqlGen
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/3
 */
public class QuerySqlGen {

    private StringBuilder sql = new StringBuilder();
    private Query query;
    private List<Column> columns;
    private List<Table> tables;
    private List<Filter> filters;
    private StringJoiner commaJoiner;
    private StringJoiner spaceJoiner;



    public QuerySqlGen(Query query){
        this.query = query;
    }

    public void init(){
        this.columns = query.getColumns().stream().filter(column -> !(0==column.getHidden())).collect(Collectors.toList());
        this.tables = query.getTables();
        this.filters = query.getFilters();
    }

    public void genSelectClause(){
        commaJoiner = new StringJoiner(SQL92.COMMA);
        if(columns.size() >= 1) {
            sql.append(SQL92.SELECT).append(SQL92.SPACE);
            this.columns.stream().forEach(column -> {
                spaceJoiner = new StringJoiner(SQL92.SPACE);
                spaceJoiner.add(column.getExpr().getBody()).add(SQL92.AS).add(column.getAsName());
                commaJoiner.add(spaceJoiner.toString());
            });
            sql.append(commaJoiner.toString());
        }
    }

    public void genFromClause(){
        spaceJoiner = new StringJoiner(SQL92.SPACE);
        if(tables.size() == 0){
            //抛出异常
        } else if(tables.size() == 1){
            spaceJoiner.add(SQL92.FROM).add(tables.get(0).getName());
        } else {
            this.tables.stream().forEach(table -> {
                spaceJoiner = new StringJoiner(SQL92.SPACE);
                spaceJoiner.add(jointrans(table.getRelation())).add(table.getName());
                if(table.getRelation() > 0) {
                    spaceJoiner.add(SQL92.ON).add(table.getExpr().getBody());
                }
            });
        }
        sql.append(spaceJoiner.toString());

    }

    public void genWhereClause(){
        spaceJoiner = new StringJoiner(SQL92.SPACE);
        if(filters.size() > 0){
            spaceJoiner.add(SQL92.WHERE);
            this.filters.stream().forEach(filter -> {
                spaceJoiner.add(andortrans(filter.getRelation())).add(filter.getExpr().getBody());

            });
        }
        sql.append(spaceJoiner.toString());
    }

    public void genGroupbyClause(){
        Boolean hasGroupBy = this.columns.stream().anyMatch(column -> 1==column.getAggregate());
        commaJoiner = new StringJoiner(SQL92.COMMA);
        if(hasGroupBy) {
            commaJoiner = new StringJoiner(SQL92.COMMA,SQL92.GROUPBY+SQL92.SPACE,SQL92.EMPTY);
            List<Column> groupbyList = this.columns.stream().filter(column -> 1 == column.getAggregate()).collect(Collectors.toList());
            groupbyList.forEach(column -> {
                commaJoiner.add(column.getExpr().getBody());
            });
        }
        sql.append(commaJoiner.toString());
    }

    public void genOrderbyClause(){
        commaJoiner = new StringJoiner(SQL92.COMMA);
        List<Column> orderByList = this.columns.stream().filter(column -> 0==column.getOrderby()).collect(Collectors.toList());
        if(orderByList.size() > 0){
            commaJoiner = new StringJoiner(SQL92.COMMA,SQL92.ORDERBY+SQL92.SPACE,SQL92.EMPTY);
            orderByList.forEach(column -> {
                commaJoiner.add(column.getAsName());
            });
        }
        sql.append(commaJoiner.toString());

    }


    private String jointrans(int i){
        switch (i){
            case 1: return SQL92.INNERJOIN;
            case 2: return SQL92.LEFTJOIN;
            case 0: return SQL92.EMPTY;
            default:return SQL92.EMPTY;
        }
    }

    private String andortrans(RelationType relationType){
        switch (relationType) {
            case NONE: return SQL92.EMPTY;
            case AND: return SQL92.AND;
            case OR: return SQL92.OR;
            default: return SQL92.EMPTY;
        }
    }

}
