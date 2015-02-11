System resource monitoring
==========================

Flux system resource monitor custom action that can be used to monitor your system on a schedule.

Deploy flux.jar to your local maven repository

```
mvn install:install-file -DgroupId=flux -DartifactId=flux -Dversion=8.0.10 -Dpackaging=jar -Dfile=flux.jar
```

Build the custom action jar using maven

```
mvn install
```

Copy the jar file from target/resource-monitor-action-1.0-SNAPSHOT.jar to $FLUX_DIR/lib directory so it is visible to the Flux engine.

Restart the engine and ops console. You should see the new custom action under notification category in designer.