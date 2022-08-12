
BUILD STATUS [![CircleCI](https://dl.circleci.com/status-badge/img/gh/irostub/whaple-bot/tree/master.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/irostub/whaple-bot/tree/master)  
다기능 텔레그램 와플봇입니다. 와플봇은 다음 명령어를 지원합니다.  
명령어 접두문자는 공백, 슬래시, 마침표 등을 지원합니다.  
각 명령어에 대한 설명은 [명령어] ? 로 확인해보세요!  
  
사용할 수 있는 명령어  
**점심 룰렛**  
밥  
밥 ?  
밥 추천  
밥 목록  
밥 추가  
밥 삭제  
밥 무시  
밥 복구  
  
**날씨 확인**
날씨 [지역]
  
**한강 수온 확인**
한강
  

### 빌드 환경
 - openjdk 11
### 빌드 방법
 mvn - DskipTests clean package
### 필요한 설정
  - annotation processor
  - application.properties 
    - 봇 토큰
    - 봇 유저명

