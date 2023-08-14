all:
	javac -d classes -sourcepath src src/main/Main.java

run:
	java -cp classes main.Main

clean:
	rm -rf classes

.PHONY: all run clean
