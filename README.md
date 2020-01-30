# Nemo 프로젝트

투두 리스트 + 작업시간을 기록하는 서비스


- [ ] 툴 사용법 익히기
    - 인텔리제이 단축키
    - [VSCode 단축키](https://dev.to/devmount/23-lesser-known-vs-code-shortcuts-as-gif-80?fbclid=IwAR2rx-YUU4RPcb0ZDnKKHW2OsUP9WyyFUh6gTVla0cl4ZwRhTWL-1xI2H0g)
    
- [ ] API 개발/문서화
    - [x] 투두 아이템, 투두 액션 CRUD API 개발 (2020-01-11 / 3h)
    - [x] Swagger CodeGen을 사용하여 Mock API 제공하기 (2020-01-26 / 2h)
        - Swagger Hub에 yaml 형식으로 API를 만들면 됨
        - 참고: [Swagger 제공 샘플](https://app.swaggerhub.com/apis/youjung-hong/Sample/1.0.0)
        - [Nemo Sample API 서버](https://app.swaggerhub.com/apis/youjung-hong/Nemo/1.0.0)
    
    - [x] Swagger UI를 사용하여 만들어진 서버 API 문서화 (2020-01-27 / 3h)
        - 참고: [Spring Boot에 애노테이션을 사용해서 Swagger-UI 설정하기](https://www.dariawan.com/tutorials/spring/documenting-spring-boot-rest-api-springdoc-openapi-3/)
        - [Nemo Local API 서버](http://localhost:8080/swagger-ui/index.html?url=/v3/api-docs&validatorUrl=#/)를 만듦
    - [x] 수정한 API가 정상 동작하게 하기
        - [x] Todo API (2020-01-28 / 1h 10m)
        - [x] Action API (2020-01-29 / 2h 15m)
    - [x] Swagger 테스트 정상 동작하게 하기 (지금은 JSON parsing 에러) ('수정한 API가 정상 동작하게 하기'하면서 같이 확인)
    - [ ] 테스트 코드 작성하기 (API 배포를 빨리 하기 위해 배포 첫 성공 후 작업하기, 배포 성공 이후에는 변경이 있을 때마다 하기)

- [ ] API 배포하기
    - [x] EC2 인스턴스 생성하기 (2020-01-30 / 2h 30m)
        - 인스턴스 연결에 애를 먹었다. 알고보니 인스턴스 생성 시 설정된 키 페어를 지우고, 다른 키 페어를 생성해서
        인스턴스에 새로운 키 페어로 인증이 안 되는 현상이었다. 인스턴스를 다시 생성해서 해결했다. 데
        기존 인스턴스를 중지시켰는 데 어떻게 반납해야 하는 지 모르겠다.
    - [ ] EC2에 jar or war 내보내기
    - [ ] DB 내보내기 (일단, EC2에 설치하고 나중에 RDS로 바꿔보자)
    - [ ] 서버 띄우기
    - [ ] RDS로 DB 이전하기
    
- [ ] UI 개발하기
    - Storybook, Cypress, CI 설정 등
