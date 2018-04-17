#Firebase Auth
-keepattributes Signature
-keepattributes *Annotation*

#Butterknife
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }