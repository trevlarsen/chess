# Chess Multiplayer Server

This project is a multiplayer chess server program that facilitates real-time gameplay between users. The system is designed to handle game state management, user authentication, serialization, and live interactions using modern web technologies. It incorporates robust server-client communication, ensuring smooth gameplay and database persistence for critical data like user accounts and ongoing matches.

## Features

- **Multiplayer Gameplay**:
  - Players can join a server and play chess against other users in real-time.
  - Game state synchronization ensures that both players see the same board.

- **Server-Side Persistence**:
  - User accounts and game states are persisted using an SQL database.
  - Ensures that progress is not lost even if the server is restarted.

- **Game Logic**:
  - Implements all chess rules.
  - Validates moves to ensure compliance with chess regulations.

- **Interactive Terminal Interface**:
  - REPL-based menu system for users to navigate pre-login, post-login, and in-game options.

- **WebSocket Communication**:
  - Enables real-time updates and messaging between clients and the server.
  - Ensures low-latency interactions for a seamless multiplayer experience.

- **Maven Integration**:
  - Builds and manages the project efficiently with Maven.
  - Manages dependencies and ensures compatibility across different environments.

- **RESTful API**:
  - Server endpoints handle user registration, login, game creation, joining games, and listing available games.
  - Provides clear and consistent communication between the client and server.

## Technologies and Tools Used

- **Java**: Core programming language for implementing the server and game logic.
- **Maven**: Build automation tool for dependency management and project compilation.
- **SQL**: Database management system for storing user accounts and game states.
- **WebSocket**: For real-time communication between clients and the server.
- **HTTP/REST**: Used for user authentication and game management endpoints.
- **JSON**: Data format for client-server communication.
- **JUnit**: For unit testing critical components like move validation and server responses.

## Architecture

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

View the full sequence diagram for this project [here](https://sequencediagram.org/index.html?presentationMode=readOnly&shrinkToFit=true#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAFZTp+1AgBXbDAAxGjAVJYwAEoo9kiqFnJIEGhBAO4AFkhgYoiopAC0AHzklDRQAFwwANoACgDyZAAqALowAPS+BlAAOmgA3gBEHZQhALYo-WX9-QA0MP246inQHBNTs-0oI8BICKtzAL6YwqUwhazsXJQVA0NQo+OTM3MLqktQK4-rm9u7j4dsnG4sDOx1EFSg0ViWSgAAoojE4pQogBHXxqMAASlMwAQzAAqp17jAcRDgBxwvIANboTAwOnFEQqU5FWTyJQqdQVewoMAEygw273LH0mTadlqVTMow6CoAMSQnBgfKgrOAMB04UFwDGtPpqvF6lOIJKYKVnVVMAAZn5OLq6aCmXkzgDLuUYL05mN1MBuRM5gBRKA+N0klBkinAalofowQ4il1Ao1FY5XKoAFicAGYWh7+l7VD6HgGg9AKqHwzAqegY3H6SngWccuYKhnM+7PRLC37+oHg2WEKTyZXI9XY2ZbZgccwAJJoKg4pAcGAQ1HomAwlAuewuWYjWKqBWOS2oBAcVTCusmx3Oi5A64d72+io9ktuvRLldouI1u3nQGUJMGVKCpKgzJwcwfAsn2LPt1TJZcUFXb8xxFetAKbDAWycJx2zzTtoJfWD3wQpCwB-dAOGxXFyF8ZQJUtXwEAQhF4gIJJfwdQwzn1OjORgbleU6AVCW1FALzpHiOUlM5jDlBUl2VC0NRgLUdRFSSJUAziKjQRiEA4q8uJZMVeNUCoQFJLJlWEyhVXE0U2VMqVZJgBQOAU81tAMxkjIcxRTPMyyUAUXwwDSGFgFCtI7N-DTDRkmVXPc1yotVbyqFEQCE1TeEoSRNRGKweNbwAp1kxKVMbhEsZViefpIrChoICjWra3tCqGyKDCwAqAAmbD3UGaqHjWOYGrSJqWr+cdKMnajZ2hEImM6E5KGDdLMrKv9XXvPDHyLQjS3XDg1As4g2OSCBLRgNboAxH9iv-TqgNTSo3GwiC9qgg7eyOmETtUM7sAumArpu197pQy9qFKxtkGbGB3pw3N8y7Z9frdf7Tqgc7Eku67bqgSHDgombPEwbw-ACQJoHYbkYAAGQgGJkkCdJMmyeH8nKmG3RqepmjaAx1DxnoquGUTavWF43g+KZDjQm8nuuIaJZqz5nn0V5llazBsuBHmMpUCoEGZhUYSZlmUS-TEqPxEIougJAAC8UCXGEUh8BwVOG1oZeWezOKlOKzP4nlrNUsTYpMqTnMS+VFUU7R1U1Ybo8c2Otu0s1bOTtAIGYa1fEVaAYAVedkCXf33g268in13bUYIjGKmL8anddlYobpfWtI6kCMwARk+puftfVuHbCju3Ye6GTi27qsMH3DR+7FuVMntJp67kmJynGjTIYpjTZZ2vfJDrlw6EyP7JDuO5MTzz5BTn21bEdSY80rPDIqJPn6LxUFwYDVyXFsMAIA0hqDPsHT+fELJhiyCFMKEVUraFvrA6SRQXJuSXEg6KXlUKGSyiVN0lsFRRFUIVX8vdv68xVpHKWY0oqTWrNNQhvN0Jcx6jAfqyM5gMI1vVZhzVWFTDHKTOaM4MAS2Wh1cG612FGy4krHaK98Jj1gljQGONgZ41BgTCGs8e4kL7nQqoSMR7qLXuPY62NcZJH0fIu6RiXrPUXojAaKMrHoxsVooGIMwaE2JmTCRXgfD+CCN4FA6BGbMz8MwNmGQsiYG6lKesIFpD+gZv6Bo-oWitGFgeJIPRxosLQEcORKi7yDVKSI6M00aGG1NCfeJKDGp1KxJIpUm9t7rnLguJcpc9wi29rUqMgciHcQwZfMAeC2kTQ6enfymcsHx3kilMKSlwhjJpB-DOX9jQ+QqHgi0+dC42kooozaVTKqQTRjBI6bdHY407i4mhhzgLpicMPNR+1rGwSeVPF5M9u6uM4bkbhQ9fnfX+Y8npwKd5ky6WQWiUkj4wBaaFaBUz9l8QEnMnZaB0G4swdKB+uDUHP2UoSpZBppIfNNCc5OADLlz02sZElFQTrcEQVFeZZTiXLK-qsioUQRgQBoBs-B8hsU3LdCi0y1CTG0M+ZUXhOY2pgoXlwvqnjd6zX3gtGRMAVoAUJrK+uJDG7eIeZjAG-i9GBMMaC95htXoWOhfcw6dq7G6IcU64MwTFGwy6jqjxfCvpevXn4nRASDGBvIraUJFNwnUwhEuBmUIYAAHFRKSkSRzFJXC0n9yqNmnJ+T7CiRKcIqMFSOFyt2oS7s-R-jKqacbDFUJc1en5YsrpeJ4Uuzdn0ucAzQawGGQeUZtb0ATJ8jAzlYdZl8sJYKul98YAJwpZs5O1LZ3lL2UK+KDLO1Mufmcq0FyLXbWqV4v5PiAVDteS65VHzXpD0sQ+21E924IpcYrOGEKl6eubjYwFW9-3iL3tRBVaLGJdriAhCwqAK7Yo5ce0O+LV0HvXU5BK5KpVbOJAe2l+HT0oGOZStULL0N+TpTMhQCAEA9rUDCPDKyyVbvWTiJiVavRkczhRiomakNg341Aq5ddb23P6BJ1QfpKguGUy0TVrrXG7Xk4p5TLhVPpRDfAMNvDcJaZAjpvTEjDXSLuDiE1cjzVSeUZa5WoGNF-XtbGx18bnGvqeqY1VHr70wsfe531canFEwA5UoDCMkaudhT67R9j8YReCaTZNlMImBGwL4KA2BuDwCCjm0SqQkmcwhSWsx-NGiVurZvMpYs5OiQAHKS2morZzqjczNufHVCTrX1byz1u2oCpp4FyBQKxvtUZZj9dEp0-eg6-3Dvdv0yuE6YBTsPCR9p4y6MX2XQS3DgnhVce3URvd2zSNHo3Sqxl1GYCXto454hLmgtRvA8+kFam31ur5p++LIW3QQe3lFhtMXMIwChe9sDT7lsvv1XbA+8G+MlfGxdfb0zDs4d23Ok78URXccVOetU+7ceHr1Bg-z93d3-2vS9nFmHAoIMm6JGEc2xgcdO9g5KaAUApH4qJfH9KO2UZM6Jac0g-SD16pmNMoKg5bQbuLsYkvpey-lz9vzKrZMSbVxUGXcuFfRdDcBnhni5h66lwbjX0GDXUQAELwU-GuGEW3vYc6jgzxtgOf1wQ-IhG2bzfsaa+eBX33qKjERd8hTVgHTcIzAhH9e0fA-okTfbqRi1bOmtgA5tl0nlcw7c4lh1-rvORd866fz7qPrJ98R55Ljiglg-nhD7hcXi8JYqDGpvAafOI4y6moIlgUDMYgALgAUhABUxWxhBB0AgUAlIi0VffXzaoeJBatAkzW8njX8vAFH1AOAEBTZQAmOsK3EwFaVM63eph5PL-PCX8f0-5+-RX4l9btt2vRcVAACsZ80AptCVZhD838z9oBZtv8FtqIltnkVtR0K5FwNt3dHA11Mcl1sNkFMCbt8NCdzsScX4aV8ChN-8LsL0C4r1i5WV2oF0lcrV684dECEclVtd18B5vkv1gs-cQcoM48TdDMzdoc7lYc4V4dvskV95WNHtqDnsC9z4sccDwo8DKcSVN0iCHsycFk60yCDkKDiCFD6ClFF0mdl0psrcucCczt1lZDlIrdhdqdO1ZCnt6dFDXsusxCS8o9nc09Y92Dq8dd-s68u8gdfCA9SJW8DN3Ek8wi-dU8oi7ckc4N6IEMgCFR61TDGcGNsdcDjt9CbCXItDadScrtycnC7sz0HtjDRtHQMNciBJLDYDKjCC7CSsHDv9KjhM59DBajFcGiAoYAMiQC2cIDKB39oDBdVdpBrDSUecq5X8AJIBpj351DMNnCxdUj1Ab1ldtiNBHogjOCqh1V3RBDwcE9IdjNehEcukmNBwIw9CPDGC3tvDu9iQBwwwhwqxowq9ExgiuDsxmCjpyxviRxfjzi29LjIUsxgSQxPiKwfiM8kcjUbNZEOF88TDrl79ZNV5wjbEks-UUsW8-iDN0lzFQi3j8Te8iTm9nVISYiw1O8qS-caTwsSTB8JwwkqZIkoAj9DMwxYBgBsB8tCAEgHEC1klUljjKhMlslcl8ljAsjSocS3RrY1wViQBuA8AjBtA9ADBhs-86ixctTBTVR9SUB2Msj2V6MhjTS8AmMEBVQrS74CMZAx8eRDBeMEIQBlhJQbIoBVAYCvRZhCVzwUjUUJRuiKD9jdimDYzDj-iZTTibj9M3EjM9VpD5prMlo7MMTXxozjTrVv1I8CSy9iT6S4zXjI1xDS9PNy9Utg8OC-sQJAsWTSy2SvNGzjcLjhDYsLcayfCyz6yKyE1kjQkgA)

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/chess-multiplayer-server.git
   ```

2. Build the project with Maven:
   ```bash
   mvn clean install
   ```

3. Run the server:
   ```bash
   java -jar target/chess-server.jar
   ```

4. Connect clients to the server and start playing!

## Future Enhancements

- **GUI Client**:
  - A graphical interface to replace the terminal-based REPL.
- **Advanced Analytics**:
  - Provide move recommendations and game analysis for players.
- **Mobile App Support**:
  - Extend the project to support mobile devices.
