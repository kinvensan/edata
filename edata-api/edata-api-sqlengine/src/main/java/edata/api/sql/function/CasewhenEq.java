package edata.api.sql.function;

import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;

/**
 * CasewhenEq
 *
 * 构造这样的语句
 * case when(score<='50')    then '实习'

 when(score>'50'  and  score<='500' )   then '赤脚医生'

 when(score>'500'  and score<='1000' )   then '村卫生员'

 when(score>'1000'  and score<='1500' )   then '乡卫生员'

 when(score>'1500'  and score<='2000' )   then '镇卫生员'

 when(score>'2000'  and score<='3000' )   then '医师'

 when(score>'3000'  and score<='5000' )   then '主治医师'

 when(score>'5000'  and score<='10000' )   then '副主任医师'

 when(score>'10000'  and score<='20000' )   then '主任医师'

 when(score>'20000'  and score<='50000' )   then '健康大使'

 else   '健康大使'  end
 *
 * @author kinven
 * @version 0.1
 * @date 2018/5/10
 */
public class CasewhenEq implements Function {
    @Override
    public String call(Object[] paras, Context ctx) {
        StringJoiner caseJoiner = new StringJoiner("","case","end");
        StringJoiner whenJoiner = new StringJoiner(" then ","when","");
        StringJoiner elseJoiner = new StringJoiner("","else","");
        if(paras.length > 0){
            if(paras[0].getClass().isAssignableFrom(Map.class)){
                Map<String,Object> params = (Map<String,Object>) paras[0];
                params.get("when");
                params.get("then");
                params.get("else");
            }
        }

        return "";
    }
}
