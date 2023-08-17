# [구름TODO](https://github.com/kwx4957/okim)
## 개요
구름 수강생들의 학습 내용을 한눈에 알아보기가 힘들다. 서로 다른 플랫폼(노션, 티스토리, 벨로그)에서 기록을 하기 때문이다. 그렇기에 각자 어떤 목표를 가지고 오늘 할 일을 진행했는지 쉽게 알아보게 하며 다른 수강생의 할 일을 보고 다른 수강생들 또한 자신의 학습에 동기 부여를 목적으로 하는 서비스 

## 개발 환경
기간: 2023.03 ~ 2023.04  
인원: 백엔드 개발자 2명, 인프라 2명  
사용 기술 및 환경: 
- AWS EKS, Docker
- Promethus, Grafana, ELK
- Jenkins, ArgoCD
- JavaScript, Vue.js  
- SpringBoot 3, Spring Data JPA, Spring Security, QueryDSL 5, Java 17, Junit 5
- MySQL, Redis

## 담당 업무
- 아이디어 기획 및 API 설계
- Jenkins & ArgoCD 파이프라인 구현 및 슬랙 알림 연동
- CRUD API 구현
- AWS S3 이미지 업로드 API 구현 
- Email 인증 API 구현 및 Redis 연동

### 사용된 레포지토리
Frontend Repository: https://github.com/kwx4957/okim-front  
Backend Repository: https://github.com/kwx4957/okim  
Deploy Repository: https://github.com/kwx4957/okim-deploy  
Auth Repository: https://github.com/kwx4957/okim-auth   

### Convention  
Github 브랜치 전략은 빠른 배포를 하기위해 `Github flow`을 선택하였습니다.  
Coding Convention은 `Google Java Style Convention`을 선택하였습니다.  
Git Commit Convention은 [Git Commit](https://kdjun97.github.io/git-github/commit-convention/) 다음 링크를 참조하였습니다.


## 전체 구성도
![](https://user-images.githubusercontent.com/33277725/237890376-091728d9-d6f1-4c8d-b8c3-5268cdb8bac8.png)

## 프로젝트 ERD  
![](https://user-images.githubusercontent.com/33277725/237890379-16dcac69-acd8-4df8-8649-18e837a1638d.png)

## 화면
![](https://user-images.githubusercontent.com/33277725/237889985-efc1f410-8e3e-48fa-b53b-ba900ce8fb30.png)|![](https://user-images.githubusercontent.com/33277725/237890004-8b3e6e4f-0d12-45f6-af62-40375888aa0e.png)|![](https://user-images.githubusercontent.com/33277725/237890011-a42478fc-042c-45cc-aa50-8c5b08a6eddc.PNG)
---|---|---|

![](https://user-images.githubusercontent.com/33277725/237889994-bb2c8eb9-13ac-4e5a-9dd5-1135281e504d.png)|![](https://user-images.githubusercontent.com/33277725/237889997-c0e9d824-47af-4fe3-814b-96b88a2265c2.png)
---|---|

## 사용 방법
1. MySQL
   1. datasources.primary.url
   2. datasources.replica.url
2. Redis
   1. redis.host, password  
3. AWS S3  
   1. access-key
   2. secret-key
4. Gmail
   1. Username, password  
