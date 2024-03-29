# 2021-2-mobile-programming

## 소개  
어플리케이션 “미니게임천국”은 3가지 미니게임을 즐길 수 있는 종합 게임 어플리케이션 입니다.  
3가지 게임은 각각 [두더지 잡기, 잰말 게임, Mega Rabbit]로 구성되어 있습니다.  
각각의 게임을 즐기고 스코어를 기록, 종합하여 점수를 경쟁하며 게임을 즐길 수 있습니다.  

## 특징  
- 각기 다른 기술을 사용한 서로 다른 3가지 게임으로 구성되어있습니다. 기울기 센서, 스레드, 음성인식 등 모바일 기기에 내장된 다양한 기술을 사용하여 게임을 구현하였습니다.  
- 3개의 게임으로 분리되어 있지만 각 게임의 점수를 종합하여 볼 수 있습니다. 각자 플레이 후 기록된 점수를 통해 친구와 함께 순위경쟁을 할 수 있다는 특징이 있습니다.     


## 사용법  
- 어플 실행 후 등장하는 3가지 게임 메뉴 중 원하는 게임 한가지를 선택하여 게임을 플레이합니다.  
- 게임 종료 후 스코어를 기록하고 점수판에서 기록된 점수를 확인할 수 있습니다.  


## 게임1(두더지게임) 플레이 방법  
- 게임 시작 전 화면에 두더지, 폭탄 등 각 개체에 대한 점수가 소개됩니다.  
- 게임 시작 버튼을 누르면 타이머가 시작됩니다. 타이머는 20초입니다.  
- 타이머 옆엔 점수가 나타납니다.  
- 타이머 및 점수 아래에 구멍에서 두더지 등 개체들이 랜덤한 위치와 시간으로 등장합니다.  
- 각 개체를 터치하면 점수가 기록됩니다.  
- 20초간 게임을 진행하며 높은 점수를 얻으면 됩니다.  
![image](https://user-images.githubusercontent.com/79445881/226109610-e80f2102-0334-47da-b1a7-ec5d32ecefd7.png)

## 게임2(잰말게임) 플레이 방법  
- 제시된 잰말 문제를 읽어 핸드폰이 인식한 텍스트와 동일할시 점수를 얻는 게임입니다.  
- 게임 화면 상단에 잰말 문제가 제시가 됩니다.   
- 게임 화면 하단에 음성인식 버튼이 있습니다. 버튼을 눌러서 해당 문제를 읽으면 됩니다.  
- 음성인식이 완료된 경우 인식된 음성 내용과 제시된 문제가 같을 경우 점수를 획득합니다.  
- 문제가 어려울시 화면 하단 pass버튼으로 다음 문제로 넘어갈 수 있습니다.  
- 총 10개문제가 랜덤으로 제시됩니다.  
![image](https://user-images.githubusercontent.com/79445881/226109624-445ed5eb-4a33-467a-8e2b-9e1c366d2d5c.png)

## 게임3(Mega Rabbit) 플레이 방법  
- 게임 시작 후 2초 뒤 토끼가 공중으로 날아오릅니다  
- 휴대폰을 좌, 우로 기울이며 기울기 센서를 통해 캐릭터를 이동시킬 수 있습니다.  
- 캐릭터가 양쪽 끝 화면으로 이동하면 반대편에서 등장하게 됩니다.  
- 파인애플을 먹으면 공중으로 날아오르며, 파인애플을 먹지 못할 시 추락하여 게임이 종료됩니다.  
- 공중에 있던 시간만큼 스코어로 기록됩니다.  
![image](https://user-images.githubusercontent.com/79445881/226109632-c361d7af-0790-46e3-a6fa-9fac400a0bad.png)


## 사용한 기술

 게임1 (두더지 게임)  
  -  멀티 스레드를 이용하여 랜덤한 위치와 시간으로 두더지 이미지를 출력   
  -  스레드를 이용한 시간 제한 구현  
  -  각 개체 이미지에 태그 값을 설정하여 이미지를 터치했을 경우 점수 변화  
  
 게임2 (잰말 게임)  
  - 음성인식을 이용한 텍스트 출력  
  - 스레드를 이용한 게임 진행시간 구현  
  - json 파일로 문제 데이터 담고 jsonParsing으로 문제 랜덤 출력  
  
 게임3 (Mega Rabbit)   
  - 멀티 스레드를 이용한 캐릭터 및 배경 오브젝트 이동  
  - 기울기 센서(Magneto Sensor)를 이용한 캐릭터 이동  
  - 뷰 충돌(겹침) 감지 알고리즘을 통한 오브젝트 획득 기능 구현   
  
 점수입력 및 점수 판  
  - key, value 쌍으로 데이터를 저장할 수 있는 sharedpreference 객체 사용  
  - sharedpreference 객체로 게임을 플레이한 사용자 ID와 점수를 각각 key, value 쌍으로 저장  
  - sharedprefence 객체로 접근하여 값을 가져오고, Collection.sort를 이용하여 점수에 따른 내림차순 정렬을 하여 점수판을 구현  
  - tablayout으로 각 게임마다 점수판을 분류해서 따로따로 점수를 볼 수 있음  
  

## 팀 기여도  
박성일: 게임2(잰말게임) 구현, 피드백, 디자인, PPT 종합, 발표  
임예광: 게임3(Mega Rabbit) 구현, 피드백, 보고서 종합, 발표  
김민종: 아이디어 제안, 게임1(두더지게임) 구현, 피드백, 게임 종합, 발표  
