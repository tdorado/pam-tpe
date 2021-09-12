curl --request POST \
  --url http://localhost:8080/users/debts \
  --header 'Content-Type: application/json' \
  --data '{
	"debt_id": 2,
	"from_user_id": 1,
	"to_user_id": 2,
	"amount": 1252.3
}'

curl --request GET \
  --url 'http://localhost:8080/users?user_email=tdallas%40itba.edu.ar'

  curl --request GET \
  --url 'http://localhost:8080/groups?user_id=2'


  curl --request GET \
  --url http://localhost:8080/groups/1

  curl --request GET \
  --url 'http://localhost:8080/events?user_id=2'

  curl --request GET \
  --url http://localhost:8080/events/1

  curl --request POST \
  --url http://localhost:8080/charges \
  --header 'Content-Type: application/json' \
  --data '{
		"owner": "tdallas@itba.edu.ar",
		"title": "Cocas",
		"chargeType": "EQUALLY",
		"debtors": ["tdorado@itba.edu.ar"],
		"amount": 250,
		"date": "2021-09-11T15:36:54.606Z"
}'