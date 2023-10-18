plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.exoplayer_glide"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.exoplayer_glide"
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
}

dependencies {
    //retrofit 사용
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")


    implementation ("androidx.recyclerview:recyclerview-selection:1.2.0-alpha01")
    implementation ("androidx.recyclerview:recyclerview:1.3.1")
    implementation ("androidx.cardview:cardview:1.0.0")

    implementation ("androidx.media3:media3-exoplayer:1.1.1")
    implementation ("androidx.media3:media3-exoplayer-dash:1.1.1")
    implementation ("androidx.media3:media3-ui:1.1.1")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}