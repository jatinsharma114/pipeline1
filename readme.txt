Docker image need to build 

- jatinsharma14@jatinsh:~$ docker build -t jatinsharma14/pipelineimg .
- jatinsharma14@jatinsh:~$ docker run -it -p 8082:8082 jatinsharma14/pipelineimg

Docker image. and Git repo up to date. 

Please finish the CI part before this.

--------------------------------------------------------------------------------------------------------------------
CD part any changes in the Git repo will affect the deployment. 
- No need to apply the deployment.yml files manually for any <image> change in the repo.
--------------------------------------------------------------------------------------------------------------------

Installing ArgoCD on Minikube and deploying a test application - Local Machine 

URL : https://medium.com/@mehmetodabashi/installing-argocd-on-minikube-and-deploying-a-test-application-caa68ec55fbf

- minikube start --driver=docker
- kubectl create ns argocd
- kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/v2.5.8/manifests/install.yaml
( Make sure you need to apply this command where your install.yaml file is located in the argocd folcer )
- kubectl get all -n argocd
- kubectl port-forward svc/argocd-server -n argocd 8085:443
( when you run in the local you can forward your port on which you are willing to see the UI of ArgoCd )

You can forward the port and access the ArgoCd by hitting the : localhost: 

**** ( Change the service to load balancer for the Cloud platform and for local you can change to node port to access the ArgoCd server )

      kubectl edit svc argocd-server -n argocd 
      = Change to NodePort mode. 
      minikube service list -n argocd
      minikube service argocd-server -n argocd 

      --- hit the url of http or https to go to ArgoCd GUI 


      Username : admin
      Password : 

      kubectl get secret -n argocd 
      kubectl edit secret ....admin-secret -n srgocd 

      = Decode the password : 
      echo <your password> | base64 --decode 

      Password : enter NOW 


      You are entered to ArgoCd. 
======================================================================================================================
- To run the target port using minikube :
jatinsharma14@jatinsh:~$ minikube ip
192.168.49.2

jatinsharma14@jatinsh:~$ kubectl get service 
NAME              TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)        AGE
kubernetes        ClusterIP   10.96.0.1       <none>        443/TCP        7h22m
spring-boot-app   NodePort    10.187.128.26   <none>        80:31961/TCP   76m

>> When type : NodePort 
Now to access the container inside the Pod you can hit the 
<minikubeIp>:<servicePort> 
192.168.49.2:31961 

>> When type : LoadBalancer 
loadbancerHostname/home 


----------------------------------------------------------------------------------------------------------------------

command to see the GUI for other component. !

minikube addons enable metrics-server
minikube dashboard
