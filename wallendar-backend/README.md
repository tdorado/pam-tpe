# Wallendar Backend

To run the backend there are two options:

## Run with the deployed backend

For this option there is no need to make changes. 

The backend is deployed in: https://wallendar.herokuapp.com/

## Run locally

For this option you have to create the PostgreSQL database locally, you can do this inside a docker or not:

### Option 1: Create database inside docker

You must have docker and docker-compose installed and run the make inside the wallendar-backend folder.
```
make
```

### Option 2: Create database without docker

You must have at least PostgreSQL 9.5 installed and execute the following commands:

```
sudo -u postgres psql
create database "wallendar-db";
create user "wallendar-db-owner" with encrypted password 'j17DgpslK';
grant all privileges on database "wallendar-db" to "wallendar-db-owner";
```
