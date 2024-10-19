package love.heer.demo.rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class ObServerTest {

    public static void main(String[] args) {

        // 被观察者
        Observable<String> observable = Observable.create(emitter -> {

            emitter.onNext("Hello World");
            emitter.onNext("Hello shijie");

            emitter.onComplete();
        });


        // 观察者
        Observer<String> obServer = new Observer<String>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                // 前置触发
                System.out.println("onSubscribe");
                // 取消订阅
//                d.dispose();
            }

            @Override
            public void onNext(@NonNull String s) {

                long threadId = Thread.currentThread().getId();
                // 获取发射的数据
                System.out.println(threadId + "-onNext: " + s);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                // 获取发射的异常数据
                System.out.println("onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };

        // 订阅
        observable.subscribe(obServer);

        // 订阅 2
        observable.subscribe(next -> {

            long threadId = Thread.currentThread().getId();
            // 获取发射的数据
            System.out.println(threadId + "-onNext: " + next);

        }, error -> {
            // 获取发射的异常数据
            System.out.println("onError: " + error.getMessage());
        });




        long threadId = Thread.currentThread().getId();
        // 获取发射的数据
        System.out.println(threadId + "-main: ");
    }


}
