package main.model.notes;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface View {
    Class<?> value();
    boolean isRecordable() default false;
    boolean isEditable() default false;
    boolean isControllable() default false;
    boolean isUpdatable() default false;
}
