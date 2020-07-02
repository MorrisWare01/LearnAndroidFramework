#include "jni.h"

JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return -1;
    }

//    env->FindClass("android.os.BinderProxy")

}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_example_learnandroidframework_native_Native_getBinderProxyHandle(JNIEnv *env,
                                                                          jobject thiz,
                                                                          jobject binder) {
    return 0;
}
