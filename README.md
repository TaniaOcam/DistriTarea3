## Instrucciones

Podemos ejecutar el proyecto en un IDE para Java que soporte Maven (como por ejemplo Apache NetBeans, Eclipse o IntellIj IDEA) o usar los siguentes comandos teniendo maven instalado en el sistema y que sea accesible desde el path de entorno del sistema

Abrimos una terminal en el directorio que contiene el pom.xml

Para ejecutar el servidor:
```bash
mvn clean install compile exec:java -Dexec.mainClass="py.una.pol.distritarea3.Server.UDPServer"
```

Para ejecutar el cliente:
```bash
mvn clean install compile exec:java -Dexec.mainClass="py.una.pol.distritarea3.Client.UDPClient"
```

O usar los scripts para Windows y Linux en el directorio /scripts
Client.Sh si es en linux 
Client.bat si es en windows 
