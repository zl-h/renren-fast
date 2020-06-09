package io.renren.common.log;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Order(-5)
public class AopControllerLogging {

    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    ThreadLocal<Long> startTime = new ThreadLocal<Long>();
    ThreadLocal<String> id = new ThreadLocal<String>();
//    @Pointcut("execution(*Controller*.*(..))")
    //所有*Controller*的类都会被切入
    @Pointcut("execution(public * io.renren.modules.*.controller.*.*(..))")
    public void webLog(){}

   /* @Around("webLog()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
//        saveSysLog(point, time);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

*//*        StringBuilder sessionId = new StringBuilder(request.getSession().getId());
        id.set(request.getSession().getId());
        logger.info(sessionId.append(" request url=").append(request.getRequestURI())
                .append(" params=").append(JSON.toJSONString(request.getParameterMap()))
                .append(" ip").append(request.getRemoteAddr()).toString());*//*



        return result;
    }*/



    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint){
        // 接收到请求，记录请求内容
//        logger.info(" WebLogAspect.doBefore()");
        startTime.set(System.currentTimeMillis());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        StringBuilder sessionId = new StringBuilder(request.getSession().getId());
        id.set(request.getSession().getId());
        logger.info(sessionId.append(" request url=").append(request.getRequestURI())
                .append(" params=").append(JSON.toJSONString(request.getParameterMap()))
                .append(" ip").append(request.getRemoteAddr()).toString());
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    @AfterThrowing("webLog()")
    public void  doAfterReturning(Object ret){
        // 处理完请求，返回内容
//        logger.info(JSON.toJSONString(ret));
              try {
                  StringBuilder stringBuilder = new StringBuilder();
                  stringBuilder.append(id.get()).append(" response ").
                          append(JSON.toJSONString(ret)).
                          append("\n[COST TIME MS] : ").
                          append(String.valueOf(System.currentTimeMillis() - startTime.get()));
                  logger.info(stringBuilder.toString());

              } catch (Throwable throwable) {
                  logger.error(id.get().concat(" response 解析错误"),throwable);
             }
        /* logger.info(sessionId.append(" response ").append(JSON.toJSONString(methodInvocationProceedingJoinPoint.proceed())).toString());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        MethodInvocationProceedingJoinPoint methodInvocationProceedingJoinPoint = (MethodInvocationProceedingJoinPoint) joinPoint;
        StringBuilder sessionId = new StringBuilder(request.getSession().getId());
        try {

        } catch (Throwable throwable) {
            logger.error(sessionId.append(" response 解析错误").toString(),throwable);
        }*/
    }

    @AfterThrowing(throwing="ex", pointcut="webLog()")
    public void doAfterThrowing(Throwable ex) {
        logger.error(id.get().concat("[EXCEPTION] : ") , ex);
    }
}
