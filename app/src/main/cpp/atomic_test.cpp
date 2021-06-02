#include <thread>
#include <atomic>
#include <android/log.h>

namespace learn {
    std::atomic<bool> ready = {false};
    int r = 1;

    void write_x() {
        while (!ready.load(std::memory_order_acquire));
        // https://zh.cppreference.com/w/cpp/atomic/memory_order
        // 注意到 2015 年 2 月为止没有产品编译器跟踪依赖链：均将消费操作提升为获得操作。
        // 释放消费顺序的规范正在修订中，而且暂时不鼓励使用 memory_order_consume(C++17 起)。
//        while (!ready.load(std::memory_order_consume));
        if (r == 1) {
            __android_log_print(ANDROID_LOG_INFO, "learn", "%d", r);
        }
    }

    void write_y() {
        r = 0;
        ready.store(true, std::memory_order_release);
    }

    void atomic_test() {
        __android_log_print(ANDROID_LOG_INFO, "learn", "start");
        std::thread a;
        std::thread b;
        for (int i = 0; i < 10000; ++i) {
            a = std::thread(write_x);
            b = std::thread(write_y);
            a.join();
            b.join();
        }
        __android_log_print(ANDROID_LOG_INFO, "learn", "end");
    }
}

