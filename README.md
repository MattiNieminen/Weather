# Weather

An application written in Clojure/ClojureScript that shows the weather from
Wunderground. Uses Vagrant for setting up MongoDB for development and Docker
for production deployment.

## Setting up for the development

1. Install Git
2. Install VirtualBox
3. Install Vagrant
4. Install Leiningen
5. Clone the repository to the server
6. Run the following commands in the cloned directory:

```bash
# Download, install and run a virtualization environment for MongoDB.
# Takes a while... make sure that Intel Virtualization techonology VT-d is on
vagrant up

# In development, the above command starts MongoDB inside a Docker container
# running inside a proxy virtual machine. The application should be run at
# the host machine by running:
lein develop

# The above command calls ClojureScript compiler, starts Figwheel and the
# application itself. If you need an uberjar, then run:
lein build

# Tests are run normally.
lein test
```

## Installation to production (uses Docker, so Linux-servers only)

1. Install Git
2. Install Docker
3. Install Leiningen (and Java)
3. As a user that can sudo:

```bash
git clone https://github.com/MattiNieminen/weather.git
cd weather
# NOW: change the apikey in app/resources/config.edn
chmod a+x os/dockerize.sh
./os/dockerize.sh
```

Let me know if you need instructions for production Windows-servers!

## License

Copyright © 2015 Matti Nieminen

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
