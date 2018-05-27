# Komunikator sieciowy wykorzystujący protokół SOAP

Zaprojektuj architekturę rozproszonego systemu, w którym wymiana informacji bazuje na wykorzystaniu SOAP. Architektura ta może przyjąć formę "pierścienia", "gwiazdy", "łańcucha" lub ich kombinacji. Zaimplementuj węzły takiego systemu. Implementację można oprzeć się na gniazdach TCP/IP lub na frameworku Apache Axis/Axis2. System może służyć do rozsyłania komunikatów pomiędzy węzłami (adresowanych bezpośrednio do jakiegoś węzła lub rozsyłanych do wszystkich węzłów). Węzły uruchamiane są jako osobne aplikacje, na interfejsie których wyświetlane są wiadomości przychodzące oraz redagowane są wiadomości wychodzące. Do realizacji logiki przesyłania komunikatów należy wykorzystać możliwości, jakie dostarcza protokół SOAP (a więc pola części head), zaś sama treść komunikatów powinna być przenoszona w części transportowej (a więc w części body). Do realizacji zadania (gdy wykorzystuje się gniazda TCP/IP) można użyć pakiet javax.xml.soap.*.


##Architektura

