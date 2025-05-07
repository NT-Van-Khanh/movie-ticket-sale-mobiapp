plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.ticket_sale"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.ticket_sale"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildToolsVersion = "34.0.0"
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.androidx.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation (libs.retrofit)
    implementation (libs.logging.interceptor)

    implementation (libs.converter.gson)
    implementation (libs.gson)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.glide)
    implementation (libs.core)
}