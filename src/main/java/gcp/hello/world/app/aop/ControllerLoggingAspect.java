package gcp.hello.world.app.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ControllerLoggingAspect {
    //Compile terms
    //@Aspect : This is controller logging aspect.
    //PointCut : expression to determine what methods should be intercepted,
                //execution(* gcp.hello.world.app.controller.*.*(..)) is a pointcut.
    //Advice : method block code, what we are going to do.

    //Runtime terms
    //Join Point : a point during the execution of program, such as execution of a method.



    @Before("execution(* gcp.hello.world.app.controller.*.*(..))")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        //This method block is a advice, what should when a point cut is met.
        log.info("Method " + joinPoint.getSignature().getName() + " is called" +
                " with arguments :: {}", joinPoint.getArgs());
    }

    @After("execution(* gcp.hello.world.app.controller.*.*(..))")
    public void logAfterMethodExecution(JoinPoint joinPoint) {
        log.info("Method " + joinPoint.getSignature().getName() + " is completed");
    }

    @AfterReturning(pointcut = "execution(* gcp.hello.world.app.controller.*.*(..))", returning = "result")
    public void logAfterMethodReturning(JoinPoint joinPoint, Object result) {
        log.info("Method " + joinPoint.getSignature().getName() + " is completed" +
                " with returning result :: {}", result);
    }

    @AfterThrowing(pointcut = "execution(* gcp.hello.world.app.controller.*.*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        System.out.println("An exception has been thrown: " + error);
    }

}
