# Weather

Proxy backend for requesting weather information from [DarkSky](https://darksky.net).

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

Before starting the web server, you need to configure the environment variables. Copy `environment.sample.sh` to `environment.sh` and fill in your Dark Sky API token.

To start a web server for the application, run:

    lein ring server

## License

Licensed under [MIT license](https://opensource.org/licenses/MIT). Copyright © 2016 Markus Kauppila.
