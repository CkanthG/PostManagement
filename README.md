# Case Management Kotlin (Java 11) Application Deployment to Minikube using DockerHub.

## Minikube installation.
Follow this link to install the Minikube on your local computer/laptop.
https://minikube.sigs.k8s.io/docs/start/

After successful installation use "minikube start" command to start your minikube.

## MongoDB Installation on minikube cluster.

Follow this link to install MongoDB on minikube.
https://devopscube.com/deploy-mongodb-kubernetes/

## Build Docker image and push to DockerHub by below commands.

docker build -t case-management:version .

docker tag case-management:latest dockerhubuser/case-management:latest

docker push dockerhubuser/case-management:latest

## Application Deployment on Minikube by using below commands.

kubectl apply -f k8s/case-management-pv.yaml

kubectl apply -f k8s/case-management-deployment.yml
