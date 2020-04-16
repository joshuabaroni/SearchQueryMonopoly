# SearchQueryMonopoly

Search Query Monopoly is my mod to the JavaScript monopoly game
originally put out by [IntrepidCoder](https://github.com/intrepidcoder/monopoly).

## Main softwares and languages used

- Tomcat v9.0 (For Java Servlet)
- Httpster (for monopoly board)
- Google Query API (Free Version - 100 Queries/Day)
  -> Looking to upgrade this post-Part 3 if the server sees enough player traffic.
- Java 1.8.1 and Java Servlets
- JavaScript
- HTML
- CSS
- See pom.xml for full list of imported libraries

## Concept explanation

- The idea was based off of the concept that you can make a monopoly game out of any theme!
- This mod was originally completed as my Computer Science Capstone at [CNU](http://cnu.edu/).
  -> Graduating Class of 2020 Computer Science (BS) Leadership Studies (Minor)
- It was designed to simulate my limited experience with the lifecycle of a fullstack
project and uses a basic Java Backend, JavaScript, Python, and HTML/CSS.

## Project Parts

### Part 1 (in-progress, 80% completed), Capstone work

- Basic backend pipeline of the project with Java Servlet frontend. One-click workflow.
- Queries are fed directly into the monopoly board from the temporary file. Google images appear
upon hovering over each tile.
- Can use any common well-known English noun as a theme.

### Part 2 (Expected Summer 2020)

- Active links. Upon clicking on a tile, a new tab will open up to the site that tile originated from.
- Essentially doubles as a re-skinned Google Search, similar to Google Gravity
- Hosted on a docker container/server separate from local machine

### Part 3 (TBA)

- Online Multiplayer functionality added. By sending friend codes to other users, they can
log into your game and play with them.

### Part 4 (TBA)

- Improved Categorization AI using [PyTorch](https://pytorch.org/). Results returned to the user are much more accurate to what
a real monopoly game would look like.

## Patch Notes (v0.0.1)
- Currently this game is not hosted on a server. You will have to pull the repo to your local machine and
change the filespaces in utils.Constants.java/SearchQueryMonopoly.js file to match your File System architecture
to get this mod to work.
- Run [Httpster](https://www.npmjs.com/package/httpster) in the git project's base directory (pipeline) before running the servlet
- If running from the Java Servlet, start at localhost:<your port>/pipeline/QueryServlet
