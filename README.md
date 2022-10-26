<br>

# <b>팀프로젝트 2차</b>
- 3조 아시겠조
- 프로젝트명 : 0칼로리
- 기능 목표 : servlet과 jsp로 crud 게시판 만들기
- 기술 스택 : Java, Javascript, jQuery, CSS, HTML, jsp, OracleDB
- 개발 환경 : Eclipse, JDK 11.0.15, Apache Tomcat 9.0, Maven
- 설명 : 다이어트 관리 웹 기반 애플리케이션 구현

## <b>팀원</b>
- 임은빈(팀장), 김지수, 김택준, 유소연, 이동현, 류정은

## <b>제작 기간</b>
- 2022.09.02 ~ 2022.09.26

## <b>제작 목표</b>
- 수업시간에 배운 servlet, jsp 를 이용해 게시판 crud 기능 구현
- DB를 이용하여 데이터 저장/삭제/수정/불러오기 기능 구현

## <b>맡은 역할</b>
- 임은빈 : 로그인, 회원가입, '고객센터' 주제의 CRUD 게시판
- 김지수 : 마이페이지 내 회원정보 편집 기능
- 김택준 : '커뮤니티' 형식의 CRUD 게시판 (회원들과의 교류 목적)
- 유소연, 류정은 : '캘린더' 형식의 CRUD 게시판, 마이페이지 내 일주일 몸무게 차트 구현
- 이동현 : 캘린더 내 회원 검색 구현


<br>
<br>

## <b>구현 화면</b>
<br>
<br>
<b>메인 페이지</b>
<br>
<br>

<img width="900" alt="(3) 메인 페이지" src="https://user-images.githubusercontent.com/93369732/197054553-08e2ee17-a73e-4f88-890b-ef49c90749fb.png">

<br>
<b>로그인</b>
<br>
<br>
<img width="900" alt="(1) 회원정보관리 - 로그인" src="https://user-images.githubusercontent.com/93369732/197056646-1114775b-4d7e-4f8a-9c27-a9f44b811329.png">

<br>
<b>회원가입</b>
<br>
<br>
<img width="900" alt="(1) 회원정보관리 - 회원가입" src="https://user-images.githubusercontent.com/93369732/197056917-fc6c4b98-8967-4d5c-b969-e996b2a823b6.png">

<br><br>
<b>마이페이지</b>
- 회원정보 변경, 몸무게 차트 보여주기
<br>
<img width="900" alt="(2) 마이페이지 - 메인" src="https://user-images.githubusercontent.com/93369732/197057337-0c585a00-da00-4e86-962c-7e4182de8283.png">
<br>
<img width="900" alt="(2) 마이페이지 - 회원정보 수정" src="https://user-images.githubusercontent.com/93369732/197057626-9ee36489-a7af-4a6f-8be6-37740d225317.png">
<br>
<br>

<b>캘린더</b>
- 달력 형식으로 일정을 관리하는 시스템 (일정 추가/삭제/수정). 모든 회원이 관리 가능

<br>
<img width="900" alt="(4) 캘린더" src="https://user-images.githubusercontent.com/93369732/197059331-ff650fb8-d7b1-4919-a543-8398d8e1d152.png">
<br>
<br>

<b>고객센터</b>
- 메인 페이지, 관리자 전용 글쓰기/수정/삭제, 상세보기
<br>
<img width="900" alt="(5) 고객센터 - 메인페이지" src="https://user-images.githubusercontent.com/93369732/197059814-93485d59-251a-40f4-95bd-bb6c6e34961e.png">
<br>
<br>

관리자 전용 글쓰기, 글 수정
<br>
<br>

<img width="900" alt="(5) 고객센터 - 자주하는질문 - 관리자 글쓰기" src="https://user-images.githubusercontent.com/93369732/197060200-b0af8e32-9d7f-45b9-b76d-b31bae5ad6ec.png">
<br>
<img width="900" alt="(5) 고객센터 - 자주하는질문 - 관리자 글수정" src="https://user-images.githubusercontent.com/93369732/197059995-a5bca09e-84ee-4c53-aa57-d6b10f839fc8.png">
<br>
<img width="900" alt="(5) 고객센터 - 자주하는질문 - 관리자 글쓰기버튼 유무" src="https://user-images.githubusercontent.com/93369732/197060358-5fb03f8e-2a95-4841-9143-de26bf0ef0a5.png">
<br>
<img width="900" alt="(5) 고객센터 - 자주하는질문 - 사이트 이용 가이드" src="https://user-images.githubusercontent.com/93369732/197060415-d8de7f70-8938-4a16-99a5-b2f6dabdcc20.png">
<br>
글 상세보기 (관리자한테 수정/삭제 버튼이 보이고 권한이 주어짐)
<br>
<br>
<img width="900" alt="(5) 고객센터 - 자주하는질문 - 상세보기" src="https://user-images.githubusercontent.com/93369732/197060479-b998ed7d-8084-48fa-aaad-1229bcd7f5ac.png">
<br>
<br>

<b>커뮤니티 게시판</b>
- 메인 페이지, 글 상세보기, 글쓰기/수정/삭제. (모든 회원 권한이 주어짐)
<br>
<img width="900" alt="(6) 커뮤니티 - 메인" src="https://user-images.githubusercontent.com/93369732/197061021-03ffaa7b-7b60-426a-ac4f-fccf10b8fa85.png">
글 상세보기
<br>
<br>

<img width="900" alt="(6) 커뮤니티 - 글 상세보기" src="https://user-images.githubusercontent.com/93369732/197060732-51949d03-18d9-4f25-a355-bbaa9f7c3ebb.png">
글 수정하기
<br>
<br>

<img width="900" alt="(6) 커뮤니티 - 글 수정하기" src="https://user-images.githubusercontent.com/93369732/197060807-b6c08a75-79bc-4544-8a45-d2d5d16af116.png">
글 작성하기
<br>
<br>
<img width="900" alt="(6) 커뮤니티 - 글 작성하기" src="https://user-images.githubusercontent.com/93369732/197060924-c2697289-cd60-41d3-a8d0-a4120a1d2247.png">
<br>


## 참고사이트
- 투두메이트 https://www.todomate.net/
