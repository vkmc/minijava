Los tests fueron corridos luego de realizar los fixes a los bugs detectados durante la defensa.
La salida de estos tests pueden verse en los logs,

- icglog - log del analisis semantico
- ceivmlog - log de la ejecucion en la ceivm

Estos tests pueden correrse nuevamente de forma automatica usando los scripts en bash propiciados, simplemente ejecutando
	'./run_tests_icg'
	'./run_tests_ceivm'
segun corresponda.

Para guardar la salida completa de estos tests, debe ejecutarse 
	'./run_tests_icg &> icglog'
	'./run_tests_ceivm &> ceivmlog'
segun corresponda.

Para guardar los errores generados al correr estos tests, debe ejecutarse
	'./run_tests_icg 2> icgfaillog'
	'./run_tests_ceivm 2> ceivmfaillog'
segun corresponda.

Para guardar la salida sin errores al correr estos tests, debe ejecutarse
	'./run_tests_icg >> icglog'
	'./run_tests_ceivm >> ceivmlog'
segun corresponda.

Los tests corridos durante la defensa se encuentran en la carpeta defensa.
