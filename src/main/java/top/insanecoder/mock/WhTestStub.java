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
 * Author: wh
 * Date: 2016/7/21
 */
public class WhTestStub {

    private static Map<Invocation, Object> map = new HashMap<>();
    private static Invocation lastInvocation;

    public static <T> T mock(Class<T> clazz, Callback callback) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(callback);
        return (T) enhancer.create();
    }


    // stub: return according to last invocation
    public static class StubMethodInterceptor<Type> implements MethodInterceptor {

        //Class<Type> whClass;
        public StubMethodInterceptor() {
        }

        public StubMethodInterceptor(Class<Type> clazz) {
            //whClass = clazz;
        }

        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

            Invocation invocation = new Invocation(o, method, objects, methodProxy);

            //<Type> = method.getReturnType();

            if (map.containsKey(invocation)) {
                return (Type)map.get(invocation);
            }

            lastInvocation = invocation;
            return null;
        }
    }

    public static When when(Object obj) {
        return new When();
    }

    public static class When {

        public void thenReturn(Object retValue) {
            map.put(lastInvocation, retValue);
        }
    }
}
