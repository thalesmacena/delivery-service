FROM postgres as dumper

COPY dump.sql /docker-entrypoint-initdb.d/

RUN ["sed", "-i", "s/exec \"$@\"/echo \"skipping...\"/", "/usr/local/bin/docker-entrypoint.sh"]

ENV PG_USER=postgres
ENV POSTGRES_PASSWORD=postgres

ENV PGDATA=/data

RUN ["/usr/local/bin/docker-entrypoint.sh", "postgres"]

# final build stage
FROM postgres

ENV PG_USER=postgres
ENV POSTGRES_PASSWORD=postgres

COPY --from=dumper /data $PGDATA
