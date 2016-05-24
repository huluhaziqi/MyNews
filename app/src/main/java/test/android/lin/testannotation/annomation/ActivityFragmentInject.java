package test.android.lin.testannotation.annomation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by linxiaobin on 2016/5/24.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ActivityFragmentInject {

    /**
     * @return
     */
    int contentViewId() default -1;
    /**
     *
     */

    int
}
