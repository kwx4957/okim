# 구름 TODO 
### 목적 
기존 goorm 쿠버네티스 전문가 양성과정을 수강하면서 추가적인 학습을 진행하는 학생들이 상당수 존재한다. 허나, slack과 notion만으로는 전체 수강생들의 공부 내역을 한 눈에 알아보기 힘든 점이 존재한다. 수강생들의 공부 내역 전체를 확인하지 못하며, 온라인과 장기간의 공부에 따른 피로감이 누적된 수강생들에게 더 이상의 학습 동기부여를 유발하지 못한다는 점으로 확산될 수 있다. 

이러한 문제점을 개선하기 위해 수강생들이 각 어떤 목표를 가지고 오늘의 할 일을 진행하였는지 한 눈에 알아보기 쉽게 하여 수강생들의 학습 의지 증가를 유발할 수 있다. 또한, 다른 수강생의 학습 계획표를 탐구할 수 있어, 정규시간 이외에 추가적인 학습 방향을 제대로 잡지 못한 수강생들에게 도움을 줄 수 있다. 이에 최종적으로 학습에 대한 동기부여 유도를 기대할 수 있는 서비스를 기획하였다.

### 개발환경
[Front]
- Vue.js   

[Back] / [Auth]
- Springboot 3.0.4, Spring Data JPA, Spring Security, Querydsl 5.0 
- Mysql 8.0
- JDK 17
- Gradle 
- Junit 5 

Devops [deploy]
- EKS  
- Jenkins / ArgoCD
- Prometheus / Grafana
- ELK

Tool
- Postman
- Notion / slack 

### 전체 구성도
![image](https://user-images.githubusercontent.com/33277725/234824704-1773847d-95d7-412d-bc41-497efc3ae342.png)

### 사용 방법
resources/application.yaml 파일에 다음 주소를 입력해줘야한다
- Mysql의 url, username, password
1. datasources.primary.url 
2. datasources.replica.url

- Redis
1. redis.host, password  

- AWS S3의 credentials
1. access-key, secret-key

- Gmail에 대한 정보
1. username, password   

- front 링크
1. 프론트 서버 url

참조 링크:  
[gmail](https://velog.io/@tjddus0302/Spring-Boot-%EB%A9%94%EC%9D%BC-%EB%B0%9C%EC%86%A1-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0-Gmail)  
[redis](https://app.redislabs.com/#/)

[front]: https://github.com/kwx4957/okim-front  
[back]: https://github.com/kwx4957/okim  
[deploy]: https://github.com/kwx4957/okim-deploy  
[auth]: https://github.com/kwx4957/okim-auth  
