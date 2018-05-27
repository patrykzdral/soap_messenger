# Komunikator sieciowy wykorzystujący protokół SOAP

Zaprojektuj architekturę rozproszonego systemu, w którym wymiana informacji bazuje na wykorzystaniu SOAP. Architektura ta może przyjąć formę "pierścienia", "gwiazdy", "łańcucha" lub ich kombinacji. Zaimplementuj węzły takiego systemu. Implementację można oprzeć się na gniazdach TCP/IP lub na frameworku Apache Axis/Axis2. System może służyć do rozsyłania komunikatów pomiędzy węzłami (adresowanych bezpośrednio do jakiegoś węzła lub rozsyłanych do wszystkich węzłów). Węzły uruchamiane są jako osobne aplikacje, na interfejsie których wyświetlane są wiadomości przychodzące oraz redagowane są wiadomości wychodzące. Do realizacji logiki przesyłania komunikatów należy wykorzystać możliwości, jakie dostarcza protokół SOAP (a więc pola części head), zaś sama treść komunikatów powinna być przenoszona w części transportowej (a więc w części body). Do realizacji zadania (gdy wykorzystuje się gniazda TCP/IP) można użyć pakiet javax.xml.soap.*.


## Architektura

![net](https://user-images.githubusercontent.com/18563832/40589700-64c0fa24-61f2-11e8-9a82-6d882d5e6259.png)


## Przykładowa konfiguracja

```shell
start javaw -jar soap.jar --layerNumber=1 --nodeName=R --port=5060 --nodeType=R --nextLayerNodePort=5061 --nextRouterNodePort=5070
start javaw -jar soap.jar --layerNumber=1 --nodeName=C --port=5061 --nodeType=L --nextLayerNodePort=5062
start javaw -jar soap.jar --layerNumber=1 --nodeName=B --port=5062 --nodeType=L --nextLayerNodePort=5060

start javaw -jar soap.jar --layerNumber=2 --nodeName=R --port=5070 --nodeType=R --nextLayerNodePort=5071 --nextRouterNodePort=5080
start javaw -jar soap.jar --layerNumber=2 --nodeName=C --port=5071 --nodeType=L --nextLayerNodePort=5072
start javaw -jar soap.jar --layerNumber=2 --nodeName=B --port=5072 --nodeType=L --nextLayerNodePort=5070


start javaw -jar soap.jar --layerNumber=3 --nodeName=R --port=5080 --nodeType=R --nextLayerNodePort=5081 --nextRouterNodePort=5060
start javaw -jar soap.jar --layerNumber=3 --nodeName=C --port=5081 --nodeType=L --nextLayerNodePort=5082
start javaw -jar soap.jar --layerNumber=3 --nodeName=B --port=5082 --nodeType=L --nextLayerNodePort=5080




