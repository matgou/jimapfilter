#!/bin/bash

cd $( dirname $0 )

server=$( grep "server=" config.properties | sed "s/server=//" )
port=$( grep "port=" config.properties | sed "s/port=//" )

echo "" | openssl s_client -connect ${server}:${port} -prexit 2>/dev/null | sed -n -e '/BEGIN\ CERTIFICATE/,/END\ CERTIFICATE/ p' > trust.crt

keytool -import -alias imap -file trust.crt -keystore trust.jks

mvn install exec:java -Djavax.net.ssl.trustStore=trust.jks -Dexec.mainClass="info.kapable.app.JImapFilter"

