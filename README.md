# MyAgent for Devpost Agent Development Kit Hackathon with Google Cloud

## Introduction

This project combines **Google Agent Development Kit** Java Application with **ModelContextProtocol** servers (Toolbox, AirBnB)  by SSE.


## Build Application Docker Images

- Install the Perplexity Sonar API MCP Server Codeset on your platform on your local environment with **npm install** command

- Build the image(s) with command : **docker compose build**

MyAgent Docker Modules Images >

![MyAgent Docker Images](https://github.com/lorcie/my-agent/assets/blob/main/my-agent-docker-images.png?raw=true)

## Start Application thru MyAgent docker modules

- start the My Agent with command : **docker compose up*

MyAgent Docker Modules Containers >

![MyAgent Docker Module Containers](https://github.com/lorcie/my-agent/assets/blob/main/my-agent-docker-module-containers.png?raw=true)

- Open or activate  the (from the json exported version) AutoSearch N8N Workflow** on some N8N platform : either locally either on some VPS either on the cloud platform

## Stop Application thru My Agent docker modules

- type **CTRL-C** to force leaving the application thru the opened console

- then stop the MyAgent Application thru the N8N platform with command : **docker compose down**

- list pending  docker modules with command : **docker ps -a**

- remove the pending (ollama) docker modules container id (s) with command  : **docker rm <container_id>**

## Usage

- open the workflow either thru the associate autosearch public url  either locally on the N8N environment

- Click on 'Test Workflow' button 

- Type in a text query and run a query such as : provide details about Perplexity Sonar API Benefits and click (see workflow execution fallback below)

- Upload an Image file to test image analysis (see workflow execution image file analysis below)

- Upload an Image file to test image analysis with very high size more than 5 Mb (see workflow execution image file size exceed below)

- After a while, the workflow executes all nodes (Form Submission / Request Router / Check File Size / MCP Search Text/Image Execute Tool) then returns Perplexity Sonar API results

- Type in a text query such as "Financial Services about payment or invoice" to get response about Stripe financial module (see associated worflow execution details below)


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

![MyAgent Java Codeset](https://github.com/lorcie/my-agent/blob/main/assets/my-agent-docker-codeset.png?raw=true)


