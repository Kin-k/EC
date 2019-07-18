package ec.utils;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static io.qameta.allure.model.Status.BROKEN;
import static io.qameta.allure.util.ResultsUtils.getStatus;
import static io.qameta.allure.util.ResultsUtils.getStatusDetails;
import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.*;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.joining;

@SuppressWarnings("unused")
@Aspect
public class CustomAllureAspect {

    private static final Logger LOG = LoggerFactory.getLogger(CustomAllureAspect.class);

    private static AllureLifecycle lifecycle;

    @Pointcut("(execution(* ec.elements.*.*(..)) || execution(* ec.pages.*.*(..))) && !execution(* ec.utils.CustomAllureAspect.*(..))")
    public void anyMethod() {
        //pointcut body, should be empty
    }

    @Around("anyMethod()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String uuid = randomUUID().toString();

        StepResult result = new StepResult().setName(generateStepName((MethodSignature) joinPoint.getSignature()))
                                            .setParameters(extractParameters(joinPoint));
        getLifecycle().startStep(uuid, result);
        try {
            Object proceed = joinPoint.proceed();
            getLifecycle().updateStep(uuid, s -> s.setStatus(Status.PASSED));
            return proceed;
        } catch (Throwable e) {
            getLifecycle().updateStep(uuid, s -> s.setStatus(getStatus(e).orElse(BROKEN))
                                                  .setStatusDetails(getStatusDetails(e).orElse(null)));
            throw e;
        } finally {
            getLifecycle().stopStep(uuid);
        }
    }

    @AfterThrowing("anyMethod()")
    public void afterThrowing() {
        getScreenshotBytes()
                .ifPresent(bytes -> lifecycle.addAttachment("Screenshot", "image/png", "png", bytes));
        getPageSourceBytes()
                .ifPresent(bytes -> lifecycle.addAttachment("Page source", "text/html", "html", bytes));
    }

    private static Optional<byte[]> getScreenshotBytes() {
        try {
            return Optional.of((TakesScreenshot) WebDriverRunner.getWebDriver())
                           .map(wd -> wd.getScreenshotAs(OutputType.BYTES));
        } catch (WebDriverException e) {
            LOG.warn("Could not get screenshot", e);
            return Optional.empty();
        }
    }

    private static Optional<byte[]> getPageSourceBytes() {
        try {
            return Optional.of(WebDriverRunner.getWebDriver())
                           .map(WebDriver::getPageSource)
                           .map(ps -> ps.getBytes(UTF_8));
        } catch (WebDriverException e) {
            LOG.warn("Could not get page source", e);
            return Optional.empty();
        }
    }

    private static AllureLifecycle getLifecycle() {
        if (lifecycle == null) {
            lifecycle = Allure.getLifecycle();
        }
        return lifecycle;
    }

    private static String generateStepName(MethodSignature methodSignature) {
        return format("%s.%s",
                      methodSignature.getDeclaringType()
                                     .getSimpleName(),
                      methodSignature.getName());
    }

    private static List<Parameter> extractParameters(ProceedingJoinPoint joinPoint) {
        List<Parameter> parameters = new ArrayList<>();
        String[] paramNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] paramValues = joinPoint.getArgs();
        for (int i = 0; i < paramNames.length; i++) {
            parameters.add(new Parameter().setName(paramNames[i])
                                          .setValue(formatParamValue(paramValues[i])));
        }
        return parameters;
    }

    private static boolean isArray(Object value) {
        return value != null && value.getClass()
                                     .isArray();
    }

    private static String formatParamValue(Object value) {
        return isArray(value) ? Stream.of((Object[]) value)
                                      .map(CustomAllureAspect::formatParamValue)
                                      .collect(joining(", ", " (", ")"))
                              : String.valueOf(value);
    }
}