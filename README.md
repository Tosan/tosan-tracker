## Tosan Tracker Spring Boot Starter

This project provides a Spring Boot Starter for logging incoming and outgoing requests and responses.
It intercepts these communications and records them in your database, providing a robust way to track and audit
application interactions.

### Usage

To install the library, add the following dependency to your `pom.xml` file:

```xml

<dependency>
    <groupId>com.tosan.tools.tracker</groupId>
    <artifactId>tosan-tracker-spring-boot-starter</artifactId>
    <version>latest-version</version>
</dependency>
```

### Configuration and Setup

1. **Extend Entity Interfaces**: Create your own implementations of these three entities:

- `ServiceEntity` : services in your project that you want to track.
- `RequestTrackEntity` : incoming requests to your services.
- `ResponseTrackEntity`: outgoing responses from your services.

  Apply these implementations to your desired JPA entities to enable automatic logging. For example:

```java

@Entity
public class MyServiceEntity implements ServiceEntity {

    @Id
    private Long id;

    private String serviceName;

    // Add any other custom fields here

    // Constructors, getters, setters, and other methods can be added as needed
}

@Entity
public class MyRequestTrackEntity implements RequestTrackEntity {

    @Id
    private Long id;

    private Date requestDate;

    private MyServiceEntity service;

    private Map<String, Object> trackData;

    // Add any other custom fields here

    // Constructors, getters, setters, and other methods can be added as needed
}

@Entity
public class MyResponseTrackEntity implements ResponseTrackEntity {

    @Id
    private Long id;

    private Date requestDate;

    private Date responseDate;

    private String exceptionName;

    private TrackType trackType;

    private MyRequestTrackEntity requestTrack;

    private Map<String, Object> trackData;

    // Add any other custom fields here

    // Constructors, getters, setters, and other methods can be added as needed
}
```

2. **Implement TrackingDataProvider**:
   Implement the `TrackingDataProvider` interface to gain full control over the data being logged.

```java
public class MyTrackingDataProvider implements TrackingDataProvider {

    // Return your custom RequestTrackEntity and fill the custom fields
    @Override
    public RequestTrackEntity getRequestTrack(Object[] args, String[] parameterNames) {
        MyRequestTrackEntity requestTrack = new MyRequestTrackEntity();
        requestTrack.setClientAddress("1.1.1.1");
        // Fill any other custom fields
        return requestTrack;
    }

    // Return your custom ResponseTrackEntity and fill the custom fields
    @Override
    public ResponseTrackEntity getResponseTrack(Object arg, RequestTrackEntity requestTrack) {
        MyResponseTrackEntity responseTrack = new MyResponseTrackEntity();
        // Fill the custom fields
        return responseTrack;
    }

    // Return your custom ResponseTrackEntity in case an exception occurs
    @Override
    public ResponseTrackEntity getExceptionTrack(Throwable exception, RequestTrackEntity requestTrack) {
        MyResponseTrackEntity responseTrack = new MyResponseTrackEntity();
        String exceptionName = "myException";
        exceptionTrack.setExceptionName(exceptionName);
        // Fill the custom fields
        return exceptionTrack;
    }

    // Return your custom ServiceEntity and fill the custom fields
    @Override
    public ServiceEntity getService(String serviceName) {
        // Find the service by serviceName and return it. for example:
        MyServiceEntity service = serviceRepository.findByName(serviceName);
        return service;
    }
}
```

3. **Add tracker annotation to services**:
   If you wish to track incoming and outgoing requests and responses for a service (method), you should annotate it with
   @Tracking, as shown below:

```java

@Tracking
public MyResponse serviceToBeTracked(MyRequest request) {
    return myResponse;
}
```

To customize tracking behavior and exclude the entire request or response string from being logged as 'trackData', you
can set the trackRequest and trackResponse fields of the @Tracking annotation to false. By default, both are set to
true. Here's how you can change them:

```java

@Tracking(trackRequest = false, trackResponse = false)
public MyResponse serviceToBeTracked(MyRequest request) {
    return myResponse;
}
```

If you prefer not to log certain fields in your request and response DTOs, you can annotate those fields with
@SkipTracking. Here's an example:

```java
public class MyRequest {
    @SkipTracking
    private String fieldToSkipTracking;

    private String fieldToBeTracked;
    // Other fields
}

public class MyResponse {
    @SkipTracking
    private String fieldToSkipTracking;

    private String fieldToBeTracked;
    // Other fields
}
```

4. **Optional Configuration**:

- create a configuration class
- `Masking sensitive data`: If you need to mask sensitive data, create a bean as follows and then create
  a `TrackConfig` bean and set the secureParameters:

```java

@Bean
public Set<SecureParameter> secureParameters() {
    Set<SecureParameter> secureParameters = new HashSet<>();
    secureParameters.add(new SecureParameter("password", MaskType.COMPLETE));
    secureParameters.add(new SecureParameter("anyOtherSensitiveFieldName", MaskType.SEMI));
    // Add more secure parameters as needed
    return secureParameters;
}

@Bean
public TrackConfig trackConfig() {
    TrackConfig trackConfig = new TrackConfig();
    trackConfig.setSecureParameters(trackerSecureParameters);
    return trackConfig;
}
```

- `Default Exception Name` : If you prefer not to specify the exception name within the getExceptionTrack method of the
  TrackingDataProvider class, you have the option to define a default exception name as follows:

```java

public static final String DEFAULT_EXCEPTION_NAME = "myException";

@Bean
public TrackConfig trackConfig() {
    TrackConfig trackConfig = new TrackConfig();
    trackConfig.setServiceExceptionName(DEFAULT_EXCEPTION_NAME);
    return trackConfig;
}

```

The complete configuration file should look like this:

```java

@Configuration
public class TrackerCustomConfiguration {

    public static final String DEFAULT_EXCEPTION_NAME = "myException";

    private final Set<SecureParameter> trackerSecureParameters = Set.of(
            new SecureParameter("password", MaskType.COMPLETE),
            new SecureParameter("username", MaskType.SEMI)
            // Add more secure parameters as needed
    );

    @Bean
    public TrackConfig trackConfig() {
        TrackConfig trackConfig = new TrackConfig();
        trackConfig.setServiceExceptionName(DEFAULT_EXCEPTION_NAME);
        trackConfig.setSecureParameters(trackerSecureParameters);
        return trackConfig;
    }
}

```

### Sample Project

You can find a sample project in tosan-tracker-sample module

### Prerequisites

This Library requires java version 17 or above and spring boot version 3 and above.

## Contributing

Any contribution is greatly appreciated. If you have a suggestion that would make this project better, please fork the
repo and create a pull request.
You can also simply open an issue with the tag "enhancement".

## License

The source files in this repository are available under the [Apache License Version 2.0](./LICENSE.txt).
