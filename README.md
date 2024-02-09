# League of Legends API Server

## Major Sections

### Data Updater
The data updater is a separate Java program from the main server
that only updates the items json and images files with the
current data from RIOT API.

The data updater should:
- Get latest item json data
- Parse and locally store only relevant information
- Compress png files to lessen bandwidth out of server and for user experience when fetching data from our server.

### Main Server
The server should always be active so users are able to access
information whenever they want to.
The server should:
- Allow front-end to receive item information such as
image, name, and description from API endpoint
- Authenticate and login users
  - Store user information in database
  - Cache user login in browser for fewer requests to server
- Allow user to search for players
  - Player pages will show profile image, name, and level
  - Show recent games
    - All players in that game (name and champion image) and what
    team they were in
    - Win or lose for player