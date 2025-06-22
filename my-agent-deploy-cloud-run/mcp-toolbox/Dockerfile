# see releases page for other versions
FROM us-central1-docker.pkg.dev/database-toolbox/toolbox/toolbox:0.7.0

MAINTAINER lorcie <adrien.chan@free.fr>

COPY ./hotels.db /app/hotels.db

COPY ./tools.yaml  /app/tools.yaml

EXPOSE 5000

CMD [ "toolbox", "--tools-file", "/app/tools.yaml", "--address", "0.0.0.0" ]
