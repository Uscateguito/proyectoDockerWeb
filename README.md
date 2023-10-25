![LogoBlanco](https://github.com/Uscateguito/proyecto_web/assets/103542486/5f23ee81-4c5b-4f7a-9cb2-08130670809c)

# Music List
___

## Descripción General:

El proyecto backend de MusicList es una aplicación desarrollada en Spring Boot que sirve como componente central de una plataforma de música en línea llamada MusicList. El proyecto está diseñado para ser consumido por el frontend de la aplicación web MusicList. 

<hr>

## A quién va dirigido:

El proyecto backend de MusicList está dirigido al frontend de la aplicación MusicList. El frontend es la interfaz de usuario a través de la cual los usuarios interactúan con la plataforma de música en línea. Los usuarios de la aplicación pueden ser de dos tipos: administradores y usuarios normales.

Administradores: Los administradores tienen la capacidad de crear, editar y eliminar listas de reproducción (géneros) en la plataforma. Además, pueden realizar acciones de gestión relacionadas con las canciones.

Usuarios Normales: Los usuarios normales pueden navegar por las listas de reproducción, escuchar música y votar por sus canciones favoritas utilizando la función de "like". También pueden explorar las canciones y las listas de reproducción disponibles en la plataforma. 

<hr>

## Funcionamiento del Proyecto:

El proyecto backend de MusicList está estructurado en torno a los siguientes componentes:

Controladores (controllers): Los controladores gestionan las solicitudes HTTP entrantes y las enrutaron a las operaciones adecuadas en los servicios correspondientes. Proporcionan una interfaz para que el frontend interactúe con el backend.

Entidades (entidades): Las entidades representan los objetos fundamentales en el sistema, como Administradores, Canciones, Listas de Reproducción, Usuarios, y las relaciones entre ellos. Estas entidades se mapean a tablas en la base de datos.

Repositorios (repository): Los repositorios proporcionan métodos para acceder y manipular los datos almacenados en la base de datos. Cada entidad tiene su repositorio correspondiente que facilita la realización de operaciones CRUD (Crear, Leer, Actualizar, Eliminar) en los objetos relacionados con esa entidad.

Servicios (services): Los servicios contienen la lógica de negocio de la aplicación. Realizan operaciones como crear, leer, actualizar y eliminar registros en la base de datos, gestionar relaciones entre entidades y aplicar lógica específica del negocio.

El funcionamiento del proyecto se basa en la interacción entre estos componentes. Cuando un usuario envía una solicitud desde el frontend, el controlador correspondiente recibe la solicitud, realiza la validación y el enrutamiento adecuado y llama a los servicios correspondientes para realizar las operaciones necesarias en la base de datos.

Por ejemplo, cuando un usuario normal da "like" a una canción en una lista de reproducción, el controlador captura la solicitud, verifica la autenticación y luego llama al servicio correspondiente para gestionar la relación entre el usuario y la canción, actualizando la cantidad de "likes". Del mismo modo, un administrador puede utilizar el controlador para crear o eliminar una lista de reproducción, lo que implica la manipulación de datos en el repositorio correspondiente.

## Librerías y Frameworks Utilizados:

- mapStruct
- Spring Boot
- Lombok
___

# Authors 
[@Alejandro Uscátegui](https://github.com/Uscateguito)<br>
[@Maria Fernanda Cercado](https://github.com/MafeCercado)<br>
[@David Perez](https://github.com/davidfer1112)<br>
[@Felipe Bolivar](https://github.com/FelipeBM1)<br>
