package com.tonga.io.utils;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 
 * <p>Title: Calculator.java</p>  
 * @author tangjia
 * @date 2018-3-9 обнГ5:16:31 
 * @version 1.0
 */
public final class Calculator {  
    private final static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
    public static Object cal(String expression) throws ScriptException{
        return jse.eval(expression);
    }
}