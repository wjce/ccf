//package com.wjc.ccf.aop;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class TestAop {
//
//    /**
//     * JoinPoint里包含了类名、被切面的方法名，参数等属性，可供读取使用。@Around参数必须为ProceedingJoinPoint，
//     * pjp.proceed相应于执行被切面的方法。@AfterReturning方法里，可以加returning = “XXX”，
//     * XXX即为在controller里方法的返回值，本例中的返回值是“first controller”。
//     * @AfterThrowing方法里，可以加throwing = "XXX"，供读取异常信息
//     */
//
//    /**
//     * execution函数用于匹配方法执行的连接点，语法为：
//     * execution(方法修饰符(可选)  返回类型  方法名  参数  异常模式(可选))
//     */
//    @Pointcut("execution(* com.wjc.ccf.web.*.*(..))")
//    public void dataSourcePointCut(){
//
//    }
//
//    @Before("dataSourcePointCut()")
//    public void doBefore(JoinPoint joinPoint){
//        System.out.println("before  标识一个前置增强方法，相当于BeforeAdvice的功能");
//    }
//
//    @After("dataSourcePointCut()")
//    public void doAfter(JoinPoint joinPoint){
//        System.out.println("after  final增强，不管是抛出异常或者正常退出都会执行");
//    }
//
//    @AfterReturning(pointcut = "dataSourcePointCut()")
//    public void doAfterReturning(JoinPoint joinPoint){
//        System.out.println("afterReturn  后置增强，相当于AfterReturningAdvice，方法退出时执行");
//    }
//
//    @AfterThrowing(pointcut = "dataSourcePointCut()", throwing = "throwable")
//    public void doAfterThrowing(JoinPoint joinPoint, Throwable throwable){
//        System.out.println("afterThrow  异常抛出增强，相当于ThrowsAdvice");
//    }
//
//    @Around("dataSourcePointCut()")
//    public Object doAround(ProceedingJoinPoint pjp) throws Throwable{
//        System.out.println("around  环绕增强，相当于MethodInterceptor");
//        return pjp.proceed();
//    }
//}
