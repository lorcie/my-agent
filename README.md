# MyAgent for Devpost Agent Development Kit Hackathon with Google Cloud

## Introduction

This project combines **Google Agent Development Kit** Java Application with **ModelContextProtocol** servers (Toolbox, AirBnB)  by SSE.


## Build Application Docker Images


- Build the image(s) with command : **docker compose build**

MyAgent Docker Modules Images >

![MyAgent Docker Images](https://github.com/lorcie/my-agent/blob/main/assets/my-agent-docker-images.png?raw=true)

## Docker Compose Modules Orchestration/Start/Stop

### Start Application Docker Containers

Start Application thru MyAgent docker modules

- start the My Agent with command : **docker compose up*

MyAgent Docker Modules Containers >

![MyAgent Docker Module Containers](https://github.com/lorcie/my-agent/blob/main/assets/my-agent-docker-containers.png?raw=true)


### Stop Application Docker Containers

- type **CTRL-C** to force leaving the application thru the opened console

- then stop the MyAgent Application thru the N8N platform with command : **docker compose down**

- list pending  docker modules with command : **docker ps -a**

- remove the pending (ollama) docker modules container id (s) with command  : **docker rm <container_id>**

## Usage

On the My Agent ADK Web UI application, you can use the following queries to test various use cases:

1. mcp-toolbox(hotel) + weather tool user query >
please find up to 3 hotels in basel and inform about weather report

2. mcp-server-airbnb user query >
describe up to 3 AirBnB accomodation at Basel

3. google-search user query >
what are the best periods to go to Basel?

- Click on any item on left hand side of the User Interface to get an Application Map with the Tools/Agents concerned by the interaction, and also details of Request/Response


## MyAgent Development

Global Architecture >

![MyAgent Global Architecture](https://github.com/lorcie/my-agent/blob/main/assets/my-agent-architecture.png?raw=true)

Tools List >

![MyAgent Tools List](https://github.com/lorcie/my-agent/blob/main/assets/my-agent-tools-list.png?raw=true)


## MyAgent Executions

Hotels Search with Weather Report >

![MyAgent Hotels Search with Weather Report](https://github.com/lorcie/my-agent/blob/main/assets/my-agent-search-hotels-with-weather.png?raw=true)

AirBnb Accomodation Search >

![MyAgent AirBnB Accomodation Search](https://github.com/lorcie/my-agent/blob/main/assets/my-agent-airbnb-search.png?raw=true)

Google Search >
![MyAgent Google Search](https://github.com/lorcie/my-agent/blob/main/assets/my-agent-google-search.png?raw=true)

## Codeset Files

Docker (Compose) Codeset >

![MyAgent Docker (Compose) Codeset](https://github.com/lorcie/my-agent/blob/main/assets/my-agent-docker-codeset.png?raw=true)

Java Codeset >

![MyAgent Java Codeset](https://github.com/lorcie/my-agent/blob/main/assets/my-agent-java-codeset.png?raw=true)

## Application Deploy on Cloud Run

This requires to deploy separately each of the components

### Database

Prepare some Database 

Here are instructions for Postgres (example with POSTGRES_15) Database on **Google Cloud SQL**

gcloud sql instances create toolbox-db \
--database-version=POSTGRES_15 \
--cpu=YOUR_CPU_NUMBER \
--memory=8GiB \
--region=YOUR_REGION \
--edition=ENTERPRISE \
--root-password=postgres

### MCP Toolbox

https://googleapis.github.io/genai-toolbox/how-to/deploy_toolbox/

see section "Deploy to Cloud Run"

### MCP server AirBnb

cd mcp-server-airbnb/

export SERVICE_NAME='my-agent-abb-app'

export AR_REPO=YOUR_AR_REPO

export GCP_REGION=YOUR_REGION

export GCP_PROJECT=YOUR_PROJECT_ID

gcloud artifacts repositories list

gcloud artifacts repositories create "$AR_REPO" --location="$GCP_REGION" --repository-format=Docker

gcloud auth configure-docker "$GCP_REGION-docker.pkg.dev"

gcloud builds submit --tag "$GCP_REGION-docker.pkg.dev/$GCP_PROJECT/$AR_REPO/$SERVICE_NAME" .

gcloud run deploy "$SERVICE_NAME" --port=8090 --image="$GCP_REGION-docker.pkg.dev/$GCP_PROJECT/$AR_REPO/$SERVICE_NAME" --allow-unauthenticated --region=$GCP_REGION --platform=managed --project=$GCP_PROJECT


### My Agent ADK applivcation

cd my-agent

export SERVICE_NAME='my-agent-app'

export AR_REPO=YOUR_AR_REPO

export GCP_REGION=YOUR_REGION

export GCP_PROJECT=YOUR_PROJECT_ID

gcloud artifacts repositories list

gcloud artifacts repositories create "$AR_REPO" --location="$GCP_REGION" --repository-format=Docker

gcloud auth configure-docker "$GCP_REGION-docker.pkg.dev"

gcloud builds submit --tag "$GCP_REGION-docker.pkg.dev/$GCP_PROJECT/$AR_REPO/$SERVICE_NAME" .

gcloud run deploy "$SERVICE_NAME"  --port=8080 --image="$GCP_REGION-docker.pkg.dev/$GCP_PROJECT/$AR_REPO/$SERVICE_NAME"  --allow-unauthenticated --region=$GCP_REGION --platform=managed --project=$GCP_PROJECT --set-env-vars=MCP_PROXY_URL=$MCP_PROXY_URL,MCP_TOOLBOX_URL=$MCP_TOOLBOX_URL,GOOGLE_API_KEY=$GOOGLE_API_KEY


