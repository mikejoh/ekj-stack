FROM praqma/jenkins4casc:latest

ARG JAVA_OPTS
ENV JAVA_OPTS "-Djenkins.CLI.disabled=true ${JAVA_OPTS:-}"

COPY plugins.txt /usr/share/jenkins/ref/plugins_extra.txt
RUN xargs /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins_extra.txt

COPY groovy/hooks/*.groovy /usr/share/jenkins/ref/init.groovy.d/
COPY groovy/jobs/*.groovy /var/jenkins_home/groovy/jobs/