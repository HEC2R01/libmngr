javac -d build source/libmngr/*.java
cd build
jar -cvf libmngr.jar libmngr
cd ..