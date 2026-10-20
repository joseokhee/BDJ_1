# ByulDaJul (별다줄) — Korean Document Summarizer

> Photo of a long Korean text → OCR → extractive summary, served through a REST API and an Android app.
> Team project, 2021.

**Demo video:** https://youtu.be/EI8dDueB7tg

## Overview

| Component | What it does |
|---|---|
| **Summarization model** | BertSum (extractive) fine-tuned for Korean, on top of ETRI's Korean pretrained BERT |
| **Morphological analysis** | ETRI WiseNLU API, used to tokenize input the same way as the pretraining corpus |
| **REST API** | FastAPI server wrapping the summarizer |
| **Android app** | Camera/photo input → Tesseract OCR → API call → saved summaries list |

### App workflow
1. Take or pick a photo → OCR (Tesseract) → call the summary API → show the result and save it as a text file on the device
2. Browse saved summaries → open any entry to read it again

## Repository layout

```
├── src/              # BertSum training / inference code, API entry (main.py)
├── app/              # Android application (Java)
├── article_to_json.py, etri_api_scraper.py   # data preparation
├── bert_config_uncased_base.json
└── urls/             # data split mapping files from the original BertSum
```

## Setup

```bash
pip install torch pytorch_pretrained_bert tensorboardX multiprocess pyrouge easydict fastapi
export ETRI_API_KEY=<your ETRI open API key>   # required for morphological analysis
```

- **Training data:** AI Hub document summarization dataset (https://aihub.or.kr/aidata/8054)
- **Pretrained BERT:** ETRI KorBERT (https://aiopen.etri.re.kr). Weights and vocab are **not included** because of the ETRI usage agreement — request them from ETRI directly.

## Acknowledgements

- Model code is adapted from [nlpyang/BertSum](https://github.com/nlpyang/BertSum) — *Fine-tune BERT for Extractive Summarization* ([arXiv:1903.10318](https://arxiv.org/abs/1903.10318)). Original copyright belongs to the BertSum authors.
- Some code is borrowed from [OpenNMT-py](https://github.com/OpenNMT/OpenNMT-py).
- Korean data pipeline references [KorBertSum](https://velog.io/@raqoon886/KorBertSum-SummaryBot).
