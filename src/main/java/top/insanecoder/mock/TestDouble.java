package top.insanecoder.mock;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import top.insanecoder.entity.Invocation;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: shaohang.zsh
 * Date: 2016/7/21
 */
public class TestDouble {

    private static Map<Invocation, Integer> map = new HashMap<>();
    private static Invocation lastInvocation;

    public static <T> T mock(Class<T> clazz, Callback callback) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(callback);
        return (T) enhancer.create();
    }

    // return a constant
    public static class MockMethodInterceptor implements MethodInterceptor {

        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            return 1;
        }
    }

    // stub: return according to last invocation
    public static class  StubMethodInterceptor implements MethodInterceptor {

        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

            Invocation invocation = new Invocation(o, method, objects, methodProxy);

            if (map.containsKey(invocation)) {
                return map.get(invocation);
            }

            lastInvocation = invocation;
            return null;
        }
    }

    // spy
    public static class SpyMethodInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            return methodProxy.invokeSuper(o, objects);
        }
    }

    public static When when(Object obj) {
        return new When();
    }

    public static class When {

        public void thenReturn(int retValue) {
            map.put(lastInvocation, retValue);
        }
    }
}
