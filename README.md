# Monpoke
Saved repo for take home test for Focused Labs "Monpoke" exercise. Saving in a private repo for reflection later.

## Usage
This was developed with Java 8 and Maven 3.6.3, which must be installed to run this program.
To build out the module and run the tests, use the command:

`mvn clean install`

Once this is run, use the following command to run and manually input rules until the game is finished:

`mvn exec:java -Dexec.mainClass="com.monpoke.GameRunner"`


Or use the following command to feed in an input file with commands:

`mvn exec:java -Dexec.mainClass="com.monpoke.GameRunner" -Dexec.args="<filepath>"`

Any argument after the first will be ignored.