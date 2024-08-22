package utils.run;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BaseUtils {

    public static void getUrl(WebDriver driver) {
        driver.get("http://localhost");
    }

    static File captureScreenFile(WebDriver driver, String methodName, String className) {
        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File(String.format("screenshots/%s.%s.png", className, methodName)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    private static <T> void orderMethod(
            T currentMethod, Map<String, T> methodMap, Map<String, Set<T>> dependedMap, Set<T> usedSet,
            List<T> destinationList, Function<T, String> getNameFunction, Function<T, String[]> getDependencyFunction) {
        usedSet.add(currentMethod);

        for (String methodName : getDependencyFunction.apply(currentMethod)) {
            methodMap.computeIfPresent(methodName, (k, method) -> {
                if (!usedSet.contains(method)) {
                    orderMethod(method, methodMap, dependedMap, usedSet, destinationList,
                            getNameFunction, getDependencyFunction);
                }

                return method;
            });
        }

        destinationList.add(currentMethod);

        dependedMap.computeIfPresent(getNameFunction.apply(currentMethod), (k, v) -> {
            for (T method : v) {
                if (!usedSet.contains(method)) {
                    orderMethod(method, methodMap, dependedMap, usedSet, destinationList,
                            getNameFunction, getDependencyFunction);
                }
            }

            return v;
        });
    }

    static <T> List<List<T>> orderMethods(
            List<T> sourceList, Function<T, String> getNameFunction, Function<T, String[]> getDependencyFunction) {

        Map<String, Set<T>> dependedMap = new HashMap<>();
        for (T method : sourceList) {
            for (String dependedName : getDependencyFunction.apply(method)) {
                dependedMap.computeIfAbsent(dependedName, key -> new HashSet<>()).add(method);
            }
        }
        Map<String, T> methodMap = sourceList.stream().collect(Collectors.toMap(getNameFunction, Function.identity()));
        Set<T> usedSet = new HashSet<>();
        List<List<T>> resultList = new ArrayList<>();

        for (T method : sourceList) {
            if (!usedSet.contains(method)) {
                List<T> destinationList = new ArrayList<>();
                resultList.add(destinationList);

                orderMethod(method, methodMap, dependedMap, usedSet, destinationList,
                        getNameFunction, getDependencyFunction);
            }
        }

        return resultList;
    }
}


