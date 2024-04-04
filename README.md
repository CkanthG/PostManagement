# Post Management Kotlin (Java 11) Application Deployment with MongoDB and RabbitMQ on Minikube using DockerHub.

## Minikube installation.
Follow this link to install the Minikube on your local computer/laptop.
https://minikube.sigs.k8s.io/docs/start/

After successful installation use "minikube start" command to start your minikube.

## MongoDB Installation on minikube cluster.

Follow this link to install MongoDB on minikube.
https://devopscube.com/deploy-mongodb-kubernetes/

## RabbitMQ Installation on minikube cluster.

Follow this link to install RabbitMQ on minikube.
https://bitnami.com/stack/rabbitmq/helm

## Build Docker image and push to DockerHub by below commands.

docker build -t post-management:version .

docker tag post-management:latest dockerhubuser/post-management:latest

docker push dockerhubuser/post-management:latest

## Application Deployment on Minikube by using below commands.

kubectl apply -f k8s/post-management-pv.yaml

kubectl apply -f k8s/post-management-deployment.yml
