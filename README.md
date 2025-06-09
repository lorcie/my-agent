# MyAgent for Devpost Agent Development Kit Hackathon with Google Cloud

## Introduction

This project combines **Google Agent Development Kit** Java Application with **ModelContextProtocol** servers (Toolbox, AirBnB)  by SSE.


## Build Application Docker Images

- Install the Perplexity Sonar API MCP Server Codeset on your platform on your local environment with **npm install** command

- Build the image(s) with command : **docker compose build**

MyAgent Docker Modules Images >

![MyAgent Docker Images](https://github.com/lorcie/my-agent/blob/main/assets/my-agent-docker-images.png?raw=true)

## Start Application thru MyAgent docker modules

- start the My Agent with command : **docker compose up*

MyAgent Docker Modules Containers >

![MyAgent Docker Module Containers](https://github.com/lorcie/my-agent/blob/main/assets/my-agent-docker-containers.png?raw=true)

- Open or activate  the (from the json exported version) AutoSearch N8N Workflow** on some N8N platform : either locally either on some VPS either on the cloud platform

## Stop Application thru My Agent docker modules

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


