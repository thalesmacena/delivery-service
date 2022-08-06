
FROM postgres

COPY opt/dump.sql /docker-entrypoint-initdb.d/

ENV PG_USER=postgres
ENV POSTGRES_PASSWORD=postgres
