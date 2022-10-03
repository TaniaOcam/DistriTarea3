
# Sistema informático Cliente/Servidor centralizado para Sistema Departamental de Suministros
## Trabajo Grupal de Sistemas distribuidos de IIN, Facultad Politécnica de la UNA.

### Colaboradores
*Alejandra  Lezcano
*Tania Ocampos
*Liz Paola Olmedo
*Ignaci o Lezcano
*Arsenio Emiliano Ortiz

### Instrucii

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

## Base de datos 
El proyecto ServerUDP ya trae una base de datos poblada en el fichero DistriTarea3/src/main/java/py/una/pol/distritarea3/DB.java con el cual ya se puede hacer pruebas, por lo tanto no es necesario crear la base de datos y poblarla

## Poblar base de datos

public void datosPrecargados() {
        this.datos.add(new NIS(1));
        this.datos.add(new NIS(2));
        this.datos.add(new NIS(3));
    }
    
    
## Compilación y ejecución
  1. El primer paso es clonar el repositorio en un directorio local.
   
  2. Una vez clonado, debemos abrir el directorio con el IDE.
  3. Como son proyectos maven, un IDE de Java debería de reconocer los directorios distritarea3/Client y distritarea3/Server como proyectos.
   Finalmente para compilar y ejecutar se debe hacer click en el boton de play verde que dice "run project (F6)", la información de compilación y ejecución ya se encuentra en el archivo pom.xml
   
    
## Documentación de la API 
## Sistema informático Cliente/Servidor por Socket UDP.

 Repositorio: https://github.com/TaniaOcam/
 
 Puerto por defecto: 4242
 
Para la representación de datos se utiliza cadenas de texto en formato/notación JSON. 

Cada interacción entre los intervinientes contiene los siguientes

 atributos: 
{ 
“estado”: integer 
“mensaje”: String 
“tipo_operacion”: integer 
“cuerpo”: String 
}


estado: numero entero que representa si una transacción fue exitosa o no. "0" corresponde a una transacción exitosa. Si el estado es -1 ; significa que ocurrió un error. 

mensaje: cadena de texto que detalla el estado de una transacción. Palabra “ok” si no existe error. El detalle del error si existe. 

tipo_operacion: 

		1: registrar_consumo
		2: conexion_suministro
		3: desconexion_suministro
   	        4: lista_activos 

cuerpo: datos específicos según el tipo de operación.

