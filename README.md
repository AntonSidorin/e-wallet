# Wallet Manager
[![E-Wallet CI Build](https://github.com/AntonSidorin/e-wallet/actions/workflows/maven.yml/badge.svg)](https://github.com/AntonSidorin/e-wallet/actions/workflows/maven.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=e-wallet&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=e-wallet)

Wallet Manager can help you to keep track of your income and expenses with quality info and keep track of
your daily, monthly and yearly finances.

## Dev environment
Add proxy in package.json to http://localhost:8080/
```
"proxy":"http://localhost:8080"
```

## How to build
```
mvn clean package
```

## How to run
* docker
```
docker-compose up
```
* docker with build
 ```
docker-compose up --build
```
```
open http://localhost:3000 in browser
```

## How to run locally
* kn-wallet-be
```
run WalletApplication
```
* kn-wallet-be
```
npm start
```
```
open http://localhost:3000 in browser
```
