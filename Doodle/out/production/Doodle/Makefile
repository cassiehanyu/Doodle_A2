# JAVAC = javac
# JAVACFLAGS = -Xlint
# SPEED = 25
# FPS = 25
# SRC= Breakout.java \
#     Model/Model.java \
#     Model/GameElement.java \
#     Model/BallElement.java \
#     Model/BrickElement.java \
#     Model/PaddleElement.java \
#     Model/GameState.java \
#     View/View.java
# CLS= $(SRC:.java=.class)

# all:  $(CLS)

# clean :
# 	rm -rf */*.class

# .SUFFIXES : .class .java
# .java.class :
# 	 $(JAVAC) $(JAVACFLAGS) $< $(FPS) $(SPEED)


# super simple makefile
# call it using 'make NAME=name_of_code_file_without_extension'
# (assumes a .java extension)
NAME = "Doodle"
SPEED = 25
FPS = 25

all:
	@echo "Compiling..."
	javac *.java Doodle.java model/*.java view/*.java DataHelper/*.java


run: all
	@echo "Running..."
	java $(NAME)

clean:
	rm -rf *.class
	rm -rf view/*.class
	rm -rf model/*.class
	rm -rf DataHelper/*.class