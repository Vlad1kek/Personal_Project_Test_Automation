package utils.run;

import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import java.util.*;
import java.util.stream.Collectors;

public class OrderTest implements IMethodInterceptor {

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> list, ITestContext iTestContext) {
        return BaseUtils.orderMethods(list, m -> m.getMethod().getQualifiedName(), m -> m.getMethod().getMethodsDependedUpon())
                .stream().flatMap(List::stream).collect(Collectors.toList());
    }
}