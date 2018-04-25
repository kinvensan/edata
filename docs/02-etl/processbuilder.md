# java去调用并执行shell脚本
## 背景

我们在开发过程中，大部分是java开发， 而在文本处理过程中，主要就是脚本进行开发。 java开发的特点就是我们可以很早地进行TDDL， METAQ 等等地对接； 而脚本开发的特点就是在进行批处理的时候非常方便。 前阵子我遇到这么一个需求场景： 对抓取的数据进行打包， 后来又遇到我要通过脚本进行抓取，比如nodejs下基于phantomjs的casperjs爬虫。
解决方法

对于第一个问题：java抓取，并且把结果打包。 
那么比较直接的做法就是，java接收各种消息（db，metaq等等），然后借助于jstorm集群进行调度和抓取。 最后把抓取的结果保存到一个文件中，并且通过调用shell打包， 回传。 也许有同学会问， 为什么不直接把java调用odps直接保存文件，答案是，我们的集群不是hz集群，直接上传odps速度很有问题，因此先打包比较合适。（这里不纠结设计了，我们回到正题）
## java调用shell的方法
### 通过ProcessBuilder进行调度

这种方法比较直观，而且参数的设置也比较方便， 比如我在实践中的代码(我隐藏了部分业务代码)：
```
ProcessBuilder pb = new ProcessBuilder("./" + RUNNING_SHELL_FILE, param1,
                                               param2, param3);
        pb.directory(new File(SHELL_FILE_DIR));
        int runningStatus = 0;
        String s = null;
        try {
            Process p = pb.start();
            try {
                runningStatus = p.waitFor();
            } catch (InterruptedException e) {
            }

        } catch (IOException e) {
        }
        if (runningStatus != 0) {
        }
        return;
```
这里有必要解释一下几个参数：
RUNNING_SHELL_FILE：要运行的脚本
SHELL_FILE_DIR：要运行的脚本所在的目录； 当然你也可以把要运行的脚本写成全路径。
runningStatus：运行状态，0标识正常。 详细可以看java文档。
param1, param2, param3：可以在RUNNING_SHELL_FILE脚本中直接通过1,2,$3分别拿到的参数。
直接通过系统Runtime执行shell

这个方法比较暴力，也比较常用， 代码如下：
```
p = Runtime.getRuntime().exec(SHELL_FILE_DIR + RUNNING_SHELL_FILE + " "+param1+" "+param2+" "+param3);
p.waitFor();
```
我们发现，通过Runtime的方式并没有builder那么方便，特别是参数方面，必须自己加空格分开，因为exec会把整个字符串作为shell运行。
可能存在的问题以及解决方法

如果你觉得通过上面就能满足你的需求，那么可能是要碰壁了。你会遇到以下情况。
没权限运行

这个情况我们团队的朱东方就遇到了， 在做DTS迁移的过程中，要执行包里面的shell脚本， 解压出来了之后，发现执行不了。 那么就按照上面的方法授权吧
```
ProcessBuilder builder = new ProcessBuilder("/bin/chmod", "755", tempFile.getPath());
            Process process = builder.start();
            int rc = process.waitFor();
```
java进行一直等待shell返回

这个问题估计更加经常遇到。 原因是， shell脚本中有echo或者print输出， 导致缓冲区被用完了! 为了避免这种情况， 一定要把缓冲区读一下， 好处就是，可以对shell的具体运行状态进行log出来。 比如上面我的例子中我会变成：
```
ProcessBuilder pb = new ProcessBuilder("./" + RUNNING_SHELL_FILE, keyword.trim(),
                                               taskId.toString(), fileName);
        pb.directory(new File(CASPERJS_FILE_DIR));
        int runningStatus = 0;
        String s = null;
        try {
            Process p = pb.start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((s = stdInput.readLine()) != null) {
                LOG.error(s);
            }
            while ((s = stdError.readLine()) != null) {
                LOG.error(s);
            }
            try {
                runningStatus = p.waitFor();
            } catch (InterruptedException e) {
            }
```
记得在start()之后， waitFor（）之前把缓冲区读出来打log， 就可以看到你的shell为什么会没有按照预期运行。 这个还有一个好处是，可以读shell里面输出的结果， 方便java代码进一步操作。
也许你还会遇到这个问题，明明手工可以运行的命令，java调用的shell中某一些命令居然不能执行，报错：命令不存在！

比如我在使用casperjs的时候，手工去执行shell明明是可以执行的，但是java调用的时候，发现总是出错。 通过读取缓冲区就能发现错误日志了。 我发现即便自己把安装的casperjs的bin已经加入了path中（/etc/profile, 各种bashrc中）还不够。 比如：
```
export NODE_HOME="/home/admin/node"
export CASPERJS_HOME="/home/admin/casperjs"
export PHANTOMJS_HOME="/home/admin/phantomjs"
export PATH=$PATH:$JAVA_HOME/bin:/root/bin:$NODE_HOME/bin:$CASPERJS_HOME/bin:$PHANTOMJS_HOME/bin
```
原来是因为java在调用shell的时候，默认用的是系统的/bin/下的指令。特别是你用root权限运行的时候。 这时候，你要在/bin下加软链了。针对我上面的例子，就要在/bin下加软链：
```
ln -s /home/admin/casperjs/bin/casperjs casperjs;
ln -s /home/admin/node/bin/node node;
ln -s /home/admin/phantomjs/bin/phantomjs phantomjs;
```
这样，问题就可以解决了。
如果是通过java调用shell进行打包，那么要注意路径的问题了

因为shell里面tar的压缩和解压可不能直接写：
```
    tar -zcf /home/admin/data/result.tar.gz /home/admin/data/result
```
直接给你报错，因为tar的压缩源必须到路径下面， 因此可以写成
```
    tar -zcf /home/admin/data/result.tar.gz -C /home/admin/data/ result
```
如果我的shell是在jar包中怎么办？

答案是：解压出来。再按照上面指示进行操作。
（1）找到路径
```
String jarPath = findClassJarPath(ClassLoaderUtil.class);
        JarFile topLevelJarFile = null;
        try {
            topLevelJarFile = new JarFile(jarPath);
            Enumeration<JarEntry> entries = topLevelJarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (!entry.isDirectory() && entry.getName().endsWith(".sh")) {
                    对你的shell文件进行处理
                }
            }
```
对文件处理的方法就简单了，直接touch一个临时文件，然后把数据流写入，代码：
```
FileUtils.touch(tempjline);
tempjline.deleteOnExit();
FileOutputStream fos = new FileOutputStream(tempjline);
IOUtils.copy(ClassLoaderUtil.class.getResourceAsStream(r), fos);
fos.close();
```
有这个这个东东，相信大家会减少踩坑，而且大胆地使用java和脚本之间的交互吧。 java可以调用shell，那么shell再调用其他就方便了。 记得一点， 不要过度地依赖缓冲区进行线程之间的通信。原因自己去学习吧。

希望这个能帮到大家。