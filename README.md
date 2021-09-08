# 별다줄

## 긴 글 요약 API

**This code is from paper `Fine-tune BERT for Extractive Summarization`**(https://arxiv.org/pdf/1903.10318.pdf)

**!New: Please see our [full paper](https://arxiv.org/abs/1908.08345) with trained models**

Package Requirements: pytorch pytorch_pretrained_bert tensorboardX multiprocess pyrouge

Some codes are borrowed from ONMT(https://github.com/OpenNMT/OpenNMT-py)

## 한국어 문서 추출요약을 위한 Modified BertSum

### 해당 문서의 원저작권은 Nlpyang의 [BertSum](https://github.com/nlpyang/BertSum) 에 있습니다.

### 학습 데이터
학습 데이터는 aihub에서 제공하는 문서 요약 텍스트 데이터를 사용하였습니다. (https://aihub.or.kr/aidata/8054)

### pretrained bert
fine tuning을 위해 ETRI에서 제공하는 한국어 pretrained BERT를 사용하였습니다. 
사용 협약으로 인해 코드 및 데이터는 비공개.(https://aiopen.etri.re.kr/service_dataset.php)

### 형태소 분리 모델
aihub에서 제공하는 언어 분석 기술 사용 (https://aihub.or.kr/ai_software/361)

### REST API
Fast api 사용 구현


## Android 어플리케이션

### work flow

1. 사진 입력 -> OCR (tesserct 사용) -> API call -> 결과 출력 -> 결과 내부 text파일로 저장
2. 저장된 결과 보기 -> 내부 text 파일 read -> list 출력 -> 선택 시 내용 출력

시연 영상 참고


