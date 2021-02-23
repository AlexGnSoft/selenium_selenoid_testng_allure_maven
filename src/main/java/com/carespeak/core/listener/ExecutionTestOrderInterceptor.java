package com.carespeak.core.listener;

import com.carespeak.core.logger.Logger;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestClass;
import org.testng.ITestContext;

import java.util.*;

/**
 * Class is designed to create a proper test method execution order.
 * The goal is to resolve issues with execution order in multi-thread mode execution
 * (for situation when thread-count less than test class count).
 *
 * By default TestNG doesn't provide any guarantee for test method execution.
 * This interceptor forces TestNG to run tests in the that the class was written.
 *
 * For example:
 * <code>
 * public class SomeTestClass {
 *
 * @Test
 * public void b(){
 * //some awesome test b here
 * }
 * @Test
 * public void a(){
 * //some awesome test a here
 * }
 * @Test
 * public void c(){
 * //some awesome test c here
 * }
 * }
 * </code>
 * Default TestNG execution order with high probability will be:
 * 1. Method a
 * 2. Method b
 * 3. Method c
 *
 * With this class execution order will be same as order the test method written in file:
 * 1. Method b
 * 2. Method a
 * 3. Method c
 *
 * Currently the execution order is based on lines in class.
 */
public class ExecutionTestOrderInterceptor implements IMethodInterceptor {

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        Comparator<IMethodInstance> comparator = new Comparator<IMethodInstance>() {
            private int getLineNo(IMethodInstance mi) {
                int result = 0;
                //Using byte-code tricks here, we are defining comparator to identify the line of code
                //where the test name is written in a test class
                String methodName = mi.getMethod().getConstructorOrMethod().getMethod().getName();
                String className = mi.getMethod().getConstructorOrMethod().getDeclaringClass().getCanonicalName();
                ClassPool pool = ClassPool.getDefault();

                try {
                    CtClass cc = pool.get(className);
                    CtMethod ctMethod = cc.getDeclaredMethod(methodName);
                    result = ctMethod.getMethodInfo().getLineNumber(0);
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }

                return result;
            }

            //Comparator method that defines which method is written earlier in the test class file
            public int compare(IMethodInstance m1, IMethodInstance m2) {
                return getLineNo(m1) - getLineNo(m2);
            }
        };

        //Sorting methods by according classes.
        Map<ITestClass, List<IMethodInstance>> methodsInClasses = new HashMap<>();
        for (IMethodInstance testMethod : methods) {
            List<IMethodInstance> methodsForClass = methodsInClasses.get(testMethod.getMethod().getTestClass());
            if (methodsForClass == null) {
                methodsForClass = new ArrayList<>();
            }
            methodsForClass.add(testMethod);
            methodsInClasses.put(testMethod.getMethod().getTestClass(), methodsForClass);
        }

        //For each TestClass receiving all test methods,
        //Sorting this methods, and sequentially adding all this to result list
        List<IMethodInstance> result = new ArrayList<>();
        for (Map.Entry<ITestClass, List<IMethodInstance>> entry : methodsInClasses.entrySet()) {
            IMethodInstance[] array = entry.getValue().toArray(new IMethodInstance[entry.getValue().size()]);
            Arrays.sort(array, comparator);
            result.addAll(Arrays.asList(array));
        }

        //Some logging for convenience
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("Method execution sequence:");
        sb.append("\n");
        int i = 1;
        for (IMethodInstance method : result) {
            sb.append(i++)
                    .append(". ")
                    .append(method.getMethod().getTestClass().getName())
                    .append(".")
                    .append(method.getMethod().getMethodName())
                    .append("\n");
        }
        Logger.info(sb.toString());
        return result;
    }
}
