import org.jetbrains.kotlin.fir.declarations.builder.buildScript

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("androidx.navigation.safeargs.kotlin")

}

android {
    namespace = "com.example.simplemorty"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.simplemorty"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        viewBinding.enable = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //splashScreen
    implementation("androidx.core:core-splashscreen:1.0.1")
    //paging
    val paging_version = "3.2.1"
    api("androidx.paging:paging-runtime-ktx:$paging_version")
    implementation("androidx.paging:paging-common:$paging_version")

    //shimmer
    implementation ("com.facebook.shimmer:shimmer:0.5.0")

    //glide
    implementation("com.github.bumptech.glide:glide:4.14.2")
    //recyclerview
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    //ViewPager2
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    //OKHTTP
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
//materialDesign
    implementation("com.google.android.material:material:1.12.0-alpha03")
    //   implementation("androidx.legacy:legacy-support-v4:1.0.0")
//navigation
    val nav_version = "2.7.7"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

//room
    val room_version = "2.6.1"
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-paging:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

//koin:
    val koin_version = "3.5.3"
    implementation("io.insert-koin:koin-test:$koin_version")
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-android:$koin_version")

//lifecycle
    val lifeCycle_version = "2.7.0"
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifeCycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifeCycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifeCycle_version")

//coroutines
    val cor_version = "1.8.0-RC2"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$cor_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$cor_version")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.10")

    //coil
    implementation("io.coil-kt:coil-compose:2.2.2")

//    //Moshi
//    implementation("com.squareup.moshi:moshi-kotlin:1.12.0")
//    implementation("com.squareup.moshi:moshi:1.14.0")
//    kapt ("com.squareup.moshi:moshi-kotlin-codegen:1.14.0")

//    implementation("androidx.compose.runtime:runtime:1.6.1")
//    implementation("androidx.compose.runtime:runtime-livedata:1.6.1")
//    implementation("androidx.compose.runtime:runtime-rxjava2:1.6.1")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    //   implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}