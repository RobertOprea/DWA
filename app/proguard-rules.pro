#Firebase Auth
-keepattributes Signature
-keepattributes *Annotation*

#Butterknife
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }

-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase


-keepclassmembers class **.R$* {
    public static <fields>;
}

# for OrmLite
-keepclassmembers class * {
  public <init>(android.content.Context);
  public static ** getSingleton();
}
-keep class com.j256.**
-keepclassmembers class com.j256.** { *; }
-keep enum com.j256.**
-keepclassmembers enum com.j256.** { *; }
-keep interface com.j256.**
-keepclassmembers interface com.j256.** { *; }
-keepclassmembers class **DateTime {
    <init>(long);
    long getMillis();
}

# keep setters and getters for OrmLite useGetSet annotation
-keep class com.mobilityMedia.** {
    <fields>;
    public void set*(***);
    public void set*(int, ***);

    public boolean is*();
    public boolean is*(int);

    *** get*();
    *** get*(int);
}

-keepclassmembers class * implements android.os.Parcelable {
    static android.os.Parcelable$Creator *;
}

# This rule will properly ProGuard all the model classes in
# the package com.dwa.ryebridge.ryebridgedwa.data
-keepclassmembers class com.dwa.ryebridge.ryebridgedwa.data.** {
  *;
}