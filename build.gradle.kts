import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

extra["queryDslVersion"] = "5.0.0"

val snippetsDir = file("./build/generated-snippets")

plugins {
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
//    id("org.asciidoctor.jvm.convert") version "3.3.2"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    kotlin("plugin.jpa") version "1.9.23"
    kotlin("kapt") version "1.9.23"
}

group = "com.fitmate"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-quartz")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.batch:spring-batch-test")
//    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    implementation("com.querydsl:querydsl-jpa:${property("queryDslVersion")}:jakarta")
    kapt("com.querydsl:querydsl-apt:${property("queryDslVersion")}:jakarta")

    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")

    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

//// Ascii Doc Create Tasks
//tasks {
//    // Test 결과를 snippet Directory에 출력
//    test {
//        outputs.dir(snippetsDir)
//    }
//
//    asciidoctor {
//        // test 가 성공해야만, 아래 Task 실행
//        dependsOn(test)
//
//        // 기존에 존재하는 Docs 삭제(문서 최신화를 위해)
//        doFirst {
//            delete(file("src/main/resources/static/docs"))
//        }
//
//        inputs.dir(snippetsDir)
//
//        // Ascii Doc 파일 생성
//        doLast {
//            copy {
//                from("build/docs/asciidoc")
//                into("src/main/resources/static/docs")
//            }
//        }
//    }
//
//    build {
//        // Ascii Doc 파일 생성이 성공해야만, Build 진행
//        dependsOn(asciidoctor)
//    }
//}