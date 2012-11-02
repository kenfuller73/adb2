default: entry.class
entry.class: src/test/entry.java
	javac -sourcepath src/test/ -classpath lib/*.jar -d bin/ src/test/*.java

# entry.java is the file which has the main() function of the project. 
# The above command compiles exec.java along with its dependedent .java files. 
# Create a directory named src and put all *.java files in it. This is specified by -sourcepath. 
# Create a directory named lib and put all your external *.jar files in it. This is specified by -classpath.
# Create an empty directory named bin where all your compiled *.class files will be created. This is specified by -d. 		
# This file should be in the directory that contains src, lib and bin directories. 
# To compile type "make" and it should compile the entire project. 

run:
	java -classpath bin/:lib/*  test.entry "$(ARG1)" "$(ARG2)" "$(ARG3)" "$(ARG4)"
		
# "rOmf0BVgre5o9F/6xOU7wYIsPkTwRSPqVMv83o3Atgo=" "$(ARG2)" "$(ARG3)"
# The above command specifies how to run SampleMain along with the input arguments. Replace them with number of required arguments.
# To run your project type "make run" and it should run the project. 
