#include "jni.h"

JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return -1;
    }

    return JNI_VERSION_1_6;
}

extern "C" JNIEXPORT jint JNICALL
Java_com_example_android_native_Native_getData(JNIEnv *env, jclass _) {
    jclass nativeClazz = env->FindClass("com/example/android/native/Native");
    jfieldID nativeDataField = env->GetStaticFieldID(nativeClazz, "data", "I");
    return env->GetStaticIntField(nativeClazz, nativeDataField);
}
