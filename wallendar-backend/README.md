# Wallendar Backend

## Configurar PostgreSQL localmente
```
sudo -u postgres psql
create database "wallendar-db";
create user "wallendar-db-owner" with encrypted password 'j17DgpslK';
grant all privileges on database "wallendar-db" to "wallendar-db-owner";
```

## Usuario PostgreSQL
```
wallendar-db-owner
j17DgpslK
```