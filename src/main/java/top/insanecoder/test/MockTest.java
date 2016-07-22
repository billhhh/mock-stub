package top.insanecoder.test;

import junit.framework.Assert;
import org.junit.Test;
import top.insanecoder.entity.UserDao;
import top.insanecoder.mock.TestDouble;
import top.insanecoder.mock.WhTestStub;

/**
 * Author: shaohang.zsh
 * Date: 2016/7/21
 */
public class MockTest {

    @Test
    public void testMock() {
        UserDao mock = TestDouble.mock(UserDao.class, new TestDouble.MockMethodInterceptor());
        System.out.println("say hello == "+mock.sayHello());
        System.out.println(mock.getAge(1, "zsh"));
        Assert.assertEquals(1, mock.getAge(1, "zsh"));
    }

    @Test
    public void testStub() {

        UserDao stub = TestDouble.mock(UserDao.class, new TestDouble.StubMethodInterceptor());
        TestDouble.when(stub.getAge(1, "zsh")).thenReturn(5);
        TestDouble.when(stub.getAge(2, "zsh")).thenReturn(100);
        TestDouble.when(stub.getAge(1, "zsy")).thenReturn(19);
        TestDouble.when(stub.getAge(2, "zsy")).thenReturn(1000);
        TestDouble.when(stub.getAge(3, "lhhl")).thenReturn(9);

        System.out.println(stub.getAge(1, "zsh"));
        System.out.println(stub.getAge(2, "zsh"));
        System.out.println(stub.getAge(1, "zsy"));
        System.out.println(stub.getAge(2, "zsy"));
        System.out.println(stub.getAge(3, "lhhl"));

        Assert.assertEquals(5, stub.getAge(1, "zsh"));
        Assert.assertEquals(100, stub.getAge(2, "zsh"));
        Assert.assertEquals(19, stub.getAge(1, "zsy"));
        Assert.assertEquals(1000, stub.getAge(2, "zsy"));
        Assert.assertEquals(9, stub.getAge(3, "lhhl"));
    }

    @Test
    public void testSpy() {

        // UserDao is an interface, so NoSuchMethodError exception is thrown
        UserDao spy = TestDouble.mock(UserDao.class, new TestDouble.SpyMethodInterceptor());
        spy.getAge(1, "zsh");
    }

    @Test
    public void testWhStub() {

        UserDao stub = WhTestStub.mock(UserDao.class, new WhTestStub.StubMethodInterceptor(java.lang.String.class));
        WhTestStub.when(stub.getAge(1, "zsh")).thenReturn("fuck");
        WhTestStub.when(stub.getAge(2, "zsh")).thenReturn("fuck you");

        System.out.println(stub.getAge(1, "zsh"));
        System.out.println(stub.getAge(2, "zsh"));

//        Assert.assertEquals(5, stub.getAge(1, "zsh"));
//        Assert.assertEquals(100, stub.getAge(2, "zsh"));
    }
}
