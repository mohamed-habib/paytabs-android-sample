#include <jni.h>

char email[] = "";
char secretKey[] = "";

JNIEXPORT
jstring JNICALL
Java_com_example_paytabssample_Keys_getSecretKey(JNIEnv *env, jclass instance) {
    return (*env)->NewStringUTF(env, secretKey);
}

JNIEXPORT jstring JNICALL
Java_com_example_paytabssample_Keys_getMerchantEmail(JNIEnv *env, jclass instance) {
    return (*env)->NewStringUTF(env, email);
}