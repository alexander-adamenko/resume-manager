## Build stage

# pull official base image
FROM node:13.12.0-alpine AS build

# set working directory
WORKDIR /app

# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH

# install app dependencies
COPY package.json ./
COPY package-lock.json ./
RUN npm ci
RUN npm install react-scripts@4.0.1 -g

# add app
COPY . ./

# build app to static files
RUN npm run build


## Production stage
FROM nginx:stable-alpine

# copy from previous stage above
COPY --from=build /app/build /usr/share/nginx/html

# copy nginx config, apparently this is needed if React Router is being used
COPY nginx/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
