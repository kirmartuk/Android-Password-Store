plugins {
  id("com.android.application")
  id("kotlin-android")
  id("kotlin-kapt")
  id("dagger.hilt.android.plugin")
  id("kotlin-parcelize")
}
val composeVersion = "1.0.1"
val additionalCompilerArgs =
  listOf(
    "-Xopt-in=kotlin.RequiresOptIn",
    "-P",
    "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=true",
  )
android {
  compileSdk = 32

  defaultConfig {
    applicationId = "com.martyuk.compose"
    minSdk = 26
    targetSdk = 32
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
    freeCompilerArgs = freeCompilerArgs + additionalCompilerArgs
  }
  buildFeatures.compose = true
  buildFeatures {
    compose = true
  }
  composeOptions { kotlinCompilerExtensionVersion = libs.versions.compose.get() }
  packagingOptions {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  kapt(libs.dagger.hilt.compiler)
  implementation(libs.dagger.hilt.android)
  implementation(libs.androidx.hilt.compose)
  implementation(project(mapOf("path" to ":compose-domain")))
  implementation(project(mapOf("path" to ":compose-data")))
  implementation(project(mapOf("path" to ":utils")))
  implementation(projects.autofillParser)
  implementation ("com.google.code.gson:gson:2.9.0")
  implementation("androidx.datastore:datastore:1.0.0")
  implementation("androidx.datastore:datastore-preferences:1.0.0")
  implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
  implementation("androidx.core:core-ktx:1.7.0")
  implementation("androidx.appcompat:appcompat:1.4.1")
  implementation("com.google.android.material:material:1.5.0")
  implementation("androidx.compose.ui:ui:$composeVersion")
  implementation("androidx.compose.material:material:$composeVersion")
  implementation("androidx.compose.material3:material3:1.0.0-alpha05")
  implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
  implementation("androidx.navigation:navigation-compose:2.5.0-alpha02")
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
  implementation("androidx.activity:activity-compose:1.4.0")
  testImplementation("junit:junit:")
  androidTestImplementation("androidx.test.ext:junit:1.1.3")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
  androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
  debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
}

fun Project.isSnapshot(): Boolean {
  with(project.providers) {
    val workflow = environmentVariable("GITHUB_WORKFLOW").forUseAtConfigurationTime()
    val snapshot = environmentVariable("SNAPSHOT").forUseAtConfigurationTime()
    return workflow.isPresent || snapshot.isPresent
  }
}