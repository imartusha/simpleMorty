import org.jetbrains.kotlin.fir.declarations.builder.buildScript
import org.jetbrains.kotlin.gradle.internal.kapt.incremental.UnknownSnapshot
import kotlin.script.experimental.jvm.util.classpathFromClass

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
    }
}

plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("org.jetbrains.kotlin.kapt") version "1.9.21" apply false
    id("androidx.navigation.safeargs.kotlin") version "2.7.7" apply false
}