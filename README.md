# SpringNavi



curl テストコマンド

curl -X POST -H "Content-Type: application/json" -d '{"Id":"1","Status":"","Request":"1","Message":""}' http://localhost:8888/v1/backend/asyncfurikomistart
curl -X POST -H "Content-Type: application/json" -d '{"Id":"1","Status":"","Request":"2","Message":""}' http://localhost:8888/v1/backend/asyncfurikomistop
curl -X POST -H "Content-Type: application/json" -d '{"shimukekouza":"0000001", "hishimukekouza":"0000002", "kingaku":"1000","idono":null}' http://localhost:8888/v1/backend/furikomi