package JavaJun;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Date;


public class RandomDateAnnotationProcessor {

    public static void processAnnotation(Object obj) {
        java.util.Random random = new java.util.Random();
        Class<?> objClass = obj.getClass();
        for (Field field : objClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(RandomDate.class)) ;//&& field.getType().isAssignableFrom(Date.class)) {
            RandomDate annotation = field.getAnnotation(RandomDate.class);
            long min = annotation.min();
            long max = annotation.max();
            if (min < max) {
                Date date = new Date(random.nextLong());
                try {
                    field.setAccessible(true);
                    if (field.getType().isAssignableFrom(Date.class)) {
                        field.set(obj, date);
                    } else if (field.getType().isAssignableFrom(Instant.class)) {
                        field.set(obj,date.toInstant());
                    }

                } catch (IllegalAccessException e) {
                    System.err.println("Не удалось вставить значение в поле: " + e.getMessage());
                }
            } else System.out.println("Максимальная дата должна быть больше минимальной");
        }

    }
}