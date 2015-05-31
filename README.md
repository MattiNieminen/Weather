# clj-web-example

A sample project with browser-based user interface written using Clojure,
ClojureScript and MongoDB. Uses Vagrant for development environment and
Docker for production deployment.

The app itself is ugly and does nothing interesting. However, the code is meant
to be a starting point or a pony for developing new apps. The Git log is even
more important than the code itself!

Why not develop a Leiningen template? Because the code contains example
business logic that should not be copied to new projects. Also, Vagrant and
Docker configurations should probably not be packaged into Leiningen templates.

## Usage

1. Clone the repository
2. Install VirtualBox
3. Install Vagrant
4. Explore the code by using the following commands in the cloned directory.

```bash
# Download, install and run a virtualization environment for MongoDB.
vagrant up

# Start the application with figwheel
lein develop

# Build an uberjar
lein build

# Run test
lein test
```

## Using this example as a starting point

1. Fork the repository
2. Install Counterclockwise
3. Use file search to replace clj-web-example with [your-project-name] in the
whole project.
4. Use Refactor -> Rename to clj-web-example namespace. Tick
"Rename subpackages"
5. Use Counterclockwise or some other tool to replace clj-web-example to
[your-project-name]
6. Review project.clj
7. Start editing the app itself (routes, api functions, reagent views,
  localizations etc.)

## License

Copyright © 2015 Matti Nieminen

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
