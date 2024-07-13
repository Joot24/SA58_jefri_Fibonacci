# SA58_jefri_Fibonacci

## Project Description
Counts the minimum number of each selected coin denominations required to make up a target amount.

## Running Locally

### Prerequisites

- JDK 17
- Maven

### Steps

1. **Build the Spring Boot Application**
   In the Spring Boot project's root directory
   ```sh
   mvn clean package
   ```

2. **Build Spring Boot Application Docker Image**
   ```sh
   docker build -t <your-image-name>
   ```

3. **Build ReactJS Application Docker Image**
   Navigate to the ReactJS project's root directory (\frontend\app)
   ```sh
   docker build -t <your-image-name>
   ```

4. **Run the Application**<br>
   ```sh
   docker-compose up
   ```

5. **Access the Application**<br>
   Open your browser and go to [http://localhost:80](http://localhost:80).

