# Utility Management System

A full-stack web application for utility management, built with React frontend and Spring Boot backend, using Microsoft SQL Server as the database.

## Project Structure

- `frontend/` - React application
- `backend/` - Spring Boot application
- `database/` - Database scripts and ER diagram (to be added)

## Prerequisites

- Java 17 or higher
- Node.js and npm
- Maven
- Microsoft SQL Server (Express, Developer, or Enterprise edition)

## Database Setup

### Microsoft SQL Server Setup

1. **Install SQL Server**:

   - Download and install SQL Server from [Microsoft's official website](https://www.microsoft.com/en-us/sql-server/sql-server-downloads)
   - Or use SQL Server Express for development: https://www.microsoft.com/en-us/download/details.aspx?id=101064

2. **Create Database**:

   - Open SQL Server Management Studio (SSMS) or Azure Data Studio
   - Connect to your SQL Server instance
   - Run the script in `sql-server-setup.sql` to create the database

3. **Configure Connection**:
   - Update `backend/src/main/resources/application.properties` with your SQL Server details:
     ```properties
     spring.datasource.url=jdbc:sqlserver://YOUR_SERVER:1433;databaseName=utility_management;encrypt=false;trustServerCertificate=true
     spring.datasource.username=YOUR_USERNAME
     spring.datasource.password=YOUR_PASSWORD
     ```

### Alternative: Use Docker for SQL Server

```bash
docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=pass123" -p 1433:1433 --name sqlserver --hostname sqlserver -d mcr.microsoft.com/mssql/server:2022-latest
```

## Setup

### Backend

1. Navigate to the `backend` directory:

   ```bash
   cd backend
   ```

2. Update database configuration in `src/main/resources/application.properties`

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### Frontend

1. Navigate to the `frontend` directory:

   ```bash
   cd frontend
   ```

2. Install dependencies:

   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm start
   ```

## Features

- [List features based on ER diagram and requirements]

## Database

- Microsoft SQL Server
- ER diagram: [Attach or describe the ER diagram here]

## Contributing

[Add contribution guidelines]

## License

[Add license information]
