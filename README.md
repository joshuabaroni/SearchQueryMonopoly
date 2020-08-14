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

- I wanted to prove that you can make a monopoly game out of any theme!
- This mod was originally completed as my Computer Science Capstone at [CNU](http://cnu.edu/).
  -> Graduating Class of 2020 Computer Science (BS) Leadership Studies (Minor)
- It was designed to simulate my limited experience with the lifecycle of a fullstack
project and uses a basic Java Backend, JavaScript, Python, and HTML/CSS.

## Project Info

- Basic backend pipeline of the project with Java Servlet frontend. One-click workflow.
- Queries are fed directly into the monopoly board from the temporary file. Google images appear
upon hovering over each tile.
- Can use any common well-known English noun as a theme.

### Deprecated
- I was asked to take down the search query edition portion of this project as per intrepidcoder's request. You can still see the project demo and powerpoint.

## Patch Notes
- Currently this game is not hosted on a server. You will have to pull the repo to your local machine and
change the filespaces in utils.Constants.java/SearchQueryMonopoly.js file to match your File System architecture
to get this mod to work.
- Run [Httpster](https://www.npmjs.com/package/httpster) in the git project's base directory (pipeline) before running the servlet
- If running from the Java Servlet, start at localhost:<your port>/pipeline/QueryServlet

## Conclusion (For capstone)
- I wish I could have had more time to focus on this project this semester. As I was taking many other classes in order to finish on time, my attention was split this semester. However, I plan on continuing work on this project and delving into bits of the remainder of this project. Thank you for checking it out!.
