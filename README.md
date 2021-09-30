# Kotlin with Vert.X

<img src = "https://img.shields.io/badge/vert.x-4.1.4-purple.svg">

This application was generated using http://start.vertx.io


## Install 

- Make sure you have the [Docker](https://docs.docker.com/get-docker/) installed on your machine. 
- Run this command to create a container from the [Docker Image](https://hub.docker.com/repository/docker/renejr3/kotlinvertx) located on Docker Hub:
> Open your terminal, if you're using Windows, make sure to run with the [Windows Power Shell](https://docs.microsoft.com/pt-br/skypeforbusiness/set-up-your-computer-for-windows-powershell/download-and-install-windows-powershell-5-1).

```bash
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
