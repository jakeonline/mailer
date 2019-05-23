# mailer

###Installation
1. Clone repository
2. Note the use of Lombok. Download your corresponding IDE plugin.
3. Due to the pending Improvement #4 below, I have to provide you my API_KEY string for MailGunEmailProvider.java and SendGridEmailProvider.java
4. Build the artifact mailer-1.0-SNAPSHOT.jar
```
    mvn -U clean install
```
5. Alternatively, source the artifact at https://s3-ap-southeast-2.amazonaws.com/jodsinadamailer/mailer-1.0-SNAPSHOT.jar

###Running the application
1. From the command line, go to
```
java -jar mailer-1.0-SNAPSHOT.jar
```
2. Using your browser or another HTTP client (e.g. Postman), do a GET request of http://localhost:8080/v1/email for the JSON request format of the payload for POST requests
3. Alternatively, this is hosted in http://13.55.2.204:8080/v1/email

### Improvements
1. Retry mechanism. Implement logic to retry (to a certain time limit) the synchronous API calls to email providers. Further improvement in #6.
2. Error message to field mapping. Split JSON validation message to have a key for the error message and a key for the field that error message applies to. At the moment, it's in one line separated by a pipe.
3. Support email attachments 
4. Keystore implementation. At the moment, the secrets (i.e. API keys for each email provider) are not committed to the repository. A keystore is now available but need to work on getting the entries.
5. Rethink own JSON response fields to reconcile with the Spring boot opinion on the error response fields.
6. Rethink of an asynchronous design to probably deal with slow APIs of providers. Will involve persistence.