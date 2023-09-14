package com.arep;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LoadClasses {

    private static Map<String, Method> actions = new HashMap<>();

    public static void load(){
        Class c = Math.class;
        Method[] methods = c.getMethods();
        for (Method m:methods){
            Class[] parameters = m.getParameterTypes();
            for (Class p:parameters){
                if (p.getName().equals("double")){
                    actions.put(m.getName(), m);
                }
            }
        }
    }

    public static String execute(String method, Double param) throws InvocationTargetException, IllegalAccessException {
        return String.valueOf(actions.get(method).invoke(null, param));
    }

    public static Map<String, Method> getActions(){
        return actions;
    }
}
