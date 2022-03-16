# DDL


# 初期はpostgresが特権ユーザになっているのでpsql -U postgresでログインできます
# initdbはしなくても大丈夫だと思います。
# schemaは分けたい場合は分けてください。今回は特に指定していません。
# create user
create user springnavi with password 'springnavi' createdb;
# create database
create database springnavi;
# grant all to springnavi on springnavi database
grant all on database springnavi to springnavi;
# swich springnavi
\c - springnavi

# create tables
create table cif
(cifno          CHAR(7)     NOT NULL,
name            TEXT        NOT NULL,
password        TEXT        NOT NULL,
PRIMARY KEY (cifno));

create table yokin
(kouzabangou    CHAR(7)     NOT NULL,
zandaka         INTEGER     NOT NULL,
cifno           CHAR(7)     NOT NULL,
FOREIGN KEY (cifno) REFERENCES cif(cifno),
PRIMARY KEY (kouzabangou));

create table idomeisai
(idono          UUID        NOT NULL,
kingaku         INTEGER     NOT NULL,
shimukekouza    CHAR(7)     NOT NULL,
hishimukekouza  CHAR(7)     NOT NULL,
status          TEXT        NOT NULL,
exectime        TEXT        NOT NULL,
PRIMARY KEY (idono));

create table kafkaexec
(id             INTEGER     NOT NULL,
status          CHAR(1)     NOT NULL,
PRIMARY KEY (id));

# 初期データ作成
# csvから読み込むとかもできます
# パスワード機能をハッシュ化するとかしたら変えてください
# （Oracleのストアドみたいなのはpostgresにはないです）
# CIFなのに管理者ユーザ入ってますがご了承ください
insert into cif values ('0000001','navi1','navi1');
insert into cif values ('0000002','navi2','navi2');
insert into cif values ('0000003','navi3','navi3');
insert into cif values ('0000004','navi4','navi4');
insert into cif values ('0000005','navi5','navi5');
insert into cif values ('0000006','navi6','navi6');
insert into cif values ('0000007','navi7','navi7');
insert into cif values ('0000008','navi8','navi8');
insert into cif values ('0000009','navi9','navi9');
insert into cif values ('1000000','admin','admin');

insert into yokin values ('0000001',10000000,'0000001');
insert into yokin values ('0000002',10000000,'0000002');
insert into yokin values ('0000003',10000000,'0000003');
insert into yokin values ('0000004',10000000,'0000004');
insert into yokin values ('0000005',10000000,'0000005');
insert into yokin values ('0000006',10000000,'0000006');
insert into yokin values ('0000007',10000000,'0000007');
insert into yokin values ('0000008',10000000,'0000008');
insert into yokin values ('0000009',10000000,'0000009');

# バッチ処理のステータスは（0:起動なし、1:jar起動済み、2:処理開始済み、9:異常発生）
insert into kafkaexec values (1,'0');
insert into kafkaexec values (10,'0');
