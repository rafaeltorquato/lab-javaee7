FROM jboss/wildfly:21.0.1.Final
ADD ear/target/javaee7-template.ear /opt/jboss/wildfly/standalone/deployments/