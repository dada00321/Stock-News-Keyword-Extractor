# Stock-News-Keyword-Extractor

爬蟲 輸入 (某檔股票或資訊) 之即時新聞，儲存、分析數篇文章後，提取出文章關鍵字
===

(1) 爬蟲模組: news_download
---
XXX_Crawler.java 可爬取某家財經/股市新聞網站的即時新聞
StockNewsModel.java 為「新聞模型類」，定義了「單篇新聞」需要獲取的資訊 (e.g., 文章標題、文章連結、文章內容等)
由 DataStorage.java 做檔案儲存 (JSON檔)
每爬取完一篇新聞(StockNewsModel)，放到 「新聞模型串列類」暫存 (i.e., StockNewsListModel.java)

(2) 分析模組: news_analysis
---
讀取已經爬好的 JSON 檔
先用 Jieba 套件分詞
再利用 TF-IDF 文字挖掘加權技術，逐一算出某單詞在所有文章的「重要程度」
並將新聞標題、新聞內文等依重要程度排序，取前 K 大的紀錄'。

(3) UI 模組: gui
---
寫個簡單的 GUI 做顯示、讓操作更簡便

(4) 測試模組: test
---
每一個小功能，測試某版本 maven 套件和相應語法的測試區
