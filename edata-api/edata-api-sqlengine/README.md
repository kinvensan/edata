# SQL引擎

## 定义

### 前端用JSON定义
```json
{
    columns:[{
        name: "a",
        table: "table",
        uiname: "字段a“,
        dimession: 1
    },{
        name: "b",
        table: "table",
        asName: "b",
        expr: {
            body: "sum(b)"
            func:"select.sum"
            params:["b",]
        },
        aggerate: 1
    }],
    tables:[{
        name: "table",
        relation: 0,
        expr:{
              body:"",//autogen
              func:""
              params:["gamecode","gamecode"]
        }
    },{
        name: "tableB",
        relation: 1, //0 主表，1关联表 inner join ，2 left join，3 right join
        expr:{
              body:"",//autogen
              func:"join.on",
              params:["gamecode","gamecode"]
        }
    }],
    filters:[{
        name:"gamecode",
        table:"table",
        relation:"and"
        expr: {
            body:"",//autogen gamecode in (?,?,?,?)
            func:"where.in",
            params:["gamecode1","gamecode2","gamecode3","gamecode4"]
        }
    }]
}

```

### 定义事实表
由于大数据存储的过程中KUDU不适合使用大量字段，查询的过程中也不适合做大量的JOIN操作。
做出如下决定：
1. 事实表中存在内容用KUDU进行存储。
2. 每个KUDU表，parquet表中的维度扩建，使用视图进行扩展，这样能够把复杂的维度变为扁平化。
注意，视图的建立不要进行innerjoin。对于不变的维度查询和过滤，建议直接按照汇总如列中。
3. 建立维护模型类。
4. 所有字段命名规则必须一致。后续出字典表和查询工具。

### 关于表数据的实时处理。
如果表数据来源于kafka,那么直接用sparkstreaming进行ETL的汇总，由于时间间隔比较短，
每次生成多条数据，明细传入明细的parquet表中，
汇总的表，可以优先传入kudu中，然后每天过后，将前置的汇总表数据写入parquet文件，
然后利用union数据获取实时部分数据，利用parquet部分获取按天数据。