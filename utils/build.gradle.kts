plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("kapt")
}

android {
  compileSdk = 32

  defaultConfig {
    minSdk = 26
    targetSdk = 32
    version = 1

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      setProguardFiles(
        listOf(
          "proguard-android-optimize.txt",
          "proguard-rules.pro",
          "proguard-rules-missing-classes.pro",
        )
      )
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
}

dependencies {
  implementation(projects.autofillParser)
  implementation(projects.resources)
  implementation(libs.kotlin.coroutines.android)
  implementation(libs.kotlin.coroutines.core)
  kapt(libs.dagger.hilt.compiler)
  implementation(libs.dagger.hilt.android)
  implementation(libs.androidx.hilt.compose)
  implementation(libs.androidx.autofill)
  implementation(libs.thirdparty.jgit) {
    exclude(group = "org.apache.httpcomponents", module = "httpclient")
  }
  implementation(libs.thirdparty.sshj)
  implementation(libs.thirdparty.kotlinResult)
  implementation(libs.thirdparty.logcat)
  implementation(libs.androidx.security)
  implementation(libs.thirdparty.eddsa)

  implementation(project(mapOf("path" to ":resources")))
  implementation("androidx.datastore:datastore:1.0.0")
  implementation("androidx.datastore:datastore-preferences:1.0.0")
  implementation("androidx.core:core-ktx:1.7.0")
  implementation("androidx.appcompat:appcompat:1.4.1")
  implementation("com.google.android.material:material:1.5.0")

  implementation("org.bouncycastle:bcprov-jdk15on:1.70")

  testImplementation("junit:junit:4.+")
  androidTestImplementation("androidx.test.ext:junit:1.1.3")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}