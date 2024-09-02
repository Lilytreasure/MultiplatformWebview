plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.dennis.multicontacts.android.sample"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dennis.multicontacts.android.sample"
        minSdk = 24
        targetSdk = compileSdk
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }


    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    api(libs.androidx.activity.compose)
    api(libs.androidx.appcompat)
    implementation(compose.material3)
    implementation(libs.core.ktx)
    implementation(project(":sample:common"))
    implementation(project(":multiplatformWebView"))

}