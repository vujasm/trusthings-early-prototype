A prototype implementation of a COMPOSE trust computation engine. The engine computes a trust score for objects and services by evaluating their trust-related characteristics in regards to the trust expectations (criteria). Trust-related characteristics and trust expectations are formally described using the trust semantic vocabulary. 
An engine is available as a java archive (trusthings-service-0.2.1-jar-with-dependencies.jar) and as a VertX web module (trusthings-vertx-0.2.1-mod.zip). mod.json is a Vertx configuration file, with  'host' and 'port' properties, that must be configured before deploying the module. After installing Vert.x runtime on a machine, a command: $ vertx runzip trusthings-vertx-0.0.1-mod.zip -conf mod.json deploys a module and starts up a server.
See examples/TrustEngineExample.java for �hello world� example of how to use the engine.