# Stage 1: Build
FROM node:lts-alpine AS builder

WORKDIR /app

# Copy package files and install dependencies without running prepare scripts
COPY package.json ./
COPY package-lock.json ./
RUN npm install --ignore-scripts

# Copy rest of the source code
COPY . ./

# Build the project explicitly
RUN npm run build

# Stage 2: Production
FROM node:lts-alpine
# Set the working directory

# Copy the built application from the builder stage
COPY --from=builder /app/dist ./dist
COPY --from=builder /app/package*.json ./

RUN npm install mcp-proxy  @modelcontextprotocol/sdk --omit=dev --ignore-scripts

# Set the default command to run the MCP server
EXPOSE 3000

# Set the entrypoint to the MCP server
CMD  ["npx", "mcp-proxy", "--port" ,"8090", "--shell",  "node dist/index.js --ignore-robots-txt"]

