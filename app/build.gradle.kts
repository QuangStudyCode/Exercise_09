plugins {
    id("com.android.application")
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.example.exercise_09"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.exercise_09"
        minSdk = 24
        targetSdk = 33
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
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.navigation:navigation-fragment:2.5.3")
    implementation("androidx.navigation:navigation-ui:2.5.3")
    implementation ("com.intuit.sdp:sdp-android:1.1.0")

//    for api
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

//slider show
    implementation ("com.github.denzcoskun:ImageSlideshow:0.1.2")

    implementation ("com.github.bumptech.glide:glide:4.16.0")


//    gson
    implementation("com.google.code.gson:gson:2.10.1")


//    glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
}