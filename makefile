combination_test:
	javac -d bin -cp bin tests/CombinationTest.java
	java -cp bin CombinationTest data/combinations.txt

call_check_test:
	java -cp bin Server < data/call_check_for_4_ppl.txt

javac_all:
	javac -d bin -cp bin src/*.java

clean:
	rm bin/*

open_all:
	sublime src/* &