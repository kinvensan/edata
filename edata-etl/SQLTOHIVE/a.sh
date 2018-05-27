cat a.txt | awk -F"\'" '{c="";for(i=1;i<=NF;i++) { if(i%2==1) { gsub("\,","\x1",$i) } c=c$i} print c }'  > a.txt.tmp

cat /data/hkdata/$1  | awk -F"\'" '{c="";for(i=1;i<=NF;i++) { if(i%2==1) { gsub("\,","\x1",$i) } c=c$i} print c }'  > $1.tmp