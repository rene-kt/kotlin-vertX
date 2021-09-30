# Kotlin with Vert.X

<img src = "https://img.shields.io/badge/vert.x-4.1.4-purple.svg">

This application was generated using http://start.vertx.io

## Install 

- Make sure you have the [Docker](https://docs.docker.com/get-docker/) installed on your machine. 
- Run this command to create a container from the [Docker Image](https://hub.docker.com/repository/docker/renejr3/kotlinvertx) located on Docker Hub:
> Open your terminal, if you're using Windows, make sure to run with the [Windows Power Shell](https://docs.microsoft.com/pt-br/skypeforbusiness/set-up-your-computer-for-windows-powershell/download-and-install-windows-powershell-5-1).

```console
docker run -d -p 8080:8888 renejr3/kotlinvertx
```

- Acess http://localhost:8080/devuser. If you had done everything right, a JSON like this will be returned: 

```json
{
  "status_code" : 403,
  "message" : "You need to create an account first",
  "data" : null
}

```

## Build

Build this image in your own Docker Hub repository.

- First of all, create a account in the [Docker Hub](https://hub.docker.com/)
- After that, create a repository: 
![image](https://user-images.githubusercontent.com/49681380/135510556-35a82b3b-56a5-4f88-b309-210c8e1a39be.png)
- Clone or download this github repository
- Go into the root folder, open your terminal and do the following steps:
  - Login in your console
  
  ```console
  docker login
  ``` 
  
  - Create a image from the Dockerfile
  
  ```console 
  docker build -f Dockerfile -t YOUR_USERNAME/YOUR_REPOSITORY_NAME .
  ```
  
  > Make sure to have the same username and the same name of the repository that you have created.
  
  - Push the image to the Docker Hub: 
  
  ```console
  docker push YOUR_USERNAME/YOUR_REPOSITORY_NAME
  ```
  
  To check if everything goes right, try to run this command and verify the output as the same way as in the **Install**
  
  ```console
  docker run -d -p 8080:8888 YOUR_USERNAME/YOUR_REPOSITORY_NAME
  ```


