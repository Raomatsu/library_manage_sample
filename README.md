## APIインタフェース概要
### ホスト
https://secure-garden-23538-d5f129a563e4.herokuapp.com  

### 書籍検索・登録・更新
- GET /v1/books
  - クエリパラメータ
    - name：書籍名検索（部分一致含む）
    - id：id検索
    - 指定なし：全件検索
    - 該当のデータなし→404
- POST /v1/books
  - リクエストボディ
    - name：書籍名
    - authorId：著者ID
    - パラメータ不足→400
- PUT /v1/books/{bookId}
  - リクエストボディ
    - name：書籍名（非必須）
    - authorId：著者ID（非必須）
    - 両方とも指定なし（null、空文字含む）→400
    - 指定したIDにあたる書籍なし→404

### 著者検索・登録・更新
- GET /v1/authors
    - クエリパラメータ
        - name：著者名検索（部分一致含む）
        - id：id検索
        - 指定なし：全件検索
        - 該当のデータなし→404
- POST /v1/authors
    - リクエストボディ
        - name：著者名
        - 著者名の指定なし→400
- PUT /v1/authors/{authorId}
    - リクエストボディ
        - name：著者名
        - 著者名の指定なし（null、空文字含む）→400
        - 指定したIDにあたる著者なし→404
- GET /v1/authors/{authorId}/books
  - 著者に紐づく書籍一覧取得
  - 指定したIDにあたる著者なし→404
  - 指定した著者に紐づく書籍なし→著者情報のみ返す（書籍データは0件で返す）


## DBテーブル定義（流したDDL）
- 著者テーブル  
CREATE TABLE IF NOT EXISTS AUTHOR(
ID SERIAL PRIMARY KEY,
NAME TEXT NOT NULL,
CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIME_STAMP,
UPDATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIME_STAMP
);
  
- 書籍テーブル  
CREATE TABLE IF NOT EXISTS BOOK(
ID SERIAL PRIMARY KEY,
NAME TEXT NOT NULL,
AUTHOR_ID INTEGER REFERENCES AUTHOR(ID) NOT NULL,
CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIME_STAMP,
UPDATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIME_STAMP
);
  

- 更新時に更新時刻（UPDATED_AT)の自動更新を行うトリガー（下記はAUTHORの記述だがBOOKに対しても実施した）  
  - CREATE FUNCTION insert_created_at_column()
RETURNS TRIGGER AS $$
BEGIN
NEW.created_at = CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;  
  - CREATE TRIGGER update_book_updated_at
BEFORE UPDATE ON AUTHOR
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

