***Terminal - to check db in terminal

psql -U admin -d postgres
\c admin
SELECT * FROM spring_ai_yt.spring_ai_chat_memory

***Docker command to run the MariaDB
docker run -d --name mariadb -p 3308:3306 -e MARIADB_ROOT_PASSWORD=password mariadb:11.8
and connect this db with dbeaver