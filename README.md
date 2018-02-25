# Malaria Plant DB

### The database of medicinal plants against malaria

## JHipster 
Malaria Plant DB was generated using the JHipster v2.23 stack, you can find documentation and help at this
[JHipster archive documentation][http://www.jhipster.tech/documentation-archive/v2.23.0/].

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools (like
[Bower][] and [BrowserSync][]). You will only need to run this command when dependencies change in package.json.

    npm install

We use [Grunt][] as our build system. Install the grunt command-line tool globally with:

    npm install -g grunt-cli

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

    mvn
    grunt

Bower is used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in `bower.json`. You can also run `bower update` and `bower install` to manage dependencies.
Add the `-h` flag on any command to see how you can use it. For example, `bower update -h`.

## Building for production

To optimize the malariaplantdb client for production, run:

    mvn -Pprod clean package

This will concatenate and minify CSS and JavaScript files. It will also modify `index.html` so it references
these new files.

To ensure everything worked, run:

    java -jar target/*.war --spring.profiles.active=prod

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

## Use in development

You need both a [PostgreSQL][] server and an [Elasticsearch][] engine to run the web server.

To have the database and elasticsearch set quicky, it's possible to use the same docker image than in production. For 
this, you can go to the directory `docker/test` and launch separately the two services :

    docker-compose up postgres
    docker-compose up elasticsearch
    
Then, to launch the web server you can use the maven plugin by taping :
    
    mvn spring-boot:run

But the quicker is to use you IDE to launch the `nc.ird.malariaplantdb.Application` main class. By default, the `dev` 
profile is set. You speed up the startup, you can also add the `fast` profile (`--spring.profiles.active=dev,fast` in a 
command line).

If the database schema is not created, the web application will init it at startup. However, it will not the case with 
the `fast` profile activated. So think about to disable it, the first time you start the web application.

To keep you web navigator synchronized with the last version on the web client, you can use the [BrowserSync][] facility
by launching :

    grunt serve
    
Then you will need to connect you at the web application by a different port : http://localhost:3000

## From development to production

   mvn -Pprod clean package
   cp target/malariaplantdb-1.0.war docker/test/malariaplantdb-web/malariaplantdb.war
   cd docker/test
   docker-compose build
   
   mkdir /data
   mkdir /data/postgres -R
   mkdir /data/es-data
   sudo chown 999:999 /data/es-data/
     
[JHipster]: https://jhipster.gitub.io/
[Node.js]: https://nodejs.org/
[Bower]: http://bower.io/
[Grunt]: http://gruntjs.com/
[BrowserSync]: http://www.browsersync.io/
[PostgreSQL]: https://www.postgresql.org/
[Elasticsearch]: https://www.elastic.co/
