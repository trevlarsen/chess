# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```


## Sequence Diagram

View the sequence diagram for this project [here](https://sequencediagram.org/index.html?presentationMode=readOnly&shrinkToFit=true#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAFZTp+1AgBXbDAAxGjAVJYwAEoo9kiqFnJIEGhBAO4AFkhgYoiopAC0AHzklDRQAFwwANoACgDyZAAqALowAPS+BlAAOmgA3gBEHZQhALYo-WX9-QA0MP246inQHBNTs-0oI8BICKtzAL6YwqUwhazsXJQVA0NQo+OTM3MLqktQK4-rm9u7j4dsnG4sDOx1EFSg0ViWSgAAoojE4pQogBHXxqMAASlMwAQzAAqp17jAcRDgBxwvIANboTAwOnFEQqU5FWTyJQqdQVewoMAEygw273LH0mTadlqVTMow6CoAMSQnBgfKgrOAMB04UFwDGtPpqvF6lOIJKYKVnVVMAAZn5OLq6aCmXkzgDLuUYL05mN1MBuRM5gBRKA+N0klBkinAalofowQ4il1Ao1FY5XKoAFicAGYWh7+l7VD6HgGg9AKqHwzAqegY3H6SngWccuYKhnM+7PRLC37+oHg2WEKTyZXI9XY2ZbZgccwAJJoKg4pAcGAQ1HomAwlAuewuWYjWKqBWOS2oBAcVTCusmx3Oi5A64d72+io9ktuvRLldouI1u3nQGUJMGVKCpKgzJwcwfAsn2LPt1TJZcUFXb8xxFetAKbDAWycJx2zzTtoJfWD3wQpCwB-dAOGxXFyF8ZQJUtXwEAQhF4gIJJfwdQwzn1OjORgbleU6AVCW1FALzpHiOUlM5jDlBUl2VC0NRgLUdRFSSJUAziKjQRiEA4q8uJZMVeNUCoQFJLJlWEyhVXE0U2VMqVZJgBQOAU81tAMxkjIcxRTPMyyUAUXwwDSGFgFCtI7N-DTDRkmVXPc1yotVbyqFEQCE1TeEoSRNRGKweNbwAp1kxKVMbhEsZViefpIrChoICjWra3tCqGyKDCwAqAAmbD3UGaqHjWOYGrSJqWr+cdKMnajZ2hEImM6E5KGDdLMrKv9XXvPDHyLQjS3XDg1As4g2OSCBLRgNboAxH9iv-TqgNTSo3GwiC9qgg7eyOmETtUM7sAumArpu197pQy9qFKxtkGbGB3pw3N8y7Z9frdf7Tqgc7Eku67bqgSHDgombPEwbw-ACQJoHYbkYAAGQgGJkkCdJMmyeH8nKmG3RqepmjaAx1DxnoquGUTavWF43g+KZDjQm8nuuIaJZqz5nn0V5llazBsuBHmMpUCoEGZhUYSZlmUS-TEqPxEIougJAAC8UCXGEUh8BwVOG1oZeWezOKlOKzP4nlrNUsTYpMqTnMS+VFUU7R1U1Ybo8c2Otu0s1bOTtAIGYa1fEVaAYAVedkCXf33g268in13bUYIjGKmL8anddlYobpfWtI6kCMwARk+puftfVuHbCju3Ye6GTi27qsMH3DR+7FuVMntJp67kmJynGjTIYpjTZZ2vfJDrlw6EyP7JDuO5MTzz5BTn21bEdSY80rPDIqJPn6LxUFwYDVyXFsMAIA0hqDPsHT+fELJhiyCFMKEVUraFvrA6SRQXJuSXEg6KXlUKGSyiVN0lsFRRFUIVX8vdv68xVpHKWY0oqTWrNNQhvN0Jcx6jAfqyM5gMI1vVZhzVWFTDHKTOaM4MAS2Wh1cG612FGy4krHaK98Jj1gljQGONgZ41BgTCGs8e4kL7nQqoSMR7qLXuPY62NcZJH0fIu6RiXrPUXojAaKMrHoxsVooGIMwaE2JmTCRXgfD+CCN4FA6BGbMz8MwNmGQsiYG6lKesIFpD+gZv6Bo-oWitGFgeJIPRxosLQEcORKi7yDVKSI6M00aGG1NCfeJKDGp1KxJIpUm9t7rnLguJcpc9wi29rUqMgciHcQwZfMAeC2kTQ6enfymcsHx3kilMKSlwhjJpB-DOX9jQ+QqHgi0+dC42kooozaVTKqQTRjBI6bdHY407i4mhhzgLpicMPNR+1rGwSeVPF5M9u6uM4bkbhQ9fnfX+Y8npwKd5ky6WQWiUkj4wBaaFaBUz9l8QEnMnZaB0G4swdKB+uDUHP2UoSpZBppIfNNCc5OADLlz02sZElFQTrcEQVFeZZTiXLK-qsioUQRgQBoBs-B8hsU3LdCi0y1CTG0M+ZUXhOY2pgoXlwvqnjd6zX3gtGRMAVoAUJrK+uJDG7eIeZjAG-i9GBMMaC95htXoWOhfcw6dq7G6IcU64MwTFGwy6jqjxfCvpevXn4nRASDGBvIraUJFNwnUwhEuBmUIYAAHFRKSkSRzFJXC0n9yqNmnJ+T7CiRKcIqMFSOFyt2oS7s-R-jKqacbDFUJc1en5YsrpeJ4Uuzdn0ucAzQawGGQeUZtb0ATJ8jAzlYdZl8sJYKul98YAJwpZs5O1LZ3lL2UK+KDLO1Mufmcq0FyLXbWqV4v5PiAVDteS65VHzXpD0sQ+21E924IpcYrOGEKl6eubjYwFW9-3iL3tRBVaLGJdriAhCwqAK7Yo5ce0O+LV0HvXU5BK5KpVbOJAe2l+HT0oGOZStULL0N+TpTMhQCAEA9rUDCPDKyyVbvWTiJiVavRkczhRiomakNg341Aq5ddb23P6BJ1QfpKguGUy0TVrrXG7Xk4p5TLhVPpRDfAMNvDcJaZAjpvTEjDXSLuDiE1cjzVSeUZa5WoGNF-XtbGx18bnGvqeqY1VHr70wsfe531canFEwA5UoDCMkaudhT67R9j8YReCaTZNlMImBGwL4KA2BuDwCCjm0SqQkmcwhSWsx-NGiVurZvMpYs5OiQAHKS2morZzqjczNufHVCTrX1byz1u2oCpp4FyBQKxvtUZZj9dEp0-eg6-3Dvdv0yuE6YBTsPCR9p4y6MX2XQS3DgnhVce3URvd2zSNHo3Sqxl1GYCXto454hLmgtRvA8+kFam31ur5p++LIW3QQe3lFhtMXMIwChe9sDT7lsvv1XbA+8G+MlfGxdfb0zDs4d23Ok78URXccVOetU+7ceHr1Bg-z93d3-2vS9nFmHAoIMm6JGEc2xgcdO9g5KaAUApH4qJfH9KO2UZM6Jac0g-SD16pmNMoKg5bQbuLsYkvpey-lz9vzKrZMSbVxUGXcuFfRdDcBnhni5h66lwbjX0GDXUQAELwU-GuGEW3vYc6jgzxtgOf1wQ-IhG2bzfsaa+eBX33qKjERd8hTVgHTcIzAhH9e0fA-okTfbqRi1bOmtgA5tl0nlcw7c4lh1-rvORd866fz7qPrJ98R55Ljiglg-nhD7hcXi8JYqDGpvAafOI4y6moIlgUDMYgALgAUhABUxWxhBB0AgUAlIi0VffXzaoeJBatAkzW8njX8vAFH1AOAEBTZQAmOsK3EwFaVM63eph5PL-PCX8f0-5+-RX4l9btt2vRcVAACsZ80AptCVZhD838z9oBZtv8FtqIltnkVtR0K5FwNt3dHA11Mcl1sNkFMCbt8NCdzsScX4aV8ChN-8LsL0C4r1i5WV2oF0lcrV684dECEclVtd18B5vkv1gs-cQcoM48TdDMzdoc7lYc4V4dvskV95WNHtqDnsC9z4sccDwo8DKcSVN0iCHsycFk60yCDkKDiCFD6ClFF0mdl0psrcucCczt1lZDlIrdhdqdO1ZCnt6dFDXsusxCS8o9nc09Y92Dq8dd-s68u8gdfCA9SJW8DN3Ek8wi-dU8oi7ckc4N6IEMgCFR61TDGcGNsdcDjt9CbCXItDadScrtycnC7sz0HtjDRtHQMNciBJLDYDKjCC7CSsHDv9KjhM59DBajFcGiAoYAMiQC2cIDKB39oDBdVdpBrDSUecq5X8AJIBpj351DMNnCxdUj1Ab1ldtiNBHogjOCqh1V3RBDwcE9IdjNehEcukmNBwIw9CPDGC3tvDu9iQBwwwhwqxowq9ExgiuDsxmCjpyxviRxfjzi29LjIUsxgSQxPiKwfiM8kcjUbNZEOF88TDrl79ZNV5wjbEks-UUsW8-iDN0lzFQi3j8Te8iTm9nVISYiw1O8qS-caTwsSTB8JwwkqZIkoAj9DMwxYBgBsB8tCAEgHEC1klUljjKhMlslcl8ljAsjSocS3RrY1wViQBuA8AjBtA9ADBhs-86ixctTBTVR9SUB2Msj2V6MhjTS8AmMEBVQrS74CMZAx8eRDBeMEIQBlhJQbIoBVAYCvRZhCVzwUjUUJRuiKD9jdimDYzDj-iZTTibj9M3EjM9VpD5prMlo7MMTXxozjTrVv1I8CSy9iT6S4zXjI1xDS9PNy9Utg8OC-sQJAsWTSy2SvNGzjcLjhDYsLcayfCyz6yKyE1kjQkgA)
