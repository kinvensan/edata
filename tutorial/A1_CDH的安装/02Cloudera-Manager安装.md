��ο����� http://blog.csdn.net/u010022051/article/details/51205765

1.����rpm��װ��

rpm��װ�����ص�ַ������jdk����Լ���װ���˿��Բ������ء�
http://archive.cloudera.com/cm5/redhat/6/x86_64/cm/5.7/RPMS/x86_64/

���������

 


2.master�ڵ㰲װ

�����غõ�rpm���ŵ�һ���ļ����У��������������뵽����ļ����ֶ���װ��
yum localinstall --nogpgcheck *.rpm
yum install --installroot=/usr/src/ 
ʹ��yum��װ��ͬʱ��װ��ص��������ǳ�����
���Ҫж��ʹ��
yum --setopt=tsflags=noscripts remove xxxx

3.slave�ڵ㰲װ

slave�в���Ҫ��װserver�İ���ֻ��Ҫ��װcloudera-manager-agent.rpm��cloudera-manager-daemons.rpm���Ƚ�����rpm��������slave�ڵ��ϣ�ʣ�°�װ������masterһ����


4.��װcloduera manager�����ư�װ��


wget
 [url=http://archive.cloudera.com/cm5/installer/latest/cloudera-manager-installer.bin]http://archive.cloudera.com/cm5/

... nager-installer.bin[/url]

 

2)chmod

u+x cloudera-manager-installer.bin

 

3)./cloudera-manager-installer.bin


4)���ݰ�װ��һ·next��ע�⣬���֮ǰmaster��û���ֶ���װrpm����ʱ�ͻ��������أ������ٶ�һ�㶼������̫��ʱ�䡣

5)��װ�����Ժ����ʾ��¼7180�˿ڡ�