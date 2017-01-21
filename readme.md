# Weather

Super simple proxy backend for requesting weather information from [DarkSky](https://darksky.net).

## Prerequisites

You will need [Leiningen](https://github.com/technomancy/leiningen) 2.0.0 or above installed.

## Running

Before starting the web server, you need to configure the environment variables. Copy `environment.sample.sh` to `environment.sh` and fill in your Dark Sky API token.

To start the web server:

    run.sh

The `run.sh` script will source all the needed environtment variables and
run the server using lein.

# Testing

For testing, it's needed to mock the used environment variables. These mocked
variables are defined in `environment-test.sh`. `test.sh` runs the unit test
suite using the mocked environment values.

## Provided API

`GET "/api/current" [latitude longitude unit-system language]`
  - Get the current weather at the location using the given unit system and language.

## License

Copyright © 2016 Markus Kauppila. Licensed under [MIT license](https://opensource.org/licenses/MIT).
