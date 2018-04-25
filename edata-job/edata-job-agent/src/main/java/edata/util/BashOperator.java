package edata.util;

import javax.smartcardio.CommandAPDU;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BashOperator {


    private String bashName;
    private String bash;

    public Runnable execute(){
        ProcessBuilder pb = new ProcessBuilder(generatorPidCommand());
        pb.redirectOutput(generatorInfoFile());
        pb.redirectError(generatorErrorFile());
        try {
             pb.start();
        } catch (IOException e) {
             e.printStackTrace();
        }
    }

    public void kill() {
        ProcessBuilder pb = new ProcessBuilder(generatorKillCommand());
        try {
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //产生shell脚本
    private List<String> generatorKillCommand(){

        List<String > command = new ArrayList<>();
        command.add("ls");
        return command;


    }

    private List<String> generatorPidCommand(){
        List<String > command = new ArrayList<>();
        command.add(bash);
        command.add("&echo $ > " + bashName + ".pid");
    }

    private File generatorInfoFile(){
        return new File(bashName + ".INFO");
    }

    private File generatorErrorFile(){
        return new File(bashName + ".ERROR");
    }

}
