package com.github.edata.sql;


/**
 * EngineException
 *
 * @author kinven
 * @version 0.1
 * @date 18-5-10
 **/
public class EngineException extends Exception{


    public static RuntimeException newBuilderException(String message){
        return new BuilderException(message);
    }

    public static RuntimeException newClauseException(String message){
        return new BuilderException(message);
    }


    public static class BuilderException extends RuntimeException{
        public BuilderException(String message){
            super(message);
        }
    }

    public static class ClauseException extends RuntimeException{
        public ClauseException(String message){
            super(message);
        }
    }
}
