# 쇼핑몰 프로젝트

---

## 1. 개발 환경
* Intellij IDEA Ultimate 2022.2.2
* Java 11
* Spring Boot 2.6.13
* Maven 4.0.0

## 2. 사용 기술 스택
* Spring Data JPA
* lombok
* MySQL8.0
* JUnit5
* Thymeleaf
* Spring Security

## 3. 주요 기능
* 회원(Member)
  * Spring Security 를 활용하여 로그인, 로그아웃, 회원가입 기능 구현([SecurityConfig.java](https://github.com/cheesecup/project-shopmall/blob/main/src/main/java/com/shop/config/SecurityConfig.java))
* 상품(Item)
  * 등록, 수정, 조회 기능 구현
    * 상품 등록 시 최대 5개의 상품 이미지 등록([FileService.java](https://github.com/cheesecup/project-shopmall/blob/main/src/main/java/com/shop/service/FileService.java))
    * 조회 시 대표 이미지가 노출
* 주문(Order)
  * 등록, 삭제, 조회 기능 구현
* 장바구니(Cart)
  * 등록 상품 조회, 삭제 , 주문 기능 구현
