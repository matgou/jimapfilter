# JImapFilter

JImapFilter est une application console java basé sur jsieve, vous permetant de trier automatiquement votre boite mail depuis un script sieve.

Sieve est un language permetant de créer des scripts pour filtrer les emails (http://sieve.info/). Avec JImapFilter vous allez pouvoir passer des batchs sur vos boites imaps pour trier automatiquements vos emails.

### Version
1.0.0

### Tech

Pour compiler et lancer le batch :
```sh
$ mvn install exec:java -Dexec.mainClass="info.kapable.app.JImapFilter"
```

Si vous avez une erreur SSL vous pouvez importer le certificat dans un keystore avec les commandes suivantes en  :
```sh
$ echo "" | openssl s_client -connect server:port -prexit 2>/dev/null | sed -n -e '/BEGIN\ CERTIFICATE/,/END\ CERTIFICATE/ p' > trust.crt
$ keytool -import -alias imap -file trust.crt -keystore trust.jks
$ mvn install exec:java -Djavax.net.ssl.trustStore=trust.jks -Dexec.mainClass="info.kapable.app.JImapFilter"
```

### Development

Want to contribute? Great! => fork it

### Docker

TODO

### Todos

 - Write Tests
 - Create docker container

License
----

 Apache License
 Version 2.0, January 2004

**Free Software, Hell Yeah!**
