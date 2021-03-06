# mailer

###Installation
1. Clone repository at https://github.com/jakeonline/mailer
2. Open module on your IDE. Note the use of Lombok, hence, you may need to download your corresponding Lombok IDE plugin.
3. Enter API_KEY String values, if you plan to build the application in your local machine. Due to the pending Improvement #4 below, I have to provide you my API_KEY string for MailGunEmailProvider.java and SendGridEmailProvider.java
4. Build the artifact. Output is mailer-1.0-SNAPSHOT.jar
```
    mvn -U clean install
```
5. Alternatively, source the artifact from https://s3-ap-southeast-2.amazonaws.com/jodsinadamailer/mailer-1.0-SNAPSHOT.jar

###Running and testing the application
#### Locally
1. From the command line, go to the location of the artifact and run the command.
```
java -jar mailer-1.0-SNAPSHOT.jar
```
2. Using your browser or another HTTP client (e.g. Postman), do a GET request of http://localhost:8080/v1/email to get the the JSON request below. This is the payload format for POST requests.
```
{
  "from": "jacob@odsinada.com",
  "to": [
    "jake@odsinada.com",
    "abigail@odsinada.com"
  ],
  "cc": [
    "jake@odsinada.com",
    "abigail@odsinada.com"
  ],
  "bcc": [
    "jake@odsinada.com",
    "abigail@odsinada.com"
  ],
  "subject": "Greetings!",
  "body": "Hello World!"
}
```
3. Alternatively, this is hosted in http://54.206.114.22:8080/v1/email

### Improvements
1. Retry mechanism. Implement logic to retry (to a certain time limit) the synchronous API calls to email providers. Further improvement in #6.
2. Error message to field mapping. Split JSON validation message to have a key for the error message and a key for the field that error message applies to. At the moment, it's in one line separated by a pipe.
3. Support email attachments 
4. Keystore implementation. At the moment, the secrets (i.e. API keys for each email provider) are not committed to the repository. A keystore is now available but need to work on getting the entries.
5. Rethink own JSON response fields to reconcile with the Spring boot opinion on the error response fields.
6. Rethink of an asynchronous design to probably deal with slow APIs of providers. Will involve persistence.