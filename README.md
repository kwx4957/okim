# 구름 TODO
## 개요
기존 goorm 쿠버네티스 전문가 양성과정을 수강하면서 추가적인 학습을 진행하는 학생들이 상당수 존재한다. 허나, slack과 notion만으로는 전체 수강생들의 공부 내역을 한 눈에 알아보기 힘든 점이 존재한다. 수강생들의 공부 내역 전체를 확인하지 못하며, 온라인과 장기간의 공부에 따른 피로감이 누적된 수강생들에게 더 이상의 학습 동기부여를 유발하지 못한다는 점으로 확산될 수 있다.

이러한 문제점을 개선하기 위해 수강생들이 각 어떤 목표를 가지고 오늘의 할 일을 진행하였는지 한 눈에 알아보기 쉽게 하여 수강생들의 학습 의지 증가를 유발할 수 있다. 또한, 다른 수강생의 학습 계획표를 탐구할 수 있어, 정규시간 이외에 추가적인 학습 방향을 제대로 잡지 못한 수강생들에게 도움을 줄 수 있다. 이에 최종적으로 학습에 대한 동기부여 유도를 기대할 수 있는 서비스를 기획하였다.

## 개발 환경
기간: 2023.03.13 ~ 2023.04.11  
인원: 백엔드 2명, 인프라 2명  
사용 기술 및 환경: 
- AWS EKS, Docker
- Promethus, Grafana, ELK
- Jenkins, ArgoCD 
- SpringBoot 3, Spring Data JPA, Spring Security, QueryDSL 5, Java 17, Junit 5
- JS, Vue.js  
- MySQL, Redis

## 담당 업무
- 아이디어 기획
- API설계
- Jenkins, ArgoCD 파이프라인 구현
- Jenkins, ArgoCD 슬랙 알림 연동
- 유저 TODO API 구현
- AWS s3 이미지 업로드(프로필 이미지 업로드) API 구현 
- Email 인증 API 구현 및 Redis 연동

### 사용된 레포지토리
Frontend Repository: https://github.com/kwx4957/okim-front  
Backend Repository: https://github.com/kwx4957/okim  
Deploy Repository: https://github.com/kwx4957/okim-deploy  
Auth Repository: https://github.com/kwx4957/okim-auth   

### Convention  
Github 브랜치 전략은 빠른 배포를 하기위해 `Github-flow`을 선택하였습니다.  
Coding Convention은 `Google Java Style Convention`을 선택하였습니다.  
Git Commit Convention은 [Git Commit](https://kdjun97.github.io/git-github/commit-convention/) 다음 링크를 참조하였습니다.


## 전체 구성도
![](https://user-images.githubusercontent.com/33277725/237890376-091728d9-d6f1-4c8d-b8c3-5268cdb8bac8.png)

## 프로젝트 ERD  
![](https://user-images.githubusercontent.com/33277725/237890379-16dcac69-acd8-4df8-8649-18e837a1638d.png)

## 화면
![](https://user-images.githubusercontent.com/33277725/237889985-efc1f410-8e3e-48fa-b53b-ba900ce8fb30.png)|![](https://user-images.githubusercontent.com/33277725/237889994-bb2c8eb9-13ac-4e5a-9dd5-1135281e504d.png)|![](https://user-images.githubusercontent.com/33277725/237889997-c0e9d824-47af-4fe3-814b-96b88a2265c2.png)|![](https://user-images.githubusercontent.com/33277725/237890004-8b3e6e4f-0d12-45f6-af62-40375888aa0e.png)|![](https://user-images.githubusercontent.com/33277725/237890011-a42478fc-042c-45cc-aa50-8c5b08a6eddc.PNG)
---|---|---|---|---|

## 사용 방법
resources/application.yaml 파일에 다음에 대한 정보를 입력해줘야 합니다.

1. Mysql
   1. datasources.primary.url
   2. datasources.replica.url
2. Redis
   1. redis.host, password  
무료로 사용 가능한 [Reids](https://app.redislabs.com/)는 다음을 사용하였습니다.   
3. AWS S3  
   1. access-key
   2. secret-key
4. Gmail
   1. Username, password  
Gmail 설정은 다음 링크를 참조하였습니다.
[Gmail](https://velog.io/@tjddus0302/Spring-Boot-%EB%A9%94%EC%9D%BC-%EB%B0%9C%EC%86%A1-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0-Gmail)
