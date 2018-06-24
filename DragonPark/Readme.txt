Dragon Parking
==============

$ mvn install

Not quite up and running but it is kind of end-to-end.

Uses an H2 in memory database.

schema.sql creates the database.
data.sql populates it.

Look at RatesTestEngine.java to get a good idea of how it fits together.

RepositoryTest.java is an integration test. 
I had it separate (src/itest/main) but moved it back into the test directory as it was easier then configuring maven.
It now builds with everything else.
