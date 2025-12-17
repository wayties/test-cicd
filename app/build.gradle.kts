plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

// 빌드 타입별 Suffix 상수
object AppConfig {
    const val DEBUG = "debug"
    const val VERIFICATION = "verification"
    const val RELEASE = "release"
    const val DEBUG_SUFFIX = ".$DEBUG"
    const val VERIFICATION_SUFFIX = ".$VERIFICATION"
    const val RELEASE_SUFFIX = ""
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
        debug {
            applicationIdSuffix = AppConfig.DEBUG_SUFFIX

            // Develop 모드: Crashlytics 비활성화
//            manifestPlaceholders["crashlytics_collection_enabled"] = false

            buildConfigField("String", "BUILD_TYPE_NAME", "\"${AppConfig.DEBUG}\"")
            buildConfigField("String", "CRASH_REPORT_URL", "\"\"")
            buildConfigField("String", "CRASH_API_KEY", "\"\"")
        }

        create(AppConfig.VERIFICATION) {
            initWith(getByName(AppConfig.DEBUG))
            applicationIdSuffix = AppConfig.VERIFICATION_SUFFIX

            // verification 빌드타입이 없을 때 debug 설정을 재사용
            matchingFallbacks += listOf(AppConfig.DEBUG)

            // Testing 모드: Crashlytics 비활성화
//            manifestPlaceholders["crashlytics_collection_enabled"] = false

            // BuildConfig 필드 추가
            buildConfigField("String", "BUILD_TYPE_NAME", "\"${AppConfig.VERIFICATION}\"")
//            buildConfigField(
//                "String",
//                "CRASH_REPORT_URL",
//                "\"https://us-central1-rhpark-cc1f1.cloudfunctions.net/reportTestCrash\"",
//            )
//            buildConfigField("String", "CRASH_API_KEY", "\"SIMPLE_UI_VER_2025_nR8kL4mX9pT2wQ7vK3sN\"")
        }

        release {
            applicationIdSuffix = AppConfig.RELEASE_SUFFIX
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )

            // Release 모드: Crashlytics 활성화
            manifestPlaceholders["crashlytics_collection_enabled"] = true

            buildConfigField("String", "BUILD_TYPE_NAME", "\"${AppConfig.RELEASE}\"")
//            buildConfigField("String", "CRASH_REPORT_URL", "\"\"")
//            buildConfigField("String", "CRASH_API_KEY", "\"\"")
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
        buildConfig = true // BuildConfig 필드 사용 활성화
        dataBinding = true // MVVM 패턴 사용 시 필수
        // viewBinding = true  // ViewBinding만 사용할 경우
    }

//    lint {
//        checkDependencies = true
//        abortOnError = true // 에러가 있으면 빌드를 실패시킨다.(팀 정책에 맞게, 기본 값 true)
//    }
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

    // Core functionality only.
    implementation("com.github.Rhpark.Simple_UI_XML:Simple_UI_Core:0.3.42")

    // XML UI components (includes Core).
    implementation("com.github.Rhpark.Simple_UI_XML:Simple_UI_XML:0.3.42")
}
