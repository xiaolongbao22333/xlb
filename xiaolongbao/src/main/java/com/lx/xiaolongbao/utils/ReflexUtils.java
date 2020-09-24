package com.lx.xiaolongbao.utils;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class ReflexUtils {

    public static void setField(Object obj, String field, Object vlaue){
        setField(obj, obj.getClass(), field, vlaue);
    }

    public static void setField(Object obj, Class cls, String field, Object vlaue){
        try {
            Field f = cls.getDeclaredField(field);
            f.setAccessible(true);
            f.set(obj, vlaue);
        } catch (NoSuchFieldException e) {
            if (cls.getSuperclass() != null && cls.getSuperclass() != Object.class){
                setField(obj, cls.getSuperclass(), field, vlaue);
                return;
            }
            LogUtils.e(Log.getStackTraceString(e));
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Object getField(Object obj, String field) {
        return getField(obj, obj.getClass(), field);
    }


    public static Object getField(Object obj, Class cls, String field){
        try {
            Field f = cls.getDeclaredField(field);
            f.setAccessible(true);
            return f.get(obj);
        } catch (NoSuchFieldException e) {
            if (cls.getSuperclass() != null && cls.getSuperclass() != Object.class){
                return getField(obj, cls.getSuperclass(), field);
            }
            LogUtils.e(Log.getStackTraceString(e));
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     *
     * @param obj 对象
     * @param cls  对象class
     * @param method 方法名
     * @param isObj 是否执行到Objcet
     * @param vlaue 方法参数
     * @param <T>
     * @return
     */
    public static <T> T getMethod(Object obj, Class cls, String method, boolean isObj, Object ... vlaue){
        try {
            Class[] classes = new Class[vlaue.length];
            for (int i = 0; i < vlaue.length; i++) {
                classes[i] = vlaue[i] == null ? Object.class : vlaue[i].getClass();
            }
            Method m = cls.getDeclaredMethod(method, classes);
            m.setAccessible(true);
            return (T) m.invoke(obj, vlaue);

        } catch (NoSuchMethodException e) {
            if (cls.getSuperclass() != null && (isObj || cls.getSuperclass() != Object.class)){
                return setMethod(obj, cls.getSuperclass(), method, isObj, vlaue);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T setMethod(Object obj, String method, boolean isObj, Object ... vlaue){
        return setMethod(obj, obj.getClass(), method, isObj, vlaue);
    }

    /**
     *
     * @param obj 对象
     * @param cls  对象class
     * @param method 方法名
     * @param isObj 是否执行到Objcet
     * @param vlaue 方法参数
     * @param <T>
     * @return
     */
    public static <T> T setMethod(Object obj, Class cls, String method, boolean isObj, Object ... vlaue){
        try {
            Class[] classes = new Class[vlaue.length];
            for (int i = 0; i < vlaue.length; i++) {
                classes[i] = vlaue[i] == null ? Object.class : vlaue[i].getClass();
            }
            Method m = cls.getDeclaredMethod(method, classes);
            m.setAccessible(true);
            return (T) m.invoke(obj, vlaue);

        } catch (NoSuchMethodException e) {
            if (cls.getSuperclass() != null && (isObj || cls.getSuperclass() != Object.class)){
                return setMethod(obj, cls.getSuperclass(), method, isObj, vlaue);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T setMethod(Object obj, Class cls, String method, ClassParam ... classParams){
        try {
            Class[] classes = new Class[classParams.length];

            for (int i = 0; i < classParams.length; i++) {
                classes[i] = classParams[i].obj == null ? Object.class : classParams[i].paramType;
            }


            Object[] objects = new Object[classParams.length];
            for (int i = 0; i < classParams.length; i++) {
                objects[i] = classParams[i].obj;
            }

            Method m = cls.getDeclaredMethod(method, classes);
            m.setAccessible(true);
            return (T) m.invoke(obj, objects);

        } catch (NoSuchMethodException e) {
            if (cls.getSuperclass() != null && (true || cls.getSuperclass() != Object.class)){
                return setMethod(obj, cls.getSuperclass(), method, classParams);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static <T> T clone(T t){
        return setMethod(t, "clone", true);
    }

    public static <T> List<T> clone(List<T> t){
        List<T> ts = new ArrayList<>();
        for (T t1 : t) {
            ts.add((T) setMethod(t1, "clone", true));
        }
        return ts;
    }

    /**
     * author  : jack(黄冲)
     * time    : 2020-04-24 11:28
     * desc    : 是否是另外一个类的子类
     */
    public static boolean is(Class classChild, String classParent){

        if (classChild.getName().contains(classParent)) {
            return true;
        }
        Class<?> superclass = classChild.getSuperclass();

        if (superclass == null){
            return false;
        }
        return is(superclass, classParent);

    }

    public static class ClassParam{
        Object obj;
        Class paramType;

        public ClassParam(Object obj, Class paramType) {
            this.obj = obj;
            this.paramType = paramType;
        }
    }

}
