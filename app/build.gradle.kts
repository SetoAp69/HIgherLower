plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
    id("kotlin-parcelize")
    kotlin("kapt")
}

android {
    namespace = "com.excal.higherlower"
    compileSdk = 35

    signingConfigs{
        create("release"){
            if(findProperty("STORE_KEY")!=null){
                storeFile=file(findProperty("STORE_KEY") as String)
                storePassword=findProperty("STORE_PASSWORD") as String?
                keyAlias=findProperty("STORE_KEY_ALIAS") as String?
                keyPassword=findProperty("STORE_PASSWORD") as String?

            }
        }
    }

    defaultConfig {
        applicationId = "com.excal.higherlower"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")

                if(findProperty("STORE_KEY")!=null){
                    signingConfig=signingConfigs.getByName("release")
                }

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding=true
        dataBinding=true
        compose=true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes +="META-INF/LICENSE"
            excludes +="META-INF/LICENSE.txt"
            excludes +="META-INF/NOTICE"
            excludes +="META-INF/NOTICE.txt"
            excludes +="META-INF/DEPENDENCIES"
            excludes +="META-INF/*.kotlin_module"


        }
    }
}

dependencies {

    //compose



    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth)

    //AndroidX fragment
    implementation("androidx.fragment:fragment:1.8.5")
    implementation("androidx.fragment:fragment-ktx:1.8.5")

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    //Firebase Auth
    implementation("com.google.firebase:firebase-auth:23.1.0")
    //Firebase Google Play Services
    implementation("com.google.android.gms:play-services-auth:21.3.0")

    //credentials
    implementation("androidx.credentials:credentials:1.3.0")

    implementation("androidx.credentials:credentials-play-services-auth:1.3.0")

    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")


    //Google API
    implementation("com.google.api-client:google-api-client:1.34.0")

    //Jackson2
    implementation("com.google.http-client:google-http-client-jackson2:1.40.1")
    //Coroutines
    implementation("androidx.room:room-coroutines:2.1.0-alpha04")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    implementation(libs.androidx.media3.common.ktx)

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    //ViewModel
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")

    //Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Compose Async Image
    implementation("io.coil-kt:coil-compose:2.3.0")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0") // Replace with the latest version


    //Google Fonts
    implementation("androidx.compose.ui:ui-text-google-fonts:1.7.6")
}