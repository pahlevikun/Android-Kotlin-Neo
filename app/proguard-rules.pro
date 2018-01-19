# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Applications/adt-bundle-mac-x86_64-20140702/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-ignorewarnings
-keep class * {
    public private *;
}
# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile

# Picasso
-dontwarn com.squareup.picasso.**
# Setting
-optimizationpasses 5
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontoptimize

# --------------------------------------------------------------------
# Square OkHttp
# --------------------------------------------------------------------
# https://github.com/levelup/Android-HttpClient/blob/master/HttpClient-OkHttp/proguard-project.txt
-keepnames class com.levelup.http.okhttp.** { *; }
-keepnames interface com.levelup.http.okhttp.** { *; }
-keepnames class com.squareup.okhttp.** { *; }
-keepnames interface com.squareup.okhttp.** { *; }

-dontwarn com.squareup.okhttp.**
-dontwarn com.squareup.okhttp.internal.**
-dontwarn com.squareup.okhttp.internal.http.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *; }
-keep interface com.squareup.okhttp3.* { *; }
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn javax.annotation.**

# --------------------------------------------------------------------
# Square Retrofit (uses GSON)
# --------------------------------------------------------------------
# https://github.com/square/retrofit/issues/372
-keepattributes Signature
-keepattributes *Annotation*

-dontwarn rx.**
-keep class com.google.gson.** { *; }
-keep class com.google.inject.* { *; }
-keep class org.apache.http.* { *; }
-keep class org.apache.james.mime4j.* { *; }
-keep class javax.inject.* { *; }
-keep class retrofit.* { *; }
-keep class sun.misc.Unsafe { *; }

-dontwarn okio.**
-dontwarn retrofit2.Platform$Java8
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}

-dontwarn retrofit.appengine.**