# Ensimag 2A POO - TP 2018/19
# ============================
#
# Ce Makefile permet de compiler le test de l'ihm en ligne de commande.
# Alternative (recommandee?): utiliser un IDE (eclipse, netbeans, ...)
# Le but est ici d'illustrer les notions de "classpath", a vous de l'adapter
# a votre projet.
#
# Organisation:
#  1) Les sources (*.java) se trouvent dans le repertoire src
#     Les classes d'un package toto sont dans src/toto
#     Les classes du package par defaut sont dans src
#
#  2) Les bytecodes (*.class) se trouvent dans le repertoire bin
#     La hierarchie des sources (par package) est conservee.
#     L'archive bin/gui.jar contient les classes de l'interface graphique
#
# Compilation:
#  Options de javac:
#   -d : repertoire dans lequel sont places les .class compiles
#   -classpath : repertoire dans lequel sont cherches les .class deja compiles
#   -sourcepath : repertoire dans lequel sont cherches les .java (dependances)

JFLAGS = -g
JC = javac
JVM = java
.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
		src/donnees/Carte.java src/donnees/Case.java src/donnees/Direction.java src/donnees/DonneesSimulation.java \
		src/donnees/Incendie.java src/donnees/NatureTerrain.java src/donnees/TraitementIncendieEtat.java src/donnees/TypeChefPompier.java \
		src/evenements/Deplacement.java src/evenements/Evenement.java src/evenements/Fini.java src/evenements/ListEvenement.java \
		src/evenements/RemplissageReservoir.java src/evenements/VerserEau.java \
		src/graph/Dijkstra.java src/graph/Graph.java src/graph/Node.java src/graph/Path.java src/graph/ResultatsPathFinder.java \
		src/io/ChefPompier.java src/io/ChefPompierMaster.java src/io/ChefPompierSimple.java src/io/LecteurDonnees.java \
		src/io/Simulateur.java src/TestSimulateur.java \
		src/robots/Robot.java src/robots/RobotAChenilles.java src/robots/RobotAPattes.java src/robots/RobotDrone.java \
		src/robots/RobotARoues.java src/robots/RobotAerien.java src/robots/RobotTerrestre.java src/robots/RobotType.java

MAIN = TestSimulateur

CLASSPATH = bin/gui.jar
SOURCEPATH = src
DESTINATIONPATH = bin
DESTINATIONPATH2 = bin:bin/gui.jar

default: classes

classes: $(CLASSES:.java=.class)

%.class: %.java
	$(JC) -d $(DESTINATIONPATH)/. -classpath $(CLASSPATH) -sourcepath $(SOURCEPATH) $<

all: $(classes)

run:
	$(JVM) -classpath bin:bin/gui.jar TestSimulateur

clean:
	$(RM) *.class

