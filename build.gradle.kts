buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.2")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
//    alias(libs.plugins.android.application) apply false
    id("com.android.application") version "8.1.2" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}