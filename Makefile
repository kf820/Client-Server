all: qclient qserver

qclient:
	javac Client*.java
	echo  'java ClientMain $$1 $$2' > qclient
	chmod u+x qclient

qserver:
	javac -cp "/.:./json-simple-1.1.1.jar" Server*.java
	echo 'java -cp json-simple-1.1.1.jar:. ServerMain' > qserver
	chmod u+x qserver
