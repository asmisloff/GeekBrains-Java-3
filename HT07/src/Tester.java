import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Tester {

    public static void Run(Class testClass) throws RuntimeException {
        Method[] methods = testClass.getMethods();
        Method before = null;
        Method after = null;
        ArrayList<Method> tests = new ArrayList<>();

        Annotation[] annotations;
        for (Method m : methods) {
            annotations = m.getAnnotations();
            for (Annotation ann : annotations) {
                if (ann instanceof BeforeSuit) {
                    if (before == null) {
                        before = m;
                    } else {
                        throw new RuntimeException();
                    }
                } else if (ann instanceof AfterSuit) {
                    if (after == null) {
                        after = m;
                    } else {
                        throw new RuntimeException();
                    }
                } else if (ann instanceof Test) {
                    tests.add(m);
                }
            }
        }
        tests.sort((m1, m2) -> m1.getAnnotation(Test.class).value() - m2.getAnnotation(Test.class).value());

        try {
            Object o = testClass.getConstructor().newInstance();
            if (before != null) {
                before.invoke(o);
            }

            for (Method m : tests) {
                System.out.println("Method " + m.getName());
                m.invoke(o);
            }

            if (after != null) {
                after.invoke(o);
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface BeforeSuit {}


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface AfterSuit {}


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Test {
    int value() default 10;
}
