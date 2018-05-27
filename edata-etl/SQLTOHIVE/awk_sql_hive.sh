#! /bin/bash
starttime=`date +'%Y-%m-%d %H:%M:%S'`
#执行解压
if [ -d "/data/hkdata/$1.gz" ];then
    gzip -d /data/hkdata/$1.gz
fi
if [ -d "/data/hkdata/$1" ];then
    line1=`head -1 /data/hkdata/$1 | grep '/*'` 
    if [[${#line1} -gt 0 ]]; then 
        echo "start transform to csv....."
        cat /data/hkdata/$1  | sed '/\/\*!/'d | sed 's/),(/\n/g' | sed 's/(/\n/g;s/);s//g' | sed '/INSERT/d;/^[[:space:]]*$/d' > /data/hkdata/$1.tmp
    else
        mv /data/hkdata/$1 /data/hkdata/$1.tmp
    fi
    echo "start transform to hive......"
    rm -rf /data/hkdata/$1 
    cat /data/hkdata/$1.tmp  | awk -F"\'" '{c="";for(i=1;i<=NF;i++) { if(i%2==1) { gsub("\,","\x1",$i) } c=c$i} print c }'  > /data/hkdata/$1
fi
endtime=`date +'%Y-%m-%d %H:%M:%S'`
start_seconds=$(date --date="$starttime" +%s);
end_seconds=$(date --date="$endtime" +%s);
echo "本次运行时间： "$((end_seconds-start_seconds))"s"