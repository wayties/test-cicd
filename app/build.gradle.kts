plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.wayties.test_cicd"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.wayties.test_cicd"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = libs.versions.appVersion.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        dataBinding = true // MVVM 패턴 사용 시 필수
        // viewBinding = true  // ViewBinding만 사용할 경우
    }
}

dependencies {
    implementation(project(":library"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("com.github.Rhpark.Simple_UI_XML:Simple_UI_Core:0.3.29") // Core functionality only.
    implementation("com.github.Rhpark.Simple_UI_XML:Simple_UI_XML:0.3.29") // XML UI components (includes Core).
}
