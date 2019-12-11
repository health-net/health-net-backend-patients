#!/bin/bash

dbname=healthnet_patients

create_patients_table_sql=../sql/create_patients_table.sql

set -e

if test ! -f "$create_patients_table_sql"; then
  echo "[ERROR] The SQL file needed to generate the patient table was not found. (../sql/create_patients_table.sql)"
  exit 1
fi

if [ "$MYSQL_PWD" == "" ]; then
  read -r -s -p "MYSQL root password (type return for no password): " MYSQL_PWD
  echo "Thanks. Please define the MYSQL_PWD env var to avoid this question."
  printf "\n"
  export MYSQL_PWD
fi

for db in $dbname ${dbname}_test
do
  echo "$(tput bold)$db database initialization $(tput sgr0)"
  mysql -u root <<QUERIES
    DROP DATABASE IF EXISTS $db;
    CREATE DATABASE IF NOT EXISTS $db;
QUERIES
  echo "$db database created"

  mysql -u root -D $db -e "$(cat $create_patients_table_sql)"
  echo "patients table created in $db"
  printf "\n"
done

