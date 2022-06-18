# Test task for Alfa-Bank  
A service that accesses the exchange rate service and displays a gif:  
if the exchange rate against USD for today has become higher than yesterday, then a random one of: https://giphy.com/search/rich  
if below - https://giphy.com/search/broke  
# Instructions for deploying the application  
The project uses Docker, so initially you have to build and run the image using docker-compose.

Go to the project directory and then, use:  
```bash  
  docker-compose up  
```  
App will be available at  
`localhost:8080`, if you use Docker Desktop  

## Request for a list of currencies  
`GET api/currencies` 
## Request to receive gif  
`GET api/gif/{currency}`  
Instead of `currency`, you need to specify the currency code  
Example:  
`GET api/gif/RUB`  
`GET api/gif/EUR` 


