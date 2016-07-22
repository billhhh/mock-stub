package top.insanecoder.entity;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Author: shaohang.zsh
 * Date: 2016/7/21
 */
public class Invocation {

    private Object o;
    private Method method;
    private Object[] objs;
    private MethodProxy methodProxy;

    public Invocation(Object o, Method method, Object[] objs, MethodProxy methodProxy) {
        this.o = o;
        this.method = method;
        this.objs = copyArgs(objs);
        this.methodProxy = methodProxy;
    }

    private Object[] copyArgs(Object[] objs) {
        Object[] args = new Object[objs.length];
        System.arraycopy(objs, 0, args, 0, objs.length);
        return args;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Invocation))
            return false;

        Invocation invocation = (Invocation) obj;
        return this.method.equals(invocation.method) && this.methodProxy.equals(invocation.methodProxy)
                && Arrays.deepEquals(this.objs, invocation.objs);
    }

    @Override
    public int hashCode() {
        int hashcode = 0;
        hashcode += this.method.hashCode();
        hashcode += this.methodProxy.hashCode();
        for (Object obj : objs) {
            hashcode += obj.hashCode();
        }
        return hashcode;
    }
}
