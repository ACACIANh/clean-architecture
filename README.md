# Example Implementation of a Hexagonal Architecture

[![CI](https://github.com/thombergs/buckpal/actions/workflows/ci.yml/badge.svg)](https://github.com/thombergs/buckpal/actions/workflows/ci.yml)

[![Get Your Hands Dirty On Clean Architecture](https://reflectoring.io/assets/img/get-your-hands-dirty-260x336.png)](https://reflectoring.io/book)

This is the companion code to my eBook [Get Your Hands Dirty on Clean Architecture](https://leanpub.com/get-your-hands-dirty-on-clean-architecture).

It implements a domain-centric "Hexagonal" approach of a common web application with Java and Spring Boot. 

## Companion Articles

* [Hexagonal Architecture with Java and Spring](https://reflectoring.io/spring-hexagonal/)
* [Building a Multi-Module Spring Boot Application with Gradle](https://reflectoring.io/spring-boot-gradle-multi-module/)

## Prerequisites

* JDK ~~11~~ -> 17
* this project uses Lombok, so enable annotation processing in your IDE

---
#### TODO list
* JwtAuthenticationFilter - 토큰으로 요청들어올때 db 로직타지 않게 제거
* DB 초기화시 회원가입 되었던 user 데이터 넣기
* Security package 모델 정리

#### DONE - 최신 역순
* Java17 SpringBoot3 migration
* Security Setting
* Swagger Setting
* Controller 에서 Principal 및 SecurityContextHolder 확인 
* UserJpaEntity - Entity 모델 분리