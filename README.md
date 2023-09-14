# Parcial Practico AREP

Para probar etse parcial, descargue el git, ejecutelo con el comando

    mvn exec:java

Y ingrese en su navegador:
    
    localhost:36000/calculadora

En esta ubicacion se pueden probar los diferentes metodos de la clase Math ingresando el metodo en la casilla seguido de dos parentesis y el valor a ingresar:

    cos(0.57)

## Diseño
El sistema se encuentra implementado sobre un web server y la llamada reflexiva de metodos implementada en la clase LoadClasses en donde solo se invocaran lo metodos de la clase que pasen parametros de tipo Double

### Autor
Isabella Manrique
