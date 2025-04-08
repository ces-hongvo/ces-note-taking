FROM node:22-alpine

WORKDIR /app

COPY ./note-taking-fe/package.json .

RUN npm install

COPY ./note-taking-fe/. .


CMD [ "npx", "vite"]
