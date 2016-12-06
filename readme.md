# Weather

Proxy backend for requesting weather information from [DarkSky](https://darksky.net).

## Prerequisites

You will need [Leiningen](https://github.com/technomancy/leiningen) 2.0.0 or above installed.

## Running

Before starting the web server, you need to configure the environment variables. Copy `environment.sample.sh` to `environment.sh` and fill in your Dark Sky API token.

TODO: Explain all the options that need/can be defined in `environment.sh`

To start the web server and source all the needed environment variables while
doing that, run:

    run.sh

## License

Licensed under [MIT license](https://opensource.org/licenses/MIT). Copyright © 2016 Markus Kauppila.
