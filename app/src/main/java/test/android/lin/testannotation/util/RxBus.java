package test.android.lin.testannotation.util;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by lxb on 16/5/29.
 */
public class RxBus {
    public volatile static RxBus sInstance;

    private RxBus() {

    }

    public static RxBus get() {
        if (sInstance == null) {
            synchronized (RxBus.class) {
                if (sInstance == null)
                    sInstance = new RxBus();
            }
        }
        return sInstance;
    }

    /**
     * 存储某个标签的Subject集合
     */
    private ConcurrentMap<Object, List<Subject>> mSubjectMap = new ConcurrentHashMap<>();

    /**
     * 注册事件
     */

    public <T> Observable<T> register(@NonNull Object tag, @NonNull Class<T> clazz) {
        List<Subject> subjectList = mSubjectMap.get(tag);
        if (null == subjectList) {
            subjectList = new ArrayList<>();
            mSubjectMap.put(tag, subjectList);
        }
        rx.subjects.Subject<T, T> subject;
        subjectList.add(subject = PublishSubject.create());
        Log.e("register", mSubjectMap.toString());

        return subject;
    }


    public void unregister(@NonNull Object tag, @NonNull Observable observable) {
        final List<Subject> list = mSubjectMap.get(tag);
        if (null != list) {
            list.remove(observable);
            if (list.isEmpty())
                mSubjectMap.remove(tag);
        }
        Log.e("unregister", mSubjectMap.toString());
    }

    public void post(@NonNull Object tag, @NonNull Object content) {
        final List<Subject> list = mSubjectMap.get(tag);
        if (null != list && !list.isEmpty()) {
            for (Subject subject : list) {
                subject.onNext(content);
            }
        }
        Log.e("post", mSubjectMap.toString());

    }
}
