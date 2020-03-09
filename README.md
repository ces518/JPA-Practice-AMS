# JPA Practice Project
- AMS (Academy Management System)

#### 개발환경
- SpringBoot 2.2.5
- Spring data JPA
- QueryDSL
- H2 Database 1.4.200
- Lombok
- Thymeleaf
- p6spy

#### 요구사항
- 학생은 여러 반에 소속될 수 있다.
- 선생님은 여러반에 담당선생님이 될 수 있다.
- 선생님은 학생의 성적을 관리할 수 있다.
- 성적은 과목별 성적으로 입력할 수 있다.


#### 기능 목록
- 학생관리
    - 목록 조회
    - 상세 조회
    - 등록
    - 수정
    - 삭제

- 선생님관리
    - 목록 조회
    - 상세 조회
    - 등록
    - 수정
    - 삭제

- 반관리
    - 목록 조회
    - 상세 조회
    - 등록
    - 수정
    - 삭제

- 성적관리
    - 목록 조회
    - 상세 조회
    - 등록
    - 수정
    - 삭제


#### 도메인 모델 분석
- 학생과 반의 관계
    - 학생은 여러 반에 소속될 수 있다.
    - 학생 소속반 이라는 모델을 만들어 다대다 관계를 일대다, 다대일 관계로 해소
- 선생님과 반의 관계
    - 선생님은 여라반의 담당선생님이 될 수 있다.
    - 담당 선생님 이라는 모델을 만들어 다대다 관계를 일대다, 다대일 관계로 해소


#### 테이블 설계
`학생`
- MEMBER
    - MEMBER_ID (PK)
    - NAME
    - CITY
    - STREET
    - ZIPCODE
    - PHONE
    - STATUS
    - CREATED_AT
    - UPDATED_AT
    - CREATED_BY
    - UPDATED_BY

`선생님`
- TEACHER
    - TEACHER_ID (PK)
    - NAME
    - PHONE
    - STATUS
    - CREATED_AT
    - UPDATED_AT
    - CREATED_BY
    - UPDATED_BY

`반`
- GROUPS
    - GROUP_ID (PK)
    - NAME
    - STATUS
    - CREATED_AT
    - UPDATED_AT
    - CREATED_BY
    - UPDATED_BY

`담당_선생님`
- GROUP_TEACHER
    - GROUP_TEACHER_ID (PK)
    - GROUP_ID (FK)
    - TEACHER_ID (FK)
    - CREATED_AT
    - CREATED_BY

`학생_소속반`
- GROUP_MEMBER
    - GROUP_MEMBER_ID (PK)
    - GROUP_ID (FK)
    - MEMBER_ID (FK)
    - CREATED_AT
    - CREATED_BY

`학년`
- GRADE
    - GRADE_ID (PK)
    - NAME
    - STATUS
    - CREATED_AT
    - UPDATED_AT
    - CREATED_BY
    - UPDATED_BY

`과목`
- SUBJECT
    - SUBJECT_ID (PK)
    - NAME
    - STATUS
    - CREATED_AT
    - UPDATED_AT
    - CREATED_BY
    - UPDATED_BY
   
`성적`
- RESULTS
    - RESULT_ID (PK)
    - MEMBER_ID (FK)
    - SUBJECT_ID (FK)
    - SCORE
    - CREATED_AT
    - CREATED_BY
