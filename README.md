# MyAgent for Devpost Agent Development Kit Hackathon with Google Cloud

[Google Cloud Run Hosted Project url: https://my-agent-app-1029043021255.us-central1.run.app](https://my-agent-app-1029043021255.us-central1.run.app) 

|           |                                                |
| --------- | ---------------------------------------------- |
| Author(s) | [Adrien Chan](https://github.com/lorcie) |

# Table of contents
1. [Introduction](#introduction)
2. [Build Application Docker Images with Docker Compose](#build-docker-compose)
3. [Docker Compose Modules Orchestration/Start/Stop ](#orchestrate-docker-compose)
4. [Usage](#usage)
5. [My Agent Development](#my-agent-development)
6. [My Agent Executions](#my-agent-executions)
7. [Codeset Files](#codeset-files)
8. [Application Deploy on Cloud Run](#application-deploy-cloud-run)
9. [Take Away](#take-away)

## Introduction <a name="introduction"></a>

This project combines **Google Agent Development Kit** Java Application with **ModelContextProtocol** MCP servers (Toolbox, AirBnB) by SSE combined with Google Search and custom Weather Report Tools 

## Build Application Docker Images with Docker Compose <a name="build-docker-compose"></a>

- Build the image(s) with command : **docker compose build**

MyAgent Docker Modules Images >

![MyAgent Docker Images](https://github.com/lorcie/my-agent/blob/main/assets/my-agent-docker-images.png?raw=true)

## Docker Compose Modules Orchestration/Start/Stop <a name="orchestrate-docker-compose"></a>

### Start Application Docker Containers

Start Application thru MyAgent docker modules

- start the My Agent with command : **docker compose up**

MyAgent Docker Modules Containers >

![MyAgent Docker Module Containers](https://github.com/lorcie/my-agent/blob/main/assets/my-agent-docker-containers.png?raw=true)


### Stop Application Docker Containers

- type **CTRL-C** to force leaving the application thru the opened console

- then stop the MyAgent Application thru the N8N platform with command : **docker compose down**

- list pending  docker modules with command : **docker ps -a**

- remove the pending (ollama) docker modules container id (s) with command  : **docker rm <container_id>**

## Usage <a name="usage"></a>

On the My Agent ADK Web UI application, you can use the following queries to test various use cases:

1. mcp-toolbox(hotel) + weather tool user query >
find some hotel at basel in end of june and display weather report

2. mcp-server-airbnb user query >
describe up to 3 AirBnB accomodation at Basel

3. google-search user query >
what are the best periods to go to Basel?

- Click on any item on left hand side of the User Interface to get an Application Map with the Tools/Agents concerned by the interaction, and also details of Request/Response


## MyAgent Development <a name="my-agent-development"></a>

Global Architecture >

![MyAgent Global Architecture](https://github.com/lorcie/my-agent/blob/main/assets/my-agent-architecture.png?raw=true)

Tools List >

![MyAgent Tools List](https://github.com/lorcie/my-agent/blob/main/assets/my-agent-tools-list.png?raw=true)


## MyAgent Executions <a name="my-agent-executions"></a>

Hotels Search with Tools on Cloud Run >

![MyAgent Hotels Search with Tools on Cloud Run](https://github.com/lorcie/my-agent/blob/main/assets/my-agent-cloud-run-with-tools-execution.png?raw=true)


Hotels Search with Weather Report >

![MyAgent Hotels Search with Weather Report](https://github.com/lorcie/my-agent/blob/main/assets/my-agent-search-hotels-with-weather.png?raw=true)

AirBnb Accomodation Search >

![MyAgent AirBnB Accomodation Search](https://github.com/lorcie/my-agent/blob/main/assets/my-agent-airbnb-search.png?raw=true)

Google Search >
![MyAgent Google Search](https://github.com/lorcie/my-agent/blob/main/assets/my-agent-google-search.png?raw=true)

Cloud Run Services >

![MyAgent Vloud Run Services](https://github.com/lorcie/my-agent/blob/main/assets/my-agent-cloud-run-services.png?raw=true)

## Codeset Files <a name="codeset-files"></a>

Docker (Compose) Codeset >

![MyAgent Docker (Compose) Codeset](https://github.com/lorcie/my-agent/blob/main/assets/my-agent-docker-codeset.png?raw=true)

Java Codeset >

![MyAgent Java Codeset](https://github.com/lorcie/my-agent/blob/main/assets/my-agent-java-codeset.png?raw=true)

## Application Deploy on Cloud Run <a name="application-deploy-cloud-run"></a>

This requires to deploy separately each of the components : Database, McpToolbox, McpAirBnB, MyAgent 

Clone the my-agent project Repo

Then You can apply following Installation/Deployment Instructions for instance on your Google Cloud Shell (CLI)

### Hotels Database

The hotels database has been initiated using SQL Lite DB tool.

#### Postgres Instructions with Google Cloud SQL

However, Here are instructions for Postgres (example with POSTGRES_15) Database on **Google Cloud SQL**

gcloud sql instances create my-agent-db \
--database-version=POSTGRES_15 \
--cpu=YOUR_CPU_NUMBER \
--memory=8GiB \
--region=YOUR_REGION \
--edition=ENTERPRISE \
--root-password=postgres

// create databse toolbox-db on the instance

gcloud sql databases create toolbox-db \
--instance=my-agent-db

// connect to the datanase server either with tool such as Google Cloud Studio either with CLI

#### SQL Lite Instructions

// Install the SQL lite tool depending on your OS

// connect to the datanase server SQL Lite Tool and save to hotels.db

// create the database schema

CREATE TABLE hotels(
  id            INTEGER NOT NULL PRIMARY KEY,
  name          VARCHAR NOT NULL,
  location      VARCHAR NOT NULL,
  price_tier    VARCHAR NOT NULL,
  checkin_date  DATE    NOT NULL,
  checkout_date DATE    NOT NULL,
  booked        BIT     NOT NULL
);

// initialize some data

INSERT INTO hotels(id, name, location, price_tier, checkin_date, checkout_date, booked)
VALUES 
  (1, 'Hilton Basel', 'Basel', 'Luxury', '2025-06-25', '2025-06-28', B'0'),
  (2, 'Marriott Zurich', 'Zurich', 'Upscale', '2025-06-24', '2025-06-29', B'0'),
  (3, 'Hyatt Regency Basel', 'Basel', 'Upper Upscale', '2025-07-02', '2025-07-20', B'0'),
  (4, 'Radisson Blu Lucerne', 'Lucerne', 'Midscale', '2025-06-24', '2025-07-05', B'0'),
  (5, 'Best Western Bern', 'Bern', 'Upper Midscale', '2025-06-23', '2025-07-01', B'0'),
  (6, 'InterContinental Geneva', 'Geneva', 'Luxury', '2025-06-26', '2025-06-28', B'0'),
  (7, 'Sheraton Zurich', 'Zurich', 'Upper Upscale', '2025-06-27', '2025-07-02', B'0'),
  (8, 'Holiday Inn Basel', 'Basel', 'Upper Midscale', '2025-06-24', '2025-07-09', B'0'),
  (9, 'Courtyard Zurich', 'Zurich', 'Upscale', '2025-07-03', '2025-07-13', B'0'),
  (10, 'Comfort Inn Bern', 'Bern', 'Midscale', '2025-07-04', '2025-07-16', B'0');


### MCP Toolbox

// go in directory mcp-toolbox

cd mcp-toolbox

#### SQL Lite Instructions

It will use the Dockerfile

export SERVICE_NAME='my-agent-mcp-toolbox'

export AR_REPO=YOUR_AR_REPO

export GCP_REGION=YOUR_REGION

export GCP_PROJECT=YOUR_PROJECT_ID

gcloud artifacts repositories list

// if not yet available, create AR_REPO
gcloud artifacts repositories create "$AR_REPO" --location="$GCP_REGION" --repository-format=Docker

gcloud auth configure-docker "$GCP_REGION-docker.pkg.dev"

// build docker image

gcloud builds submit --tag "$GCP_REGION-docker.pkg.dev/$GCP_PROJECT/$AR_REPO/$SERVICE_NAME" .

// deploy on Cloud Run

gcloud run deploy "$SERVICE_NAME" --port=5000 --image="$GCP_REGION-docker.pkg.dev/$GCP_PROJECT/$AR_REPO/$SERVICE_NAME"  --allow-unauthenticated --region=$GCP_REGION --platform=managed --project=$GCP_PROJECT

#### Postgres Instructions with Google Cloud SQL

MCP Toolbox to Cloud Run deployment need to point the Cloud SQL Database.

// only for Cloud SQL
gcloud projects add-iam-policy-binding $PROJECT_ID \
    --member serviceAccount:toolbox-identity@$PROJECT_ID.iam.gserviceaccount.com \
    --role roles/cloudsql.client

gcloud secrets create tools --data-file=tools.yaml

deploy the Toolbox to Cloud Run , based on the release version of the MCP Toolbox image

export IMAGE=YOUR_REGION-docker.pkg.dev/database-toolbox/toolbox/toolbox:latest

gcloud run deploy mcp-toolbox \
    --image="$IMAGE" \
    --service-account=toolbox-identity \
    --region=$GCP_REGION \
    --set-secrets "/app/tools.yaml=tools:latest" \
    --args="--tools-file=/app/tools.yaml","--address=0.0.0.0","--port=8080"
    --allow-unauthenticated

Verify that the Toolbox is running by getting the Cloud Run logs:

gcloud run services logs read mcp-toolbox --region $GCP_REGION

### MCP server AirBnb

// go in mcp-server-airbnb directory

cd mcp-server-airbnb/

// build the module into dist subdirectory
npm run build

export SERVICE_NAME='my-agent-abb-app'

export AR_REPO=YOUR_AR_REPO

export GCP_REGION=YOUR_REGION

export GCP_PROJECT=YOUR_PROJECT_ID

gcloud artifacts repositories list

// if not yet available, create AR_REPO
gcloud artifacts repositories create "$AR_REPO" --location="$GCP_REGION" --repository-format=Docker

gcloud auth configure-docker "$GCP_REGION-docker.pkg.dev"

gcloud builds submit --tag "$GCP_REGION-docker.pkg.dev/$GCP_PROJECT/$AR_REPO/$SERVICE_NAME" .

gcloud run deploy "$SERVICE_NAME" --port=8090 --image="$GCP_REGION-docker.pkg.dev/$GCP_PROJECT/$AR_REPO/$SERVICE_NAME" --allow-unauthenticated --region=$GCP_REGION --platform=managed --project=$GCP_PROJECT


### My Agent ADK application

// go in my-agent directory

cd my-agent

export SERVICE_NAME='my-agent-app'

export AR_REPO=YOUR_AR_REPO

export GCP_REGION=YOUR_REGION

export GCP_PROJECT=YOUR_PROJECT_ID

gcloud artifacts repositories list

gcloud artifacts repositories create "$AR_REPO" --location="$GCP_REGION" --repository-format=Docker

gcloud auth configure-docker "$GCP_REGION-docker.pkg.dev"

gcloud builds submit --tag "$GCP_REGION-docker.pkg.dev/$GCP_PROJECT/$AR_REPO/$SERVICE_NAME" .

// set correctly the environment variables  MCP_TOOLBOX_URL, MCP_PROXY_URL (AirBnB MCP service), GOOGLE_API_KEY

gcloud run deploy "$SERVICE_NAME"  --port=8080 --image="$GCP_REGION-docker.pkg.dev/$GCP_PROJECT/$AR_REPO/$SERVICE_NAME"  --allow-unauthenticated --region=$GCP_REGION --platform=managed --project=$GCP_PROJECT --set-env-vars=MCP_PROXY_URL=$MCP_PROXY_URL,MCP_TOOLBOX_URL=$MCP_TOOLBOX_URL,GOOGLE_API_KEY=$GOOGLE_API_KEY

## Take Away <a name="take-away"></a>

**Google ADK (Agent Development Kit)** offers visual and interactive features to develop/debug and showcase Agent(s) by highlighting the tools/request/response for each user query.

It can be combined easily with **MCP servers** (Toolbox for Databases,...) tools to grant AI agents context and standardized interaction with external resources (databases,...) and services

**Google Cloud** technologies such as **Cloud Shell** **Cloud Run** **Cloud Logging** **Cloud Container Registry** serverless (pay per use) enables developers to deploy/manage/monitor easily application(s)/components

