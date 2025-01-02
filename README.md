<img width="575" alt="image" src="https://github.com/user-attachments/assets/9d93e92a-6f3e-461f-8c97-7e20a5c2ad08" />

---
- lecture 테이블에는 특강의 정보를 컬럼으로 갖는다.
- 현재 신청된 수(current_enrollment) 컬럼을 통해 30 이상이면 수강 신청 불가능하게 한다.
- 특강 시간(start_date_time) 컬럼을 통해 픽한 날짜의 특강들 중 신청된 수가 30미만이면 신청 가능 리스트에 뜨게한다.
---
- registration 테이블은 특강 신청 정보들을 컬럼으로 갖는다.
- 하나의 user가 여러개의 lecture를 들을 수 있고, 요구사항에서 신청이 완료된 특강들을 모아볼 수 있어야하기 때문에, registration 테이블에선 lecture_id를 fk로 갖는다.
- 즉, ManyToOne 조인을 한다.
