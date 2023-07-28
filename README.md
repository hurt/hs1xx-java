# TP-Link Wifi Smart Plug Java Client
This is a java client for TP-Link smart plugs of the HS1xx device family. Currently, only the following 
features are implemented:
* Relay control
* LED control
* Device Reboot
* Reading general system information
* Reading power measurement info

## Usage
### Add dependency
The project is published on [maven central](https://search.maven.org/artifact/net.draal.home/hs1xx).

#### Maven
```xml
<dependency>
  <groupId>net.draal.home</groupId>
  <artifactId>hs1xx</artifactId>
  <version>2.0.0</version>
</dependency>
```

#### Gradle
```groovy
implementation 'net.draal.home:hs1xx:2.0.0'
```

### Usage sample
```java
public class Introduction {
 public static void main(String[] args) {
  // Create facade
  Hs1xxFacade hs1xxFacade = Hs1xxFacade.withDefaultConfiguration();

  // Create device
  Hs1xxDevice device = Hs1xxDevice.builder()
          .host("localhost")
          .build();

  // do stuff
  System.out.println(hs1xxFacade.getPowerStats(device));
 }
}
```

The `withDefaultConfiguration` factory method should provide a configuration
that should fit most needs, but there also is a constructor that may give you control
over its dependencies. Please note that the provided objectMapper is configured in a
way that may be required for reliable communication with the device.

Please note that this library provides some increased log output based on the configured
log threshold. JSON payloads are logged with level `debug`, raw payloads are logged with `trace`.

## Documentation
The javadoc currently is rather incomplete. Will be extended in the future.

## Versioning
This project is versioned with [semver v2](https://semver.org/). That's the plan at least.

## Contributing
Contributions are always welcome via PR. Just bear with me, this is the first OSS project that I
maintain.

The build is a pretty standard gradle build and the project doesn't need any special build
environment (aside from OpenJDK 11):

```
# build the project
./gradlew clean assemble

# Run tests
./gradlew check
```

Reports for tests are written to `build/reports`.

I am trying to keep the number of runtime dependencies of this library reasonably small and limited
to very common packages (guava, apache commons, jackson-databind) which are probably present in most
java projects anyways.

Minimum JDK version is 17 at the moment.

## (probably) FAQ
### Why is device feature X not implemented?
Because I wrote this mostly for power consumption monitoring. Other features can be added
rather easily though. Please note that I only own a couple of HS110's, so if you're interested
in other types of devices, I will have to purchase these first.

### Why is the line coverage threshold set to 100%?
I don't want to give code to other people for usage that wasn't even executed once in tests.
There is still plenty of opportunity for bugs, but there should be at least some minimum standard.

### Why is there such a weird log layout for tests?
Because I got carried away when playing with log4j2 pattern layouts.

### I'm not getting responses when sending a lot of requests
It seems that the device has 4 listeners for incoming requests. You will have to be careful
with concurrent requests to stay below that limit.

## Acknowledgements
Special thanks to the folks at SoftScheck for their work in reverse engineering
 the protocol. See their story in [their blog](https://www.softscheck.com/en/reverse-engineering-tp-link-hs110/).
This library is largely based on their work published on 
[github/tplink-smartplug](https://github.com/softScheck/tplink-smartplug).