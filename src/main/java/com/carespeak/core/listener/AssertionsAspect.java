package com.carespeak.core.listener;

import com.carespeak.core.helper.IStepsReporter;
import com.carespeak.domain.steps.reporter.step.AllureStepReporter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class AssertionsAspect {

    @Around("execution(* org.testng.Assert.*(..))")
    public Object assertionMethod(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        IStepsReporter reporter = new AllureStepReporter();
        Object[] args = thisJoinPoint.getArgs();
        String errorMessage = (String) args[args.length - 1];
        try {
            String prettyMessage = "[Assert." + thisJoinPoint.getSignature().getName() + "] " + errorMessage.replaceAll(" not", "");
            Object result = thisJoinPoint.proceed();
            reporter.reportStep(prettyMessage);
            return result;
        } catch (Throwable t) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i <= args.length - 1; i++) {
                sb.append(args[i]);
                sb.append(",");
            }
            String argsString = sb.toString();
            argsString = argsString.substring(0, argsString.length() - 1);
            String prettyMessage = "[Assert." + thisJoinPoint.getSignature().getName() + "] " + errorMessage + " arguments(" + argsString + ")";
            reporter.reportStep(prettyMessage);
            throw t;
        }
    }
}
