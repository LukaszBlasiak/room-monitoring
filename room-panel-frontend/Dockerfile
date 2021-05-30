FROM node:14.13.1-alpine AS build
WORKDIR /home/room-monitoring-app/node/src
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:1.17.1-alpine
EXPOSE 80
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /home/room-monitoring-app/node/src/dist/room-panel-frontend /usr/share/nginx/html
