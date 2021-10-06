activity_group.xml tiene viewgroups innecesarios, no entiendo porque necesitan top_background y linear_group_container
En row_balances.xml pueden usar compoundDrawable en vez de tener un linear para mostrar un icono al lado de un texto (de hecho, el IDE ahora lo sugiere)
En row_events.xml no deberían tener hardcodeados strings, todos deberían estar en strings.xml
En row_events.xml, ojo con tener el alto del viewGroup padre fijo, si alguno de los hijos crece, pueden tener problemas, es preferible restringir a cada componente, para evitar que se corte la row, por ejemplo con un texto que se va a 2 lineas
En row_group_activity.xml no necesitan los 2 linear anidados al constraint layout, podrían dibujar todo sólo teniendo el constraint
Es recomendable, tener en un style toda la configuración sobre los textos de su app, de esa forma si quieren ajustar algún estilo de texto, modificando el style se aplica a todos los textos que tengan en la app
En view_profile.xml, no es necesario tener un relativeLayout como separador, es uno de los viewGroups más pesados de construir, si necesitan tener algo similar, pueden usar un frame_layout o una view pelada
Con los conceptos vistos en la clase de inyección de dependencias, pueden resolver de mejor manera la existencia del ServiceModule
Ojo con el dateFormatter, cuando cambien de idioma es un problema, además recuerdo que las utilities de java tienen resuelto ese problema, encontré algo en una búsqueda rápida
En AddChargeActivity, les recomiendo separar el setup de la vista en métodos para hacer más fácil la lectura
Es raro que la activity sea quien provee el user_id al presenter, imaginaría que eso se toma de algún repositorio de datos y que la activity no necesita conocer ese concepto, sino el presenter, para poder modelar la vista según corresponda
La activity no debería conocer como se crea un cargo, debería pasar al presenter los datos y el presenter validar y hacer los cambios visuales correspondientes mientras un repositorio crea y almacena esos datos
El método addGroups podría recibir directo stringsGroup del presenter, la vista no necesita saber como construir toda esa lógica, la vista recibe un modelo y lo presenta, no lo manipula
Las dependencias de AddChargePresenter, deberían recibirse por constructor
En el mismo presenter, alcanza con tener un solo compositeDisposable
Es un error conceptual que guarden la view en el presenter, están leakeando la activity al guardar una referencia en el presenter, porque ambos guardan una referencia strong de cada uno (dependencia circular), deberían guardar una WeakReference de la vista, para evitar esto, es importante que entiendan esta diferencia
Los flujos de addgroup y addevent quedaron bastante incompletos, no hay mucho para comentar
Nuevamente en GroupActivity les recomiendo hacer más legible el onCreate, ayuda a entender rápidamente los componentes del activity y las responsabilidades que tiene solo viendo el setup
Bien usando weak reference en este caso
No es una buena práctica hacer un notifyDatasetChanged, les recomiendo leer un poco respecto a diffutils
En GroupHistoryViewHolder están guardando estado en el VH, al ocultar el titulo cuando paga la deuda, siempre que se reuse esa vista, va a estar oculto el titulo, deberían hacerlo visible en el otro branch
Hay que revisar como quedan algunos modelos de cargos con la versión final, algunos modelos me parecen confusos 
Con la clase de networking van a poder acomodar algunos repositorios rest, creo que hay algunos puntos para corregir ahí, destaco que lo hayan resuelto antes de que lo veamos en el temario
Los métodos del show en HomeActivity podrían ser uno solo que recibe la vista a mostrar, en vez de 4 iguales, bien la separación de responsabilidades!
Probablemente en HomeActivity tengan que mover los binds a cuando el presenter se attachea, en el onCreate no hay datos que bindear..
En las custom views (balanceView), no es tan común que el presenter necesite guardar una referencia a su vista, de hecho, la custom view no debería acceder a un repositorio y tirar un apicall, la activity debería proveer los datos a la vista, y la vista dibujar lo que corresponde, no es responsabilidad de una vista saber a donde ir a buscar los datos (aunque sea en balancePresenter)
Lo anterior aplica a todas las vistas custom que definieron para HomeActivity
